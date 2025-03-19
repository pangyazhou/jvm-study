package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc:    参数信息
 * Date: 2025/3/19 下午1:59
 **/
public class Parameters {
    /**
     * 0或者常量池有效索引,
     * 0: 描述的是没有名称的形式参数
     * !0: 指向Constant_Utf8_info结构
     */
    private short nameIndex;
    /**
     * 访问标志, ParameterAccessConstant
     */
    private short accessFlags;
}
