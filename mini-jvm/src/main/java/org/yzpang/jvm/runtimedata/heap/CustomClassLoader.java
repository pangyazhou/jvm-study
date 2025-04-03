package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassFile;
import org.yzpang.jvm.classpath.*;
import org.yzpang.jvm.runtimedata.CustomSlots;
import org.yzpang.jvm.runtimedata.heap.constantpool.DoubleConstant;
import org.yzpang.jvm.runtimedata.heap.constantpool.FloatConstant;
import org.yzpang.jvm.runtimedata.heap.constantpool.IntegerConstant;
import org.yzpang.jvm.runtimedata.heap.constantpool.LongConstant;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/24 下午3:56
 **/
@Data
public class CustomClassLoader {
    private CustomClasspath classpath;

    private Map<String, CustomClass> classMap;


    public CustomClassLoader(CustomClasspath classpath) {
        this.classpath = classpath;
        this.classMap = new HashMap<>();
    }

    /**
     * 加载类
     * 先从方法区缓存查找, 没有再根据类名加载
     * @param name 类的完全限定名 java.lang.String
     * @return 类对象
     */
    public CustomClass loadClass(String name) throws Exception {
        if (classMap.containsKey(name)) {
            return classMap.get(name);
        }
        return loadNonArrayClass(name);
    }

    /**
     * 加载非数组类
     * @param name 类名
     * @return CustomClass 对象
     * @throws IOException e
     */
    private CustomClass loadNonArrayClass(String name) throws Exception {
        byte[] bytes = readClass(name);
        CustomClass clazz = defineClass(bytes);
        link(clazz);
        return clazz;
    }

    /**
     * 根据类名读取类文件的二进制流
     * @param name 类名 java/lang/String
     * @return byte[]
     * @throws IOException e
     */
    private byte[] readClass(String name) throws IOException {
        return this.classpath.readClass(name);
    }

    private CustomClass defineClass(byte[] bytes) throws Exception {
        CustomClass clazz = parseClass(bytes);
        clazz.setClassloader(this);
        resolveSuperClass(clazz);
        resolveInterfaces(clazz);
        this.classMap.put(clazz.getName(), clazz);
        return clazz;
    }

    /**
     * 将Class文件数据转换为CustomClass结构体
     * @param bytes 二进制流
     * @return Class结构体
     */
    private CustomClass parseClass(byte[] bytes) throws Exception {
        ClassFile classFile = ClassFile.parse(bytes);
        CustomClass clazz = new CustomClass(classFile);
        return clazz;
    }

    /**
     * 解析父类
     * @param clazz 当前类结构
     */
    private void resolveSuperClass(CustomClass clazz) throws Exception {
        if (!Objects.equals(clazz.getName(), "java/lang/Object")) {
            clazz.setSuperClass(clazz.getClassloader().loadClass(clazz.getSuperClassName()));
        }
    }

    /**
     * 加载类的接口列表
     * @param clazz 当前类结构
     */
    private void resolveInterfaces(CustomClass clazz) throws Exception {
        String[] interfaceNames = clazz.getInterfaceNames();
        int interfacesCount = interfaceNames.length;
        CustomClass[] interfaces = new CustomClass[interfacesCount];
        clazz.setInterfaces(interfaces);
        if (interfacesCount > 0) {
            for (int i = 0; i < interfacesCount; i++) {
                String interfaceName = interfaceNames[i];
                CustomClass loadClass = clazz.getClassloader().loadClass(interfaceName);
                interfaces[i] = loadClass;
            }
        }
    }

    /**
     * 类链接
     */
    private void link(CustomClass clazz) {
        verify(clazz);
        prepare(clazz);
    }

    /**
     * 验证阶段
     */
    private void verify(CustomClass clazz) {
        // todo
    }

    /**
     * 准备阶段
     */
    private void prepare(CustomClass clazz) {
        calcInstanceFieldSlotIds(clazz);
        calcStaticFieldSlotIds(clazz);
        allocAndInitStaticVars(clazz);
    }

    /**
     * 计算实例变量的slotId
     */
    private void calcInstanceFieldSlotIds(CustomClass clazz) {
        int slotId = 0;
        // 先计算父类的实例变量slotId
        if (clazz.getSuperClass() != null){
            slotId = clazz.getSuperClass().getInstanceSlotCount();
        }
        for (CustomField field : clazz.getFields()) {
            if (!field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                // long 和 double 占两个slot
                if (field.isLongOrDouble()){
                    slotId++;
                }
            }
        }
        clazz.setInstanceSlotCount(slotId);
    }

    /**
     * 计算类变量的slotId
     */
    private void calcStaticFieldSlotIds(CustomClass clazz) {
        int slotId = 0;
        for (CustomField field : clazz.getFields()) {
            if (field.isStatic()) {
                field.setSlotId(slotId);
                slotId++;
                if (field.isLongOrDouble()){
                    slotId++;
                }
            }
        }
        clazz.setStaticSlotCount(slotId);
    }

    /**
     * 初始化类变量并分配空间
     */
    private void allocAndInitStaticVars(CustomClass clazz) {
        clazz.setStaticVariables(new CustomSlots(clazz.getStaticSlotCount()));
        for (CustomField field : clazz.getFields()) {
            // static final 值在常量池中
            if (field.isStatic() && field.isFinal()) {
                initStaticFinalVars(clazz, field);
            }
        }
    }

    /**
     * 将常量池中的 static final 值填充到分配的类变量空间中
     */
    private void initStaticFinalVars(CustomClass clazz, CustomField field) {
        CustomSlots staticVariables = clazz.getStaticVariables();
        CustomConstantPool constantPool = clazz.getConstantPool();
        int constantValueIndex = field.getConstantValueIndex();
        int slotId = field.getSlotId();

        if (constantValueIndex > 0) {
            switch (field.getDescriptor()){
                case "Z":
                case "B":
                case "C":
                case "S":
                case "I":
                    IntegerConstant integerConstant = (IntegerConstant)constantPool.getConstant(constantValueIndex);
                    staticVariables.setInt(slotId, integerConstant.get());
                    break;
                case "J":
                    LongConstant longConstant = (LongConstant)constantPool.getConstant(constantValueIndex);
                    staticVariables.setLong(slotId, longConstant.get());
                    break;
                case "F":
                    FloatConstant floatConstant = (FloatConstant)constantPool.getConstant(constantValueIndex);
                    staticVariables.setFloat(slotId, floatConstant.get());
                    break;
                case "D":
                    DoubleConstant doubleConstant = (DoubleConstant)constantPool.getConstant(constantValueIndex);
                    staticVariables.setDouble(slotId, doubleConstant.get());
                    break;
                case "Ljava/lang/String;":
                    break;
            }
        }
    }

}
