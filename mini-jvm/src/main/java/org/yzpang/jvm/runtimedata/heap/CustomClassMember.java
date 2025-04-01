package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.MemberInfo;

/**
 * 类成员
 * field/method
 */
@Data
public class CustomClassMember {
    protected int accessFlags;
    protected String name;
    protected String descriptor;
    protected CustomClass clazz;

    public void copyMemberInfo(MemberInfo memberInfo) {
        this.accessFlags = memberInfo.getAccessFlags();
        this.name = memberInfo.getName();
        this.descriptor = memberInfo.getDescriptor();
    }


}
