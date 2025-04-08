package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: 子类
 * Date: 2025/4/2 下午4:58
 **/
public class Dog extends Animal {

    private void fuck(){
        System.out.println("dog fuck...");
    }

    public void run(){
        System.out.println("Dog run");
        super.sleep();
    }

    @Override
    public void eat() {
        System.out.println("xxx");
    }

    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.eat();
        dog.run();
        dog.fuck();
        Class<Dog> dogClass = Dog.class;
        Class<? extends Dog> aClass = dog.getClass();
    }
}
