package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          Code属性中
 *          调试器在执行方法的过程中,可以用来确定某个局部变量的值
 * Date: 2025/3/19 上午9:36
 **/
public class LocalVariableTypeTableAttribute extends AttributeInfo {
    private int localVariableTypeTableLength;
    private LocalVariableTypeTable[] localVariableTypeTables;
}
