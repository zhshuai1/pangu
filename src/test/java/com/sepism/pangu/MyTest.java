package com.sepism.pangu;

import org.hibernate.SessionFactory;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class MyTest {
    @Test
    public void test() {
        new Animal().speak();
        MyHandler myHandler = new MyHandler(new Animal());
        myHandler.getSpeaker().speak();
        myHandler.getEater().eat();
        myHandler.getEater().eat2("zhangsh");
    }

    interface Speaker {
        void speak();
    }

    interface Eater {
        void eat();

        void eat2(String name);
    }

    class Animal implements Speaker, Eater {
        public void speak() {
            System.out.println("This is the original speak method!");
        }

        @Override
        public void eat() {
            System.out.println("This is the original eat method!");
        }

        @Override
        public void eat2(String name) {
            System.out.println("What I know is to eat, eat and eat... " + name);
        }
    }

    class MyHandler implements InvocationHandler {
        private Object object;

        public MyHandler(Animal animal) {
            object = Proxy.newProxyInstance(animal.getClass().getClassLoader(), animal.getClass()
                            .getInterfaces(),
                    this);
            //new SessionFactory().getCurrentSession().fin
        }


        public Speaker getSpeaker() {
            Speaker speaker = (Speaker) object;
            System.out.println(speaker.getClass().getCanonicalName());
            return speaker;
        }

        public Eater getEater() {
            return (Eater) object;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            switch (method.getName()) {
                case "eat":
                    System.out.println("I'm the proxied eater!");
                    break;
                case "speak":
                    System.out.println("I'm the proxied speaker");
                    break;
                default:
                    System.out.println("No override found, will run the original method");
                    //method.invoke(object, args);
                    break;
            }
            return null;
        }
    }

}
