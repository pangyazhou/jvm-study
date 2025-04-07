package org.yzpang.jvm.runtimedata.heap;

import org.yzpang.jvm.runtimedata.util.ClassNameHelper;

/**
 * Author: yzpang
 * Desc: 数组类
 * Date: 2025/4/7 上午8:05
 **/
public class CustomArrayClass extends CustomClass{

    public CustomObject newArray(int count) throws Exception {
        if (!isArray()) {
            throw new Exception("No array class: " + this.name);
        }
        switch (this.name) {
            case "[Z":
            case "[B":
                return newArrayObject(byte.class, count);
            case "[S":
                return newArrayObject(short.class, count);
            case "[C":
                return newArrayObject(char.class, count);
            case "[I":
                return newArrayObject(int.class, count);
            case "[J":
                return newArrayObject(long.class, count);
            case "[F":
                return newArrayObject(float.class, count);
            case "[D":
                return newArrayObject(double.class, count);
            default:
                return newArrayObject(CustomObject.class, count);
        }
    }

    private CustomArrayObject newArrayObject(Class<?> arrayType, int count) {
        return new CustomArrayObject(this, arrayType, count);
    }


    /**
     * 返回数组元素类
     * @return class
     */
    public CustomClass getComponentClass() throws Exception {
        String componentClassName = ClassNameHelper.getComponentClassName(this.name);
        return classloader.loadClass(componentClassName);
    }
}
