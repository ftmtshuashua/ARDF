package com.lfp.androidrapiddevelopmentframework;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Banner<br/>
 * Created by LiFuPing on 2018/6/26.
 */
public class BannerView extends ViewGroup {

    Adapter mAdapter;

    public BannerView(Context context) {
        super(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        if (mAdapter == null) {
//            LogUtil.e("No adapter attached; skipping layout");
//            return;
//        }
//        if (mLayout == null) {
//            LogUtil.e("No layout manager attached; skipping layout");
//            return;
//        }

//        mFirstLayoutComplete = true;
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    ViewHolder obtainViewHolder() {

        return null;
    }

    public abstract static class ViewHolder {
        View itemView;
        int mItemViewType;
    }


    public abstract static class Adapter<VH extends BannerView.ViewHolder> {

        public final VH createViewHolder(@NonNull ViewGroup parent, int viewType) {
            final VH holder = onCreateViewHolder(parent, viewType);
            if (holder.itemView.getParent() != null) {
                throw new IllegalStateException("ViewHolder views must not be attached when"
                        + " created. Ensure that you are not passing 'true' to the attachToRoot"
                        + " parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
            }
            holder.mItemViewType = viewType;
            return holder;
        }

        @NonNull
        public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    }

}
