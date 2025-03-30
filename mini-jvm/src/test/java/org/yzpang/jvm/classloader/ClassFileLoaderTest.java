package org.yzpang.jvm.classloader;

import org.junit.Test;

import java.io.IOException;

public class ClassFileLoaderTest {

    @Test
    public void loadFile() throws ClassNotFoundException, IOException {
        CustomClassLoader customClassLoader = new CustomClassFileLoader();
        String classFilePath = "org/yzpang/jvm/file/Demo";
        Clazz clazz = customClassLoader.findClass("target/classes/".concat(classFilePath).concat(".class"));
        System.out.println(clazz);
    }

}