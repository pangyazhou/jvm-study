package org.yzpang.jvm.classloader;

/**
 * Author: yzpang
 * Desc: Class文件验证
 * Date: 2025/3/24 上午10:53
 **/
public class ClassVerify {

    /**
     * 文件格式验证
     * 验证字节流是否符合Class文件个的规范
     * @param clazz
     * @return
     */
    public boolean verifyClassFileFormat(Clazz clazz){
        return true;
    }

    /**
     * 元数据验证
     * 字节码描述的信息进行语义分析
     * @param clazz
     * @return
     */
    public boolean verifyMetadata(Clazz clazz){
        return true;
    }

    /**
     * 字节码验证
     * 通过数据流分析和控制流分析, 确定程序语义是合法的,符合逻辑的.
     * @param clazz
     * @return
     */
    public boolean verifyBytecode(Clazz clazz){
        return true;
    }

    /**
     * 符号引用验证
     * 验证该类是否缺少依赖的外部类/方法/字段等资源
     * @param clazz
     * @return
     */
    public boolean verifySymbolicReference(Clazz clazz){
        return true;
    }

}
