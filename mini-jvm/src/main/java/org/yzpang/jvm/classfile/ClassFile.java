package org.yzpang.jvm.classfile;

import org.yzpang.jvm.classfile.constantpool.ConstantPoolInfo;

import java.util.List;

/**
 * Author: yzpang
 * Desc: 类文件结构
 * Date: 2025/3/18 上午8:56
 **/
public class ClassFile {
    /**
     * 魔数 必须为0xCAFEBABE
     */
    private int magic;
    /**
     * 次版本
     */
    private int minorVersion;
    /**
     * 主版本 jdk7--51  jdk8--52   jdk11--55
     */
    private int majorVersion;

    /**
     * 常量池数量
     */
    private int constantPoolCount;

    /**
     * 常量池列表
     */
    private List<ConstantPoolInfo> constantPoolInfos;

    /**
     * 类访问标志
     */
    private int accessFlags;

    /**
     * 类索引
     * 有效的常量池索引值, 指向Constant_Class_info结构
     */
    private int thisClass;

    /**
     * 父类索引
     * 有效的常量池索引值, 指向Constant_Class_info结构
     */
    private int superClass;

    /**
     * 接口计数器
     */
    private int interfacesCount;

    /**
     * 接口索引数组, u2, 长度 interfacesCount
     */
    private int[] interfaces;

    /**
     * 字段表数量
     */
    private int fieldsCount;
    /**
     * 字段表
     */
    private FieldInfo[] fields;
    /**
     * 方法表数量
     */
    private int methodsCount;
    /**
     * 方法表
     */
    private MethodInfo[] methods;
    /**
     * 属性表数量
     */
    private int attributesCount;
    /**
     * 属性表
     */
    private AttributeInfo[] attributes;
}
