package com.lfp.ardf.control;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lfp.ardf.debug.LogUtil;
import com.lfp.ardf.exception.MsgException;
import com.lfp.ardf.framework.I.IAppFramework;

import java.text.MessageFormat;
import java.util.List;

/**
 * Fragment控制器，帮助实现性能优化和防止内存泄漏<br/>
 * <p>
 * 延时加载：准备即用即加载的原则，在显示的时候才会初始化对应Fragment<br/>
 * 缓存：加载过的Fragment不会被重复加载<br/>
 * </p>
 * Created by LiFuPing on 2018/6/1.
 */
public abstract class FragmentControl<T> {
    FragmentManager mFragmentManager;
    int mFragmentContentLayout;
    /**
     * 当前显示中Fragment
     */
    Fragment mCurrentFragment;

    public FragmentControl(@LayoutRes int contentLayout) {
        mFragmentContentLayout = contentLayout;
    }


    public FragmentControl(@NonNull IAppFramework appFk, @LayoutRes int contentLayout) {
        mFragmentContentLayout = contentLayout;
        init(appFk);
    }

    public FragmentControl(@NonNull FragmentActivity activity, @LayoutRes int contentLayout) {
        mFragmentContentLayout = contentLayout;
        init(activity);
    }

    public FragmentControl(@NonNull Fragment fragment, @LayoutRes int contentLayout) {
        mFragmentContentLayout = contentLayout;
        init(fragment);
    }


    public void init(@NonNull IAppFramework appFk) {
        mFragmentManager = appFk.getSmartFragmentManager();
        resetChache();
    }

    public void init(@NonNull FragmentActivity activity) {
        mFragmentManager = activity.getSupportFragmentManager();
        resetChache();
    }

    public void init(@NonNull Fragment fragment) {
        mFragmentManager = fragment.getChildFragmentManager();
        resetChache();
    }


    void resetChache() { /*异常情况重构Fragment的时候重置缓信息*/
//        List<Fragment> mData = mFragmentManager.getFragments();
//        if (mData != null) {
//            for (Fragment f : mData) {
//                if (f instanceof OnFragmentControlProcessor && !f.isHidden()) {
//                    mOnFragmentChange = (OnFragmentChange) f;
//                    mCurrentTag = f.getTag();
//                    return;
//                }
//            }
//        }
    }

    OnFragmentInit mOnFragmentInit;

    public void setOnFragmentInit(OnFragmentInit l) {
        mOnFragmentInit = l;
    }

    OnFragmentChange mOnFragmentChange;

    public void setOnFragmentChange(OnFragmentChange l) {
        mOnFragmentChange = l;
    }

    String parseTag(T tag) {
        return String.valueOf(tag);
    }

    public boolean isCurrentTag(T tag) {
        Fragment fragment = getCurrentFragment();
        if (fragment == null) return false;
        return parseTag(tag).equals(fragment.getTag());
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    /**
     * 切换显示的Fragment
     */
    public void change(@NonNull T tag) {
        checNonNull();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        Fragment fragment = mFragmentManager.findFragmentByTag(parseTag(tag));
        if (fragment != null && fragment == mCurrentFragment) return;

        List<Fragment> mData = mFragmentManager.getFragments();
        if (mData != null && !mData.isEmpty()) {
            for (Fragment f : mData) {
                if (f.getId() != mFragmentContentLayout) continue;
                if (fragment != null && fragment == f) {
                    mCurrentFragment = f;
                    if (f.isAdded() && f.isHidden()) mFragmentTransaction.show(fragment);

                } else if (f.isAdded() && !f.isHidden()) {
                    mFragmentTransaction.hide(f);
                }

            }
        }

        if (fragment == null) {
            fragment = getFragment(tag);
            if (fragment == null) return;
            mFragmentTransaction.add(mFragmentContentLayout, fragment, parseTag(tag));
            mFragmentTransaction.show(fragment);
            mCurrentFragment = fragment;
        }

        mFragmentTransaction.commit();

        onChange(tag, fragment);
        if (mOnFragmentChange != null) mOnFragmentChange.onChange(fragment, tag);

        if (fragment instanceof OnFragmentControlProcessor) {
            OnFragmentControlProcessor processor = (OnFragmentControlProcessor) fragment;
            if (fragment.isResumed()) processor.onFragmentShow();
        }

    }

    /**
     * 获得tag对应的Fagment
     *
     * @param tag 通过这个tag查找或者创建Fragment
     * @return ag对应的Fagment
     */
    public Fragment getFragment(T tag) {
        checNonNull();
        Fragment fragment = mFragmentManager.findFragmentByTag(parseTag(tag));
        if (fragment == null) {
            fragment = onInit(tag);
            if (mOnFragmentInit != null) mOnFragmentInit.onInit(fragment, tag);
        }
        return fragment;
    }

    /**
     * 初始化一个新的Fragment
     */
    public abstract Fragment onInit(T tag);

    /**
     * 当用户切换Framgent的时候回调
     *
     * @param tag      所显示Fragment对应的标记
     * @param fragment 当前显示的Fragment
     */
    public void onChange(T tag, Fragment fragment) {

    }

    public interface OnFragmentControlProcessor {
        /**
         * 当Fragment已经创建 显示的时候回调方法
         */
        void onFragmentShow();
    }

    /*当页面切换的时候回调方法*/
    public interface OnFragmentChange<T> {
        void onChange(Fragment fragment, T tag);
    }

    public interface OnFragmentInit<T> {
        void onInit(Fragment fragment, T tag);
    }

    private void checNonNull() {
        if (mFragmentManager == null) throw new MsgException("未调用init()方法进行初始化!");
    }
}
