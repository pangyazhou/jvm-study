package org.yzpang.jvm.classfile;

import lombok.Getter;

/**
 * Author: yzpang
 * Desc: 类成员
 * Date: 2025/3/31 下午3:59
 **/
public class MemberInfo {
    protected ConstantPoolInfo constantPoolInfo;
    @Getter
    protected int accessFlags;
    protected int nameIndex;
    protected int descriptorIndex;
    @Getter
    protected AttributeInfo[] attributes;

    public static MemberInfo readMember(ClassReader reader, ConstantPoolInfo constantPoolInfo) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.constantPoolInfo = constantPoolInfo;
        memberInfo.accessFlags = reader.readUShort();
        memberInfo.nameIndex = reader.readUShort();
        memberInfo.descriptorIndex = reader.readUShort();
        memberInfo.attributes = AttributeInfo.readAttributes(reader, constantPoolInfo);
        return memberInfo;
    }

    public static MemberInfo[] readMembers(ClassReader reader, ConstantPoolInfo constantPoolInfo) {
        int memberCount = reader.readUShort();
        MemberInfo[] members = new MemberInfo[memberCount];
        for (int i = 0; i < memberCount; i++) {
            members[i] = readMember(reader, constantPoolInfo);
        }
        return members;
    }

    public String getName(){
        return this.constantPoolInfo.getUtf8(this.nameIndex);
    }

    public String getDescriptor(){
        return this.constantPoolInfo.getUtf8(this.descriptorIndex);
    }
}
