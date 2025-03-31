package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.MemberInfo;
import org.yzpang.jvm.classfile.attribute.CodeAttribute;

/**
 * 方法对象
 */
@Data
public class CustomMethod extends ClassMember{
    private int maxLocal;
    private int maxStack;
    private byte[] code;

    public void copyCodeAttribute(MemberInfo memberInfo) {
        for (AttributeInfo attributeInfo : memberInfo.getAttributes()) {
            if (attributeInfo instanceof CodeAttribute) {
                CodeAttribute codeAttribute = (CodeAttribute) attributeInfo;
                this.maxStack = codeAttribute.getMaxStack();
                this.maxLocal = codeAttribute.getMaxLocals();
                this.code = codeAttribute.getCode();
            }
        }
    }
}
