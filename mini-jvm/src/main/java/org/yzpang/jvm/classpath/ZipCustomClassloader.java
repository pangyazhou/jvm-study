package org.yzpang.jvm.classpath;

import java.nio.file.Paths;

/**
 * Author: yzpang
 * Desc: zip/jar文件形式的类路径
 * Date: 2025/3/24 下午4:12
 **/
public class ZipCustomClassloader extends CustomClassloader {
    /**
     * 存放zip或jar文件的绝对路径
     */
    private String absDir;

    public ZipCustomClassloader() {
        this.absDir = System.getProperty("user.dir");
    }

    public ZipCustomClassloader(String absDir) {
        this.absDir = Paths.get(absDir).toAbsolutePath().toString();
    }


}
