package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.control.RadioGroupControl;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.util.ToastUtil;

/**
 * RadioGroupControl demo<br/>
 * Created by LiFuPing on 2018/6/6.
 */
public class Demo_RadioGroupControl extends BaseActivity {

    public static final void start(IAppFramework appfk) {
        Intent intent = new Intent(appfk.getContext(), Demo_RadioGroupControl.class);
        appfk.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_radiogroupcontrol);
        new ActionBarControl(getActivity())
                .showBack().setBackFinishActivity(getActivity())
                .setTitle("RadioGroupControl Demo")
                .setSubTitle("点击选项试试");

        /*RadioButton*/
        RadioGroupControl RadioGroup_RadioButton = new RadioGroupControl();
        RadioGroup_RadioButton.addRadio(new RadioGroupControl.SimperRadioItem(findViewById(R.id.view_RadioButton_1)));
        RadioGroup_RadioButton.addRadio(new RadioGroupControl.SimperRadioItem(findViewById(R.id.view_RadioButton_2)));
        RadioGroup_RadioButton.addRadio(new RadioGroupControl.SimperRadioItem(findViewById(R.id.view_RadioButton_3)));

        RadioGroupControl RadioGroup_TextView = new RadioGroupControl();
        RadioGroup_TextView.addRadio(new RadioTextViewItem((TextView) findViewById(R.id.view_TextView_1)));
        RadioGroup_TextView.addRadio(new RadioTextViewItem((TextView) findViewById(R.id.view_TextView_2)));
        RadioGroup_TextView.addRadio(new RadioTextViewItem((TextView) findViewById(R.id.view_TextView_3)));


        RadioGroupControl RadioGroup_HH = new RadioGroupControl();
        RadioGroup_HH.addRadio(new RadioGroupControl.SimperRadioItem(findViewById(R.id.view_hh_RadioButton)));
        RadioGroup_HH.addRadio(new RadioTextViewItem((TextView) findViewById(R.id.view_hh_TextView)));
        RadioGroup_HH.addRadio(new RadioIcoSwitchItem(findViewById(R.id.layout_hh_IcoSwitch)));


    }


    public static final class RadioTextViewItem extends RadioGroupControl.RadioItem<TextView> {

        public RadioTextViewItem(TextView view) {
            super(view);
        }

        @Override
        public boolean onInterceptCheck(boolean isCheck) {
            if (isCheck) {
                ToastUtil.show("不要摸我,已经选中了~");
            }
            return super.onInterceptCheck(isCheck);
        }

        @Override
        public void onChange() {
            TextView tv = getView();
            tv.setSelected(isCheck());
            tv.setText(isCheck() ? "选中☺" : "┭┮");
        }
    }

    public static final class RadioIcoSwitchItem extends RadioGroupControl.RadioItem {

        boolean mIcoSelected = false;
        TextView mTV_IcoSwitch;
        ImageView mIV_Ico;

        public RadioIcoSwitchItem(View view) {
            super(view);
            mTV_IcoSwitch = view.findViewById(R.id.view_hh_IcoSwitch);
            mIV_Ico = view.findViewById(R.id.view_hh_IcoSwitch_Ico);
        }

        @Override
        public boolean onInterceptCheck(boolean isCheck) {
            if (isCheck) mIcoSelected = !mIcoSelected;

            return super.onInterceptCheck(isCheck);
        }

        @Override
        public void onChange() {
            mIV_Ico.setSelected(mIcoSelected);
            mTV_IcoSwitch.setText(mIcoSelected ? "展开" : "收起");
        }
    }

    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "RadioGroupControl");
            setInfo("让任何布局实现RadioGroup效果");
        }

        @Override
        public void call() {
            Demo_RadioGroupControl.start(getAppFk());
        }
    }

}
