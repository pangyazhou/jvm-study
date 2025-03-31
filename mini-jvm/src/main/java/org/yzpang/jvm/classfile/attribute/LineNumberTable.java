package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.ClassReader;

/**
 * Author: yzpang
 * Desc: 行号表
 * Date: 2025/3/18 下午5:21
 **/
public class LineNumberTable {
    /**
     * u2 code[]数组的索引,
     */
    private int startPC;
    /**
     * u2 源文件中对应的行号
     */
    private int lineNumber;

    public static LineNumberTable readLineNumberTable(ClassReader reader) {
        LineNumberTable lineNumberTable = new LineNumberTable();
        lineNumberTable.startPC = reader.readUShort();
        lineNumberTable.lineNumber = reader.readUShort();
        return lineNumberTable;
    }

    public static LineNumberTable[] readLineNumberTables(ClassReader reader) {
        int count = reader.readUShort();
        LineNumberTable[] tables = new LineNumberTable[count];
        for (int i = 0; i < count; i++) {
            tables[i] = readLineNumberTable(reader);
        }
        return tables;
    }
}
