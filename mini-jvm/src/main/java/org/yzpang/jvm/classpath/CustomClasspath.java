package org.yzpang.jvm.classpath;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 类加载器加载文件路径
 */
@Data
public class CustomClasspath {
    private CustomClassloader bootClasspath;
    private CustomClassloader extClasspath;
    private CustomClassloader appClasspath;

    public static CustomClasspath parse(String jreOption, String cpOption) throws IOException {
        CustomClasspath cp = new CustomClasspath();
        cp.parseBootAndExtClasspath(jreOption);
        cp.parseAppClasspath(cpOption);
        cp.appClasspath.setParent(cp.extClasspath);
        cp.extClasspath.setParent(cp.bootClasspath);
        cp.appClasspath.setClasspath(cp);
        cp.bootClasspath.setClasspath(cp);
        cp.extClasspath.setClasspath(cp);
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
        this.bootClasspath = CustomClassloader.getCustomClassloader(jreLibPath + "*");
        String jreExtPath = Paths.get(jreDir, "jre", "lib", "ext").toString();
        // 附加类加载器
        this.extClasspath = CustomClassloader.getCustomClassloader(jreExtPath + "*");
    }

    private void parseAppClasspath(String cpOption) throws IOException {
        if (StrUtil.isEmpty(cpOption)){
            cpOption = ".";
        }
        this.appClasspath = CustomClassloader.getCustomClassloader(cpOption);
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
}
