package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.ClassReader;

/**
 * Author: yzpang
 * Desc:  变长属性
 *          Code结构中
 *          确定源文件中给定的行号所表示的内容,对应于Code[]数组中的那一部分
 * Date: 2025/3/18 下午5:17
 **/
public class LineNumberTableAttribute extends AttributeInfo {
    /**
     * u2 行号表数量
     */
    private int lineNumberTableLength;
    private LineNumberTable[] lineNumberTables;

    @Override
    protected void readInfo(ClassReader reader) {
        this.lineNumberTables = LineNumberTable.readLineNumberTables(reader);
        this.lineNumberTableLength = this.lineNumberTables.length;
    }

    /**
     * 根据字节码pc获取源文件行号
     * @param pc 指令索引
     * @return 行号
     */
    public int getLineNumber(int pc) {
        for (int i = lineNumberTableLength - 1; i >= 0; i--) {
            LineNumberTable lineNumberTable = this.lineNumberTables[i];
            if (pc >= lineNumberTable.getStartPC()) {
                return lineNumberTable.getLineNumber();
            }
        }
        return -1;
    }

}
