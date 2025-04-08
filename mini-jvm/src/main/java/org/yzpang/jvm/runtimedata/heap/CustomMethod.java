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
            methods[i] = newMethod(clazz, methodInfos[i]);
        }
        return methods;
    }
    
    private static CustomMethod newMethod(CustomClass clazz, MemberInfo memberInfo){
        CustomMethod method = new CustomMethod();
        method.setClazz(clazz);
        method.copyMemberInfo(memberInfo);
        method.copyCodeAttribute(memberInfo);
        CustomMethodDescriptor methodDescriptor = DescriptorParserUtil.parseMethodDescriptor(method.descriptor);
        method.calcArgSlotCount(methodDescriptor.getParameterTypes());
        if (method.isNative()) {
            method.injectCodeAttribute(methodDescriptor.getReturnType());
        }
        return method;
    }

    private void copyCodeAttribute(MemberInfo memberInfo) {
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
    private void calcArgSlotCount(String[] parameterTypes){
        for (String parameterType : parameterTypes) {
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

    /**
     * 注入Code属性
     * @param returnType 方法返回类型
     */
    private void injectCodeAttribute(String returnType) {
        this.maxStack = 4;
        this.maxLocal = this.argSlotCount;
        switch(returnType.charAt(0)){
            case 'V':
                this.code = new byte[] {(byte) 0xfe, (byte) 0xb1};  // return
                break;
            case 'D':
                this.code = new byte[] {(byte) 0xfe, (byte) 0xaf};  // dreturn
                break;
            case 'F':
                this.code = new byte[] {(byte) 0xfe, (byte) 0xae};  // freturn
                break;
            case 'J':
                this.code = new byte[] {(byte) 0xfe, (byte) 0xad};  // lreturn
                break;
            case 'L':
            case '[':
                this.code = new byte[] {(byte) 0xfe, (byte) 0xb0};  // areturn
                break;
            default:
                this.code = new byte[] {(byte) 0xfe, (byte) 0xac};  // ireturn
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
