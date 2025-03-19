package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc: 行号表
 * Date: 2025/3/18 下午5:21
 **/
public class LineNumberTable {
    /**
     * code[]数组的索引,
     */
    private int startPC;
    /**
     * 源文件中对应的行号
     */
    private int lineNumber;
}
