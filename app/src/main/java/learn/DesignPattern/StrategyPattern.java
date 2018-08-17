package learn.DesignPattern;

import java.util.HashMap;

/**
 * <pre>
 * desc:
 *      策略模式(delegate , perform)
 *      定义了算法族,分别封装起来,让它们之间可以互相替换,此模式让算法的变化独立于使用算法的客户
 *
 *      1.找出应用中可能需要变化之处,把他们独立出来,不要和那些不需要变化的代码混在一起(delegate , perform)
 *      2.针对接口编程,而不是针对实现编程
 *      3.多用组合,少用继承
 *
 * function:
 *
 * Created by LiFuPing on 2018/8/3.
 * </pre>
 */
public class StrategyPattern {

    public static final void main(String... arg) {
        Duck duck = new Duck_Child();
        duck.performQuack();
        duck.performFly();
        duck.setQuackBehavior(new Quack2());
        duck.setFlyBehavior(new Fly2());
        duck.performQuack();
        duck.performFly();
    }


    /*------ 定义框架 ----*/
    /*鸭子超类*/
    public abstract static class Duck {
        String name;
        FlyBehavior flyBehavior;
        QuackBehavior quackBehavior;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setFlyBehavior(FlyBehavior flyBehavior) {
            this.flyBehavior = flyBehavior;
        }

        public void setQuackBehavior(QuackBehavior quackBehavior) {
            this.quackBehavior = quackBehavior;
        }

        public void performQuack() {
            if (quackBehavior != null) quackBehavior.quack();
            else System.out.println("这只鸭子不能叫!!");
        }

        public void performFly() {
            if (quackBehavior != null) flyBehavior.quack();
            else System.out.println("这只鸭子不能飞!!");
        }
    }

    /*飞行*/
    public interface FlyBehavior {
        void quack();
    }

    /*叫声*/
    public interface QuackBehavior {
        void quack();
    }

    /*----- 行为实现 ---*/

    public static final class Quack1 implements QuackBehavior {

        @Override
        public void quack() {
            System.out.println("鸭子说:嘎嘎嘎");
        }
    }

    public static final class Quack2 implements QuackBehavior {

        @Override
        public void quack() {
            System.out.println("鸭子说:哈哈哈");
        }
    }


    public static final class Fly1 implements FlyBehavior {

        @Override
        public void quack() {
            System.out.println("鸭子展开翅膀飞走了!");
        }
    }

    public static final class Fly2 implements FlyBehavior {

        @Override
        public void quack() {
            System.out.println("鸭子骑着火箭飞走了!");
        }
    }


    public static final class Duck_Child extends Duck {
        public Duck_Child() {
            setFlyBehavior(new Fly1());
            setQuackBehavior(new Quack1());
        }
    }


}
