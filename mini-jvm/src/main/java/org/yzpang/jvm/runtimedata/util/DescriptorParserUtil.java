package org.yzpang.jvm.runtimedata.util;

import org.yzpang.jvm.runtimedata.heap.CustomMethodDescriptor;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yzpang
 * Desc: 描述符解析工具类
 * Date: 2025/4/2 下午3:37
 **/
public class DescriptorParserUtil {

    /**
     * 解析方法描述符
     * @param descriptor 方法描述符  (IJ)Ljava.lang.String;  ([CII[CIII)[Ljava/lang/String;
     * @return 方法描述符结构体对象
     */
    public static CustomMethodDescriptor parseMethodDescriptor(String descriptor) {
        String returnType = "";
        List<String> parameterTypes = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < descriptor.length(); i++) {
            char c = descriptor.charAt(i);
            if (c == '(') {
            } else if (c == ')') {
                returnType = descriptor.substring(i + 1);
                break;
            } else if (c == '[') {
                sb.append(c);
            } else if (c == 'L') {
                sb.append(c);
                while (c != ';') {
                    c = descriptor.charAt(++i);
                    sb.append(c);
                }
                parameterTypes.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
                parameterTypes.add(sb.toString());
                sb.setLength(0);
            }
        }
        return new CustomMethodDescriptor(parameterTypes.toArray(new String[0]), returnType);
    }
}
