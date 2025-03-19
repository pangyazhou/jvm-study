package org.yzpang.jvm.classloader;

/**
 * Author: yzpang
 * Desc: 类加载器
 * Date: 2025/3/19 下午4:17
 **/
public abstract class ClassLoader {

    public Clazz loadClass(String name) throws ClassNotFoundException{
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


    protected Clazz findClass(String name) throws ClassNotFoundException{
        throw new ClassNotFoundException(name);
    }
}
