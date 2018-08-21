package learn.DesignPattern;

/**
 * <pre>
 * desc:
 *      状态模式
 *
 *      允许对象的内部状态改变时改变他们的行为,对象看起来好像修改了它的类
 *
 * function:
 *
 * Created by admin on 2018/8/16.
 * </pre>
 */
public class StatePatterm {


    public static final void main(String... arg) {
        RemoteControl mRemoteControl = new RemoteControl();

        mRemoteControl.press();
        mRemoteControl.uplift();

        mRemoteControl.press();
        mRemoteControl.press();

        mRemoteControl.uplift();
        mRemoteControl.uplift();

    }

    /*遥控器*/
    private static final class RemoteControl implements State {
        State bottom_state;//遥控器上按钮的状态控制
        boolean isPress = false;//遥控器按钮状态


        public RemoteControl() {
            bottom_state = new UpliftState(this);
        }

        public void setState(State state) {
            bottom_state = state;
        }

        @Override
        public void press() {
            bottom_state.press();
        }

        @Override
        public void uplift() {
            bottom_state.uplift();
        }
    }

    /*状态*/
    private interface State {

        /*按下*/
        void press();

        /*提起*/
        void uplift();
    }


    /*按下状态*/
    private static final class PressState implements State {
        RemoteControl mRemoteControl;

        public PressState(RemoteControl mRemoteControl) {
            this.mRemoteControl = mRemoteControl;
        }

        @Override
        public void press() {
            if (mRemoteControl.isPress) System.out.println("错误 -> 已经按下!!");
        }

        @Override
        public void uplift() {
            if (mRemoteControl.isPress) {
                System.out.println("抬起按钮");
                mRemoteControl.isPress = false;
                mRemoteControl.setState(new UpliftState(mRemoteControl));
            }
        }
    }

    /*抬起状态*/
    private static final class UpliftState implements State {
        RemoteControl mRemoteControl;

        public UpliftState(RemoteControl mRemoteControl) {
            this.mRemoteControl = mRemoteControl;
        }

        @Override
        public void press() {
            if (!mRemoteControl.isPress) {
                System.out.println("按下按钮");
                mRemoteControl.isPress = true;
                mRemoteControl.setState(new PressState(mRemoteControl));
            }
        }

        @Override
        public void uplift() {
            if (!mRemoteControl.isPress) System.out.println("错误 -> 已经抬起!!");
        }
    }

}
