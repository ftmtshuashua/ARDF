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

import java.text.MessageFormat;

/**
 * RadioGroupControl demo<br>
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
        RadioGroup_RadioButton.addRadio(new RadioGroupControl.SimpleRadioItem(findViewById(R.id.view_RadioButton_1)));
        RadioGroup_RadioButton.addRadio(new RadioGroupControl.SimpleRadioItem(findViewById(R.id.view_RadioButton_2)));
        RadioGroup_RadioButton.addRadio(new RadioGroupControl.SimpleRadioItem(findViewById(R.id.view_RadioButton_3)));

        /*TextView*/
        RadioGroupControl RadioGroup_TextView = new RadioGroupControl();
        RadioGroup_TextView.addRadio(new RadioTextViewItem((TextView) findViewById(R.id.view_TextView_1)));
        RadioGroup_TextView.addRadio(new RadioTextViewItem((TextView) findViewById(R.id.view_TextView_2)));
        RadioGroup_TextView.addRadio(new RadioTextViewItem((TextView) findViewById(R.id.view_TextView_3)));

        /*混合*/
        RadioGroupControl RadioGroup_HH = new RadioGroupControl();
        RadioGroup_HH.addRadio(new RadioGroupControl.SimpleRadioItem(findViewById(R.id.view_hh_RadioButton)));
        RadioGroup_HH.addRadio(new RadioTextViewItem((TextView) findViewById(R.id.view_hh_TextView)));
        RadioGroup_HH.addRadio(new RadioIcoSwitchItem(findViewById(R.id.layout_hh_IcoSwitch)));
        RadioGroup_HH.addRadio(new RadioAnimationItem(findViewById(R.id.view_AnimationTextView)));
        RadioGroup_RadioButton.recoverRadioState();

    }


    public static final class RadioTextViewItem extends RadioGroupControl.SimpleRadioItem<TextView> {

        public RadioTextViewItem(TextView view) {
            super(view);
        }

        @Override
        public boolean onInterceptCheck(boolean check) {
            if (check && isCheck()) {
                ToastUtil.show("不要摸我,已经选中了~");
            }
            return super.onInterceptCheck(check);
        }

        @Override
        public void onChange(TextView tv, boolean check) {
            super.onChange(tv, check);
            tv.setText(check ? "选中☺" : "┭┮");
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
        public boolean onInterceptCheck(boolean check) {
            if (isCheck() && check) mIcoSelected = !mIcoSelected;
            return super.onInterceptCheck(check);
        }

        @Override
        public boolean isCheck() {
            return mTV_IcoSwitch.isSelected();
        }

        @Override
        public void onChange(View v, boolean check) {
            mTV_IcoSwitch.setSelected(check);
            if (!check) {
                mTV_IcoSwitch.setText("(┬＿┬) 选我~~");
                mIV_Ico.setSelected(false);
                mIcoSelected = false;
            } else {
                mIV_Ico.setSelected(mIcoSelected);
                mTV_IcoSwitch.setText(mIcoSelected ? "o(∩_∩)o 展开" : "o(∩_∩)o 收起");
            }
        }
    }

    public static final class RadioAnimationItem extends RadioGroupControl.RadioItem {
        TextView mTV_Animation;

        static final int GROUP_CLICK_COUNT = 3; /*当点击次数达到 能整除这个次数当时候,允许点击*/
        int mClickCount = 0;


        public RadioAnimationItem(View view) {
            super(view);
            mTV_Animation = view.findViewById(R.id.view_AnimationTextView);
        }

        @Override
        public boolean onInterceptCheck(boolean check) {
            if (!check) return super.onInterceptCheck(check);

            int group = ++mClickCount % GROUP_CLICK_COUNT;
            if (group == 0) return false;
            mTV_Animation.setText(MessageFormat.format("点不到o(∩_∩)o +{0}", group));

            View parent = (View) mTV_Animation.getParent();
            int width = parent.getWidth() - mTV_Animation.getWidth();
            int height = parent.getHeight() - mTV_Animation.getHeight();
            mTV_Animation.setX((float) (width * Math.random() * 0.9));
            mTV_Animation.setY((float) (height * Math.random() * 0.9));
            mTV_Animation.getParent().requestLayout();

            return true;
        }

        @Override
        public boolean isCheck() {
            return mTV_Animation.isSelected();
        }

        @Override
        public void onChange(View view, boolean check) {
            mTV_Animation.setSelected(check);
            if (check) mTV_Animation.setText("让你摸一下~~");
        }
    }

    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "RadioGroupControl","让任何布局实现RadioGroup效果");
        }

        @Override
        public void call() {
            Demo_RadioGroupControl.start(getAppFk());
        }
    }

}
