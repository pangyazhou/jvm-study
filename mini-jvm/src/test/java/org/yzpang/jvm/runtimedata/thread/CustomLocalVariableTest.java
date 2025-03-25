package org.yzpang.jvm.runtimedata.thread;

import org.junit.Test;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

public class CustomLocalVariableTest {
    @Test
    public void test() {
        CustomLocalVariable localVariable = new CustomLocalVariable(20);
        localVariable.setInt(0, 100);
        localVariable.setLong(1, 9999888999988899L);
        localVariable.setFloat(3, 100.23F);
        localVariable.setDouble(4, 1123456789.12345);
        localVariable.setReference(6, "hello,world");

        System.out.println(localVariable.getInt(0));
        System.out.println(localVariable.getLong(1));
        System.out.println(localVariable.getFloat(3));
        System.out.println(localVariable.getDouble(4));
        System.out.println(localVariable.getReference(6));
    }

    @Test
    public void testOperandStack(){
        CustomOperandStack operandStack = new CustomOperandStack(20);
        operandStack.pushInt(100);
        operandStack.pushLong(9999888999988899L);
        operandStack.pushFloat(3.23F);
        operandStack.pushDouble(456.456);
        operandStack.pushReference("hello,world");

        System.out.println(operandStack.popReference());
        System.out.println(operandStack.popDouble());
        System.out.println(operandStack.popFloat());
        System.out.println(operandStack.popLong());
        System.out.println(operandStack.popInt());
    }

    @Test
    public void testLong(){
        long longValue = 9999888999988899L;
        double doubleValue = 1123456789.12345;
        System.out.println(longValue);
        System.out.println(new BigDecimal(String.valueOf(doubleValue)).toPlainString());
        int low = (int) longValue;
        int high = (int)(longValue>>>32);
        System.out.println(low);
        System.out.println(high);
        long highValue = (long) high << 32;
        System.out.println(highValue);
        long lowValue = low & 0xFFFFFFFFL;
        System.out.println(lowValue);
        System.out.println(highValue | lowValue);
    }

    @Test
    public void testByteBuffer(){
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        System.out.println(buffer.position());
        System.out.println(buffer.get());
        System.out.println(buffer.position());
        System.out.println(buffer.getShort());
        System.out.println(buffer.position());
        System.out.println(buffer.getInt());
        System.out.println(buffer.position());
    }
}