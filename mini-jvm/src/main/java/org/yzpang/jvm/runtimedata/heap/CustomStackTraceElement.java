package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 堆栈跟踪元素
 * Date: 2025/4/11 上午10:49
 **/
@Data
public class CustomStackTraceElement {
    // 文件名
    private String fileName;
    // 类名
    private String className;
    // 方法名
    private String methodName;
    // 行号
    private int lineNumber;

    public CustomStackTraceElement() {
    }

    public CustomStackTraceElement(String fileName, String className, String methodName, int lineNumber) {
        this.fileName = fileName;
        this.className = className;
        this.methodName = methodName;
        this.lineNumber = lineNumber;
    }


}
