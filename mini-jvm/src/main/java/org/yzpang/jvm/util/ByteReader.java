package org.yzpang.jvm.util;

import java.nio.ByteBuffer;

/**
 * Author: yzpang
 * Desc: 字节读取器
 * Date: 2025/3/25 下午2:14
 **/
public class ByteReader {
    private final ByteBuffer buffer;
    public ByteReader(byte[] bytes) {
        buffer = ByteBuffer.wrap(bytes);
    }

    public byte readByte() {
        return buffer.get();
    }

    public short readShort() {
        return buffer.getShort();
    }

    public int readInt() {
        return buffer.getInt();
    }

    public long readLong() {
        return buffer.getLong();
    }

    public float readFloat() {
        return buffer.getFloat();
    }

    public double readDouble() {
        return buffer.getDouble();
    }

    public byte[] readBytes(int length) {
        byte[] bytes = new byte[length];
        return buffer.get(bytes).array();
    }
}
