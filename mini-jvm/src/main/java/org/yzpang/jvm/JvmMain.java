package org.yzpang.jvm;


import org.yzpang.jvm.classfile.ClassFile;
import org.yzpang.jvm.classfile.MethodInfo;
import org.yzpang.jvm.classloader.CustomClassFileLoader;
import org.yzpang.jvm.classloader.CustomClassLoader;
import org.yzpang.jvm.classloader.Clazz;
import org.yzpang.jvm.classpath.Command;
import org.yzpang.jvm.classpath.CustomClasspath;

import java.io.IOException;
import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 程序入口
 * Date: 2025/3/24 下午2:43
 **/
public class JvmMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Command command = Command.parse(args);
        if (command.isHelpFlag()) {
            printHelp();
        } else if (command.isVersionFlag()) {
            printVersion();
        } else {
            startJvm(command);
        }
    }

    public static void startJvm(Command command) throws IOException, ClassNotFoundException {
        CustomClasspath classpath = CustomClasspath.parse(command.getJreOption(), command.getCpOption());
        System.out.printf("启动虚拟机. classpath:%s, class:%s, args:%s\n",
                command.getCpOption(), command.getClazz(), Arrays.toString(command.getArgs()));
        String className = command.getClazz().replaceAll("\\.", "/").concat(".class");
        System.out.println(className);
        CustomClassLoader customClassLoader = new CustomClassFileLoader();
        customClassLoader.setCustomClassloader(classpath.getAppClasspath());
        Clazz clazz = customClassLoader.findClass(className);
        ClassFile classFile = clazz.getClassFile();
        MethodInfo mainMethod = classFile.getMainMethod();
        if (mainMethod != null) {
            new Interpreter().interpret(mainMethod);
        } else {
            System.out.println("类文件中没有主方法: main");
        }
    }

    public static void printHelp(){
        System.out.println("Usage: java -jar jvm.jar [options] <class> <args>");
    }

    public static void printVersion(){
        System.out.println("Java Version: " + System.getProperty("java.version"));
    }
}
