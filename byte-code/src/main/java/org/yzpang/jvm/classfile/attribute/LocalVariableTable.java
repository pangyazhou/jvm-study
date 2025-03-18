package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc: 局部变量表
 * Date: 2025/3/18 下午5:28
 **/
public class LocalVariableTable {
    /**
     * 局部变量在[startPC, startPC+length)范围内,有值
     */
    private short startPC;
    private short length;
    /**
     * 常量池的有效索引,指定Constan_Utf8_info结构,表示有效的非限定名
     */
    private short nameIndex;
    /**
     * 常量池的有效索引,指定Constan_Utf8_info结构,表示局部变量类型的字段描述符
     */
    private short descriptorIndex;
    /**
     * 局部变量在当前栈帧的局部变量表中的索引
     */
    private short index;
}
