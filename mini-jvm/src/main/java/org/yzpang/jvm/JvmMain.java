package org.yzpang.jvm;


import org.yzpang.jvm.classloader.ClassFileLoader;
import org.yzpang.jvm.classloader.ClassLoader;
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
        ClassLoader classLoader = new ClassFileLoader();
        classLoader.setCustomClassloader(classpath.getAppClasspath());
        Clazz clazz = classLoader.findClass(className);
        System.out.println(clazz);

    }

    public static void printHelp(){
        System.out.println("Usage: java -jar jvm.jar [options] <class> <args>");
    }

    public static void printVersion(){
        System.out.println("Java Version: " + System.getProperty("java.version"));
    }
}
