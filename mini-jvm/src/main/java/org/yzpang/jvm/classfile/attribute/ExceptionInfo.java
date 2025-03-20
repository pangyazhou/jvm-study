package org.yzpang.jvm.classfile.attribute;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 异常处理器
 * Date: 2025/3/18 下午1:56
 **/
@Data
public class ExceptionInfo {
    /**
     * u2 异常处理器作用范围
     */
    private int startPc;
    /**
     * u2 当前code[]和其中某一指令操作码的有效索引
     */
    private int endPc;
    /**
     * u2 异常处理器的起点
     */
    private int handlerPc;
    /**
     * u2   !=0: 常量池的有效索引,指向Constant_Class_info结构, 表示当前异常处理器捕捉的异常类型.
     *      ==0: 所有异常抛出都调用此异常处理器, 用于处理finally语句.
     */
    private int catchType;
}
