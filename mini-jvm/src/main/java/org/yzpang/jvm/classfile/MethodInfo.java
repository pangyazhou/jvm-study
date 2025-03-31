package org.yzpang.jvm.classfile;

import lombok.Data;
import org.yzpang.jvm.classfile.attribute.CodeAttribute;
import org.yzpang.jvm.classfile.util.ClassFileUtil;

/**
 * Author: yzpang
 * Desc: 方法表
 * Date: 2025/3/18 上午11:28
 **/
@Data
public class MethodInfo {
    /**
     * u2 方法修饰符
     */
    public int accessFlags;
    /**
     * u2 常量池的有效索引, 方法的简单名称
     */
    private int nameIndex;
    /**
     * u2 常量池的有效索引, 方法的描述符
     */
    private int descriptorIndex;
    /**
     * u2 属性计数器
     */
    private int attributeCount;
    /**
     * u2 属性表
     */
    private AttributeInfo[] attributes;

    private ClassFile classFile;

    public String getName(){
        return ClassFileUtil.getUtf8Info(classFile.getConstantInfos(), nameIndex);
    }

    public String getDescriptor(){
        return ClassFileUtil.getUtf8Info(classFile.getConstantInfos(), descriptorIndex);
    }

    public CodeAttribute getCodeAttribute() {
        for (AttributeInfo attribute : attributes) {
            if (attribute instanceof CodeAttribute) {
                return (CodeAttribute) attribute;
            }
        }
        return null;
    }
}
