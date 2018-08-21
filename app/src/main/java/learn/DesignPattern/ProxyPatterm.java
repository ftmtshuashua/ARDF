package learn.DesignPattern;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <pre>
 * desc:
 *      代理模式 - 控制对象访问
 *      AOP
 *      动态代理
 *
 *      为另一个对象提供一个替身或者占位符以控制对这个对象的访问
 *
 * function:
 *
 * Created by admin on 2018/8/20.
 * </pre>
 */
public class ProxyPatterm {

    public static final void main(String... arg) {
        RemoteControl remoteControl = new RemoteControl();
        RemoteControl proxy = ProxyFactory.getUserControl(remoteControl);
        proxy.getInfo();
        proxy.button();
//        proxy.setFun("hh");

    }

    /*用户*/
    private static final class User implements InvocationHandler {
        RemoteControl mRemoteControl;

        public User(RemoteControl mRemoteControl) {
            this.mRemoteControl = mRemoteControl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().startsWith("set")) {
                throw new IllegalAccessException("只有厂家能修改遥控器的设置!");
            } else {
                return method.invoke(mRemoteControl, args);
            }
        }
    }

    /*动态代理创建工程*/
    private static final class ProxyFactory {

        public static RemoteControl getUserControl(RemoteControl person) {
            return (RemoteControl) Proxy.newProxyInstance(
                    person.getClass().getClassLoader()
                    , person.getClass().getInterfaces()
                    , new User(person)
            );
        }

    }


    /*遥控器*/
    private static final class RemoteControl {

        String fun = "我的工作是控制电视!";

        public void setFun(String fun) {
            this.fun = fun;
        }

        public void button() {
            System.out.println(fun);
        }

        public void getInfo() {
            System.out.println("我是遥控器");
        }
    }


}
