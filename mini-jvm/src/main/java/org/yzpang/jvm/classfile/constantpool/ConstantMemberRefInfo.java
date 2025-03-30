package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;

@Data
public class ConstantMemberRefInfo extends ConstantPoolInfo {
    /**
     * u2 常量池的有效索引, 指向Constant_Class_Info结构, 可以是类或接口
     */
    protected int classIndex;
    /**
     * u2 常量池的有效索引, 指向Constant_NameAndType_info结构, 必须是类描述符
     */
    protected int nameAndTypeIndex;
}
