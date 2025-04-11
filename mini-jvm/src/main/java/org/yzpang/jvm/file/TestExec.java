package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: 异常测试
 * Date: 2025/4/11 上午8:39
 **/
public class TestExec extends Throwable{
    public static void main(String[] args) {
        System.out.println("hello,world");
    }

    public void cantBeZero(int i) throws TestExec {
        if (i == 0) {
            throw new TestExec();
        }
    }

    public void catchOne() {
        try{
            tryItOut();
        } catch (Exception e){
            handleExc(e);
        }
    }

    private void tryItOut(){

    }

    private void handleExc(Exception e) {

    }
}
