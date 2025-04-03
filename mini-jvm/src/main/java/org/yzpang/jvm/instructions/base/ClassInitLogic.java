package org.yzpang.jvm.instructions.base;

import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

/**
 * Author: yzpang
 * Desc: 类初始化
 * Date: 2025/4/3 下午3:12
 **/
public class ClassInitLogic {

    public static void initClass(CustomThread thread, CustomClass clazz) {
        // 设置类初始化状态
        clazz.startInit();
        // 执行类的初始化方法
        scheduleClinit(thread, clazz);
        // 初始化超类
        initSuperClass(thread, clazz);
    }

    /**
     * 类初始化方法执行
     * @param thread 当前线程
     * @param clazz 要初始化的类
     */
    public static void scheduleClinit(CustomThread thread, CustomClass clazz) {
        CustomMethod clinitMethod = clazz.getClinitMethod();
        if (clinitMethod != null) {
            // 将类初始化方法帧加入到线程栈中进行执行
            CustomFrame frame = thread.newFrame(clinitMethod);
            thread.pushFrame(frame);
        }
    }

    /**
     * 初始化超类
     */
    public static void initSuperClass(CustomThread thread, CustomClass clazz) {
        if (!clazz.isInterface()) {
            CustomClass superClass = clazz.getSuperClass();
            // 超类不为空且未进行初始化
            if (superClass != null && !superClass.initStarted()) {
                initClass(thread, superClass);
            }
        }
    }
}
