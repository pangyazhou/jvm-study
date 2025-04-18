package org.yzpang.jvm.classpath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: yzpang
 * Desc: 通配符加载
 * 加载当前目录的.jar和.zip
 * Date: 2025/3/24 下午5:24
 **/
public class WildcardCustomEntry extends CustomEntry {
    List<CustomEntry> customEntries = new ArrayList<>();
    public WildcardCustomEntry(String path) {
        String baseDir = path.substring(0, path.length() - 1);
        try {
            Files.walk(Paths.get(baseDir), 1)
                    .filter(path1 -> path1.toString().endsWith(".jar") || path1.toString().endsWith(".zip"))
                    .forEach(path1 -> {
                        customEntries.add(new ZipCustomEntry(path1.toString()));
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        for (CustomEntry entry : customEntries) {
            try {
                byte[] data = entry.readClass(className);
                if (data != null) {
                    return data;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
