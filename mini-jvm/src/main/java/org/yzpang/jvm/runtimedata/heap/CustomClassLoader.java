package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classpath.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/24 下午3:56
 **/
@Data
public class CustomClassLoader {
    public static final String PATH_LIST_SEPARATOR = ";";

    private CustomClasspath classpath;

    private Map<String, CustomClass> classMap;

    /**
     * 父类加载器
     */
    private CustomClassLoader parent;

    public CustomClassLoader(CustomClasspath classpath) {
        this.classpath = classpath;
        this.classMap = new HashMap<>();
    }

    /**
     * 加载类
     * 先从方法区缓存查找, 没有再根据类名加载
     * @param className 类的完全限定名 java.lang.String
     * @return 类对象
     * @throws ClassNotFoundException e
     */
    public CustomClass loadClass(String className) throws ClassNotFoundException {
        if (classMap.containsKey(className)) {
            return classMap.get(className);
        }
        return loadNonArrayClass(className);
    }

    public CustomClass loadNonArrayClass(String className) throws ClassNotFoundException {
        return null;
    }

    public byte[] readClass(String className) {
        return new byte[0];
    }

    public CustomClass defineClass(byte[] bytes) {
        return null;
    }

}
