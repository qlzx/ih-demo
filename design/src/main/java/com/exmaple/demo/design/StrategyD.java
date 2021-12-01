package com.exmaple.demo.design;

/**
 * @author lh0
 * @date 2021/12/1
 * @desc
 */
public class StrategyD {
    interface StrategyI{
        void goHome();
    }

    static class Simple implements StrategyI{

        @Override
        public void goHome() {
            System.out.println("just walk");
        }
    }

    static class ByCar implements StrategyI{

        @Override
        public void goHome() {
            System.out.println("by car");
        }
    }

    static class Context{
        private final StrategyI strategyI;

        Context(StrategyI strategyI) {this.strategyI = strategyI;}

        void execute(){
            strategyI.goHome();
        }
    }

    public static void main(String[] args) {
        StrategyI strategyI = new ByCar();
        Context context = new Context(strategyI);
        context.execute();
    }
}
