package com.lfp.androidrapiddevelopmentframework.activity.module.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment.TestFragment;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.ardf.control.FragmentControl;

/**
 * Demo列表
 * Created by LiFuPing on 2018/5/9.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    public static final void start(Context c) {
        Intent intent = new Intent(c, HomeActivity.class);
        c.startActivity(intent);
    }

    FragmentControl<Integer> mFragmentControl = new FragmentControl<Integer>(R.id.view_Content) {
        @Override
        public Fragment onInit(Integer tag) {
            switch (tag) {
                case R.id.tab_1:
                    return new TestFragment("1");
                case R.id.tab_2:
                    return new TestFragment("2");
                case R.id.tab_3:
                    return new TestFragment("3");
            }
            return null;
        }

    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFragmentControl.init(getAppFk());

        findViewById(R.id.tab_1).setOnClickListener(this);
        findViewById(R.id.tab_2).setOnClickListener(this);
        findViewById(R.id.tab_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mFragmentControl.change(v.getId());
    }
}
