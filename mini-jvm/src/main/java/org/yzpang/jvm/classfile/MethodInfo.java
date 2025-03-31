package org.yzpang.jvm.classfile;

import org.yzpang.jvm.classfile.attribute.CodeAttribute;

/**
 * Author: yzpang
 * Desc: 方法表
 * Date: 2025/3/18 上午11:28
 **/
public class MethodInfo extends MemberInfo{

    public CodeAttribute getCodeAttribute() {
        for (AttributeInfo attribute : attributes) {
            if (attribute instanceof CodeAttribute) {
                return (CodeAttribute) attribute;
            }
        }
        return null;
    }
}
