package com.exmaple.demo.design;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc
 */
public class SingletonD {

    static class DCL{
        private static volatile DCL instance;

        private DCL(){

        }

        public DCL getInstance(){
            if(instance==null){
                synchronized (DCL.class){
                    if(instance==null){
                        instance = new DCL();
                    }
                }
            }
            return instance;
        }
    }

    static class Singleton{
        private static Singleton singleton = new Singleton();

        private Singleton(){

        }

        public Singleton getInstance(){
            return singleton;
        }
    }

    static class InnerClassSingleton{

        private InnerClassSingleton(){

        }

        public InnerClassSingleton getInstance(){
            return InnerCreator.instance;
        }

        static class InnerCreator{
            public static InnerClassSingleton instance = new InnerClassSingleton();
        }
    }


}
