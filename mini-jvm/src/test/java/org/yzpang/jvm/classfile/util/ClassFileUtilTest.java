package org.yzpang.jvm.classfile.util;

import org.junit.Test;
import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.attribute.ConstantValueAttribute;

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
}