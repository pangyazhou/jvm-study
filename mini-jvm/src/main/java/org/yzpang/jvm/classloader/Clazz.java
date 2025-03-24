package org.yzpang.jvm.classloader;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassFile;

/**
 * Author: yzpang
 * Desc: 类类型
 * Date: 2025/3/19 下午4:19
 **/
@Data
public class Clazz {
    private String className;

    private ClassFile classFile;
}
