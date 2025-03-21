package org.yzpang.jvm.classfile.attribute;

import lombok.Data;

/**
 * Author: yzpang
 * Desc:  运行时可见的注解
 * Date: 2025/3/19 下午2:14
 **/
@Data
public class Annotations {
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_info结构
     * 表示一个字段描述符, 用来表示一个注解类型
     */
    private int typeIndex;
    /**
     * u2 注解中的键值对格式
     */
    private int numElementValuePairs;
    /**
     * 数组的每个成员表示注解中的一个键值对
     */
    private ElementValuePairs[] elementValuePairs;
}
