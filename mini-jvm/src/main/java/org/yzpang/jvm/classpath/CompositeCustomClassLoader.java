package org.yzpang.jvm.classpath;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: yzpang
 * Desc: 符合类加载器
 * Date: 2025/3/24 下午5:01
 **/
public class CompositeCustomClassLoader extends CustomClassLoader {
    private List<CustomClassLoader> customClassLoaders;
    public CompositeCustomClassLoader(String path) {
        for (String pathElement : path.split(CustomClassLoader.PATH_LIST_SEPARATOR)) {
            customClassLoaders.add(getClassloader(pathElement));
        }
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        for (CustomClassLoader customClassloader : customClassLoaders) {
            try {
                return customClassloader.readClass(className);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        throw new IOException("class not found: " + className);
    }

    @Override
    public String toString() {
        List<String> strs = new ArrayList<>();
        for (CustomClassLoader customClassloader : customClassLoaders) {
            strs.add(customClassloader.toString());
        }
        return String.join(PATH_LIST_SEPARATOR, strs);
    }
}
