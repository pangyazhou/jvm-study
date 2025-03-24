package org.yzpang.jvm.classpath;

import lombok.Data;

import java.io.IOException;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/24 下午3:56
 **/
@Data
public class CustomClassloader {
    public static final String PATH_LIST_SEPARATOR = ";";

    private CustomClasspath classpath;

    /**
     * 父类加载器
     */
    private CustomClassloader parent;

    public byte[] findClass(String className) throws IOException {
        byte[] c = findLoadedClass(className);
        // 未加载
        if (c == null) {
            try {
                // 父级类加载器加载
                if (parent != null) {
                    c = parent.findClass(className);
                } else {
                    c = findBootstrapClass(className);
                }
            }catch (IOException e){
            }
            if (c == null) {
                c = readClass(className);
            }
        }
        return c;
    }

    public byte[] readClass(String className) throws IOException {
        return new byte[0];
    }

    public static CustomClassloader getCustomClassloader(String path) {
        if (path.contains(PATH_LIST_SEPARATOR)) {
            return new CompositeCustomClassloader(path);
        }
        if (path.endsWith("*")){
            return new  WildcardCustomClassloader(path);
        }
        if (path.endsWith(".jar") || path.endsWith(".zip") ||
        path.endsWith(".JAR") || path.endsWith(".ZIP")) {
            return new ZipCustomClassloader(path);
        }
        return new DirCustomClassloader(path);
    }

    protected byte[] findBootstrapClass(String className) throws IOException {
        return readClass(className);
    }

    /**
     * 从已加载的类中取
     * @param className
     * @return
     */
    protected byte[] findLoadedClass(String className) {
        return null;
    }

}
