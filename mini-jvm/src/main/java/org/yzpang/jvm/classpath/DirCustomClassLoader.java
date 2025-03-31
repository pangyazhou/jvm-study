package org.yzpang.jvm.classpath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Author: yzpang
 * Desc: 目录加载  dir/clazz.class
 * Date: 2025/3/24 下午3:58
 **/
public class DirCustomClassLoader extends CustomClassLoader {
    /**
     * 类文件所在绝对路径
     */
    private String absDir;

    public DirCustomClassLoader() {
        this.absDir = System.getProperty("user.dir");
    }

    public DirCustomClassLoader(String absDir) {
        this.absDir = Paths.get(absDir).toAbsolutePath().toString();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        // 构建Class文件的绝对路径
        String fileName = Paths.get(absDir, className).toString();
        // 读取Class文件二进制流

        try {
            return Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            throw new IOException("class not found: " + className);
        }
    }

    @Override
    public String toString() {
        return absDir;
    }
}
