package org.yzpang.jvm.instructions.base;

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
    private int pc;

    public BytecodeReader(byte[] code) {
        this.code = code;
        this.pc = 0;
    }

    /**
     * 重置
     * @param code
     * @param pc
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

    public short readShort(){
        short s = this.code[this.pc];
        this.pc += 2;
        return s;
    }

    public int readInt(){
        int i = this.code[this.pc];
        this.pc += 4;
        return i;
    }

}
