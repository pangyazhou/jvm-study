package org.yzpang.jvm.constant;

public interface ConstantPoolConstants {
    int UTF8 = 1; //(1, "UTF-8编码的字符串"),
    int INTEGER = 3; //(3, "整型字面量"),
    int FLOAT = 4; //(4, "浮点型字面量"),
    int LONG = 5; //(5, "长整型字面量"),
    int DOUBLE = 6; //(6, "双精度浮点型字面量"),
    int CLASS = 7; //(7, "类或接口的符号引用"),
    int STRING = 8; //(8, "字符串类型字面量"),
    int FIELDREF = 9; //(9, "字段的符号引用过"),
    int METHODREF = 10; //(10, "类中方法的符号引用"),
    int INTERFACEMETHODREF = 11; //(11, "接口中方法的符号引用"),
    int NAMEANDTYPE = 12; //(12, "字段或方法的部分符号引用"),
    int METHOD_HANDLE = 15; //(15, "方法句柄"),
    int METHOD_TYPE = 16; //(16, "方法类型"),
    int DYNAMIC = 17; //(17, "动态计算常量"),
    int INVOKEDYNAMIC = 18; //(18, "动态方法调用点"),
    int MODULE = 19; //(19, "模块"),
    int PACKAGE = 20; //(20, "模块中开放或导出的包"),
}
