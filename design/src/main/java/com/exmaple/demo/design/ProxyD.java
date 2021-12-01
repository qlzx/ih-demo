package com.exmaple.demo.design;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author lh0
 * @date 2021/12/1
 * @desc
 */
public class ProxyD {

    // jdk
    interface ServiceI{
        void say();
    }

    static class MyProxy implements InvocationHandler{
        private final ServiceI target;

        MyProxy(ServiceI target) {this.target = target;}

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println(method.getName() + "-invoke");
            return method.invoke(target, args);
        }
    }

    static class ProxyFactory{
        public static ServiceI createProxy(ServiceI target){
            return (ServiceI) Proxy.newProxyInstance(ServiceI.class.getClassLoader(), new Class[] {ServiceI.class}, new MyProxy(target));
        }
    }

    public static void main(String[] args) {
        ProxyFactory.createProxy(new ServiceI() {
            @Override
            public void say() {
                System.out.println("target running");
            }
        }).say();
    }
}
