package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 常量池
 * Date: 2025/3/18 上午9:00
 **/
@Data
public class ConstantPoolInfo {
    /**
     * u1 常量标志
     */
    private int tag;
}
