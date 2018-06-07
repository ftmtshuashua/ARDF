package com.lfp.ardf.control;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * RadioGroup控制器<br/>
 * 可以将任何形式的布局实现RadioGroup的效果<br/>
 * Created by LiFuPing on 2018/6/4.
 */
public class RadioGroupControl<T extends RadioGroupControl.RadioItem> {

    /**
     * 缓存所有添加到RadioGroupControl中到布局,在主动check()到时候使用
     */
    private final List<T> mRadioArray;

    /**
     * 当前被选中的Radio
     */
    T mCheckedRadio;

    /**
     * 监听Radio状态改变,在某些时候非常有用
     */
    OnRadioChangeListener mOnCheckedChangeListener;

    public RadioGroupControl() {
        mRadioArray = new ArrayList<>();
    }

    /**
     * 设置状态改变监听
     *
     * @param l
     */
    public void setOnRadioChangeListener(OnRadioChangeListener l) {
        mOnCheckedChangeListener = l;
    }

    public void addRadio(T item) {
        mRadioArray.add(item);
        item.attach(this);
    }

    public void addRadio(Iterable<T> array) {
        Iterator<T> arrays = array.iterator();
        while (arrays.hasNext()) {
            addRadio(arrays.next());
        }
    }

    /**
     * 当RadioGroupControl被回收,会导致状态丢失
     * 必要时在重新创建之后调用此方法恢复之前状态
     */
    public void recoverRadioState() {
        List<T> array = mRadioArray;
        for (T r : array) {
            if (r.isCheck()) {
                setCheck(r);
                break;
            }
        }
    }

    /**
     * 选中下标对应当Radio
     *
     * @param index radio 下标
     */
    public void checkByIndex(int index) {
        setCheck(mRadioArray.get(index));
    }

    /**
     * 遍历Radio View到ID,当找到对应View当时候选中这个View
     *
     * @param id
     */
    public void check(@IdRes int id) {
        for (T radio : mRadioArray) {
            if (id == radio.getView().getId()) {
                setCheck(radio);
                break;
            }
        }
    }

    /**
     * 选中Radio
     *
     * @param radio radio
     */
    public void check(T radio) {
        setCheck(radio);
    }

    /*
       切换逻辑
    */
    private void setCheck(T radio) {
        T current = mCheckedRadio;

        boolean isCheck = radio.setCheck(true);
        if (isCheck) {
            mCheckedRadio = radio;
            if (current != null && current != radio)
                current.setCheck(false);
            notifyRadioChange(radio);
        } else {
            mCheckedRadio = current;
        }
    }

    /**
     * 获得当前选中当radio
     *
     * @return radio
     */
    public T getCheckRadio() {
        return mCheckedRadio;
    }

    /**
     * 获得当前选中radio当view id
     *
     * @return view id
     */
    @IdRes
    public int getCheckedId() {
        return getCheckRadio().getView().getId();
    }

    /**
     * 通知状态改变
     *
     * @param radio 当前被选中当radio
     */
    private void notifyRadioChange(T radio) {
        onRadioChange(radio);
        if (mOnCheckedChangeListener != null) mOnCheckedChangeListener.onRadioChange(radio);
    }

    /**
     * 交给子类实现,当radio改变或者被重复选中当时候调用
     *
     * @param radio 当前被选中当radio
     */
    public void onRadioChange(T radio) {

    }


    /**
     * RadioItem 在任何View外套一个的壳。以实现类似RadioGroup的效果
     * <p>
     * 简单实现可以使用{@Code SimpleRadioItem}
     */
    public static abstract class RadioItem<V extends View> implements View.OnClickListener {
        RadioGroupControl mControl;
        V mView;


        public RadioItem(V view) {
            mView = view;
            view.setOnClickListener(this);
        }

        /*关联RadioGroupControl*/
        protected void attach(RadioGroupControl control) {
            mControl = control;
        }

        /**
         * 被Radio包裹控制的View
         *
         * @return
         */
        public V getView() {
            return mView;
        }

        /**
         * 拦截点击事件，在一些特殊情况下通过拦截事件来阻止RadioGroup切换选中状态
         */
        public boolean onInterceptCheck(boolean check) {
            return false;
        }

        /**
         * 准备改变这个Raido的选中状态
         *
         * @param check 准备改变到 isCheck 状态
         * @return 改变之后的选中状态
         */
        protected boolean setCheck(boolean check) {
            boolean isIntercept = onInterceptCheck(check);
            if (!isIntercept) {
                onChange(getView(), check);
            }
            return isCheck();
        }

        /**
         * 获得这个radio的选中状态
         * 在一些特殊场景,如被内存被回收之后还能获得正确当状态
         *
         * @return 当前选中状态
         */
        public abstract boolean isCheck();

        /**
         * 根据根据最终结果改变View显示状态
         *
         * @param v       被radio包裹当View
         * @param check 准备改变为当选中状态
         */
        public abstract void onChange(V v, boolean check);

        @Override
        public void onClick(View v) {
            if (v == mView) mControl.check(this);
        }
    }

    /**
     * RadioItem简单实现
     * 如果View是RadioButton则通过setChecked()方法改变他的状态
     * 如果是其他View则通过setSelected()方法来改变状态
     */
    public static class SimpleRadioItem<V extends View> extends RadioItem<V> {

        public SimpleRadioItem(V view) {
            super(view);
        }

        @Override
        public boolean isCheck() {
            View v = getView();
            if (v instanceof RadioButton) {
                return ((RadioButton) v).isChecked();
            } else return v.isSelected();
        }

        @Override
        public boolean onInterceptCheck(boolean isCheck) {
            return isCheck() == isCheck;
        }

        @Override
        public void onChange(V v, boolean isCheck) {
            if (v instanceof RadioButton) {
                ((RadioButton) v).setChecked(isCheck);
            } else v.setSelected(isCheck);
        }
    }

    /**
     * Radio切换监听
     */
    public interface OnRadioChangeListener<T extends RadioItem> {

        void onRadioChange(T radio);
    }
}
