package com.exmaple.demo.design;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc 定义产品构造方式，方便后续产品扩展，新增一个产品只需要新增一个产品工厂类，而不需要修改原代码
 */
public class FactorMethodD {

    interface Product{

    }

    static class ProductA implements Product{

    }

    static class ProductB implements Product{

    }

    interface Factor{
        Product create();
    }

    static class FactorA implements Factor{
        @Override
        public Product create() {
            return new ProductA();
        }
    }

    static class FactorB implements Factor{

        @Override
        public Product create() {
            return new ProductB();
        }
    }

    public static void main(String[] args) {
        Product productA = new FactorA().create();

        Product productB = new FactorB().create();
    }
}
