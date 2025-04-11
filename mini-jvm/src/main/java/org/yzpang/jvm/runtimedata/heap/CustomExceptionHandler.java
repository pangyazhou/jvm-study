package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.runtimedata.heap.constantpool.ClassRef;

/**
 * Author: yzpang
 * Desc: 异常处理表
 * Date: 2025/4/11 上午9:40
 **/
@Data
public class CustomExceptionHandler {
    private int startPc;
    private int endPc;
    private int handlerPc;
    private ClassRef catchType;
}
