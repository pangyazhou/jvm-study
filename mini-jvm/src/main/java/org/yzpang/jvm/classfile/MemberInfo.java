package org.yzpang.jvm.classfile;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 类成员
 * Date: 2025/3/31 下午3:59
 **/
@Data
public class MemberInfo {
    protected ConstantPoolInfo constantPoolInfo;
    protected int accessFlags;
    protected int nameIndex;
    protected int descriptorIndex;
    protected AttributeInfo[] attributes;

    public String getName(){
        return this.constantPoolInfo.getUtf8(this.nameIndex);
    }

    public String getDescriptor(){
        return this.constantPoolInfo.getUtf8(this.descriptorIndex);
    }
}
