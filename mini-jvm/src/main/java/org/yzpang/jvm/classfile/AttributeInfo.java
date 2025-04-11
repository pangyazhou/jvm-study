package org.yzpang.jvm.classfile;

import lombok.Data;
import org.yzpang.jvm.classfile.attribute.*;
import org.yzpang.jvm.constant.AttributeNameConstants;

/**
 * Author: yzpang
 * Desc: 属性表
 * Date: 2025/3/18 上午11:30
 * **/
@Data
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

    protected ConstantPoolInfo constantPool;

    protected void readInfo(ClassReader reader) {
        info = reader.readBytes(attributeLength);
    }

    public static AttributeInfo readAttribute(ClassReader reader, ConstantPoolInfo constantPoolInfo) {
        int attributeNameIndex = reader.readUShort();
        int attributeLength = reader.readInt();
        String attributeName = constantPoolInfo.getUtf8(attributeNameIndex);
        AttributeInfo attributeInfo = newAttributeInfo(attributeName);
        attributeInfo.setConstantPool(constantPoolInfo);
        attributeInfo.setAttributeLength(attributeLength);
        attributeInfo.readInfo(reader);
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

    public static AttributeInfo newAttributeInfo(String attributeName){
        switch (attributeName){
            case AttributeNameConstants.CODE:
                /* Code属性 */
                CodeAttribute codeAttribute = new CodeAttribute();
                return codeAttribute;
            case AttributeNameConstants.LINE_NUMBER_TABLE:
                LineNumberTableAttribute lineNumberTableAttribute = new LineNumberTableAttribute();
                return lineNumberTableAttribute;
            case AttributeNameConstants.LOCAL_VARIABLE_TABLE:
                LocalVariableTableAttribute localVariableTableAttribute = new LocalVariableTableAttribute();
                return localVariableTableAttribute;
            case AttributeNameConstants.INNER_CLASSES:
                /* 内部类 */
                InnerClassesAttribute innerClassesAttribute = new InnerClassesAttribute();
                return innerClassesAttribute;
            case AttributeNameConstants.SOURCE_FILE:
                /* 源文件 */
                SourceFileAttribute sourceFileAttribute = new SourceFileAttribute();
                return sourceFileAttribute;
            case AttributeNameConstants.DEPRECATED:
                /* 弃用注解 */
                DeprecatedAttribute deprecatedAttribute = new DeprecatedAttribute();
                return deprecatedAttribute;
            case AttributeNameConstants.SYNTHETIC:
                /* 编译器生成 */
                SyntheticAttribute syntheticAttribute = new SyntheticAttribute();
                return syntheticAttribute;
            case AttributeNameConstants.CONSTANT_VALUE:
                /* 字段常量 */
                ConstantValueAttribute constantValueAttribute = new ConstantValueAttribute();
                return constantValueAttribute;
            case AttributeNameConstants.EXCEPTIONS:
                /* 异常属性 */
                ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute();
                return exceptionsAttribute;
            case AttributeNameConstants.BOOTSTRAPMETHODS:
                /* 动态调用 */
                BootstrapMethodsAttribute bootstrapMethodsAttribute = new BootstrapMethodsAttribute();
                return bootstrapMethodsAttribute;
            case AttributeNameConstants.METHOD_PARAMETERS:
                MethodParametersAttribute methodParametersAttribute = new MethodParametersAttribute();
                return methodParametersAttribute;
            case AttributeNameConstants.RUNTIME_VISIBLE_ANNOTATIONS:
                /* 可见注解 */
                RuntimeVisibleAnnotationsAttribute runtimeVisibleAnnotationsAttribute = new RuntimeVisibleAnnotationsAttribute();
                return runtimeVisibleAnnotationsAttribute;
        }
        return new AttributeInfo();
    }

}
