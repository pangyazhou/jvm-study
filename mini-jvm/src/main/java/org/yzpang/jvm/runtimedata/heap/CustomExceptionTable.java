package org.yzpang.jvm.runtimedata.heap;

import org.yzpang.jvm.classfile.attribute.ExceptionInfo;
import org.yzpang.jvm.runtimedata.heap.constantpool.ClassRef;

/**
 * Author: yzpang
 * Desc: 异常表
 * Date: 2025/4/11 上午9:18
 **/
public class CustomExceptionTable {
    private final CustomExceptionHandler[] exceptionTable;


    public CustomExceptionTable(ExceptionInfo[] exceptionInfos, CustomConstantPool constantPool) {
        exceptionTable = new CustomExceptionHandler[exceptionInfos.length];
        for (int i = 0; i < exceptionInfos.length; i++) {
            exceptionTable[i] = new CustomExceptionHandler();
            exceptionTable[i].setStartPc(exceptionInfos[i].getStartPc());
            exceptionTable[i].setEndPc(exceptionInfos[i].getEndPc());
            exceptionTable[i].setHandlerPc(exceptionInfos[i].getHandlerPc());
            exceptionTable[i].setCatchType(getCatchType(exceptionInfos[i].getCatchType(), constantPool));
        }
    }

    /**
     * 根据异常发生位置 pc 和异常类型 clazz 查找匹配的异常处理器
     * @param exClazz 抛出异常类型
     * @param pc 异常发生位置
     * @return 异常处理器 handler
     */
    public CustomExceptionHandler findExceptionHandler(CustomClass exClazz, int pc) throws Exception {
        for (CustomExceptionHandler handler : exceptionTable) {
            if (pc >= handler.getStartPc() && pc < handler.getEndPc()) {
                // catch-all
                if (handler.getCatchType() == null) {
                    return handler;
                }
                CustomClass catchClass = handler.getCatchType().resolvedClass();
                // 抛出异常类型是处理异常类型的子类
                if (catchClass == exClazz || catchClass.isSuperClassOf(exClazz)) {
                    return handler;
                }
            }
        }
        return null;
    }

    /**
     * 解析异常处理的异常类型
     * @param index 常量池索引
     * @param constantPool 常量池
     * @return 异常类型
     */
    private ClassRef getCatchType(int index, CustomConstantPool constantPool) {
        if (index == 0) {
            return null;
        }
        ClassRef classRef = (ClassRef) constantPool.getConstant(index);
        return classRef;
    }
}
