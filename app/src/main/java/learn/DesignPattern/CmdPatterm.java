package learn.DesignPattern;

/**
 * <pre>
 * desc:
 *      命令模式 - 封装调用
 *
 *      将"动作的请求者"和"动作的执行者"解耦
 *      将"请求"封装成对象,以便使用不同的请求/队列或者日志来参数化其他对象,命令模式也支持可撤销的操作
 * function:
 *
 * Created by admin on 2018/8/16.
 * </pre>
 */
public class CmdPatterm {


    public static final void main(String... arg) {
        Car car = new Car();
        TV tv = new TV();

        RemoteControl remoteControl = new RemoteControl(); //实例化遥控器
        System.out.println("-- 传入遥控对象 --");
        remoteControl.setCommand(car);
        remoteControl.execute();
        remoteControl.undo();
        System.out.println("-- 修改命令 --");
        remoteControl.setCommand(tv);
        remoteControl.execute();
        remoteControl.undo();

        System.out.println("-- 要求遥控器同时控制 玩具车和电视 --");
        MacroCommand macroCommand = new MacroCommand(car , tv);
        remoteControl.setCommand(macroCommand);
        remoteControl.execute();
        remoteControl.undo();

    }

    /**
     * 遥控器 - 请求执行命令,不关心具体命令
     */
    public static final class RemoteControl {
        Command command;

        public RemoteControl() {
            this.command = NoCommand.getInstance();//不用判断(command!=null)
        }

        /*插入命令*/
        public void setCommand(Command command) {
            this.command = command;
        }



        /* execute() 和 undo() 模拟遥控器上有两个按键*/
        /*启动*/
        public void execute() {
            command.on();
        }

        /*撤销*/
        public void undo() {
            command.off();
        }


        /*创建一个默认的,不做任何事情的命令,就不用判断(command!=null) */
        private static final class NoCommand implements Command {
            private NoCommand() {
            }

            static volatile NoCommand mInstance;

            public static final NoCommand getInstance() {
                if (mInstance == null) {
                    synchronized (NoCommand.class) {
                        if (mInstance == null) mInstance = new NoCommand();
                    }
                }
                return mInstance;
            }

            @Override
            public void on() {

            }

            @Override
            public void off() {

            }
        }

    }

    /**
     * 命令 - 命令执行者
     */
    public interface Command {

        /*开启*/
        void on();

        /*关闭*/
        void off();
    }

    /*玩具汽车*/
    public static final class Car implements Command {

        @Override
        public void on() {
            System.out.println("玩具汽车 -> 启动");
        }

        @Override
        public void off() {
            System.out.println("玩具汽车 -> 停止");
        }
    }

    /*电视机*/
    public static final class TV implements Command {

        @Override
        public void on() {
            System.out.println("电视 -> 打开");
        }

        @Override
        public void off() {
            System.out.println("电视 -> 关闭");
        }
    }


    /*命令宏*/
    public static final class MacroCommand implements Command {
        Command[] array;

        public MacroCommand(Command... array) {
            this.array = array;
        }

        @Override
        public void on() {
            if (array == null) return;
            for (Command command : array) command.on();
        }

        @Override
        public void off() {
            if (array == null) return;
            for (Command command : array) command.off();

        }
    }


}
