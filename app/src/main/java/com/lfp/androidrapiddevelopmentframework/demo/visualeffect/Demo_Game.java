package com.lfp.androidrapiddevelopmentframework.demo.visualeffect;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.framework.I.IAppFramework;

/**
 * <pre>
 * desc:
 *      游戏效果实现
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public class Demo_Game extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_game);
        new ActionBarControl(getActivity())
                .setfitsSystemWindows()
                .setTitle("游戏效果")
                .setSubTitle("触摸界面查看效果")
                .showBack()
                .setBackFinishActivity(getActivity())
        ;

    }


    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "打飞机游戏", "游戏效果解决方案");
        }

        @Override
        public void call() {
            Intent intent = new Intent(getAppFk().getContext(), Demo_Game.class);
            getAppFk().startActivity(intent);
        }
    }

}
