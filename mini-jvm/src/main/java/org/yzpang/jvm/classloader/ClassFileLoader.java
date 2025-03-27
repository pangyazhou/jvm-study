package org.yzpang.jvm.classloader;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.ClassFile;
import org.yzpang.jvm.classfile.FieldInfo;
import org.yzpang.jvm.classfile.MethodInfo;
import org.yzpang.jvm.classfile.attribute.*;
import org.yzpang.jvm.classfile.constant.AttributeNameConstants;
import org.yzpang.jvm.classfile.constantpool.*;
import org.yzpang.jvm.classfile.util.ClassFileUtil;
import org.yzpang.jvm.classpath.CustomClassloader;
import org.yzpang.jvm.classpath.DirCustomClassloader;
import org.yzpang.jvm.constant.ConstantPoolConstants;
import org.yzpang.jvm.constant.JvmConstants;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 类文件加载
 * Date: 2025/3/18 上午8:46
 **/
@Data
public class ClassFileLoader extends ClassLoader {

    /**
     * 读取字节码文件为字节数组
     * @param fileName class文件名
     * @return 文件字节数据
     */
    public byte[] loadFile(String fileName){
        try {
            CustomClassloader classloader = new DirCustomClassloader();
            return classloader.readClass(fileName);
//            return Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Clazz findClass(String name) throws ClassNotFoundException, IOException {
        byte[] bytes = this.customClassloader.findClass(name);
        if (bytes == null){
            throw new ClassNotFoundException(name);
        }
        ClassFile classFile = new ClassFile();
        try {
            int currentIndex = 0;
            // 读取魔数
            classFile.setMagic(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 4)).getInt());
            currentIndex += 4;
            // 读取主次版本
            classFile.setMinorVersion(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
            currentIndex += 2;
            classFile.setMajorVersion(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
            currentIndex += 2;
            // 常量池常量个数
            int constantPoolCount = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
            classFile.setConstantPoolCount(constantPoolCount);
            currentIndex += 2;
            // 常量池解析
            ConstantPoolInfo[] constantPoolInfos = new ConstantPoolInfo[constantPoolCount];
            int[] index = {currentIndex};
            for (int i = 1; i < constantPoolCount; i++) {
                constantPoolInfos[i] = parseConstantPool(bytes, index);
                // long和double占有两个表成员
                if (constantPoolInfos[i].getTag() == ConstantPoolConstants.LONG || constantPoolInfos[i].getTag() == ConstantPoolConstants.DOUBLE) {
                    i++;
                }
            }
            classFile.setConstantPoolInfos(constantPoolInfos);
            currentIndex = index[0];
            // 访问标志
            classFile.setAccessFlags(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
            currentIndex += 2;
            // 类索引
            classFile.setThisClass(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
            currentIndex += 2;
            // 父索引
            classFile.setSuperClass(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
            currentIndex += 2;
            // 接口索引个数
            int interfacesCount = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
            classFile.setInterfacesCount(interfacesCount);
            currentIndex += 2;
            // 接口索引列表
            if (interfacesCount > 0) {
                int[] interfaces = new int[interfacesCount];
                for (int i = 0; i < interfacesCount; i++) {
                    interfaces[i] = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
                    currentIndex += 2;
                }
                classFile.setInterfaces(interfaces);
            }
            // 字段表数量
            int fieldsCount = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
            classFile.setFieldsCount(fieldsCount);
            currentIndex += 2;
            // 字段表
            if (fieldsCount > 0) {
                index = new int[]{currentIndex};
                FieldInfo[] fields = new FieldInfo[fieldsCount];
                for (int i = 0; i < fieldsCount; i++) {
                    fields[i] = parseFieldInfo(bytes, index, classFile);
                }
                classFile.setFields(fields);
                currentIndex = index[0];
            }
            // 方法表数量
            int methodsCount = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
            classFile.setMethodsCount(methodsCount);
            currentIndex += 2;
            // 方法表
            if (methodsCount > 0) {
                index = new int[]{currentIndex};
                MethodInfo[] methods = new MethodInfo[methodsCount];
                for (int i = 0; i < methodsCount; i++) {
                    methods[i] = parseMethodInfo(bytes, index, classFile);
                }
                classFile.setMethods(methods);
                currentIndex = index[0];
            }
            // 属性表数量
            int attributesCount = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
            classFile.setAttributesCount(attributesCount);
            currentIndex += 2;
            // 属性表
            if (attributesCount > 0) {
                index = new int[]{currentIndex};
                AttributeInfo[] attributes = new AttributeInfo[attributesCount];
                for (int i = 0; i < attributesCount; i++) {
                    attributes[i] = parseAttributeInfo(bytes, index, classFile);
                }
                classFile.setAttributes(attributes);
                currentIndex = index[0];
            }

            boolean validate = validate(classFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Clazz clazz = new Clazz();
        clazz.setClassFile(classFile);
        return clazz;
    }

    /**
     * 解析常量池
     * @param bytes Class文件二进制流
     * @param index 当前解析索引位置
     * @return ConstantPoolInfo
     */
    private ConstantPoolInfo parseConstantPool(byte[] bytes, int[] index) throws Exception {
        int currentIndex = index[0];
        int tag = bytes[currentIndex];
        currentIndex++;
        switch (tag) {
            case ConstantPoolConstants.UTF8:
                ConstantUtf8Info constantUtf8Info = new ConstantUtf8Info();
                int length = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
                constantUtf8Info.setLength(length);
                currentIndex += 2;
                constantUtf8Info.setBytes(Arrays.copyOfRange(bytes, currentIndex, currentIndex + length));
                currentIndex += length;
                index[0] = currentIndex;
                return constantUtf8Info;
            case ConstantPoolConstants.INTEGER:
                ConstantIntegerInfo constantIntegerInfo = new ConstantIntegerInfo();
                constantIntegerInfo.setBytes(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 4)).getInt());
                currentIndex += 4;
                index[0] = currentIndex;
                return constantIntegerInfo;
            case ConstantPoolConstants.FLOAT:
                ConstantFloatInfo constantFloatInfo = new ConstantFloatInfo();
                constantFloatInfo.setBytes(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 4)).getFloat());
                currentIndex += 4;
                index[0] = currentIndex;
                return constantFloatInfo;
            case ConstantPoolConstants.LONG:
                ConstantLongInfo constantLongInfo = new ConstantLongInfo();
                constantLongInfo.setBytes(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 8)).getLong());
                currentIndex += 8;
                index[0] = currentIndex;
                return constantLongInfo;
            case ConstantPoolConstants.DOUBLE:
                ConstantDoubleInfo constantDoubleInfo = new ConstantDoubleInfo();
                constantDoubleInfo.setBytes(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 8)).getDouble());
                currentIndex += 8;
                index[0] = currentIndex;
                return constantDoubleInfo;
            case ConstantPoolConstants.CLASS:
                ConstantClassInfo constantClassInfo = new ConstantClassInfo();
                constantClassInfo.setNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantClassInfo;
            case ConstantPoolConstants.STRING:
                ConstantStringInfo constantStringInfo = new ConstantStringInfo();
                constantStringInfo.setStringIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantStringInfo;
            case ConstantPoolConstants.FIELDREF:
                ConstantFieldRefInfo constantFieldRefInfo = new ConstantFieldRefInfo();
                constantFieldRefInfo.setClassIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                constantFieldRefInfo.setNameAndTypeIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantFieldRefInfo;
            case ConstantPoolConstants.METHODREF:
                ConstantMethodRefInfo constantMethodRefInfo = new ConstantMethodRefInfo();
                constantMethodRefInfo.setClassIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                constantMethodRefInfo.setNameAndTypeIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantMethodRefInfo;
            case ConstantPoolConstants.INTERFACEMETHODREF:
                ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo = new ConstantInterfaceMethodRefInfo();
                constantInterfaceMethodRefInfo.setClassIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                constantInterfaceMethodRefInfo.setNameAndTypeIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantInterfaceMethodRefInfo;
            case ConstantPoolConstants.NAMEANDTYPE:
                ConstantNameAndTypeInfo constantNameAndTypeInfo = new ConstantNameAndTypeInfo();
                constantNameAndTypeInfo.setNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                constantNameAndTypeInfo.setDescriptorIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantNameAndTypeInfo;
            case ConstantPoolConstants.METHOD_HANDLE:
                ConstantMethodHandleInfo constantMethodHandleInfo = new ConstantMethodHandleInfo();
                constantMethodHandleInfo.setReferenceKind(bytes[currentIndex]);
                currentIndex += 1;
                constantMethodHandleInfo.setReferenceIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantMethodHandleInfo;
            case ConstantPoolConstants.METHOD_TYPE:
                ConstantMethodTypeInfo constantMethodTypeInfo = new ConstantMethodTypeInfo();
                constantMethodTypeInfo.setDescriptorIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantMethodTypeInfo;
            case ConstantPoolConstants.DYNAMIC:
                ConstantDynamicInfo constantDynamicInfo = new ConstantDynamicInfo();
                constantDynamicInfo.setBootstrapMethodAttrIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                constantDynamicInfo.setNameAndTypeIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantDynamicInfo;
            case ConstantPoolConstants.INVOKEDYNAMIC:
                ConstantInvokeDynamicInfo constantInvokeDynamicInfo = new ConstantInvokeDynamicInfo();
                constantInvokeDynamicInfo.setBootstrapMethodAttrIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                constantInvokeDynamicInfo.setNameAndTypeIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantInvokeDynamicInfo;
            case ConstantPoolConstants.MODULE:
                ConstantModuleInfo constantModuleInfo = new ConstantModuleInfo();
                constantModuleInfo.setNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantModuleInfo;
            case ConstantPoolConstants.PACKAGE:
                ConstantPackageInfo constantPackageInfo = new ConstantPackageInfo();
                constantPackageInfo.setNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                index[0] = currentIndex;
                return constantPackageInfo;
            default:
                throw new Exception("标志错误: " + tag);
        }
    }

    /**
     * 解析字段表
     * @param bytes Class文件二进制流
     * @param index 当前解析索引
     * @return FieldInfo
     */
    private FieldInfo parseFieldInfo(byte[] bytes, int[] index, ClassFile classFile){
        int currentIndex = index[0];
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setAccessFlags(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        fieldInfo.setNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        fieldInfo.setDescriptorIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        int attributesCount = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
        fieldInfo.setAttributeCount(attributesCount);
        currentIndex += 2;
        index[0] = currentIndex;
        if (attributesCount > 0){
            AttributeInfo[] attributeInfos = new AttributeInfo[attributesCount];
            for (int i = 0; i < attributesCount; i++){
                attributeInfos[i] = parseAttributeInfo(bytes, index, classFile);
            }
            fieldInfo.setAttributes(attributeInfos);
        }
        fieldInfo.setClassFile(classFile);
        return fieldInfo;
    }

    /**
     * 解析方法表
     * @param bytes Class文件二进制流
     * @param index 当前解析索引
     * @return  MethodInfo
     */
    private MethodInfo parseMethodInfo(byte[] bytes, int[] index, ClassFile classFile){
        int currentIndex = index[0];
        MethodInfo methodInfo = new MethodInfo();
        methodInfo.setAccessFlags(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        methodInfo.setNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        methodInfo.setDescriptorIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        int attributesCount = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
        methodInfo.setAttributeCount(attributesCount);
        currentIndex += 2;
        index[0] = currentIndex;
        if (attributesCount > 0){
            AttributeInfo[] attributeInfos = new AttributeInfo[attributesCount];
            for (int i = 0; i < attributesCount; i++){
                attributeInfos[i] = parseAttributeInfo(bytes, index, classFile);
            }
            methodInfo.setAttributes(attributeInfos);
        }
        methodInfo.setClassFile(classFile);
        return methodInfo;
    }

    /**
     * 解析属性
     * @param bytes Class文件二进制流
     * @param index 当前解析索引
     * @return AttributeInfo
     */
    private AttributeInfo parseAttributeInfo(byte[] bytes, int[] index, ClassFile classFile){
        int currentIndex = index[0];
        AttributeInfo attributeInfo = new AttributeInfo();
        // name
        attributeInfo.setAttributeNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        // length
        int length = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 4)).getInt();
        attributeInfo.setAttributeLength(length);
        currentIndex += 4;
        // info
        attributeInfo.setInfo(Arrays.copyOfRange(bytes, currentIndex, currentIndex + length));
        currentIndex += length;
        index[0] = currentIndex;
        attributeInfo.setClassFile(classFile);
        return parseSpecificAttributeInfo(attributeInfo, classFile);
    }


    /**
     * 解析通用属性到具体属性
     * @param attributeInfo 通用属性
     * @return 具体属性
     */
    private AttributeInfo parseSpecificAttributeInfo(AttributeInfo attributeInfo, ClassFile classFile){
        String attributeName = ClassFileUtil.getUtf8Info(classFile.getConstantPoolInfos(), attributeInfo.getAttributeNameIndex());
        int currentIndex = 0;
        switch (attributeName){
            case AttributeNameConstants.CODE:
                /* Code属性 */
                CodeAttribute codeAttribute = new CodeAttribute();
                BeanUtil.copyProperties(attributeInfo, codeAttribute);
                // 操作数栈最大深度
                codeAttribute.setMaxStack(ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                // 局部变量表存储空间
                codeAttribute.setMaxLocals(ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                // 字节码长度
                int codeLength = ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 4)).getInt();
                codeAttribute.setCodeLength(codeLength);
                currentIndex += 4;
                // 字节码
                codeAttribute.setCode(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + codeLength));
                currentIndex += codeLength;
                // 异常表长度
                int exceptionTableLength = ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                codeAttribute.setExceptionTableLength(exceptionTableLength);
                currentIndex += 2;
                // 异常表
                ExceptionInfo[] exceptionTable = new ExceptionInfo[exceptionTableLength];
                for (int i = 0; i < exceptionTableLength; i++){
                    ExceptionInfo exceptionInfo = new ExceptionInfo();
                    exceptionInfo.setStartPc(ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    exceptionInfo.setEndPc(ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    exceptionInfo.setHandlerPc(ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    exceptionInfo.setCatchType(ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    exceptionTable[i] = exceptionInfo;
                }
                codeAttribute.setExceptionInfo(exceptionTable);
                // 属性数量
                int attributesCount = ByteBuffer.wrap(Arrays.copyOfRange(codeAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                codeAttribute.setAttributesCount(attributesCount);
                currentIndex += 2;
                AttributeInfo[] attributeInfos = new AttributeInfo[attributesCount];
                int[] index = {currentIndex};
                for (int i = 0; i < attributesCount; i++){
                    attributeInfos[i] = parseAttributeInfo(codeAttribute.getInfo(), index, classFile);
                }
                codeAttribute.setAttributes(attributeInfos);
                return codeAttribute;
            case AttributeNameConstants.LINE_NUMBER_TABLE:
                LineNumberTableAttribute lineNumberTableAttribute = new LineNumberTableAttribute();
                BeanUtil.copyProperties(attributeInfo, lineNumberTableAttribute);
                int lineNumberTableLength = ByteBuffer.wrap(Arrays.copyOfRange(lineNumberTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                lineNumberTableAttribute.setLineNumberTableLength(lineNumberTableLength);
                currentIndex += 2;
                LineNumberTable[] lineNumberTables = new LineNumberTable[lineNumberTableLength];
                for (int i = 0; i < lineNumberTableLength; i++){
                    LineNumberTable lineNumberTable = new LineNumberTable();
                    lineNumberTable.setStartPC(ByteBuffer.wrap(Arrays.copyOfRange(lineNumberTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    lineNumberTable.setLineNumber(ByteBuffer.wrap(Arrays.copyOfRange(lineNumberTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    lineNumberTables[i] = lineNumberTable;
                }
                lineNumberTableAttribute.setLineNumberTables(lineNumberTables);
                return lineNumberTableAttribute;
            case AttributeNameConstants.LOCAL_VARIABLE_TABLE:
                LocalVariableTableAttribute localVariableTableAttribute = new LocalVariableTableAttribute();
                BeanUtil.copyProperties(attributeInfo, localVariableTableAttribute);
                int localVariableTableLength = ByteBuffer.wrap(Arrays.copyOfRange(localVariableTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                localVariableTableAttribute.setLocalVariableTableLength(localVariableTableLength);
                currentIndex += 2;
                LocalVariableTable[] localVariableTables = new LocalVariableTable[localVariableTableLength];
                for (int i = 0; i < localVariableTableLength; i++){
                    LocalVariableTable localVariableTable = new LocalVariableTable();
                    localVariableTable.setStartPC(ByteBuffer.wrap(Arrays.copyOfRange(localVariableTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    localVariableTable.setLength(ByteBuffer.wrap(Arrays.copyOfRange(localVariableTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    localVariableTable.setNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(localVariableTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    localVariableTable.setDescriptorIndex(ByteBuffer.wrap(Arrays.copyOfRange(localVariableTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    localVariableTable.setIndex(ByteBuffer.wrap(Arrays.copyOfRange(localVariableTableAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    localVariableTables[i] = localVariableTable;
                }
                localVariableTableAttribute.setLocalVariableTables(localVariableTables);
                return localVariableTableAttribute;
            case AttributeNameConstants.INNER_CLASSES:
                /* 内部类 */
                InnerClassesAttribute innerClassesAttribute = new InnerClassesAttribute();
                BeanUtil.copyProperties(attributeInfo, innerClassesAttribute);
                // 内部类数量
                int numberOfClasses = ByteBuffer.wrap(Arrays.copyOfRange(innerClassesAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                innerClassesAttribute.setNumberOfClasses(numberOfClasses);
                currentIndex += 2;
                InnerClassTable[] innerClassTables = new InnerClassTable[numberOfClasses];
                for (int i = 0; i < numberOfClasses; i++){
                    InnerClassTable innerClassTable = new InnerClassTable();
                    innerClassTable.setInnerClassInfoIndex(ByteBuffer.wrap(Arrays.copyOfRange(innerClassesAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    innerClassTable.setOuterClassInfoIndex(ByteBuffer.wrap(Arrays.copyOfRange(innerClassesAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    innerClassTable.setInnerNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(innerClassesAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    innerClassTable.setInnerClassAccessFlags(ByteBuffer.wrap(Arrays.copyOfRange(innerClassesAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    innerClassTables[i] = innerClassTable;
                }
                innerClassesAttribute.setInnerClasses(innerClassTables);
                return innerClassesAttribute;
            case AttributeNameConstants.SOURCE_FILE:
                /* 源文件 */
                SourceFileAttribute sourceFileAttribute = new SourceFileAttribute();
                BeanUtil.copyProperties(attributeInfo, sourceFileAttribute);
                sourceFileAttribute.setSourceFileIndex(ByteBuffer.wrap(sourceFileAttribute.getInfo()).getShort());
                return sourceFileAttribute;
            case AttributeNameConstants.DEPRECATED:
                /* 弃用 */
                DeprecatedAttribute deprecatedAttribute = new DeprecatedAttribute();
                BeanUtil.copyProperties(attributeInfo, deprecatedAttribute);
                return deprecatedAttribute;
            case AttributeNameConstants.SYNTHETIC:
                /* 编译器生成 */
                SyntheticAttribute syntheticAttribute = new SyntheticAttribute();
                BeanUtil.copyProperties(attributeInfo, syntheticAttribute);
                return syntheticAttribute;
            case AttributeNameConstants.CONSTANT_VALUE:
                /* 字段常量 */
                ConstantValueAttribute constantValueAttribute = new ConstantValueAttribute();
                BeanUtil.copyProperties(attributeInfo, constantValueAttribute);
                constantValueAttribute.setConstantValueIndex(ByteBuffer.wrap(constantValueAttribute.getInfo()).getShort());
                return constantValueAttribute;
            case AttributeNameConstants.EXCEPTIONS:
                /* 异常属性 */
                ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute();
                BeanUtil.copyProperties(attributeInfo, exceptionsAttribute);
                int numberOfExceptions = ByteBuffer.wrap(Arrays.copyOfRange(exceptionsAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                exceptionsAttribute.setNumberOfExceptions(numberOfExceptions);
                currentIndex += 2;
                int[] exceptionIndexTable = new int[numberOfExceptions];
                for (int i = 0; i < numberOfExceptions; i++){
                    exceptionIndexTable[i] = ByteBuffer.wrap(Arrays.copyOfRange(exceptionsAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                    currentIndex += 2;
                }
                exceptionsAttribute.setExceptionIndexTable(exceptionIndexTable);
                return exceptionsAttribute;
            case AttributeNameConstants.BOOTSTRAPMETHODS:
                /* 动态调用 */
                BootstrapMethodsAttribute bootstrapMethodsAttribute = new BootstrapMethodsAttribute();
                BeanUtil.copyProperties(attributeInfo, bootstrapMethodsAttribute);
                int numBootstrapMethods = ByteBuffer.wrap(Arrays.copyOfRange(bootstrapMethodsAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                bootstrapMethodsAttribute.setNumBootstrapMethods(numBootstrapMethods);
                currentIndex += 2;
                BootstrapMethod[] bootstrapMethods = new BootstrapMethod[numBootstrapMethods];
                for (int i = 0; i < numBootstrapMethods; i++){
                    BootstrapMethod bootstrapMethod = new BootstrapMethod();
                    bootstrapMethod.setBootstrap_method_ref(ByteBuffer.wrap(Arrays.copyOfRange(bootstrapMethodsAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    int numBootstrapArguments = ByteBuffer.wrap(Arrays.copyOfRange(bootstrapMethodsAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                    bootstrapMethod.setNumBootstrapArguments(numBootstrapArguments);
                    currentIndex += 2;
                    int[] bootstrapArguments = new int[numBootstrapArguments];
                    for (int j = 0; j < numBootstrapArguments; j++){
                        bootstrapArguments[j] = ByteBuffer.wrap(Arrays.copyOfRange(bootstrapMethodsAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                        currentIndex += 2;
                    }
                    bootstrapMethod.setBootstrapArguments(bootstrapArguments);
                    bootstrapMethods[i] = bootstrapMethod;
                }
                bootstrapMethodsAttribute.setBootstrapMethods(bootstrapMethods);
                return bootstrapMethodsAttribute;
            case AttributeNameConstants.METHOD_PARAMETERS:
                MethodParametersAttribute methodParametersAttribute = new MethodParametersAttribute();
                BeanUtil.copyProperties(attributeInfo, methodParametersAttribute);
                int parametersCount = methodParametersAttribute.getInfo()[0];
                methodParametersAttribute.setParameterCount(parametersCount);
                currentIndex += 1;
                Parameters[] parameters = new Parameters[parametersCount];
                for (int i = 0; i < parametersCount; i++){
                    Parameters parameter = new Parameters();
                    parameter.setNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(methodParametersAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    parameter.setAccessFlags(ByteBuffer.wrap(Arrays.copyOfRange(methodParametersAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort());
                    currentIndex += 2;
                    parameters[i] = parameter;
                }
                methodParametersAttribute.setParameters(parameters);
                return methodParametersAttribute;
            case AttributeNameConstants.RUNTIME_VISIBLE_ANNOTATIONS:
                /* 可见注解 */
                RuntimeVisibleAnnotationsAttribute runtimeVisibleAnnotationsAttribute = new RuntimeVisibleAnnotationsAttribute();
                BeanUtil.copyProperties(attributeInfo, runtimeVisibleAnnotationsAttribute);
                int numAnnotations = ByteBuffer.wrap(Arrays.copyOfRange(runtimeVisibleAnnotationsAttribute.getInfo(), currentIndex, currentIndex + 2)).getShort();
                runtimeVisibleAnnotationsAttribute.setNumAnnotations(numAnnotations);
                currentIndex += 2;
                index = new int[]{currentIndex};
                Annotations[] annotations = new Annotations[numAnnotations];
                for (int i = 0; i < numAnnotations; i++){
                    annotations[i] = parseAnnotations(runtimeVisibleAnnotationsAttribute.getInfo(), index);
                }
                runtimeVisibleAnnotationsAttribute.setAnnotations(annotations);
                return runtimeVisibleAnnotationsAttribute;
        }
        return attributeInfo;
    }

    /**
     * 解析注解项
     * @param bytes 二进制流
     * @param index 当前索引
     * @return 注解对象
     */
    private Annotations parseAnnotations(byte[] bytes, int[] index){
        int currentIndex = index[0];
        Annotations annotation = new Annotations();
        annotation.setTypeIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        int numElementValuePairs = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
        annotation.setNumElementValuePairs(numElementValuePairs);
        currentIndex += 2;
        ElementValuePairs[] elementValuePairs = new ElementValuePairs[numElementValuePairs];
        for (int i = 0; i < numElementValuePairs; i++){
            ElementValuePairs elementValuePair = new ElementValuePairs();
            // 注解字段名称
            elementValuePair.setElementNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
            currentIndex += 2;
            // 注解字段值
            index[0] = currentIndex;
            ElementValue elementValue = parseElementValue(bytes, index);
            elementValuePair.setElementValue(elementValue);
            currentIndex = index[0];
            elementValuePairs[i] = elementValuePair;
        }
        annotation.setElementValuePairs(elementValuePairs);
        index[0] = currentIndex;
        return annotation;
    }

    private ElementValue parseElementValue(byte[] bytes, int[] index){
        int currentIndex = index[0];
        ElementValue elementValue = new ElementValue();
        char tag = (char)bytes[currentIndex];
        elementValue.setTag(tag);
        currentIndex += 1;
        switch (tag){
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
            case 's':
                elementValue.setConstValueIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                break;
            case 'c':
                elementValue.setClassInfoIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                break;
            case 'e':
                EnumConstValue enumConstValue = new EnumConstValue();
                enumConstValue.setTypeNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                enumConstValue.setConstNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                elementValue.setEnumConstValue(enumConstValue);
                break;
            case '@':
                // todo
                break;
            case '[':
                // todo
                break;
        }
        index[0] = currentIndex;
        return elementValue;
    }

    private boolean validate(ClassFile classFile) throws Exception {
        if (classFile.getMagic() != JvmConstants.MAGIC_NUMBER){
            throw new Exception("魔数不匹配,此文件非Class文件格式");
        }
        if (classFile.getMajorVersion() > JvmConstants.MAJOR_VERSION){
            throw new Exception("JDK版本" + JvmConstants.MAJOR_VERSION + "不支持此版本:" + classFile.getMajorVersion());
        }
        return true;
    }
}
