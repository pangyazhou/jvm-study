package org.yzpang.jvm.classfile;

import lombok.Data;
import org.yzpang.jvm.classfile.attribute.*;
import org.yzpang.jvm.classfile.constant.AttributeNameConstants;
import org.yzpang.jvm.classfile.constant.ClassAccessConstants;
import org.yzpang.jvm.classfile.constant.MethodAccessConstants;
import org.yzpang.jvm.classfile.constantpool.*;
import org.yzpang.jvm.classfile.util.ClassFileUtil;
import org.yzpang.jvm.constant.ConstantPoolConstants;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Author: yzpang
 * Desc: 类文件结构
 * Date: 2025/3/18 上午8:56
 **/
@Data
public class ClassFile {
    /**
     * u4 魔数 必须为0xCAFEBABE
     */
    private int magic;
    /**
     * u2 次版本
     */
    private int minorVersion;
    /**
     * u2 主版本 jdk7--51  jdk8--52   jdk11--55
     */
    private int majorVersion;

    /**
     * u2 常量池数量
     */
    private int constantPoolCount;

    /**
     * 常量池列表
     */
    private ConstantPoolInfo[] constantPoolInfos;

    /**
     * u2 类访问标志
     */
    private int accessFlags;

    /**
     * u2 类索引
     * 有效的常量池索引值, 指向Constant_Class_info结构
     */
    private int thisClass;

    /**
     * u2 父类索引
     * 有效的常量池索引值, 指向Constant_Class_info结构
     */
    private int superClass;

    /**
     * u2 接口计数器
     */
    private int interfacesCount;

    /**
     * 接口索引数组, u2, 长度 interfacesCount
     */
    private int[] interfaces;

    /**
     * u2 字段表数量
     */
    private int fieldsCount;
    /**
     * 字段表
     */
    private FieldInfo[] fields;
    /**
     * u2 方法表数量
     */
    private int methodsCount;
    /**
     * 方法表
     */
    private MethodInfo[] methods;
    /**
     * u2 属性表数量
     */
    private int attributesCount;
    /**
     * 属性表
     */
    private AttributeInfo[] attributes;

    public MethodInfo getMainMethod(){
        for (MethodInfo methodInfo : methods) {
            String methodName = methodInfo.getName();
            String descriptor = methodInfo.getDescriptor();
            if (methodName.equals("main") && descriptor.equals("([Ljava/lang/String;)V")) {
                return methodInfo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ClassFile:\n");
        sb.append("public class ").append(ClassFileUtil.getClassInfo(constantPoolInfos, thisClass));
        if (superClass > 0) {
            sb.append(" extends ").append(ClassFileUtil.getClassInfo(constantPoolInfos, superClass));
        }
        if (interfacesCount > 0) {
            sb.append(" implements ");
            for (int i = 0; i < interfacesCount; i++) {
                sb.append(ClassFileUtil.getClassInfo(constantPoolInfos, interfaces[i])).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\n");
        }
        sb.append("\tmagic: ").append(Integer.toHexString(magic).toUpperCase()).append("\n");
        sb.append("\tminorVersion: ").append(minorVersion).append("\n");
        sb.append("\tmajorVersion: ").append(majorVersion).append("\n");
        sb.append("\tflags: ").append(ClassFileUtil.getClassAccessFlags(accessFlags)).append("\n");
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("\n");
        sb.append("Constant pool: \n");
        for (int i = 1; i < constantPoolInfos.length; i++) {
            ConstantPoolInfo constantPoolInfo = constantPoolInfos[i];
            sb.append("\t#" + i);
            switch (constantPoolInfo.getTag()){
                case ConstantPoolConstants.UTF8:
                    ConstantUtf8Info constantUtf8Info = (ConstantUtf8Info) constantPoolInfo;
                    sb.append(" = Utf8\t\t\t" + new String(constantUtf8Info.getBytes(), StandardCharsets.UTF_8) + "\n");
                    break;
                case ConstantPoolConstants.INTEGER:
                    ConstantIntegerInfo constantIntegerInfo = (ConstantIntegerInfo) constantPoolInfo;
                    sb.append(" = Integer\t\t\t" + constantIntegerInfo.getBytes() + "\n");
                    break;
                case ConstantPoolConstants.FLOAT:
                    ConstantFloatInfo constantFloatInfo = (ConstantFloatInfo) constantPoolInfo;
                    sb.append(" = Float\t\t\t" + constantFloatInfo.getBytes() + "f\n");
                    break;
                case ConstantPoolConstants.LONG:
                    ConstantLongInfo constantLongInfo = (ConstantLongInfo) constantPoolInfo;
                    sb.append(" = Long\t\t\t" + constantLongInfo.getBytes() + "l\n");
                    i++;
                    break;
                case ConstantPoolConstants.DOUBLE:
                    ConstantDoubleInfo constantDoubleInfo = (ConstantDoubleInfo) constantPoolInfo;
                    sb.append(" = Double\t\t\t" + constantDoubleInfo.getBytes() + "d\n");
                    i++;
                    break;
                case ConstantPoolConstants.CLASS:
                    ConstantClassInfo constantClassInfo = (ConstantClassInfo) constantPoolInfo;
                    sb.append(" = Class\t\t\t#" + constantClassInfo.getNameIndex() + "\n");
                    break;
                case ConstantPoolConstants.STRING:
                    ConstantStringInfo constantStringInfo = (ConstantStringInfo) constantPoolInfo;
                    sb.append(" = String\t\t#" + constantStringInfo.getStringIndex() + "\n");
                    break;
                case ConstantPoolConstants.FIELDREF:
                    ConstantFieldRefInfo constantFieldRefInfo = (ConstantFieldRefInfo) constantPoolInfo;
                    sb.append(" = Fieldref\t\t#" + constantFieldRefInfo.getClassIndex() + ".#" + constantFieldRefInfo.getNameAndTypeIndex() + "\n");
                    break;
                case ConstantPoolConstants.METHODREF:
                    ConstantMethodRefInfo constantMethodRefInfo = (ConstantMethodRefInfo) constantPoolInfo;
                    sb.append(" = Methodref\t\t#" + constantMethodRefInfo.getClassIndex() + ".#" + constantMethodRefInfo.getNameAndTypeIndex() + "\n");
                    break;
                case ConstantPoolConstants.INTERFACEMETHODREF:
                    ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo = (ConstantInterfaceMethodRefInfo) constantPoolInfo;
                    sb.append(" = InterfaceMethodref\t#" + constantInterfaceMethodRefInfo.getClassIndex() + ".#" + constantInterfaceMethodRefInfo.getNameAndTypeIndex() + "\n");
                    break;
                case ConstantPoolConstants.NAMEANDTYPE:
                    ConstantNameAndTypeInfo constantNameAndTypeInfo = (ConstantNameAndTypeInfo) constantPoolInfo;
                    sb.append(" = NameAndType\t\t#" + constantNameAndTypeInfo.getNameIndex() + ".#" + constantNameAndTypeInfo.getDescriptorIndex() + "\n");
                    break;
                case ConstantPoolConstants.METHOD_HANDLE:
                    ConstantMethodHandleInfo constantMethodHandleInfo = (ConstantMethodHandleInfo) constantPoolInfo;
                    sb.append(" = MethodHandle\t\t#" + constantMethodHandleInfo.getReferenceKind() + ".#" + constantMethodHandleInfo.getReferenceIndex() + "\n");
                    break;
                case ConstantPoolConstants.METHOD_TYPE:
                    ConstantMethodTypeInfo constantMethodTypeInfo = (ConstantMethodTypeInfo) constantPoolInfo;
                    sb.append(" = MethodType\t\t#" + constantMethodTypeInfo.getDescriptorIndex() + "\n");
                    break;
                case ConstantPoolConstants.DYNAMIC:
                    // JDK11之后使用过
                    ConstantDynamicInfo constantDynamicInfo = (ConstantDynamicInfo) constantPoolInfo;
                    break;
                case ConstantPoolConstants.INVOKEDYNAMIC:
                    ConstantInvokeDynamicInfo constantInvokeDynamicInfo = (ConstantInvokeDynamicInfo) constantPoolInfo;
                    sb.append(" = InvokeDynamic\t#" + constantInvokeDynamicInfo.getBootstrapMethodAttrIndex() + ".#" + constantInvokeDynamicInfo.getNameAndTypeIndex() + "\n");
                    break;
            }
        }
        sb.append("Fields:\n");
        for (int i = 0; i < fieldsCount; i++) {
            FieldInfo fieldInfo = fields[i];
            sb.append("\t").append(ClassFileUtil.parseFieldAccessFlags(fieldInfo.getAccessFlags())).append(" ")
                    .append(ClassFileUtil.parseFieldDescriptor(ClassFileUtil.getUtf8Info(constantPoolInfos, fieldInfo.getDescriptorIndex()))).append(" ")
                    .append(ClassFileUtil.getUtf8Info(constantPoolInfos, fieldInfo.getNameIndex())).append(";\n");
            sb.append("\t\tdescriptor: ").append(ClassFileUtil.getUtf8Info(constantPoolInfos, fieldInfo.getDescriptorIndex())).append("\n");
            sb.append("\t\tflags: ").append(ClassFileUtil.getFieldAccessFlags(fieldInfo.getAccessFlags())).append("\n");
            sb.append("\t\tattributes: ").append("\n");
            for (int j = 0; j < fieldInfo.getAttributeCount(); j++) {
                AttributeInfo attributeInfo = fieldInfo.getAttributes()[j];
                String attributeName = ClassFileUtil.getUtf8Info(constantPoolInfos, attributeInfo.getAttributeNameIndex());
                // ConstantValue属性
                sb.append("\t\t\t").append(attributeName).append(": ");
                if (attributeInfo instanceof ConstantValueAttribute){
                    ConstantValueAttribute constantValueAttribute = (ConstantValueAttribute) attributeInfo;
                    ConstantPoolInfo constantPoolInfo = constantPoolInfos[constantValueAttribute.getConstantValueIndex()];
                    if (constantPoolInfo instanceof ConstantIntegerInfo){
                        ConstantIntegerInfo constantIntegerInfo = (ConstantIntegerInfo) constantPoolInfo;
                        sb.append("Integer ").append(constantIntegerInfo.getBytes()).append("\n");
                    } else if (constantPoolInfo instanceof ConstantLongInfo){
                        ConstantLongInfo constantLongInfo = (ConstantLongInfo) constantPoolInfo;
                        sb.append("Long ").append(constantLongInfo.getBytes()).append("L\n");
                    } else if (constantPoolInfo instanceof ConstantFloatInfo){
                        ConstantFloatInfo constantFloatInfo = (ConstantFloatInfo) constantPoolInfo;
                        sb.append("Float ").append(constantFloatInfo.getBytes()).append("f\n");
                    } else if (constantPoolInfo instanceof ConstantDoubleInfo){
                        ConstantDoubleInfo constantDoubleInfo = (ConstantDoubleInfo) constantPoolInfo;
                        sb.append("Double ").append(constantDoubleInfo.getBytes()).append("\n");
                    } else if (constantPoolInfo instanceof ConstantStringInfo){
                        ConstantStringInfo constantStringInfo = (ConstantStringInfo) constantPoolInfo;
                        sb.append("String ").append(ClassFileUtil.getUtf8Info(constantPoolInfos, constantStringInfo.getStringIndex())).append("\n");
                    }
                } else if (attributeInfo instanceof DeprecatedAttribute){
                    sb.append("True").append("\n");
                } else {
                    sb.append("\n");
                }
            }
        }
        sb.append("Methods:\n");
        for (int i = 0; i < methodsCount; i++) {
            MethodInfo methodInfo = methods[i];
            // 解析后的方法修饰符
            String parsedMethodAccessFlags = ClassFileUtil.parseMethodAccessFlags(methodInfo.accessFlags);
            // 方法描述符
            String descriptor = ClassFileUtil.getUtf8Info(constantPoolInfos, methodInfo.getDescriptorIndex());
            // 解析后的方法描述符
            String[] descriptors = ClassFileUtil.parseMethodDescriptor(descriptor);
            // 方法名
            String methodName = ClassFileUtil.getUtf8Info(constantPoolInfos, methodInfo.getNameIndex());
            if (methodName.equals("<init>")) {
                sb.append("\t")
                        .append(parsedMethodAccessFlags).append(" ")
                        .append(ClassFileUtil.getClassInfo(constantPoolInfos, thisClass))
                        .append("(").append(descriptors[0]).append(")")
                        .append(";\n");
            } else if (methodName.equals("<clinit>")) {
                sb.append("\t")
                        .append(parsedMethodAccessFlags).append(" ")
                        .append("{}")
                        .append(";\n");
            } else {
                sb.append("\t")
                        .append(parsedMethodAccessFlags).append(" ")
                        .append(descriptors[1]).append(" ")
                        .append(methodName)
                        .append("(").append(descriptors[0]).append(")")
                        .append(";\n");
            }
            sb.append("\t\tdescriptor: ").append(descriptor).append("\n");
            sb.append("\t\tflags: ").append(ClassFileUtil.getMethodAccessFlags(methodInfo.getAccessFlags())).append("\n");
            for (int j = 0; j < methodInfo.getAttributeCount(); j++) {
                AttributeInfo attributeInfo = methodInfo.getAttributes()[j];
                String attributeName = ClassFileUtil.getUtf8Info(constantPoolInfos, attributeInfo.getAttributeNameIndex());
                sb.append("\t\t").append(attributeName).append(": ");
                if (attributeInfo instanceof DeprecatedAttribute){
                    sb.append("true").append("\n");
                } else if (attributeInfo instanceof CodeAttribute) {
                    CodeAttribute codeAttribute = (CodeAttribute) attributeInfo;
                    sb.append("\n\t\t\t").append("stack=").append(codeAttribute.getMaxStack())
                            .append(", locals=").append(codeAttribute.getMaxLocals())
                            .append(", args_size=").append((ClassFileUtil.calculateArgs(descriptor)
                                    + ((methodInfo.getAccessFlags() & MethodAccessConstants.ACC_STATIC) == 0 ? 1 : 0)))
                            .append("\n");
                    for (AttributeInfo attribute : codeAttribute.getAttributes()) {
                        String attributeNameOfCode = ClassFileUtil.getUtf8Info(constantPoolInfos, attribute.getAttributeNameIndex());
                        sb.append("\t\t\t").append(attributeNameOfCode).append(": ").append("\n");
                        if (attribute instanceof LineNumberTableAttribute){
                            LineNumberTableAttribute lineNumberTableAttribute = (LineNumberTableAttribute) attribute;
                            LineNumberTable[] lineNumberTables = lineNumberTableAttribute.getLineNumberTables();
                            for (LineNumberTable lineNumberTable : lineNumberTables) {
                                sb.append("\t\t\t\t").append("line ").append(lineNumberTable.getLineNumber()).append(" ").append(lineNumberTable.getStartPC()).append("\n");
                            }
                        } else if (attribute instanceof LocalVariableTableAttribute) {
                            LocalVariableTableAttribute localVariableTableAttribute = (LocalVariableTableAttribute) attribute;
                            LocalVariableTable[] localVariableTables = localVariableTableAttribute.getLocalVariableTables();
                            sb.append("\t\t\t\t").append("Start\t").append("Length\t").append("Slot\t").append("Name\t").append("Signature").append("\n");
                            for (LocalVariableTable localVariableTable : localVariableTables) {
                                sb.append("\t\t\t\t\t").append(localVariableTable.getStartPC())
                                        .append("\t").append(localVariableTable.getLength())
                                        .append("\t").append(localVariableTable.getIndex())
                                        .append("\t").append(ClassFileUtil.getUtf8Info(constantPoolInfos, localVariableTable.getNameIndex()))
                                        .append("\t").append(ClassFileUtil.getUtf8Info(constantPoolInfos, localVariableTable.getDescriptorIndex()))
                                        .append("\n");
                            }
                        }
                    }
                } else {
                    sb.append("\n");
                }
            }
        }
        sb.append("Attributes:\n");
        for (int i = 0; i < attributesCount; i++) {
            AttributeInfo attributeInfo = attributes[i];
            String attributeName = ClassFileUtil.getUtf8Info(constantPoolInfos, attributeInfo.getAttributeNameIndex());
            sb.append("\t").append(attributeName).append(": ");
            if (attributeInfo instanceof DeprecatedAttribute){
                sb.append("True").append("\n");
            } else if (attributeInfo instanceof SourceFileAttribute) {
                SourceFileAttribute sourceFileAttribute = (SourceFileAttribute) attributeInfo;
                String sourceFileName = ClassFileUtil.getUtf8Info(constantPoolInfos, sourceFileAttribute.getSourceFileIndex());
                sb.append("\"").append(sourceFileName).append("\"").append("\n");
            } else if (attributeInfo instanceof InnerClassesAttribute) {
                InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute) attributeInfo;
                int classesCount = innerClassesAttribute.getNumberOfClasses();
                for (int j = 0; j < classesCount; j++) {
                    InnerClassTable innerClassTable = innerClassesAttribute.getInnerClasses()[j];
                    sb.append("\t\t").append(ClassFileUtil.parseClassAccessFlags(innerClassTable.getInnerClassAccessFlags())).append(" ");
                    sb.append("#").append(innerClassTable.getInnerNameIndex()).append("= ")
                            .append("#").append(innerClassTable.getInnerClassInfoIndex()).append(" of ")
                            .append("#").append(innerClassTable.getOuterClassInfoIndex()).append("; ")
                            .append("//").append(ClassFileUtil.getUtf8Info(constantPoolInfos, innerClassTable.getInnerNameIndex()))
                            .append("=class ").append(ClassFileUtil.getClassInfo(constantPoolInfos, innerClassTable.getInnerClassInfoIndex()))
                            .append(" of class ").append(ClassFileUtil.getClassInfo(constantPoolInfos, innerClassTable.getOuterClassInfoIndex()))
                            .append("\n");
                }
            } else if (attributeInfo instanceof BootstrapMethodsAttribute) {
                BootstrapMethodsAttribute bootstrapMethodsAttribute = (BootstrapMethodsAttribute) attributeInfo;
                int numBootstrapMethods = bootstrapMethodsAttribute.getNumBootstrapMethods();
                sb.append("\n");
                for (int j = 0; j < numBootstrapMethods; j++) {
                    BootstrapMethod bootstrapMethod = bootstrapMethodsAttribute.getBootstrapMethods()[j];
                    sb.append("\t\t").append(j).append(": ")
                            .append("#").append(bootstrapMethod.getBootstrap_method_ref())
                            .append(" ").append(ClassFileUtil.parseConstantPoolIndex(constantPoolInfos, bootstrapMethod.getBootstrap_method_ref()))
                            .append("\n");
                    sb.append("\t\t\t").append("Method arguments: ").append("\n");
                    int numBootstrapArguments = bootstrapMethod.getNumBootstrapArguments();
                    for (int k = 0; k < numBootstrapArguments; k++) {
                        sb.append("\t\t\t\t#")
                                .append(bootstrapMethod.getBootstrapArguments()[k])
                                .append(" ").append(ClassFileUtil.parseConstantPoolIndex(constantPoolInfos, bootstrapMethod.getBootstrapArguments()[k]))
                                .append("\n");
                    }
                }
            } else if (attributeInfo instanceof RuntimeVisibleAnnotationsAttribute) {
                RuntimeVisibleAnnotationsAttribute runtimeVisibleAnnotationsAttribute = (RuntimeVisibleAnnotationsAttribute) attributeInfo;
                int numAnnotations = runtimeVisibleAnnotationsAttribute.getNumAnnotations();
                sb.append("\n");
                for (int j = 0; j < numAnnotations; j++) {
                    Annotations annotation = runtimeVisibleAnnotationsAttribute.getAnnotations()[j];
                    sb.append("\t\t").append(j).append(": ")
                            .append("#").append(annotation.getTypeIndex())
                            .append("(");
                    int numElementValuePairs = annotation.getNumElementValuePairs();
                    for (int k = 0; k < numElementValuePairs; k++) {
                        ElementValuePairs elementValuePairs = annotation.getElementValuePairs()[k];
                        sb.append("#").append(elementValuePairs.getElementNameIndex())
                                .append("=")
                                .append(elementValuePairs.getElementValue().getTag())
                                .append("#")
                                .append(elementValuePairs.getElementValue().getConstValueIndex())
                                .append(",");
                    }
                    if (numElementValuePairs > 0) {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                    sb.append(")")
                            .append("\n");
                }
            } else {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
