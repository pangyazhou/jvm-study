package org.yzpang.jvm;


import org.yzpang.jvm.classpath.Command;
import org.yzpang.jvm.classpath.CustomClasspath;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomClassLoader;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 程序入口
 * Date: 2025/3/24 下午2:43
 **/
public class JvmMain {
    public static void main(String[] args) throws Exception {
        Command command = Command.parse(args);
        if (command.isHelpFlag()) {
            printHelp();
        } else if (command.isVersionFlag()) {
            printVersion();
        } else {
            startJvm(command);
        }
    }

    public static void startJvm(Command command) throws Exception {
        // 类文件加载路径
        CustomClasspath classpath = CustomClasspath.parse(command.getJreOption(), command.getCpOption());
        System.out.printf("启动虚拟机. classpath:%s, className:%s, args:%s\n",
                command.getCpOption(), command.getClazz(), Arrays.toString(command.getArgs()));
        // 类加载器
        CustomClassLoader classLoader = new CustomClassLoader(classpath, command.isVerboseClassFlag());
        String className = command.getClazz().replaceAll("\\.", "/");
        CustomClass mainClass = classLoader.loadClass(className);
        CustomMethod mainMethod = mainClass.getMainMethod();
        if (mainMethod != null) {
            new Interpreter().interpret(mainMethod, command.isVerboseInstFlag());
        } else {
            System.out.println("主方法为空");
        }
    }

    public static void printHelp(){
        System.out.println("Usage: java -jar jvm.jar [options] <class> <args>");
    }

    public static void printVersion(){
        System.out.println("Java Version: " + System.getProperty("java.version"));
    }
}
