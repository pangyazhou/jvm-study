package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.MemberInfo;
import org.yzpang.jvm.classfile.attribute.CodeAttribute;
import org.yzpang.jvm.constant.MethodAccessConstants;
import org.yzpang.jvm.runtimedata.util.DescriptorParserUtil;

/**
 * 方法对象
 */
@Data
public class CustomMethod extends CustomClassMember {
    private int maxLocal;
    private int maxStack;
    private byte[] code;
    // 参数slot数量
    private int argSlotCount;

    public static CustomMethod[] newMethods(CustomClass clazz, MemberInfo[] methodInfos){
        CustomMethod[] methods = new CustomMethod[methodInfos.length];
        for(int i = 0; i < methodInfos.length; i++){
            methods[i] = new CustomMethod();
            methods[i].setClazz(clazz);
            methods[i].copyMemberInfo(methodInfos[i]);
            methods[i].copyCodeAttribute(methodInfos[i]);
            methods[i].calcArgSlotCount();
        }
        return methods;
    }

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

    /**
     * 计算方法参数slot数量
     */
    private void calcArgSlotCount(){
        CustomMethodDescriptor methodDescriptor = DescriptorParserUtil.parseMethodDescriptor(this.descriptor);
        for (String parameterType : methodDescriptor.getParameterTypes()) {
            this.argSlotCount++;
            // long和double占用两个slot
            if (parameterType.equals("J") || parameterType.equals("D")) {
                this.argSlotCount++;
            }
        }
        // 实例方法多一个obj引用
        if (!isStatic()){
            this.argSlotCount++;
        }
    }

    public boolean isSynthetic(){
        return (this.accessFlags & MethodAccessConstants.ACC_SYNTHETIC) != 0;
    }

    public boolean isSynchronized(){
        return (this.accessFlags & MethodAccessConstants.ACC_SYNCHRONIZED) != 0;
    }

    public boolean isAbstract(){
        return (this.accessFlags & MethodAccessConstants.ACC_ABSTRACT) != 0;
    }

    public boolean isNative() {
        return (this.accessFlags & MethodAccessConstants.ACC_NATIVE) != 0;
    }
}
