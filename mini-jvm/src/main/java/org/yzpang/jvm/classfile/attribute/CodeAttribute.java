package org.yzpang.jvm.classfile.attribute;

import com.sun.org.apache.bcel.internal.classfile.ExceptionTable;
import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:  变长属性
 *          method_info结构属性表中
 *          包含某个方法/实例初始化方法/类或者接口初始化方法的Java虚拟机指令及相关辅助信息
 *          native/abstract方法, 属性中不能有Code属性
 *          method_info中有且智能有一个Code属性.
 * Date: 2025/3/18 下午1:51
 **/
public class CodeAttribute extends AttributeInfo {
    /**
     * 操作数栈最大深度
     */
    private int maxStack;
    /**
     * 局部变量表中的局部变量个数
     * long/double类型局部变量最大索引是 max_locals-2, 其他类型局部变量最大索引是max_locals-1
     */
    private int maxLocals;
    /**
     * code[] 数组的长度, 必须大于0
     */
    private int codeLength;
    /**
     * 实现当前方法的Java虚拟机代码的实际字节内容
     */
    private byte[] code;
    /**
     * exception_table表成员个数
     */
    private int exceptionTableLength;
    /**
     * code[]数组中的一个异常处理器, 顺序不可改动
     */
    private ExceptionTable[] exceptionTable;
    /**
     * 属性表数量
     */
    private int attributesCount;
    private AttributeInfo[] attributes;
}
