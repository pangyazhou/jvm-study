package org.yzpang.jvm.classpath;

import cn.hutool.core.util.StrUtil;
import com.sun.org.apache.bcel.internal.util.ClassPath;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 类加载器加载文件路径
 */
@Data
public class CustomClasspath extends CustomEntry {
    private CustomEntry bootClasspath;
    private CustomEntry extClasspath;
    private CustomEntry appClasspath;

    public static CustomClasspath parse(String jreOption, String cpOption) throws IOException {
        CustomClasspath cp = new CustomClasspath();
        cp.parseBootAndExtClasspath(jreOption);
        cp.parseAppClasspath(cpOption);
        return cp;
    }

    /**
     *
     * @param jreOption
     * @return
     */
    private void parseBootAndExtClasspath(String jreOption) throws IOException {
        String jreDir = getJreDir(jreOption);
        String jreLibPath = Paths.get(jreDir, "jre", "lib").toString();

        // 引导类加载器
        this.bootClasspath = getEntry(jreLibPath + "*");
        String jreExtPath = Paths.get(jreDir, "jre", "lib", "ext").toString();
        // 附加类加载器
        this.extClasspath = getEntry(jreExtPath + "*");
    }

    private void parseAppClasspath(String cpOption) throws IOException {
        if (StrUtil.isEmpty(cpOption)){
            cpOption = ".";
        }
        this.appClasspath = getEntry(cpOption);
    }

    /**
     * 获取JRE运行环境
     * @param jreOption
     * @return
     * @throws IOException
     */
    private static String getJreDir(String jreOption) throws IOException {
        if (StrUtil.isNotEmpty(jreOption)){
            if (Files.exists(Paths.get(jreOption))){
                return jreOption;
            }
        }
        if (Files.exists(Paths.get("./jre"))){
            return "./jre";
        }
        if (System.getenv("JAVA_HOME") != null){
            return System.getenv("JAVA_HOME");
        }
        throw new IOException("Can not find jre folder: " + jreOption);
    }

    /**
     * 根据ClassName读取Class文件的二进制流
     * @param className Class名 java/lang/String
     * @return byte[]
     */
    @Override
    public byte[] readClass(String className) throws IOException {
        className = className + ".class";
        // 引导路径
        byte[] bytes = this.bootClasspath.readClass(className);
        if (bytes != null){
            return bytes;
        }
        // 附加路径
        bytes = this.extClasspath.readClass(className);
        if (bytes != null){
            return bytes;
        }
        // 应用路径
        return this.appClasspath.readClass(className);
    }

    @Override
    public String toString() {
        return "CustomClasspath{}";
    }
}
