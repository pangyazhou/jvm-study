package org.yzpang.jvm.classfile;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClassReaderTest {

    @Test
    public void readInt() {
        byte[] bytes = {(byte) 0xca, (byte) 0xfe, (byte) 0xba, (byte) 0xbe};
        ClassReader cr = new ClassReader(bytes);
        int magic = cr.readShort();
        System.out.println(Integer.toHexString(magic));
    }
}