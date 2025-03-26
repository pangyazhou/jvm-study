package org.yzpang.jvm.instructions.base;

import lombok.Getter;

/**
 * Author: yzpang
 * Desc: 字节码操作
 * Date: 2025/3/25 下午2:35
 **/
public class BytecodeReader {
    /**
     * 字节码数组
     */
    private byte[] code;
    /**
     * PC寄存器
     */
    @Getter
    private int pc;

    public BytecodeReader(byte[] code) {
        this.code = code;
        this.pc = 0;
    }

    /**
     * 重置
     */
    public void reset(byte[] code, int pc) {
        this.code = code;
        this.pc = pc;
    }

    public byte readByte(){
        byte b = this.code[this.pc];
        this.pc++;
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


    /**
     * 读取int数组
     * @param count 数组长度
     * @return int[]
     */
    public int[] readInts(int count){
        int[] ints = new int[count];
        for (int i = 0; i < count; i++) {
            ints[i] = this.readInt();
        }
        return ints;
    }

    public void skipPadding(){
        while (this.pc % 4 != 0) {
            readByte();
        }
    }

}
