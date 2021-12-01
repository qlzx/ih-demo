package com.exmaple.demo.design;

/**
 * @author lh0
 * @date 2021/11/30
 * @desc 使用组合的方式对原有产品功能进行扩展
 */
public class DecoratorD {

    interface Car{
        void run();
    }

    static class Baoma implements Car {
        @Override
        public void run() {
            System.out.println("baoma running");
        }
    }

    static class Benchi implements Car{

        @Override
        public void run() {
            System.out.println("benchi running");
        }
    }

    // 当需要对car添加新的业务功能时，比如自动驾驶

    static class CarDecorator implements Car{

        protected Car decorator;
        public CarDecorator(Car car){
            this.decorator = car;
        }

        @Override
        public void run() {
            decorator.run();
        }
    }

    static class AutoDriverCar extends CarDecorator{


        public AutoDriverCar(Car car) {
            super(car);
        }

        @Override
        public void run() {
            decorator.run();
            autoRun();
        }

        private void autoRun(){
            System.out.println("auto running");
        }
    }

    static class AIDriverCar extends CarDecorator{


        public AIDriverCar(Car car) {
            super(car);
        }

        @Override
        public void run() {
            decorator.run();
            ai();
        }

        private void ai(){
            System.out.println("AI");
        }
    }

    public static void main(String[] args) {

        Car benz = new Benchi();
        AIDriverCar benzAiCar = new AIDriverCar(benz);
        AutoDriverCar autoDriverCar = new AutoDriverCar(benzAiCar);

        autoDriverCar.run();

    }

}
