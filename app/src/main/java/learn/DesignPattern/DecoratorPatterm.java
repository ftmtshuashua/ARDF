package learn.DesignPattern;

/**
 * <pre>
 * desc:
 *      装饰者模式
 *      动态地将责任附加到对象上.若要扩展功能.装饰者模式提供了比集成更有弹性的替代方案
 *
 *      在不改变源码的情况下实现了对源码的拓展
 * function:
 *
 * Created by admin on 2018/8/9.
 * </pre>
 */
public class DecoratorPatterm {


    public static final void main(String... arg) {

        Machine car = new Car();
        Machine airplane = new Airplane();
        System.out.println("我买了一个:" + car.getDescribe());
        System.out.println("我买了一个:" + airplane.getDescribe());

//        接下来装饰他们
        car = new RedDecorator(car);
        airplane = new OldDecorator(new GreenDecorator(airplane));
        System.out.println("我买了一个:" + car.getDescribe());
        System.out.println("我买了一个:" + airplane.getDescribe());
    }

    /*--------------------------- 源码 ---------------------------*/

    /**
     * 机械超类 - 所有机械集成这个类
     */
    private static abstract class Machine {
        /**
         * 获得描述信息
         */
        public abstract String getDescribe();
    }


    /**
     * 汽车
     */
    private static class Car extends Machine {
        @Override
        public String getDescribe() {
            return "汽车";
        }
    }

    /**
     * 飞机
     */
    private static class Airplane extends Machine {
        @Override
        public String getDescribe() {
            return "飞机";
        }
    }

    /*--------------------------- 在不改变源码的基础上增加属性 ---------------------------*/

    /**
     * 机械装饰者超类
     */
    private static abstract class MachineDecorator extends Machine {
        Machine machine;

        public MachineDecorator(Machine machine) {
            this.machine = machine;
        }

        @Override
        public String getDescribe() {
            return getDecorator() + machine.getDescribe();
        }

        /**
         * 获得修饰信息
         */
        public abstract String getDecorator();
    }

    private static class GreenDecorator extends MachineDecorator {

        public GreenDecorator(Machine machine) {
            super(machine);
        }

        @Override
        public String getDecorator() {
            return "绿色的";
        }
    }

    private static class RedDecorator extends MachineDecorator {

        public RedDecorator(Machine machine) {
            super(machine);
        }

        @Override
        public String getDecorator() {
            return "红色的";
        }
    }

    private static class OldDecorator extends MachineDecorator {

        public OldDecorator(Machine machine) {
            super(machine);
        }

        @Override
        public String getDecorator() {
            return "破旧的";
        }
    }

}
