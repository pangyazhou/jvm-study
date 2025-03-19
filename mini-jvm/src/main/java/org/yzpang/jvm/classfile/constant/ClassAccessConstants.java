package org.yzpang.jvm.classfile.constant;

/**
 * Author: yzpang
 * Desc: 类或接口层次的访问信息
 * Date: 2025/3/18 上午10:57
 **/
public interface ClassAccessConstants {
    /**
     * 是否为public类型
     */
    int ACC_PUBLIC = 0x0001;
    /**
     * 是否被声明为final, 只有类可以被设置
     */
    int ACC_FINAL = 0x0010;
    /**
     * 是否允许使用invokespecial字节码指令的新语义, 常为True
     */
    int ACC_SUPER = 0x0020;
    /**
     * 标识这是一个接口
     */
    int ACC_INTERFACE = 0x0200;
    /**
     * 是否为abstract类型, 对于接口与抽象类为真,其他为假
     */
    int ACC_ABSTRACT = 0x0400;

    /**
     * 标识这个类并非由用户代码产生
     */
    int ACC_SYNTHETIC = 0x1000;

    /**
     * 标识这是一个注解
     */
    int ACC_ANNOTATION = 0x2000;
    /**
     * 标识这是一个枚举
     */
    int ACC_ENUM = 0x4000;
    /**
     * 标识知识一个模块
     */
    int ACC_MODULE = 0x8000;
}
