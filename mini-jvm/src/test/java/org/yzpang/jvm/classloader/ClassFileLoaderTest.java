package org.yzpang.jvm.classloader;

import org.junit.Test;

import java.io.IOException;

public class ClassFileLoaderTest {

    @Test
    public void loadFile() throws ClassNotFoundException, IOException {
        ClassLoader classLoader = new ClassFileLoader();
        String classFilePath = "org/yzpang/jvm/file/Demo";
        Clazz clazz = classLoader.findClass("target/classes/".concat(classFilePath).concat(".class"));
        System.out.println(clazz);
    }

}