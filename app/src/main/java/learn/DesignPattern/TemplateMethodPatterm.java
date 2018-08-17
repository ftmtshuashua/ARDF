package learn.DesignPattern;

/**
 * <pre>
 * desc:
 *      模板方法模式
 *
 *      在一个方法中定义一个算法的骨架,而将一些步骤延迟到子类中,模板方法使得子类可以在不改变算法结构的情况下,重新定义算法中的某些步骤
 * function:
 *
 * Created by admin on 2018/8/16.
 * </pre>
 */
public class TemplateMethodPatterm {


    public static final void main(String... ar) {
        /*执行不同的模板*/
        Template template = new Child_1();
        template.execute();
        template = new Child_2();
        template.execute();
    }

    /*方法模板*/
    public static abstract class Template {

        public void execute() {
            int number = fun1(5);
            number = fun2(number);
            fun3(number);
        }

        abstract int fun2(int number);

        public int fun1(int number) {
            return number + 1;
        }

        public void fun3(int number) {
            System.out.println(number);
        }
    }

    /*使用模板*/
    public static final class Child_1 extends Template {

        @Override
        int fun2(int number) {
            return number * 22;
        }
    }

    /*使用模板*/
    public static final class Child_2 extends Template {

        @Override
        int fun2(int number) {
            return number + 22;
        }
    }
}
