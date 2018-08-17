package learn.DesignPattern;

/**
 * <pre>
 * desc:
 *      单例模式
 *
 *      确保一个类只有一个实例,并提供全局访问点
 * function:
 *
 * Created by admin on 2018/8/15.
 * </pre>
 */
public class SingletonPatterm {

    private SingletonPatterm() {
    }


    /*---------------  急切模式  ----------------*/
    /*
    private static SingletonPatterm mEagerly = new SingletonPatterm();

    在静态初始化器中的创建,保证线程安全. 但是会拖慢启动速度
    */


    /*---------------  双重检查枷锁  ----------------*/
    private volatile static SingletonPatterm mInstance;

    /*  线程安全的  */
    public static final SingletonPatterm getInstance2() {
        if (mInstance == null) {
            synchronized (SingletonPatterm.class) {
                if (mInstance == null) mInstance = new SingletonPatterm();
            }
        }
        return mInstance;
    }


}
