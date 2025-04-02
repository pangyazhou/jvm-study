package org.yzpang.jvm.classfile.util;

import org.yzpang.jvm.constant.ClassAccessConstants;
import org.yzpang.jvm.constant.FieldAccessConstants;
import org.yzpang.jvm.constant.MethodAccessConstants;

/**
 * Author) != 0) { yzpang
 * Desc) != 0) {
 * Date) != 0) { 2025/3/20 下午1) != 0) {58
 **/
public class ClassFileUtil {

    /**
     * 解析字段的修饰符
     * @param accessFlags 修饰值
     * @return 修饰符字符串
     */
    public static String getFieldAccessFlags(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if ((accessFlags & FieldAccessConstants.ACC_PUBLIC) != 0) {
            sb.append("ACC_PUBLIC, ");
           }
        if ((accessFlags & FieldAccessConstants.ACC_PRIVATE) != 0) {
            sb.append("ACC_PRIVATE, ");
           }
        if ((accessFlags & FieldAccessConstants.ACC_PROTECTED) != 0) {
            sb.append("ACC_PROTECTED, ");
           }
        if ((accessFlags & FieldAccessConstants.ACC_STATIC) != 0) {
            sb.append("ACC_STATIC, ");
           }
        if ((accessFlags & FieldAccessConstants.ACC_FINAL) != 0) {
            sb.append("ACC_FINAL, ");
           }
        if ((accessFlags & FieldAccessConstants.ACC_VOLATILE) != 0) {
            sb.append("ACC_VOLATILE, ");
           }
        if ((accessFlags & FieldAccessConstants.ACC_TRANSIENT) != 0) {
            sb.append("ACC_TRANSIENT, ");
           }
        if ((accessFlags & FieldAccessConstants.ACC_SYNTHETIC) != 0) {
            sb.append("ACC_SYNTHETIC, ");
           }
        if ((accessFlags & FieldAccessConstants.ACC_ENUM) != 0) {
            sb.append("ACC_ENUM, ");
           }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 解析修饰符  public static final
     * @param accessFlags 访问标志
     * @return 修饰符字符串
     */
    public static String parseFieldAccessFlags(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if ((accessFlags & FieldAccessConstants.ACC_PUBLIC) != 0) {
            sb.append("public ");
        }
        if ((accessFlags & FieldAccessConstants.ACC_PRIVATE) != 0) {
            sb.append("private ");
        }
        if ((accessFlags & FieldAccessConstants.ACC_PROTECTED) != 0) {
            sb.append("protected ");
        }
        if ((accessFlags & FieldAccessConstants.ACC_STATIC) != 0) {
            sb.append("static ");
        }
        if ((accessFlags & FieldAccessConstants.ACC_FINAL) != 0) {
            sb.append("final ");
        }

        if ((accessFlags & FieldAccessConstants.ACC_VOLATILE) != 0) {
            sb.append("volatile ");
        }
        if ((accessFlags & FieldAccessConstants.ACC_TRANSIENT) != 0) {
            sb.append("transient ");
        }
        if ((accessFlags & FieldAccessConstants.ACC_SYNTHETIC) != 0) {
            sb.append("synthetic ");
        }
        if ((accessFlags & FieldAccessConstants.ACC_ENUM) != 0) {
            sb.append("enum ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 解析描述符  [[Ljava.lang.String; --> java.lang.String[][]
     * @param descriptor 描述符字符串
     * @return 解析后的描述符
     */
    public static String parseFieldDescriptor(String descriptor) {
        StringBuilder sb = new StringBuilder();
        StringBuilder arraySb = new StringBuilder();
        for (int i = 0; i < descriptor.length(); i++) {
            char c = descriptor.charAt(i);
            switch (c) {
                case 'B':
                    sb.append("byte");
                    break;
                case 'C':
                    sb.append("char");
                    break;
                case 'D':
                    sb.append("double");
                    break;
                case 'F':
                    sb.append("float");
                    break;
                case 'I':
                    sb.append("int");
                    break;
                case 'J':
                    sb.append("long");
                    break;
                case 'S':
                    sb.append("short");
                    break;
                case 'Z':
                    sb.append("boolean");
                    break;
                case 'V':
                    sb.append("void");
                    break;
                case 'L':
                    int endIndex = descriptor.indexOf(';', i + 1);
                    sb.append(descriptor.replaceAll("/", "."), i + 1, endIndex);
                    i = endIndex;
                    break;
                case '[':
                    arraySb.append("[]");
                    continue;
            }
            if (arraySb.length() > 0) {
                sb.append(arraySb);
                arraySb = new StringBuilder();
            }
            sb.append(", ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 解析方法的修饰符
     * @param accessFlags 修饰值
     * @return 修饰符字符串
     */
    public static String getMethodAccessFlags(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if ((accessFlags & MethodAccessConstants.ACC_PUBLIC) != 0) {
            sb.append("ACC_PUBLIC, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_PRIVATE) != 0) {
            sb.append("ACC_PRIVATE, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_PROTECTED) != 0) {
            sb.append("ACC_PROTECTED, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_STATIC) != 0) {
            sb.append("ACC_STATIC, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_FINAL) != 0) {
            sb.append("ACC_FINAL, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_SYNCHRONIZED) != 0) {
            sb.append("ACC_SYNCHRONIZED, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_BRIDGE) != 0) {
            sb.append("ACC_BRIDGE, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_VARARGS) != 0) {
            sb.append("ACC_VARARGS, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_NATIVE) != 0) {
            sb.append("ACC_NATIVE, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_ABSTRACT) != 0) {
            sb.append("ACC_ABSTRACT, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_STRICT) != 0) {
            sb.append("ACC_STRICT, ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_SYNTHETIC) != 0) {
            sb.append("ACC_SYNTHETIC, ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 解析修饰符  public static final
     * @param accessFlags 访问标志
     * @return 修饰符字符串
     */
    public static String parseMethodAccessFlags(int accessFlags){
        StringBuilder sb = new StringBuilder();
        if ((accessFlags & MethodAccessConstants.ACC_PUBLIC) != 0) {
            sb.append("public ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_PRIVATE) != 0) {
            sb.append("private ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_PROTECTED) != 0) {
            sb.append("protected ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_STATIC) != 0) {
            sb.append("static ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_FINAL) != 0) {
            sb.append("final ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_SYNCHRONIZED) != 0) {
            sb.append("synchronized ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_BRIDGE) != 0) {
            sb.append("bridge ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_VARARGS) != 0) {
            sb.append("varargs ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_NATIVE) != 0) {
            sb.append("native ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_ABSTRACT) != 0) {
            sb.append("abstract ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_STRICT) != 0) {
            sb.append("strict ");
        }
        if ((accessFlags & MethodAccessConstants.ACC_SYNTHETIC) != 0) {
            sb.append("synthetic ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 解析方法参数列表与返回类型  ([CII[CIII)I -> int (char[], int, int, char[], int, int, int)
     * @param descriptor 方法描述符
     * @return 解析后的描述符 [parametersDescriptor, returnDescriptor]
     */
    public static String[] parseMethodDescriptor(String descriptor) {
        int index = descriptor.indexOf(")");
        String parametersDescriptor = parseFieldDescriptor(descriptor.substring(1, index));
        String returnDescriptor = parseFieldDescriptor(descriptor.substring(index + 1));
        return new String[]{parametersDescriptor, returnDescriptor};
    }

    /**
     * 解析类的修饰符
     * @param accessFlags 修饰值
     * @return 修饰符字符串
     */
    public static String getClassAccessFlags(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if ((accessFlags & ClassAccessConstants.ACC_PUBLIC) != 0) {
            sb.append("ACC_PUBLIC, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_PRIVATE) != 0) {
            sb.append("ACC_PRIVATE, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_PROTECTED) != 0) {
            sb.append("ACC_PROTECTED, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_STATIC) != 0) {
            sb.append("ACC_STATIC, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_FINAL) != 0) {
            sb.append("ACC_FINAL, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_SUPER) != 0) {
            sb.append("ACC_SUPER, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_INTERFACE) != 0) {
            sb.append("ACC_INTERFACE, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_ABSTRACT) != 0) {
            sb.append("ACC_ABSTRACT, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_SYNTHETIC) != 0) {
            sb.append("ACC_SYNTHETIC, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_ANNOTATION) != 0) {
            sb.append("ACC_ANNOTATION, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_ENUM) != 0) {
            sb.append("ACC_ENUM, ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_MODULE) != 0) {
            sb.append("ACC_MODULE, ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 解析类的修饰符  public static final
     * @param accessFlags 访问标志
     * @return 修饰符字符串
     */
    public static String parseClassAccessFlags(int accessFlags) {
        StringBuilder sb = new StringBuilder();
        if ((accessFlags & ClassAccessConstants.ACC_PUBLIC) != 0) {
            sb.append("public ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_PRIVATE) != 0) {
            sb.append("private ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_PROTECTED) != 0) {
            sb.append("protected ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_STATIC) != 0) {
            sb.append("static ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_FINAL) != 0) {
            sb.append("final ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_SUPER) != 0) {
            sb.append("super ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_INTERFACE) != 0) {
            sb.append("interface ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_ABSTRACT) != 0) {
            sb.append("abstract ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_SYNTHETIC) != 0) {
            sb.append("synthetic ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_ANNOTATION) != 0) {
            sb.append("annotation ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_ENUM) != 0) {
            sb.append("enum ");
        }
        if ((accessFlags & ClassAccessConstants.ACC_MODULE) != 0) {
            sb.append("module ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 根据方法句柄类型解析字节码行为
     * @param kind
     * @return
     */
    public static String getByteCodeBehavior(int kind){
        switch (kind){
            case 1:
                return "getfield";
            case 2:
                return "getstatic";
            case 3:
                return "putfield";
            case 4:
                return "putstatic";
            case 5:
                return "invokevirtual";
            case 6:
                return "invokestatic";
            case 7:
                return "invokespecial";
            case 8:
                return "newinvokespecial";
            case 9:
                return "invokeinterface";
        }
        return "";
    }

    /**
     * 根据方法描述符计算参数个数
     * @param descriptor 方法描述符 (IJ)Ljava.lang.String;  ([CII[CIII)[Ljava/lang/String;
     * @return 参数个数
     */
    public static int calculateArgs(String descriptor){
        String argsDescriptor = descriptor.substring(descriptor.indexOf("(") + 1, descriptor.indexOf(")"));
        int count = 0;
        for (int i = 0; i < argsDescriptor.length(); i++) {
            char ch = argsDescriptor.charAt(i);
            if (ch == 'L') {
                count++;
                while (ch != ';'){
                    ch = argsDescriptor.charAt(++i);
                }
            } else if (ch != '[') {
                count++;
            }
        }
        return count;
    }
}
