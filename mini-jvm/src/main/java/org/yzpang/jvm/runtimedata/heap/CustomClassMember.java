package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.MemberInfo;
import org.yzpang.jvm.constant.AccessConstants;

import java.util.Objects;

/**
 * 类成员
 * field/method
 */
@Data
public class CustomClassMember {
    protected int accessFlags;
    protected String name;
    protected String descriptor;
    protected CustomClass clazz;

    public void copyMemberInfo(MemberInfo memberInfo) {
        this.accessFlags = memberInfo.getAccessFlags();
        this.name = memberInfo.getName();
        this.descriptor = memberInfo.getDescriptor();
    }

    /**
     * 成员是否可被指定类访问
     * @param clazz 访问的类结构
     * @return bool
     */
    public boolean isAccessibleTo(CustomClass clazz) {
        // public 均可访问
        if (isPublic()){
            return true;
        }
        // protected 可被子类或同一包下的类访问
        if (isProtected()){
            return clazz == this.clazz
                    || clazz.isSubClassOf(this.clazz)
                    || Objects.equals(clazz.getPackageName(), this.clazz.getPackageName());
        }
        // 非private可被同一包下的类访问
        if (!isPrivate()){
            return Objects.equals(clazz.getPackageName(), this.clazz.getPackageName());
        }
        // 声明字段的类可以访问
        return clazz == this.clazz;
    }

    public boolean isPublic(){
        return (this.accessFlags & AccessConstants.ACC_PUBLIC) != 0;
    }

    public boolean isProtected(){
        return (this.accessFlags & AccessConstants.ACC_PROTECTED) != 0;
    }

    public boolean isPrivate(){
        return (this.accessFlags & AccessConstants.ACC_PRIVATE) != 0;
    }

    public boolean isStatic(){
        return (this.accessFlags & AccessConstants.ACC_STATIC) != 0;
    }

    public boolean isFinal(){
        return (this.accessFlags & AccessConstants.ACC_FINAL) != 0;
    }
}
