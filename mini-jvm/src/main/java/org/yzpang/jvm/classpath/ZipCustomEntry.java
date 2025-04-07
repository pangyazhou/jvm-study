package org.yzpang.jvm.classpath;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Author: yzpang
 * Desc: zip/jar文件形式的类路径
 * Date: 2025/3/24 下午4:12
 **/
public class ZipCustomEntry extends CustomEntry {
    /**
     * 存放zip或jar文件的绝对路径
     */
    private String absDir;

    public ZipCustomEntry() {
        this.absDir = System.getProperty("user.dir");
    }

    public ZipCustomEntry(String absDir) {
        this.absDir = Paths.get(absDir).toAbsolutePath().toString();
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        // todo 每个Class文件都要打开jar,后期优化
        ZipFile zipFile = new ZipFile(this.absDir);
        ZipEntry entry = zipFile.getEntry(className);
        if (entry != null) {
            InputStream zipFileInputStream = zipFile.getInputStream(entry);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            byte[] temp = new byte[4096];
            int read = 0;
            while ((read = zipFileInputStream.read(temp)) != -1) {
                buffer.write(temp, 0, read);
            }
            return buffer.toByteArray();
        }
        return null;
    }

    @Override
    public String toString() {
        return absDir;
    }
}
