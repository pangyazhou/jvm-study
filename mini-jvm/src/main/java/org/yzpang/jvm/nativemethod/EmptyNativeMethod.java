package org.yzpang.jvm.nativemethod;

import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/4/8 上午8:43
 **/
public class EmptyNativeMethod implements NativeMethod {
    @Override
    public void invokeNativeMethod(CustomFrame frame) {
        // do nothing
    }
}
