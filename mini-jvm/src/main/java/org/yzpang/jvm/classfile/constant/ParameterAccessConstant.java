package org.yzpang.jvm.classfile.constant;

/**
 * 参数修饰符
 */
public interface ParameterAccessConstant {
    /**
     * 该参数被final修饰
     */
    int ACC_FINAL = 0x0010;
    /**
     * 该参数未出现在源文件中,是编译器自动生成的
     */
    int ACC_SYNTHETIC = 0x1000;
    /**
     * 该参数是在源文件中隐式定义的, 典型场景: this
     */
    int ACC_MANDATED = 0x8000;
}
