package org.yzpang.jvm.classpath;

import lombok.Data;

import java.io.IOException;

/**
 * Author: yzpang
 * Desc: Class文件入口
 * Date: 2025/3/24 下午3:56
 **/
@Data
public class CustomEntry {
    public static final String PATH_LIST_SEPARATOR = ";";

    public byte[] readClass(String className) throws IOException {
        return new byte[0];
    }

    public CustomEntry getEntry(String path) {
        if (path.contains(PATH_LIST_SEPARATOR)) {
            return new CompositeCustomEntry(path);
        }
        if (path.endsWith("*")){
            return new WildcardCustomEntry(path);
        }
        if (path.endsWith(".jar") || path.endsWith(".zip") ||
        path.endsWith(".JAR") || path.endsWith(".ZIP")) {
            return new ZipCustomEntry(path);
        }
        return new DirCustomEntry(path);
    }
}
