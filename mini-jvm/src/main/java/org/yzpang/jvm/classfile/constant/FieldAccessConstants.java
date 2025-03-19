package org.yzpang.jvm.classfile.constant;

/**
 * 字段修饰符常量
 */
public interface FieldAccessConstants {
    /**
     * 是否public
     */
    int ACC_PUBLIC = 0x0001;
    /**
     * 是否private
     */
    int ACC_PRIVATE = 0x0002;
    /**
     * 是否protected
     */
    int ACC_PROTECTED = 0x0004;
    /**
     * 是否static
     */
    int ACC_STATIC = 0x0008;
    /**
     * 是否final
     */
    int ACC_FINAL = 0x0010;
    /**
     * 是否volatile
     */
    int ACC_VOLATILE = 0x0040;
    /**
     * 是否transient
     */
    int ACC_TRANSIENT = 0x0080;
    /**
     * 是否由编译器自动产生
     */
    int ACC_SYNTHETIC = 0x1000;
    /**
     * 是否enum
     */
    int ACC_ENUM = 0x4000;
}
