package org.yzpang.jvm.classfile;

/**
 * Author: yzpang
 * Desc: 属性表
 * Date: 2025/3/18 上午11:30
 **/
public class AttributeInfo {
    /**
     * 属性名 u2 常量池有效索引 指向Constant_Utf8_info结构
     */
    protected int attributeNameIndex;
    /**
     * 属性值长度 u4
     */
    protected int attributeLength;

    protected byte[] info;

    protected void readInfo(ClassReader reader) {}

    public static AttributeInfo readAttribute(ClassReader reader, ConstantPoolInfo constantPoolInfo) {
        AttributeInfo attributeInfo = new AttributeInfo();
        return attributeInfo;
    }

    public static AttributeInfo[] readAttributes(ClassReader reader, ConstantPoolInfo constantPoolInfo) {
        int attributeCount = reader.readUShort();
        AttributeInfo[] attributeInfos = new AttributeInfo[attributeCount];
        for (int i = 0; i < attributeCount; i++) {
            attributeInfos[i] = AttributeInfo.readAttribute(reader, constantPoolInfo);
        }
        return attributeInfos;
    }
}
