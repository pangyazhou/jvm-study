package org.yzpang.jvm.classpath;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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
            System.out.println("entry: " + entry.getName());
            InputStream zipFileInputStream = zipFile.getInputStream(entry);
            byte[] data = new byte[zipFileInputStream.available()];
            zipFileInputStream.read(data);
            return data;
        }
        return null;
    }

    @Override
    public String toString() {
        return absDir;
    }
}
