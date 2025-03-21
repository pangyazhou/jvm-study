package org.yzpang.jvm.classfile.attribute;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 行号表
 * Date: 2025/3/18 下午5:21
 **/
@Data
public class LineNumberTable {
    /**
     * u2 code[]数组的索引,
     */
    private int startPC;
    /**
     * u2 源文件中对应的行号
     */
    private int lineNumber;
}
