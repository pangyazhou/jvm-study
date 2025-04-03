package org.yzpang.jvm.classfile.util;

import org.yzpang.jvm.classfile.ConstantPoolInfo;
import org.yzpang.jvm.classfile.constantpool.*;

import java.nio.charset.StandardCharsets;

/**
 * Author: yzpang
 * Desc: 常量池工具类
 * Date: 2025/4/2 上午10:40
 **/
public class ConstantPoolUtil {
    public static String getUtf8(ConstantPoolInfo constantPool, int index) {
        ConstantUtf8Info constantUtf8Info =  (ConstantUtf8Info) constantPool.getConstantPoolInfo(index);
        if (constantUtf8Info == null) {
            return "";
        }
        return new String(constantUtf8Info.getBytes(), StandardCharsets.UTF_8);
    }
    public static int getInteger(ConstantPoolInfo constantPool, int index) {
        ConstantIntegerInfo constantIntegerInfo =  (ConstantIntegerInfo) constantPool.getConstantPoolInfo(index);
        return constantIntegerInfo.getBytes();
    }
    public static float getFloat(ConstantPoolInfo constantPool, int index) {
        ConstantFloatInfo constantFloatInfo =  (ConstantFloatInfo) constantPool.getConstantPoolInfo(index);
        return constantFloatInfo.getBytes();
    }
    public static long getLong(ConstantPoolInfo constantPool, int index) {
        ConstantLongInfo constantLongInfo =  (ConstantLongInfo) constantPool.getConstantPoolInfo(index);
        return constantLongInfo.getBytes();
    }
    public static double getDouble(ConstantPoolInfo constantPool, int index) {
        ConstantDoubleInfo constantDoubleInfo =  (ConstantDoubleInfo) constantPool.getConstantPoolInfo(index);
        return constantDoubleInfo.getBytes();
    }
}
