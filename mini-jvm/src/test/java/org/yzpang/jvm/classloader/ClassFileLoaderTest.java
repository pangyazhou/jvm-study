package org.yzpang.jvm.classloader;

import org.junit.Test;
import org.yzpang.jvm.classfile.constantpool.ConstantInfoEnum;

import static org.junit.Assert.*;

public class ClassFileLoaderTest {

    @Test
    public void loadFile() throws ClassNotFoundException {
        ClassLoader classLoader = new ClassFileLoader();
        String classFilePath = "org/yzpang/jvm/file/Demo";
        Clazz clazz = classLoader.findClass("target/classes/".concat(classFilePath).concat(".class"));
    }

}