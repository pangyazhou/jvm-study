package org.yzpang.jvm.runtimedata.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: yzpang
 * Desc: 类名解析工具
 * Date: 2025/4/7 上午9:14
 **/
public class ClassNameHelper {
    private static final Map<String, String> primitiveTypes = new HashMap<>();
    {
        primitiveTypes.put("boolean", "Z");
        primitiveTypes.put("byte", "B");
        primitiveTypes.put("char", "C");
        primitiveTypes.put("short", "S");
        primitiveTypes.put("int", "I");
        primitiveTypes.put("long", "J");
        primitiveTypes.put("float", "F");
        primitiveTypes.put("double", "D");
        primitiveTypes.put("void", "V");
    }

    /**
     * 解析数组类名
     * @param className 常量池中类名
     * @return 解析后的数组类名
     */
    public static String getArrayClassName(String className) {
        return "[" + toDescriptor(className);
    }

    public static String toDescriptor(String className) {
        // 数组
        if (className.startsWith("[")) {
            return className;
        }
        // 基本类型
        String type = primitiveTypes.get(className);
        if (type != null) {
            return type;
        }
        // 常规类
        return "L" + className + ";";
    }

    /**
     * 根据数组类名解析数组元素类名
     * @param className 数组类名
     * @return 数组元素类名
     */
    public static String getComponentClassName(String className) throws Exception {
        if (className.startsWith("[")) {
            return toClassName(className.substring(1));
        }
        throw new Exception("不是数组");
    }

    public static String toClassName(String descriptor) throws Exception {
        // 数组
        if (descriptor.startsWith("[")) {
            return descriptor;
        }
        // 引用
        if (descriptor.startsWith("L")) {
            return descriptor.substring(1, descriptor.length() - 1);
        }
        // 基本类型
        for (Map.Entry<String, String> entry : primitiveTypes.entrySet()) {
            if (descriptor.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        // 非法类型
        throw new Exception("非法的描述符: " + descriptor);
    }
}
