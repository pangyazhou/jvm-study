package org.yzpang.jvm;


import org.yzpang.jvm.classpath.Command;

/**
 * Author: yzpang
 * Desc: 程序入口
 * Date: 2025/3/24 下午2:43
 **/
public class Main {
    public static void main(String[] args) throws Exception {
        Command command = Command.parse(args);
        if (command.isHelpFlag()) {
            printHelp();
        } else if (command.isVersionFlag()) {
            printVersion();
        } else {
            new Jvm(command).start();
        }
    }

    public static void printHelp(){
        System.out.println("Usage: java -jar jvm.jar [options] <class> <args>");
    }

    public static void printVersion(){
        System.out.println("Java Version: " + System.getProperty("java.version"));
    }
}
