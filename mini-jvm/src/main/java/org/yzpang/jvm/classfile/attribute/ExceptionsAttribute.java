package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.ClassReader;

/**
 * Author: yzpang
 * Desc: 异常属性
 * Date: 2025/3/18 下午3:30
 **/
public class ExceptionsAttribute extends AttributeInfo {
    /**
     * u2 异常表中的数量
     */
    private int numberOfExceptions;
    /**
     * u2 数组的每个成员都是常量池的有效索引, 指向Constant_Class_info结构
     * 抛出RuntimeException或其子类
     * 抛出Error或其子类
     * 抛出exception_index_table数组中声明的异常类或其子类的实例
     */
    private short[] exceptionIndexTable;

    @Override
    protected void readInfo(ClassReader reader) {
        this.exceptionIndexTable = reader.readShorts();
    }
}
