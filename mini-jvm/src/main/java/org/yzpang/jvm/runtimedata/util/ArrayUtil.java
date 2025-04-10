package org.yzpang.jvm.runtimedata.util;

import org.yzpang.jvm.runtimedata.heap.CustomArrayClass;
import org.yzpang.jvm.runtimedata.heap.CustomArrayObject;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomObject;

/**
 * Author: yzpang
 * Desc: 数组操作类
 * Date: 2025/4/10 上午9:01
 **/
public class ArrayUtil {


    /**
     * 校验两个数组是否适配
     * 1.确保src/dest都是数组
     * 2.校验数组类型
     *  2.1 都是引用数组,可以拷贝
     *  2.2 都是基本类型数组, 类型必须相同
     * @param src 源数组
     * @param dest 模板数组
     * @return 适配结果  boolean
     */
    public static boolean checkArrayCopy(CustomObject src, CustomObject dest) throws Exception {
        CustomClass srcClazz = src.getClazz();
        CustomClass destClazz = dest.getClazz();
        if (!srcClazz.isArray() || !destClazz.isArray()) {
            return false;
        }
        if (((CustomArrayClass) srcClazz).getComponentClass().isPrimitive()
            || ((CustomArrayClass) destClazz).getComponentClass().isPrimitive()) {
            return srcClazz == destClazz;
        }
        return true;
    }

    /**
     * 数组拷贝
     * @param srcObj 源
     * @param destObj 目标
     * @param srcPos 源起始位置
     * @param destPos 目标起始位置
     * @param length 长度
     */
    public static void arrayCopy(CustomArrayObject srcObj, CustomArrayObject destObj, int srcPos, int destPos, int length) {
        String name = srcObj.getClazz().getName();
        Object src;
        Object dest;
        switch (name) {
            case "[Z":
            case "[B":
                src = srcObj.getBytes();
                dest = destObj.getBytes();
                break;
            case "[S":
                src = srcObj.getShorts();
                dest = destObj.getShorts();
                break;
            case "[C":
                src = srcObj.getChars();
                dest = destObj.getChars();
                break;
            case "[I":
                src = srcObj.getInts();
                dest = destObj.getInts();
                break;
            case "[J":
                src = srcObj.getLongs();
                dest = destObj.getLongs();
                break;
            case "[F":
                src = srcObj.getFloats();
                dest = destObj.getFloats();
                break;
            case "[D":
                src = srcObj.getDoubles();
                dest = destObj.getDoubles();
                break;
            default:
                src = srcObj.getRefs();
                dest = destObj.getRefs();
        }
        System.arraycopy(src, srcPos, dest, destPos, length);
    }
}
