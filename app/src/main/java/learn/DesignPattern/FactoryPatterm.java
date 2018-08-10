package learn.DesignPattern;

/**
 * <pre>
 * desc:
 *      工厂模式
 *      用来封装对象的创建
 *
 *      将实例化类统一管理,减少对源码的修改
 *
 * function:
 *
 * Created by admin on 2018/8/9.
 * </pre>
 */
public class FactoryPatterm {

    public static final void main(String... arg) {
        CarStore store;
        System.out.println("我要买车!!");
        System.out.println("先去奥迪商店看看..");
        store = new CarStore(new FactoryAudi());
        System.out.println("他们店有:" + store.create(null).getName());
        System.out.println("再去奔驰商店看看..");
        store = new CarStore(new FactoryBenz());
        System.out.println("他们店有:" + store.create(null).getName());


    }


    /**
     * 汽车超类
     */
    public static abstract class Car {
        public abstract String getName();
    }


    /*Smart*/
    public static final class Smart extends Car {

        @Override
        public String getName() {
            return "Smart";
        }
    }

    /*奥迪*/
    public static final class Audi extends Car {

        @Override
        public String getName() {
            return "奥迪 Q100";
        }
    }


    /**
     * 汽车工厂
     */
    public interface CarFactory {

        Car create(String type);

    }

    /**
     * 奔驰生产工厂
     */
    public static final class FactoryAudi implements CarFactory {

        @Override
        public Car create(String type) {
            return new Audi();
        }
    }

    /**
     * 奥迪生产工厂
     */
    public static final class FactoryBenz implements CarFactory {

        @Override
        public Car create(String type) {
            return new Smart();
        }
    }

    /**
     * 汽车商店 - 装饰者模式装饰汽车工厂
     */
    public static final class CarStore implements CarFactory {
        CarFactory factory;

        /**
         * @param factory 商店使用的
         */
        public CarStore(CarFactory factory) {
            this.factory = factory;
        }

        @Override
        public Car create(String type) {
            return factory.create(type);
        }
    }


}
