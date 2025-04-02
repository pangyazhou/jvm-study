package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: 子类
 * Date: 2025/4/2 下午4:58
 **/
public class Dog extends Animal {
    public void run(){
        System.out.println("Dog run");
    }

    @Override
    public void eat() {
        super.eat();
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat();
        dog.sleep();
        dog.run();
    }

}
