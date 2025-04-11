package org.yzpang.jvm.runtimedata.util;

import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.CustomStackTraceElement;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 堆栈跟踪工具类
 * Date: 2025/4/11 上午10:55
 **/
public class StackTraceUtil {

    /**
     * 创建堆栈跟踪表
     * @param object 方法调用对象
     * @param thread 当前线程
     * @return stackTrace[]
     */
    public static CustomStackTraceElement[] createStackTraceElements(CustomObject object, CustomThread thread) {
        int skip = distanceToObject(object.getClazz()) + 2;
        CustomFrame[] frames = Arrays.copyOfRange(thread.getFrames(), skip, thread.getFrames().length);
        CustomStackTraceElement[] stackTrace = new CustomStackTraceElement[frames.length];
        for (int i = 0; i < frames.length; i++) {
            stackTrace[i] = createStackTraceElement(frames[i]);
        }
        return stackTrace;
    }

    /**
     * 计算异常类的继承层次
     * @param clazz 异常类
     * @return 层级数
     */
    public static int distanceToObject(CustomClass clazz) {
        int distance = 0;
        for (CustomClass c = clazz.getSuperClass(); c != null; c = c.getSuperClass()) {
            distance++;
        }
        return distance;
    }

    public static CustomStackTraceElement createStackTraceElement(CustomFrame frame) {
        CustomMethod method = frame.getMethod();
        CustomClass clazz = method.getClazz();
        return new CustomStackTraceElement(clazz.getSourceFile(), clazz.getJavaName(), method.getName(), method.getLineNumber(frame.getNextPC() - 1));
    }
}
