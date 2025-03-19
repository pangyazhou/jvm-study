package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc:  类型注解属性
 *
 * Date: 2025/3/19 下午3:21
 **/
public class TypeAnnotation {
    /**
     * u1
     * targetType值的含义
     * 值	        目标的种类		                                                      target_info项
     * 0x00	    泛型类或接口的类型参数声明		                                            type_parameter_target
     * 0x01	    泛型方法或构造器的类型参数声明		                                        type_parameter_target
     * 0x10	    类或接口声明(也包括匿名类声明中的直接超类声明)中的extends子句里的类型，或者接口声明中的implements子句里的类型		supertype_target
     * 0x11	    在声明泛型类或接口的类型参数界限时，所用到的类型		                            type_parameter_bound_target
     * 0x12	    在声明泛型方法或构造器的类型参数界限时，所用到的类型		                        type_parameter_bound_target
     * 0x13	    字段声明中的类型		                                                    empty_target
     * 0x14	    方法的返回值类型，或者新构建好的对象的类型		                                empty_target
     * 0x15	    方法或构造器的接收者(receiver)类型		                                    empty_target
     * 0x16	    方法、构造器或lambda表达式的形式参数声明中的类型		                        formal_parameter_target
     * 0x17	    方法或构造器throws子句中的类型		                                        throws_target
     * 0x40		局部变量声明中的类型		                                                localvar_target
     * 0x41		资源变量声明中的类型		                                                localvar_target
     * 0x42		异常参数声明中的类型		                                                catch_target
     * 0x43		instanceof表达式中的类型		                                            offset_target
     * 0x44		new表达式中的类型		                                                    offset_target
     * 0x45		以：new的形式来表述的方法引用表达式中的类型		                            offset_target
     * 0x46		以：:Identifier的形式来表述的方法引用表达式中的类型		                    offset_target
     * 0x47		类型转换表达式中的类型		                                                type_argument_target
     * 0x48		new表达式中的泛型构造器或显式构造器调用语句中的类型参数	                        type_argument_target
     * 0x49	    方法调用表达式中的泛型方法的类型参数	                                        type_argument_target
     * 0x4A		在以：:new的形式来表述的方法引用表达式中，泛型构造器的类型参数	                    type_argument_target
     * 0x4B		在以：:Identifer的形式来表述的方法引用表达式中，泛型方法的类型参数	            type_argument_target
     *
     * targetType值所在的属性应该出现的位置
     *  值		                目标的种类			                                        位置
     *   0x00		    泛型类或接口的类型参数声明			                                    ClassFile
     *   0x01		    泛型方法或构造器的类型参数声明			                                method_info
     *   0x10		    类或接口声明中的extends子句里的类型，或者接口声明中的implements子句里的类型    ClassFile
     *   0x11		    在声明泛型类或接口的类型参数界限时，所用到的类型			                    ClassFile
     *   0x12		    在声明泛型方法或构造器的类型参数界限时，所用到的类型			                method_info
     *   0x13		    字段声明中的类型			                                            field_info
     *   0x14		    方法或构造器的返回值类型			                                    method_info
     *   0x15		    方法或构造器的接收者类型			                                    method_info
     *   0x16		    方法、构造器或lambda表达式的形式参数声明中的类型			                method_info
     *   0x17		    方法或构造器throws子句中的类型			                                method_info
     *   0x40-0x4B		局部变量声明、资源变量声明、异常参数声明及表达式中所用到的类型			        Code
     */
    private short targetType;
    /**
     * 精确指出本注解添加在声明或表达式中的哪个类型上面
     */
    private Object typeParameterTarget;
    private Object supertypeTarget;
    private Object typeParameterBoundTarget;
    private Object emptyTarget;
    private Object methodFormalParameterTarget;
    private Object throwsTarget;
    private Object localvarTarget;
    private Object catchTarget;
    private Object offsetTarget;
    private Object typeArgumentTarget;

    /**
     * 精确指出本条注解添加在由target_info所指出的类型的那一部分上面
     */
    private Object typePath;
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_info结构
     * 表示一个字段描述符, 用来表示一个注解类型
     */
    private short typeIndex;
    /**
     * u2
     */
    private short numElementValuePairs;
    private ElementValuePairs[] elementValuePairs;

}
