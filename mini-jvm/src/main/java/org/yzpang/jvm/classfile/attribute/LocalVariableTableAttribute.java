package org.yzpang.jvm.classfile.attribute;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:  变长属性
 *          Code属性结构
 * Date: 2025/3/18 下午5:27
 **/
@Data
public class LocalVariableTableAttribute extends AttributeInfo {
    /**
     * u2 局部变量表的数量
     */
    private int localVariableTableLength;
    private LocalVariableTable[] localVariableTables;
}
