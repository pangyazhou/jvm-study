package org.yzpang.jvm.runtimedata.heap;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: yzpang
 * Desc: 字符串池
 * Date: 2025/4/7 下午3:52
 **/
public class StringPool {
    // 字符串池结构
    private static final Map<String, CustomObject> internedStrings = new HashMap<>();

    /**
     * 从字符串池中获取字符串
     * @param classLoader 类加载器
     * @param name 字符串名称
     * @return String Object
     */
    public static CustomObject jString(CustomClassLoader classLoader, String name) throws Exception {
        CustomObject strObj = internedStrings.get(name);
        // 缓存中读取
        if (strObj != null) {
            return strObj;
        }
        char[] chars = stringToUtf16(name);
        CustomArrayObject jChars = new CustomArrayObject(classLoader.loadClass("[C"), chars);
        CustomObject jStr = classLoader.loadClass("java/lang/String").newObject();
        jStr.setRefVar("value", "[C", jChars);
        internedStrings.put(name, jStr);
        return jStr;
    }

    private static char[] stringToUtf16(String str) {
        return str.toCharArray();
    }

    private static String utf16ToString(char[] chars) {
        return new String(chars);
    }

    /**
     * 根据String的对象引用获取字符串
     * @param jStr String 对象引用
     * @return String字符串
     */
    public static String goString(CustomObject jStr) {
        CustomArrayObject charArr = (CustomArrayObject) jStr.getRefVar("value", "[C");
        return utf16ToString(charArr.getChars());
    }
}
