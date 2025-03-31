package org.yzpang.jvm.classfile;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 类文件读取器
 * Date: 2025/3/31 下午4:24
 **/
public class ClassReader {
    private final byte[] data;

    private int offset;

    public ClassReader(byte[] data) {
        this.data = data;
        this.offset = 0;
    }

    public byte readByte(){
        byte b = data[offset];
        this.offset++;
        return b;
    }

    public int readUByte(){
        byte b = this.readByte();
        return b & 0xFF;
    }

    public short readShort(){
        return (short) ((this.readByte() << 8) | this.readByte());
    }

    public int readUShort(){
        short s = this.readShort();
        return s & 0xFFFF;
    }

    public int readInt(){
        return this.readByte() << 24 | this.readByte() << 16 | this.readByte() << 8 | this.readByte();
    }

    public long readLong(){
        return (long) this.readInt() << 32 | (this.readInt() & 0xFFFFFFFFL);
    }

    public byte[] readBytes(int length){
        byte[] bytes = Arrays.copyOfRange(data, offset, offset + length);
        offset += length;
        return bytes;
    }

    public short[] readShorts(){
        int count = readUShort();
        short[] shorts = new short[count];
        for (int i = 0; i < count; i++) {
            shorts[i] = this.readShort();
        }
        return shorts;
    }
}
