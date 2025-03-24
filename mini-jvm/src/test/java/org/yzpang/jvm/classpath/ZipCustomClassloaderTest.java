package org.yzpang.jvm.classpath;

import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


public class ZipCustomClassloaderTest {
    @Test
    public void testReadClass() throws IOException {
        ZipCustomClassloader classloader = new ZipCustomClassloader("D:\\dev\\java\\program\\study\\jvm-study\\mini-jvm\\target\\mini-jvm-1.0.0.jar");
        classloader.readClass("org/yzpang/jvm/JvmMain.class");
    }

    @Test
    public void testSeparator(){
        System.out.println(CustomClassloader.PATH_LIST_SEPARATOR);
    }

    @Test
    public void testWildcard() throws IOException {
        String path = "D:\\dev\\java\\program\\study\\jvm-study\\mini-jvm\\target*";
        String baseDir = path.substring(0, path.length() - 1);
        Files.walk(Paths.get(baseDir), 1)
                .filter(path1 -> path1.toString().endsWith(".jar"))

                .forEach(System.out::println);
    }

    @Test
    public void testBootstrapClassloader() throws IOException {
        String className = "java/lang/String.class";
        CustomClassloader bootClasspath = CustomClasspath.parse("", "").getBootClasspath();
        byte[] classData = bootClasspath.findClass(className);
        System.out.println(Arrays.toString(classData));
    }

}