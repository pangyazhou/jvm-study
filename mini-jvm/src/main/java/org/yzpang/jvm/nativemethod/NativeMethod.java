package org.yzpang.jvm.nativemethod;

import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: 本地方法接口
 * Date: 2025/4/8 上午8:34
 **/
public interface NativeMethod {

    // 执行本地方法
    void invokeNativeMethod(CustomFrame frame) throws Exception;
}
