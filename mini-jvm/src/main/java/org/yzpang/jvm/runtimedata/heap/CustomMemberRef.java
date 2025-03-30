package org.yzpang.jvm.runtimedata.heap;

/**
 * 类成员引用父类
 */
public class CustomMemberRef extends CustomSymbolRef{
    protected CustomSymbolRef symbolRef;
    protected String name;
    protected String descriptor;

}
