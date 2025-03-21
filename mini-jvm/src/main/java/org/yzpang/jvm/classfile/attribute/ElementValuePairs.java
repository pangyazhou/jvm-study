package org.yzpang.jvm.classfile.attribute;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 注解中的键值对
 * Date: 2025/3/19 下午2:16
 **/
@Data
public class ElementValuePairs {
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_info结构, 表示键值对中键的名字
     */
    private int elementNameIndex;
    /**
     * 给出了键值对中的value值
     */
    private ElementValue elementValue;
}
