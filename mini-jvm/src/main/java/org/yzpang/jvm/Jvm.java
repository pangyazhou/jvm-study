package org.yzpang.jvm;

import org.yzpang.jvm.classpath.Command;
import org.yzpang.jvm.classpath.CustomClasspath;
import org.yzpang.jvm.instructions.base.ClassInitLogic;
import org.yzpang.jvm.runtimedata.heap.*;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: JVM类
 * Date: 2025/4/14 上午11:19
 **/
public class Jvm {
    // 接收命令参数
    private Command command;
    // 类加载器
    private CustomClassLoader classLoader;
    // JVM启动主线程
    private CustomThread mainThread;

    public Jvm(Command command) throws Exception {
        // 类文件加载路径
        CustomClasspath classpath = CustomClasspath.parse(command.getJreOption(), command.getCpOption());
        System.out.printf("启动虚拟机. classpath:%s, className:%s, args:%s\n",
                command.getCpOption(), command.getClazz(), Arrays.toString(command.getArgs()));
        // 类加载器
        CustomClassLoader classLoader = new CustomClassLoader(classpath, command.isVerboseClassFlag());
        this.command = command;
        this.classLoader = classLoader;
        this.mainThread = new CustomThread();
    }

    /**
     * 启动JVM
     */
    public void start() throws Exception {
        initVM();
        executeMain();
    }

    /**
     * 初始化虚拟机
     */
    private void initVM() throws Exception {
        CustomClass vmClass = this.classLoader.loadClass("sun/misc/VM");
        ClassInitLogic.initClass(this.mainThread, vmClass);
        new Interpreter().interpret(this.mainThread, this.command.isVerboseInstFlag());
    }

    /**
     * 执行主方法
     */
    private void executeMain() throws Exception {
        String className = this.command.getClazz().replaceAll("\\.", "/");
        CustomClass mainClass = this.classLoader.loadClass(className);
        CustomMethod mainMethod = mainClass.getMainMethod();
        if (mainMethod == null) {
            System.out.println("主方法为空");
            return;
        }
        CustomObject argsArray = createArgsArray();
        CustomFrame frame = this.mainThread.newFrame(mainMethod);
        frame.getLocalVariable().setReference(0, argsArray);
        this.mainThread.pushFrame(frame);
        new Interpreter().interpret(this.mainThread, this.command.isVerboseInstFlag());
    }

    /**
     * 将命令行的arg参数解析为String[]
     */
    private CustomObject createArgsArray() throws Exception {
        CustomClass stringClass = this.classLoader.loadClass("java/lang/String");
        String[] args = this.command.getArgs();
        CustomArrayObject arrayObject = stringClass.getArrayClass().newArray(args.length);
        CustomObject[] array = arrayObject.getRefs();
        for (int i = 0; i < args.length; i++) {
            array[i] = CustomStringPool.jString(this.classLoader, args[i]);
        }
        return arrayObject;
    }
}
