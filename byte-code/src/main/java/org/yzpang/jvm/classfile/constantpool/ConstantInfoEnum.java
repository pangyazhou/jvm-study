package org.yzpang.jvm.classfile.constantpool;

/**
 * 常量池表标志位
 */
public enum ConstantInfoEnum {
    UTF8(1, "UTF-8编码的字符串"),
    INTEGER(3, "整型字面量"),
    FLOAT(4, "浮点型字面量"),
    LONG(5, "长整型字面量"),
    DOUBLE(6, "双精度浮点型字面量"),
    CLASS(7, "类或接口的符号引用"),
    STRING(8, "字符串类型字面量"),
    FIELDREF(9, "字段的符号引用过"),
    METHODREF(10, "类中方法的符号引用"),
    INTERFACEMETHODREF(11, "接口中方法的符号引用"),
    NAMEANDTYPE(12, "字段或方法的部分符号引用"),
    METHOD_HANDLE(15, "方法句柄"),
    METHOD_TYPE(16, "方法类型"),
    DYNAMIC(17, "动态计算常量"),
    INVOKEDYNAMIC(18, "动态方法调用点"),
    MODULE(19, "模块"),
    PACKAGE(20, "模块中开放或导出的包"),
    ;

    private final Integer code;
    private final String msg;

    ConstantInfoEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
