package com.lfp.androidrapiddevelopmentframework.activity.module.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment.AboutUsFragment;
import com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment.DemoFragment;
import com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment.DiscoverFragment;
import com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment.ReadmeFragment;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.util.PermissionManager;
import com.lfp.ardf.control.FragmentControl;
import com.lfp.ardf.control.RadioGroupControl;
import com.yanzhenjie.permission.Permission;

/**
 * Demo列表
 * Created by LiFuPing on 2018/5/9.
 */
public class HomeActivity extends BaseActivity {

    public static final void start(Context c) {
        Intent intent = new Intent(c, HomeActivity.class);
        c.startActivity(intent);
    }

    /*Fragment init*/
    FragmentControl<Integer> mFragmentControl = new FragmentControl<Integer>(R.id.view_Content) {
        @Override
        public Fragment onInit(Integer tag) {
            switch (tag) {
                case R.id.tab_1:
                    return DiscoverFragment.newInstance();
                case R.id.tab_2:
                    return DemoFragment.newInstance();
                case R.id.tab_3:
                    return ReadmeFragment.newInstance();
                case R.id.tab_4:
                    return AboutUsFragment.newInstance();
            }
            return null;
        }
    };

    /*Radio */
    RadioGroupControl mRadioGroupControl = new RadioGroupControl() {
        @Override
        public void onRadioChange(RadioItem radio) {
            mFragmentControl.change(radio.getView().getId());
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFragmentControl.init(getAppFk());

        mRadioGroupControl.addRadio(new RadioGroupControl.SimpleRadioItem(findViewById(R.id.tab_1)));
        mRadioGroupControl.addRadio(new RadioGroupControl.SimpleRadioItem(findViewById(R.id.tab_2)));
        mRadioGroupControl.addRadio(new RadioGroupControl.SimpleRadioItem(findViewById(R.id.tab_3)));
        mRadioGroupControl.addRadio(new RadioGroupControl.SimpleRadioItem(findViewById(R.id.tab_4)));
        mRadioGroupControl.check(R.id.tab_2);

        new PermissionManager(getContext()) {
        }.request(Permission.ACCESS_COARSE_LOCATION);
    }


}
