package org.yzpang.jvm.classfile.constant;

/**
 * 方法修饰符
 */
public interface MethodAccessConstants {
    int ACC_PUBLIC = 0x0001;
    int ACC_PRIVATE = 0x0002;
    int ACC_PROTECTED = 0x0004;
    int ACC_STATIC = 0x0008;
    int ACC_FINAL = 0x0010;
    int ACC_SYNCHRONIZED = 0x0020;
    /**
     * 是否为编译器产生的桥接方法
     */
    int ACC_BRIDGE = 0x0040;
    /**
     * 是否接收不定参数
     */
    int ACC_VARARGS = 0x0080;
    int ACC_NATIVE = 0x0100;
    int ACC_ABSTRACT = 0x0400;
    int ACC_STRICT = 0x0800;
    /**
     * 是否为编译器自动产生
     */
    int ACC_SYNTHETIC = 0x1000;
}
