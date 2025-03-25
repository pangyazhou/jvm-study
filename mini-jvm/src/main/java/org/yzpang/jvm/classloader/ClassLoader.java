package org.yzpang.jvm.classloader;

import lombok.Data;
import org.yzpang.jvm.classpath.CustomClassloader;

import java.io.IOException;

/**
 * Author: yzpang
 * Desc: 类加载器
 * Date: 2025/3/19 下午4:17
 **/
@Data
public abstract class ClassLoader {

    protected CustomClassloader customClassloader;

    public Clazz loadClass(String name) throws ClassNotFoundException, IOException {
        Clazz clazz = null;
        clazz = findClass(name);
        return clazz;
    }

    /**
     * 验证类的完全限定名是否是合法的
     * @param name 类名
     * @return boolean
     */
    private boolean checkName(String name) {
        if (name == null || name.isEmpty()){
            return false;
        }
        return true;
    }


    public Clazz findClass(String name) throws ClassNotFoundException, IOException {
        throw new ClassNotFoundException(name);
    }
}
