package com.lfp.ardf.control;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.util.Utils;

import java.util.List;

/**
 * Fragment控制器，帮助实现性能优化和防止内存泄漏<br>
 * <p>
 * 延时加载：准备即用即加载的原则，在显示的时候才会初始化对应Fragment<br>
 * 缓存：加载过的Fragment不会被重复加载<br>
 * </p>
 * Created by LiFuPing on 2018/6/1.
 */
public abstract class FragmentControl<T> {

    FragmentManager mFragmentManager;
    /**
     * Optional identifier of the container this fragment is
     * to be placed in.  If 0, it will not be placed in a container.
     */
    int mCcontainerViewId;
    /**
     * 当前显示中的Fragment
     */
    Fragment mCurrentFragment;
    /**
     * Fragment初始化监听器
     */
    OnFragmentInit mOnFragmentInit;
    /**
     * Fragment切换监听器
     */
    OnFragmentChange mOnFragmentChange;


    public FragmentControl(@NonNull IAppFramework appFk, @LayoutRes int containerViewId) {
        mCcontainerViewId = containerViewId;
        init(appFk);
    }

    public FragmentControl(@NonNull FragmentActivity activity, @LayoutRes int containerViewId) {
        mCcontainerViewId = containerViewId;
        init(activity);
    }

    public FragmentControl(@NonNull Fragment fragment, @LayoutRes int containerViewId) {
        mCcontainerViewId = containerViewId;
        init(fragment);
    }

    /**
     * 初始化
     * @param appFk IAppFramework
     */
    private void init(@NonNull IAppFramework appFk) {
        mFragmentManager = appFk.getSmartFragmentManager();
        recover();
    }

    /**
     * 初始化
     * @param activity FragmentActivity
     */
    private void init(@NonNull FragmentActivity activity) {
        mFragmentManager = activity.getSupportFragmentManager();
        recover();
    }

    /**
     * 初始化
     * @param fragment Fragment
     */
    private void init(@NonNull Fragment fragment) {
        mFragmentManager = fragment.getChildFragmentManager();
        recover();
    }

    /**
     * 一些情况下导致缓存信息被回收。在这里进行恢复
     */
    void recover() {
        List<Fragment> mData = mFragmentManager.getFragments();
        if (mData != null && !mData.isEmpty()) {
            for (Fragment f : mData) {
                if (f.getId() != mCcontainerViewId) continue;
                if (f.isAdded() && !f.isHidden()) {
                    mCurrentFragment = f;
                }
            }
        }
    }


    /**
     * 设置Fragment初始化监听器
     *
     * @param l 初始化监听器
     */
    public void setOnFragmentInit(OnFragmentInit l) {
        mOnFragmentInit = l;
    }


    /**
     * 设置Fragment切换监听器
     *
     * @param l 切换监听器
     */
    public void setOnFragmentChange(OnFragmentChange l) {
        mOnFragmentChange = l;
    }

    /*转换tag为Fragment认识的tag*/
    String parseTag(T tag) {
        return String.valueOf(tag);
    }

    /**
     * 判断当前显示的Fragment是否为tag的Fragment
     *
     * @param tag Fragment私有的唯一标准
     * @return 如果Fragment是tag对应的Fragment则return true
     */
    public boolean isCurrentTag(T tag) {
        Fragment fragment = getCurrentFragment();
        if (fragment == null) return false;
        return parseTag(tag).equals(fragment.getTag());
    }

    /**
     * 如果有Fragment是显示的,获得当前显示的Fragment
     *
     * @return 当前显示的Fragment
     */
    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }


    /**
     * 切换到 tag 对应的Fragment，并且因此旧的Fragment。
     *
     * @param tag 每一个Fragment对应一个不同的tag
     */
    public void change(@NonNull T tag) {
        checNonNull();

        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        onFragmentTransactionConfig(mFragmentTransaction);


        Fragment fragment = mFragmentManager.findFragmentByTag(parseTag(tag));
        if (fragment != null && fragment == mCurrentFragment) return;

        List<Fragment> mData = mFragmentManager.getFragments();
        if (mData != null && !mData.isEmpty()) {
            for (Fragment f : mData) {
                if (f.getId() != mCcontainerViewId) continue;
                if (fragment != null && fragment == f) {
                    mCurrentFragment = f;
                    if (f.isAdded() && f.isHidden()) mFragmentTransaction.show(fragment);

                } else if (f.isAdded() && !f.isHidden()) {
                    if (f instanceof OnFragmentControlProcessor) {
                        ((OnFragmentControlProcessor) f).onFragmentHidden();
                    }
                    mFragmentTransaction.hide(f);
                }

            }
        }

        if (fragment == null) {
            fragment = getFragment(tag);
            if (fragment == null) return;
            mFragmentTransaction.add(mCcontainerViewId, fragment, parseTag(tag));
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
     * 在切换的时候回调用FragmentTransaction，根据需求更改它的Style
     *
     * @param ft change -&gt;　FragmentTransaction
     */
    protected void onFragmentTransactionConfig(FragmentTransaction ft) {

    }

    /**
     * 通过tag创建，tag所对应的Fragment。这个tag是唯一指向这个Fragment的比标记
     * @param tag T
     * @return Fragment
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

    /**
     * 当调用 change(T tag) 的时候如果切换到Fragment已创建将会回调 onFragmentShow() 方法。
     * 使用onFragmentShow来表示从其他地方回到该Fragment的时机
     */
    public interface OnFragmentControlProcessor {
        /**
         * 当Fragment已经创建，从其他fragment回来的时候回调
         */
        void onFragmentShow();

        /**
         * 当Fragment已经创建，改变到其他fragment的时候回调
         */
        void onFragmentHidden();

    }

    /**
     * 当页面切换的时候回调方法
     */
    public interface OnFragmentChange<T> {
        void onChange(Fragment fragment, T tag);
    }

    /**
     * 当页面初始化的时候回调方法
     */
    public interface OnFragmentInit<T> {
        void onInit(Fragment fragment, T tag);
    }

    private void checNonNull() {
        Utils.checkNotNull(mFragmentManager, "未调用init()方法进行初始化!");
    }
}