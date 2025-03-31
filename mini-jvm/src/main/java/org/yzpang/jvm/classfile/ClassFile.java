package org.yzpang.jvm.classfile;

import lombok.Data;
import org.yzpang.jvm.classfile.attribute.*;
import org.yzpang.jvm.classfile.constant.MethodAccessConstants;
import org.yzpang.jvm.classfile.constantpool.*;
import org.yzpang.jvm.classfile.util.ClassFileUtil;
import org.yzpang.jvm.constant.ConstantPoolConstants;
import org.yzpang.jvm.constant.JvmConstants;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
    private ConstantPoolInfo constantPool;

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

    /**
     * 解析Class文件
     * @param bytes Class二进制流
     * @return ClassFile对象
     */
    public ClassFile parse(byte[] bytes) throws Exception {
        ClassReader reader = new ClassReader(bytes);
        ClassFile classFile = new ClassFile();
        classFile.read(reader);

        return classFile;
    }

    public void read(ClassReader reader) throws Exception {
        // 读取魔数
        this.magic = reader.readInt();
        if (!checkMagic()){
            throw new Exception("文件格式错误, 不是Class文件.");
        }
        // 读取主次版本
        this.minorVersion = reader.readUShort();
        this.majorVersion = reader.readUShort();
        if (!checkVersion()){
            throw new Exception("JVM版本与Class文件不适配, JVM版本: " + JvmConstants.MAJOR_VERSION + ", Class文件版本: " + this.getMajorVersion());
        }
        // 读取常量池
        this.constantPool = new ConstantPoolInfo().readConstantPoolInfo(reader);
        // 访问标志
        this.accessFlags = reader.readUShort();
        // 类索引
        this.thisClass = reader.readUShort();
        // 父索引
        this.superClass = reader.readUShort();
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

    }

    private boolean checkMagic() {
        return this.magic == JvmConstants.MAGIC_NUMBER;
    }

    private boolean checkVersion() {
        return this.majorVersion <= JvmConstants.MAJOR_VERSION;
    }

    public String getClassName(){
        return ClassFileUtil.getClassInfo(constantInfos, thisClass);
    }

    public String getSuperClassName(){
        return ClassFileUtil.getClassInfo(constantInfos, superClass);
    }

    public String[] getInterfacesNames(){
        String[] names = new String[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            names[i] = ClassFileUtil.getClassInfo(constantInfos, interfaces[i]);
        }
        return names;
    }

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
        sb.append("public class ").append(ClassFileUtil.getClassInfo(constantInfos, thisClass));
        if (superClass > 0) {
            sb.append(" extends ").append(ClassFileUtil.getClassInfo(constantInfos, superClass));
        }
        if (interfacesCount > 0) {
            sb.append(" implements ");
            for (int i = 0; i < interfacesCount; i++) {
                sb.append(ClassFileUtil.getClassInfo(constantInfos, interfaces[i])).append(",");
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
        for (int i = 1; i < constantInfos.length; i++) {
            ConstantInfo constantInfo = constantInfos[i];
            sb.append("\t#" + i);
            switch (constantInfo.getTag()){
                case ConstantPoolConstants.UTF8:
                    ConstantUtf8Info constantUtf8Info = (ConstantUtf8Info) constantInfo;
                    sb.append(" = Utf8\t\t\t" + new String(constantUtf8Info.getBytes(), StandardCharsets.UTF_8) + "\n");
                    break;
                case ConstantPoolConstants.INTEGER:
                    ConstantIntegerInfo constantIntegerInfo = (ConstantIntegerInfo) constantInfo;
                    sb.append(" = Integer\t\t\t" + constantIntegerInfo.getBytes() + "\n");
                    break;
                case ConstantPoolConstants.FLOAT:
                    ConstantFloatInfo constantFloatInfo = (ConstantFloatInfo) constantInfo;
                    sb.append(" = Float\t\t\t" + constantFloatInfo.getBytes() + "f\n");
                    break;
                case ConstantPoolConstants.LONG:
                    ConstantLongInfo constantLongInfo = (ConstantLongInfo) constantInfo;
                    sb.append(" = Long\t\t\t" + constantLongInfo.getBytes() + "l\n");
                    i++;
                    break;
                case ConstantPoolConstants.DOUBLE:
                    ConstantDoubleInfo constantDoubleInfo = (ConstantDoubleInfo) constantInfo;
                    sb.append(" = Double\t\t\t" + constantDoubleInfo.getBytes() + "d\n");
                    i++;
                    break;
                case ConstantPoolConstants.CLASS:
                    ConstantClassInfo constantClassInfo = (ConstantClassInfo) constantInfo;
                    sb.append(" = Class\t\t\t#" + constantClassInfo.getNameIndex() + "\n");
                    break;
                case ConstantPoolConstants.STRING:
                    ConstantStringInfo constantStringInfo = (ConstantStringInfo) constantInfo;
                    sb.append(" = String\t\t#" + constantStringInfo.getStringIndex() + "\n");
                    break;
                case ConstantPoolConstants.FIELDREF:
                    ConstantFieldRefInfo constantFieldRefInfo = (ConstantFieldRefInfo) constantInfo;
                    sb.append(" = Fieldref\t\t#" + constantFieldRefInfo.getClassIndex() + ".#" + constantFieldRefInfo.getNameAndTypeIndex() + "\n");
                    break;
                case ConstantPoolConstants.METHODREF:
                    ConstantMethodRefInfo constantMethodRefInfo = (ConstantMethodRefInfo) constantInfo;
                    sb.append(" = Methodref\t\t#" + constantMethodRefInfo.getClassIndex() + ".#" + constantMethodRefInfo.getNameAndTypeIndex() + "\n");
                    break;
                case ConstantPoolConstants.INTERFACEMETHODREF:
                    ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo = (ConstantInterfaceMethodRefInfo) constantInfo;
                    sb.append(" = InterfaceMethodref\t#" + constantInterfaceMethodRefInfo.getClassIndex() + ".#" + constantInterfaceMethodRefInfo.getNameAndTypeIndex() + "\n");
                    break;
                case ConstantPoolConstants.NAMEANDTYPE:
                    ConstantNameAndTypeInfo constantNameAndTypeInfo = (ConstantNameAndTypeInfo) constantInfo;
                    sb.append(" = NameAndType\t\t#" + constantNameAndTypeInfo.getNameIndex() + ".#" + constantNameAndTypeInfo.getDescriptorIndex() + "\n");
                    break;
                case ConstantPoolConstants.METHOD_HANDLE:
                    ConstantMethodHandleInfo constantMethodHandleInfo = (ConstantMethodHandleInfo) constantInfo;
                    sb.append(" = MethodHandle\t\t#" + constantMethodHandleInfo.getReferenceKind() + ".#" + constantMethodHandleInfo.getReferenceIndex() + "\n");
                    break;
                case ConstantPoolConstants.METHOD_TYPE:
                    ConstantMethodTypeInfo constantMethodTypeInfo = (ConstantMethodTypeInfo) constantInfo;
                    sb.append(" = MethodType\t\t#" + constantMethodTypeInfo.getDescriptorIndex() + "\n");
                    break;
                case ConstantPoolConstants.DYNAMIC:
                    // JDK11之后使用过
                    ConstantDynamicInfo constantDynamicInfo = (ConstantDynamicInfo) constantInfo;
                    break;
                case ConstantPoolConstants.INVOKEDYNAMIC:
                    ConstantInvokeDynamicInfo constantInvokeDynamicInfo = (ConstantInvokeDynamicInfo) constantInfo;
                    sb.append(" = InvokeDynamic\t#" + constantInvokeDynamicInfo.getBootstrapMethodAttrIndex() + ".#" + constantInvokeDynamicInfo.getNameAndTypeIndex() + "\n");
                    break;
            }
        }
        sb.append("Fields:\n");
        for (int i = 0; i < fieldsCount; i++) {
            FieldInfo fieldInfo = fields[i];
            sb.append("\t").append(ClassFileUtil.parseFieldAccessFlags(fieldInfo.getAccessFlags())).append(" ")
                    .append(ClassFileUtil.parseFieldDescriptor(ClassFileUtil.getUtf8Info(constantInfos, fieldInfo.getDescriptorIndex()))).append(" ")
                    .append(ClassFileUtil.getUtf8Info(constantInfos, fieldInfo.getNameIndex())).append(";\n");
            sb.append("\t\tdescriptor: ").append(ClassFileUtil.getUtf8Info(constantInfos, fieldInfo.getDescriptorIndex())).append("\n");
            sb.append("\t\tflags: ").append(ClassFileUtil.getFieldAccessFlags(fieldInfo.getAccessFlags())).append("\n");
            sb.append("\t\tattributes: ").append("\n");
            for (int j = 0; j < fieldInfo.getAttributeCount(); j++) {
                AttributeInfo attributeInfo = fieldInfo.getAttributes()[j];
                String attributeName = ClassFileUtil.getUtf8Info(constantInfos, attributeInfo.getAttributeNameIndex());
                // ConstantValue属性
                sb.append("\t\t\t").append(attributeName).append(": ");
                if (attributeInfo instanceof ConstantValueAttribute){
                    ConstantValueAttribute constantValueAttribute = (ConstantValueAttribute) attributeInfo;
                    ConstantInfo constantInfo = constantInfos[constantValueAttribute.getConstantValueIndex()];
                    if (constantInfo instanceof ConstantIntegerInfo){
                        ConstantIntegerInfo constantIntegerInfo = (ConstantIntegerInfo) constantInfo;
                        sb.append("Integer ").append(constantIntegerInfo.getBytes()).append("\n");
                    } else if (constantInfo instanceof ConstantLongInfo){
                        ConstantLongInfo constantLongInfo = (ConstantLongInfo) constantInfo;
                        sb.append("Long ").append(constantLongInfo.getBytes()).append("L\n");
                    } else if (constantInfo instanceof ConstantFloatInfo){
                        ConstantFloatInfo constantFloatInfo = (ConstantFloatInfo) constantInfo;
                        sb.append("Float ").append(constantFloatInfo.getBytes()).append("f\n");
                    } else if (constantInfo instanceof ConstantDoubleInfo){
                        ConstantDoubleInfo constantDoubleInfo = (ConstantDoubleInfo) constantInfo;
                        sb.append("Double ").append(constantDoubleInfo.getBytes()).append("\n");
                    } else if (constantInfo instanceof ConstantStringInfo){
                        ConstantStringInfo constantStringInfo = (ConstantStringInfo) constantInfo;
                        sb.append("String ").append(ClassFileUtil.getUtf8Info(constantInfos, constantStringInfo.getStringIndex())).append("\n");
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
            String descriptor = ClassFileUtil.getUtf8Info(constantInfos, methodInfo.getDescriptorIndex());
            // 解析后的方法描述符
            String[] descriptors = ClassFileUtil.parseMethodDescriptor(descriptor);
            // 方法名
            String methodName = ClassFileUtil.getUtf8Info(constantInfos, methodInfo.getNameIndex());
            if (methodName.equals("<init>")) {
                sb.append("\t")
                        .append(parsedMethodAccessFlags).append(" ")
                        .append(ClassFileUtil.getClassInfo(constantInfos, thisClass))
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
                String attributeName = ClassFileUtil.getUtf8Info(constantInfos, attributeInfo.getAttributeNameIndex());
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
                        String attributeNameOfCode = ClassFileUtil.getUtf8Info(constantInfos, attribute.getAttributeNameIndex());
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
                                        .append("\t").append(ClassFileUtil.getUtf8Info(constantInfos, localVariableTable.getNameIndex()))
                                        .append("\t").append(ClassFileUtil.getUtf8Info(constantInfos, localVariableTable.getDescriptorIndex()))
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
            String attributeName = ClassFileUtil.getUtf8Info(constantInfos, attributeInfo.getAttributeNameIndex());
            sb.append("\t").append(attributeName).append(": ");
            if (attributeInfo instanceof DeprecatedAttribute){
                sb.append("True").append("\n");
            } else if (attributeInfo instanceof SourceFileAttribute) {
                SourceFileAttribute sourceFileAttribute = (SourceFileAttribute) attributeInfo;
                String sourceFileName = ClassFileUtil.getUtf8Info(constantInfos, sourceFileAttribute.getSourceFileIndex());
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
                            .append("//").append(ClassFileUtil.getUtf8Info(constantInfos, innerClassTable.getInnerNameIndex()))
                            .append("=class ").append(ClassFileUtil.getClassInfo(constantInfos, innerClassTable.getInnerClassInfoIndex()))
                            .append(" of class ").append(ClassFileUtil.getClassInfo(constantInfos, innerClassTable.getOuterClassInfoIndex()))
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
                            .append(" ").append(ClassFileUtil.parseConstantPoolIndex(constantInfos, bootstrapMethod.getBootstrap_method_ref()))
                            .append("\n");
                    sb.append("\t\t\t").append("Method arguments: ").append("\n");
                    int numBootstrapArguments = bootstrapMethod.getNumBootstrapArguments();
                    for (int k = 0; k < numBootstrapArguments; k++) {
                        sb.append("\t\t\t\t#")
                                .append(bootstrapMethod.getBootstrapArguments()[k])
                                .append(" ").append(ClassFileUtil.parseConstantPoolIndex(constantInfos, bootstrapMethod.getBootstrapArguments()[k]))
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
