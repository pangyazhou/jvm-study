package org.yzpang.jvm.classpath;

import lombok.Data;
import org.yzpang.jvm.runtimedata.heap.CustomClass;

import java.io.IOException;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/24 下午3:56
 **/
@Data
public class CustomClassLoader {
    public static final String PATH_LIST_SEPARATOR = ";";

    private CustomClasspath classpath;
    /**
     * 父类加载器
     */
    private CustomClassLoader parent;

    public CustomClass loadNonArrayClass(String className) throws ClassNotFoundException {
        return null;
    }

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

    public static CustomClassLoader getClassloader(String path) {
        if (path.contains(PATH_LIST_SEPARATOR)) {
            return new CompositeCustomClassLoader(path);
        }
        if (path.endsWith("*")){
            return new WildcardCustomClassLoader(path);
        }
        if (path.endsWith(".jar") || path.endsWith(".zip") ||
        path.endsWith(".JAR") || path.endsWith(".ZIP")) {
            return new ZipCustomClassLoader(path);
        }
        return new DirCustomClassLoader(path);
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
