package org.yzpang.jvm.classfile.util;

import com.sun.org.apache.bcel.internal.util.ClassPath;
import org.junit.Test;
import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.attribute.ConstantValueAttribute;

import java.nio.ByteBuffer;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ClassFileUtilTest {

    @Test
    public void parseFieldDescriptor() {
        String descriptor = "[CII[CIII[[[Ljava/lang/String;";
        String result = ClassFileUtil.parseFieldDescriptor(descriptor);
        System.out.println(result);
    }

    @Test
    public void parseMethodDescriptor() {
        String descriptor = "([CII[CIII)[Ljava/lang/String;";
        String[] result = ClassFileUtil.parseMethodDescriptor(descriptor);
        System.out.println(result[0]);
        System.out.println(result[1]);
    }

    @Test
    public void testBeanCopy(){
        AttributeInfo attributeInfo = new AttributeInfo();
        ConstantValueAttribute constantValueAttribute = (ConstantValueAttribute) attributeInfo;
    }

    @Test
    public void testParseChar(){
        byte[] bytes = new byte[]{0, 115};
        int currentIndex = 0;
        char c = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getChar();
        System.out.println(c);
    }

    @Test
    public void testParseMethodDescriptor(){
        String descriptor = "([[IJLjava.lang.String;S)Ljava.lang.String;";
        int args = ClassFileUtil.calculateArgs(descriptor);
        System.out.println(args);
    }
}