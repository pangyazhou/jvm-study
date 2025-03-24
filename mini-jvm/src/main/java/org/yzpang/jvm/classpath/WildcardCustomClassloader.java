package org.yzpang.jvm.classpath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Author: yzpang
 * Desc: 通配符加载
 * 加载当前目录的.jar和.zip
 * Date: 2025/3/24 下午5:24
 **/
public class WildcardCustomClassloader extends CustomClassloader {
    List<CustomClassloader> customClassloaders;
    public WildcardCustomClassloader(String path) throws IOException {
        String baseDir = path.substring(0, path.length() - 1);
        Files.walk(Paths.get(baseDir), 1)
                .filter(path1 -> path1.toString().endsWith(".jar") || path1.toString().endsWith(".zip"))
                .forEach(path1 -> {
                    customClassloaders.add(new ZipCustomClassloader(path1.toString()));
                });
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        for (CustomClassloader customClassloader : customClassloaders) {
            try {
                return customClassloader.readClass(className);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IOException("class not found: " + className);
    }
}
