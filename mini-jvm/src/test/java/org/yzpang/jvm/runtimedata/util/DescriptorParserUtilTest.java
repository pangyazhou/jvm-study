package org.yzpang.jvm.runtimedata.util;

import org.junit.Test;
import org.yzpang.jvm.runtimedata.heap.CustomMethodDescriptor;

import static org.junit.Assert.*;

public class DescriptorParserUtilTest {

    @Test
    public void parseMethodDescriptor() {
        String descriptor = "([CILjava/lang/String;I[C[Ljava/lang/Object;III)[Ljava/lang/String;";
        CustomMethodDescriptor methodDescriptor = DescriptorParserUtil.parseMethodDescriptor(descriptor);
        System.out.println(methodDescriptor);
    }
}