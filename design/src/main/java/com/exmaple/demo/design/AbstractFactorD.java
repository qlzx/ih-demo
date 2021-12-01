package com.exmaple.demo.design;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc 相较于工厂方法模式，抽象工厂会处理一组多个产品
 */
public class AbstractFactorD {

    static interface ItemA {

    }

    static interface ItemB{

    }

    static class FooItemA implements ItemA{

    }

    static class FooItemB implements ItemB{

    }

    static class BarItemA implements ItemA{

    }

    static class BarItemB implements ItemB{

    }

    static interface AbstractFactor{
        ItemA createA();

        ItemB createB();
    }

    static class FooFactor implements AbstractFactor{

        @Override
        public ItemA createA() {
            return new FooItemA();
        }

        @Override
        public ItemB createB() {
            return new FooItemB();
        }
    }

    static class BarFactor implements AbstractFactor{

        @Override
        public ItemA createA() {
            return new BarItemA();
        }

        @Override
        public ItemB createB() {
            return new BarItemB();
        }
    }

    public static void main(String[] args) {
        FooFactor fooFactor = new FooFactor();

        BarFactor barFactor = new BarFactor();

    }

}
