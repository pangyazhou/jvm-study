package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.ClassReader;
import org.yzpang.jvm.classfile.ConstantPoolInfo;

/**
 * Author: yzpang
 * Desc:    定长属性
 *          ClassFile结构
 * Date: 2025/3/19 上午11:19
 **/
public class SourceFileAttribute extends AttributeInfo {
    /**
     * u2 '常量池有效索引, 指向Constant_Utf8_info结构, 常量值是源码文件的文件名
     */
    private int sourceFileIndex;

    @Override
    protected void readInfo(ClassReader reader) {
        this.sourceFileIndex = reader.readUShort();
    }

    public String fileName(){
        return this.constantPool.getUtf8(sourceFileIndex);
    }
}
