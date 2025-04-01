package org.yzpang.jvm.runtimedata.heap;


import lombok.Data;
import org.yzpang.jvm.classfile.MemberInfo;
import org.yzpang.jvm.classfile.attribute.ConstantValueAttribute;

import java.util.Objects;

/**
 * 字段对象
 */
@Data
public class CustomField extends CustomClassMember {
    private int slotId;
    // 静态常量在常量池中的索引
    private int constantValueIndex;

    public static CustomField[] newFields(CustomClass clazz, MemberInfo[] fieldInfos) {
        CustomField[] fields = new CustomField[fieldInfos.length];
        for (int i = 0; i < fieldInfos.length; i++) {
            fields[i] = new CustomField();
            fields[i].setClazz(clazz);
            fields[i].copyMemberInfo(fieldInfos[i]);
            fields[i].copyAttributes(fieldInfos[i]);
        }
        return fields;
    }

    public boolean isStatic(){
        // todo
        return false;
    }

    public boolean isFinal(){
        // todo
        return false;
    }

    public boolean isLongOrDouble(){
        return Objects.equals(this.descriptor, "J") || Objects.equals(this.descriptor, "D");
    }

    public void copyAttributes(MemberInfo fieldInfo){
        ConstantValueAttribute constantValueAttribute = fieldInfo.constantValueAttribute();
        if (constantValueAttribute != null) {
            this.constantValueIndex = constantValueAttribute.constantValueIndex();
        }
        // todo 其他字段属性
    }

}
