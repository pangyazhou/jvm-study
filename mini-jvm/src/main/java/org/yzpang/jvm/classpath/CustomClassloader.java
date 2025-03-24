package org.yzpang.jvm.classpath;

import java.io.IOException;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/24 下午3:56
 **/
public class CustomClassloader {
    public static final String PATH_LIST_SEPARATOR = ";";
    public byte[] readClass(String className) throws IOException {
        return new byte[0];
    }

    public CustomClassloader getCustomClassloader(String path) {
        if (path.contains(PATH_LIST_SEPARATOR)) {
            return new CompositeCustomClassloader(path);
        }
        if (path.endsWith("*")){

        }
        if (path.endsWith(".jar") || path.endsWith(".zip") ||
        path.endsWith(".JAR") || path.endsWith(".ZIP")) {
            return new ZipCustomClassloader(path);
        }
        return new DirCustomClassloader(path);
    }
}
