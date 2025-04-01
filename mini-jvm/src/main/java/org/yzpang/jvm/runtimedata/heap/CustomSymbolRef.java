package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;

@Data
public class CustomSymbolRef {
    protected CustomConstantPool constantPool;
    protected String className;
    protected CustomClass clazz;

    public CustomClass resolvedClass() throws Exception {
        if(clazz == null){
            resolveClassRef();
        }
        return clazz;
    }

    /**
     * 解析类符号引用
     */
    private void resolveClassRef() throws Exception {
        CustomClass d = this.constantPool.getClazz();
        CustomClass c = d.getClassloader().loadClass(this.className);
        if (c.isAccessibleTo(d)){
            this.clazz = c;
        }
        throw new IllegalAccessException();
    }
}
