package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;

/**
 * 类成员
 * field/method
 */
@Data
public class ClassMember {
    private int accessFlags;
    private String name;
    private String descriptor;
    private CustomClass clazz;
}
