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
    private MemberInfo[] fields;
    /**
     * u2 方法表数量
     */
    private int methodsCount;
    /**
     * 方法表
     */
    private MemberInfo[] methods;
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
        this.interfacesCount = reader.readUShort();
        // 接口索引列表
        if (this.interfacesCount > 0) {
            this.interfaces = new int[interfacesCount];
            for (int i = 0; i < interfacesCount; i++) {
                interfaces[i] = reader.readUShort();
            }
        }
        // 字段表
        this.fields = MemberInfo.readMembers(reader, this.constantPool);
        // 方法表
        this.methods = MemberInfo.readMembers(reader, this.constantPool);
        // 属性表
        this.attributes = AttributeInfo.readAttributes(reader, this.constantPool);

    }

    private boolean checkMagic() {
        return this.magic == JvmConstants.MAGIC_NUMBER;
    }

    private boolean checkVersion() {
        return this.majorVersion <= JvmConstants.MAJOR_VERSION;
    }

    public String getClassName(){
        return this.constantPool.getClassName(thisClass);
    }

    public String getSuperClassName(){
        return this.constantPool.getClassName(thisClass);
    }

    public String[] getInterfacesNames(){
        String[] names = new String[interfacesCount];
        for (int i = 0; i < interfacesCount; i++) {
            names[i] = this.constantPool.getClassName(interfaces[i]);
        }
        return names;
    }

    public MemberInfo getMainMethod(){
        for (MemberInfo methodInfo : methods) {
            String methodName = methodInfo.getName();
            String descriptor = methodInfo.getDescriptor();
            if (methodName.equals("main") && descriptor.equals("([Ljava/lang/String;)V")) {
                return methodInfo;
            }
        }
        return null;
    }

}
