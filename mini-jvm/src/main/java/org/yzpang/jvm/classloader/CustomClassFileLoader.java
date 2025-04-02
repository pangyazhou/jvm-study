package org.yzpang.jvm.classloader;

import org.yzpang.jvm.classfile.ClassFile;
import org.yzpang.jvm.classfile.attribute.Annotations;
import org.yzpang.jvm.classfile.attribute.ElementValue;
import org.yzpang.jvm.classfile.attribute.ElementValuePairs;
import org.yzpang.jvm.classfile.attribute.EnumConstValue;
import org.yzpang.jvm.constant.JvmConstants;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 类文件加载
 * Date: 2025/3/18 上午8:46
 **/
public class CustomClassFileLoader {

    /**
     * 解析注解项
     * @param bytes 二进制流
     * @param index 当前索引
     * @return 注解对象
     */
    private Annotations parseAnnotations(byte[] bytes, int[] index){
        int currentIndex = index[0];
        Annotations annotation = new Annotations();
        annotation.setTypeIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
        currentIndex += 2;
        int numElementValuePairs = ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort();
        annotation.setNumElementValuePairs(numElementValuePairs);
        currentIndex += 2;
        ElementValuePairs[] elementValuePairs = new ElementValuePairs[numElementValuePairs];
        for (int i = 0; i < numElementValuePairs; i++){
            ElementValuePairs elementValuePair = new ElementValuePairs();
            // 注解字段名称
            elementValuePair.setElementNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
            currentIndex += 2;
            // 注解字段值
            index[0] = currentIndex;
            ElementValue elementValue = parseElementValue(bytes, index);
            elementValuePair.setElementValue(elementValue);
            currentIndex = index[0];
            elementValuePairs[i] = elementValuePair;
        }
        annotation.setElementValuePairs(elementValuePairs);
        index[0] = currentIndex;
        return annotation;
    }

    private ElementValue parseElementValue(byte[] bytes, int[] index){
        int currentIndex = index[0];
        ElementValue elementValue = new ElementValue();
        char tag = (char)bytes[currentIndex];
        elementValue.setTag(tag);
        currentIndex += 1;
        switch (tag){
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z':
            case 's':
                elementValue.setConstValueIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                break;
            case 'c':
                elementValue.setClassInfoIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                break;
            case 'e':
                EnumConstValue enumConstValue = new EnumConstValue();
                enumConstValue.setTypeNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                enumConstValue.setConstNameIndex(ByteBuffer.wrap(Arrays.copyOfRange(bytes, currentIndex, currentIndex + 2)).getShort());
                currentIndex += 2;
                elementValue.setEnumConstValue(enumConstValue);
                break;
            case '@':
                // todo
                break;
            case '[':
                // todo
                break;
        }
        index[0] = currentIndex;
        return elementValue;
    }

    private boolean validate(ClassFile classFile) throws Exception {
        if (classFile.getMagic() != JvmConstants.MAGIC_NUMBER){
            throw new Exception("魔数不匹配,此文件非Class文件格式");
        }
        if (classFile.getMajorVersion() > JvmConstants.MAJOR_VERSION){
            throw new Exception("JDK版本" + JvmConstants.MAJOR_VERSION + "不支持此版本:" + classFile.getMajorVersion());
        }
        return true;
    }
}
