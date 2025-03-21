package org.yzpang.jvm.classfile.attribute;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:  变长属性
 *          Code结构中
 *          确定源文件中给定的行号所表示的内容,对应于Code[]数组中的那一部分
 * Date: 2025/3/18 下午5:17
 **/
@Data
public class LineNumberTableAttribute extends AttributeInfo {
    /**
     * u2 行号表数量
     */
    private int lineNumberTableLength;
    private LineNumberTable[] lineNumberTables;

}
