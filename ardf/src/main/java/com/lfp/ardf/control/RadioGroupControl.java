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

    private final List<T> mRadioArray;

    /**
     * 当前选中的Radio
     */
    T mCheckedRadio;
    OnRadioChangeListener mOnCheckedChangeListener;

    public RadioGroupControl() {
        mRadioArray = new ArrayList<>();
    }

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

    public void checkByIndex(int index) {
        setCheck(mRadioArray.get(index));
    }

    public void check(@IdRes int id) {
        for (T radio : mRadioArray) {
            if (id == radio.getView().getId()) {
                setCheck(radio);
                break;
            }
        }
    }

    public void check(T radio) {
        setCheck(radio);
    }

    private void setCheck(T radio) {

        if (mCheckedRadio != null && mCheckedRadio == radio) return;
        if (mCheckedRadio != null && mCheckedRadio != radio) mCheckedRadio.setCheck(false);

        mCheckedRadio = radio;
        radio.setCheck(true);

        onRadioChange(radio);
        if (mOnCheckedChangeListener != null) mOnCheckedChangeListener.onRadioChange(radio);
    }

    public T getCheckRadio() {
        return mCheckedRadio;
    }

    @IdRes
    public int getCheckedId() {
        return getCheckRadio().getView().getId();
    }

    public void onRadioChange(T radio) {

    }


    /**
     * RadioItem 在任何View外套一个的壳。以实现类似RadioGroup的效果
     */
    public static abstract class RadioItem<V extends View> implements View.OnClickListener {
        RadioGroupControl mControl;
        V mView;
        boolean mIsCheck;


        public RadioItem(V view) {
            mIsCheck = false;
            mView = view;
            view.setOnClickListener(this);
        }

        public void attach(RadioGroupControl control) {
            mControl = control;
        }

        public V getView() {
            return mView;
        }

        /**
         * 拦截点击事件，在一些特殊情况下通过拦截事件来阻止RadioGroup切换选中状态
         */
        public boolean onInterceptCheck(boolean isCheck) {
            return isCheck() && isCheck;
        }

        protected boolean setCheck(boolean isCheck) {
            boolean isIntercept = onInterceptCheck(isCheck);
            if (isIntercept) return false;

            mIsCheck = isCheck;
            onChange();
            return true;
        }

        public boolean isCheck() {
            return mIsCheck;
        }

        public abstract void onChange();

        @Override
        public void onClick(View v) {
            if (v == mView) mControl.check(this);
        }
    }

    /**
     * RadioItem简单实现
     */
    public static class SimperRadioItem extends RadioItem {

        public SimperRadioItem(View view) {
            super(view);
        }

        @Override
        public void onChange() {
            View v = getView();
            if (v instanceof RadioButton) {
                ((RadioButton) v).setChecked(isCheck());
            } else getView().setSelected(isCheck());
        }
    }

    /**
     * 切换监听
     */
    public interface OnRadioChangeListener<T extends RadioItem> {

        void onRadioChange(T radio);
    }
}
