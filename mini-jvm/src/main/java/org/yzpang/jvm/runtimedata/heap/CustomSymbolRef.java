package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;

@Data
public class CustomSymbolRef{
    protected CustomConstantPool constantPool;
    protected String className;
    protected CustomClass clazz;

}
