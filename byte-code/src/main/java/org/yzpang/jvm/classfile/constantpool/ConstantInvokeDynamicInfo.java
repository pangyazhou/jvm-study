package org.yzpang.jvm.classfile.constantpool;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:26
 **/
public class ConstantInvokeDynamicInfo extends ConstantPoolInfo {
    private int tag = ConstantInfoEnum.INVOKEDYNAMIC.ordinal();
    /**
     * 当前class文件中引导方法表的bootstrap_methods数组的有效索引
     */
    private int bootstrapMethodAttrIndex;

    /**
     * 常量池的有效索引, 指向Constant_NameAndType_info结构, 此结构表示方法名和方法描述符
     */
    private int nameAndTypeIndex;
}
