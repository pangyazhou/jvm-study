package org.yzpang.jvm.classfile;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 常量池
 * Date: 2025/3/18 上午9:00
 **/
@Data
public class ConstantInfo {
    /**
     * u1 常量标志
     */
    protected int tag;

    public void readInfo(ClassReader reader){
    }

}
