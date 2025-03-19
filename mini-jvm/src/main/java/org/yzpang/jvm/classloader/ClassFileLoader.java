package org.yzpang.jvm.classloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author: yzpang
 * Desc: 类文件加载
 * Date: 2025/3/18 上午8:46
 **/
public class ClassFileLoader extends ClassLoader {

    /**
     * 读取字节码文件为字节数组
     * @param fileName class文件名
     * @return 文件字节数据
     */
    public byte[] loadFile(String fileName){
        try {
            return Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Clazz findClass(String name) throws ClassNotFoundException {
        byte[] bytes = loadFile(name);
        return super.findClass(name);
    }
}
