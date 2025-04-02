package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.constantpool.ConstantMemberRefInfo;


/**
 * 类成员引用父类
 */
@Data
public class CustomMemberRef extends CustomSymbolRef{
    protected String name;
    protected String descriptor;

    public void copyMemberRefInfo(ConstantMemberRefInfo memberRefInfo) {
        this.className = memberRefInfo.getClassName();
        String[] nameAndType = memberRefInfo.getNameAndType();
        this.name = nameAndType[0];
        this.descriptor = nameAndType[1];
    }


}
