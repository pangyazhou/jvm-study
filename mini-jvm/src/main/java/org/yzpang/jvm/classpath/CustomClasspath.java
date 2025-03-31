package org.yzpang.jvm.classpath;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 类加载器加载文件路径
 */
@Data
public class CustomClasspath {
    private CustomClassLoader bootClassLoader;
    private CustomClassLoader extClassLoader;
    private CustomClassLoader appClassLoader;

    public static CustomClasspath parse(String jreOption, String cpOption) throws IOException {
        CustomClasspath cp = new CustomClasspath();
        cp.parseBootAndextClassLoader(jreOption);
        cp.parseappClassLoader(cpOption);
        cp.appClassLoader.setParent(cp.extClassLoader);
        cp.extClassLoader.setParent(cp.bootClassLoader);
        cp.appClassLoader.setClasspath(cp);
        cp.bootClassLoader.setClasspath(cp);
        cp.extClassLoader.setClasspath(cp);
        return cp;
    }

    /**
     *
     * @param jreOption
     * @return
     */
    private void parseBootAndextClassLoader(String jreOption) throws IOException {
        String jreDir = getJreDir(jreOption);
        String jreLibPath = Paths.get(jreDir, "jre", "lib").toString();

        // 引导类加载器
        this.bootClassLoader = CustomClassLoader.getClassloader(jreLibPath + "*");
        String jreExtPath = Paths.get(jreDir, "jre", "lib", "ext").toString();
        // 附加类加载器
        this.extClassLoader = CustomClassLoader.getClassloader(jreExtPath + "*");
    }

    private void parseappClassLoader(String cpOption) throws IOException {
        if (StrUtil.isEmpty(cpOption)){
            cpOption = ".";
        }
        this.appClassLoader = CustomClassLoader.getClassloader(cpOption);
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
