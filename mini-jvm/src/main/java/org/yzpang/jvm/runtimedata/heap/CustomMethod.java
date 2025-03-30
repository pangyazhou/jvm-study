package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.MethodInfo;
import org.yzpang.jvm.classfile.attribute.CodeAttribute;

/**
 * 方法对象
 */
@Data
public class CustomMethod extends ClassMember{
    private int maxLocal;
    private int maxStack;
    private byte[] code;


}
