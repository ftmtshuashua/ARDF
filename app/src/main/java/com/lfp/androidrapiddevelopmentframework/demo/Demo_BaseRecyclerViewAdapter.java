package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.androidrapiddevelopmentframework.event.DemoEvent;
import com.lfp.androidrapiddevelopmentframework.event.InfoEvent;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
import com.lfp.ardf.adapter.SimpleRecyclerViewAdapter;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.util.ToastUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

/**
 * Demo for BaseRecyclerViewAdapter
 * Created by LiFuPing on 2018/5/9.
 */
public class Demo_BaseRecyclerViewAdapter extends BaseActivity {

    public static final void start(IAppFramework appfk) {
        Intent intent = new Intent(appfk.getContext(), Demo_BaseRecyclerViewAdapter.class);
        appfk.startActivity(intent);
    }

    SimpleRecyclerViewAdapter<DataEventImp> mMenuAdapter;
    SimpleRecyclerViewAdapter<String> mListAdapter;

    ActionBarControl mActionBarControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_recycleradapter);
        mActionBarControl = new ActionBarControl(getActivity())
                .showBack().setBackFinishActivity(getActivity())
                .setTitle("RecyclerViewAdapter Demo");

        RecyclerView MenuView = findViewById(R.id.view_RecyclerViewMenu);
        MenuView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MenuView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        MenuView.setAdapter(mMenuAdapter = new SimpleRecyclerViewAdapter<>(MenuViewHolder.class, R.layout.layout_simpler_textview));

        RecyclerView mRecyclerView = findViewById(R.id.view_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.setAdapter(mListAdapter = new SimpleRecyclerViewAdapter<>(ListViewHolder.class, R.layout.demo_recycleradapter_listitem));
        mListAdapter.setOnAdapterDataChange(new BaseRecyclerViewAdapter.OnAdapterDataChange<String>() {
            @Override
            public void onChange(BaseRecyclerViewAdapter<String> adapter) {
                mActionBarControl.setSubTitle(MessageFormat.format("总数据：{0} 条", adapter.getItemCount()));
            }
        });
//        mAdapter.setFlag(ListAdapter.FLAG_DISABLE_ITEM_ANIMATOR); /*禁用Item动画*/

        /*-- 加载事件数据 --*/
        mMenuAdapter.addData(new DataEvent_Set(mListAdapter, 3));
        mMenuAdapter.addData(new DataEvent_Set(mListAdapter, 5));
        mMenuAdapter.addData(new DataEvent_Add(mListAdapter, 3));
        mMenuAdapter.addData(new DataEvent_Insert(mListAdapter, 1, 2));
        mMenuAdapter.addData(new DataEvent_Move(mListAdapter, 1, 3));
        mMenuAdapter.notifyDataSetChanged();
    }

    private static final class MenuViewHolder extends SimpleRecyclerViewAdapter.NotProguardViewHolder<DataEventImp> implements View.OnClickListener {
        TextView mTV_Info;

        public MenuViewHolder(View itemView) {
            super(itemView);
            mTV_Info = itemView.findViewById(R.id.view_Info);
            mTV_Info.setOnClickListener(this);
        }

        @Override
        public void onUpdateUI(DataEventImp data) {
            mTV_Info.setText(data.getInfo());
        }

        @Override
        public void onClick(View v) {
            getSaveData().call();
        }
    }

    private static final class ListViewHolder extends SimpleRecyclerViewAdapter.NotProguardViewHolder<String> implements View.OnClickListener {
        TextView mTV_Info;

        public ListViewHolder(View itemView) {
            super(itemView);
            mTV_Info = itemView.findViewById(R.id.view_Info);
            mTV_Info.setOnClickListener(this);
            itemView.findViewById(R.id.view_Delet).setOnClickListener(this);
        }

        @Override
        public void onUpdateUI(String data) {
            mTV_Info.setText(data);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.view_Info:
                    break;
                case R.id.view_Delet:
                    getAdapter().removeAndUpdata(getAdapterPosition());
                    break;
            }
        }
    }

    /* ---- 数据事件 ---- */
    private abstract static class DataEventImp extends InfoEvent {

        BaseRecyclerViewAdapter<String> mAdapter;

        public DataEventImp(BaseRecyclerViewAdapter<String> adapter, String info) {
            super(info);
            mAdapter = adapter;
        }

        BaseRecyclerViewAdapter<String> getAdapter() {
            return mAdapter;
        }

    }

    private static final class DataEvent_Set extends DataEventImp {
        int mCount;

        public DataEvent_Set(BaseRecyclerViewAdapter<String> adapter, int count) {
            super(adapter, MessageFormat.format("Set\n{0}条", count));
            mCount = count;
        }

        @Override
        public void call() {
            getAdapter().setAndUpdata(buildData(MessageFormat.format("Set({0})", mCount), mCount));
        }
    }

    private static final class DataEvent_Add extends DataEventImp {
        int mCount;

        public DataEvent_Add(BaseRecyclerViewAdapter<String> adapter, int count) {
            super(adapter, MessageFormat.format("Add\n{0}条", count));
            mCount = count;
        }

        @Override
        public void call() {
            getAdapter().addAndUpdata(buildData(MessageFormat.format("Add({0})", mCount), mCount));
        }
    }

    private static final class DataEvent_Insert extends DataEventImp {
        int mCount;
        int mIndex;

        public DataEvent_Insert(BaseRecyclerViewAdapter<String> adapter, int index, int count) {
            super(adapter, MessageFormat.format("Insert\nIndex:{0}\n{1}条", index, count));
            mCount = count;
            mIndex = index;
        }

        @Override
        public void call() {
            if (getAdapter().getItemCount() >= mIndex) {
                getAdapter().insertAndUpdata(mIndex, buildData(MessageFormat.format("Insert({0}->{1})", mIndex, mCount), mCount));
            } else {
                ToastUtil.show(MessageFormat.format("请先插入{0}条数据", mIndex));
            }
        }
    }

    private static final class DataEvent_Move extends DataEventImp {
        int mOldIndex, mNewIndex;

        public DataEvent_Move(BaseRecyclerViewAdapter<String> adapter, int oldinde, int newindex) {
            super(adapter, MessageFormat.format("Move\n{0}->{1}", oldinde, newindex));
            mOldIndex = oldinde;
            mNewIndex = newindex;
        }

        @Override
        public void call() {
            int limitcount = Math.max(mOldIndex, mNewIndex) + 1;
            if (getAdapter().getItemCount() >= limitcount) {
                getAdapter().moveAndUpdata(mOldIndex, mNewIndex);
            } else {
                ToastUtil.show(MessageFormat.format("请先插入{0}条数据", limitcount));
            }
        }
    }

    /*构建测试数据*/
    private static List<String> buildData(String tag, int count) {
        List<String> arrays = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arrays.add(MessageFormat.format("{0} _ {1}", tag, i));
        }
        return arrays;
    }


    /* ---- Demo入口 ---- */
    public static final class Demo extends DemoEvent {

        public Demo(IAppFramework appfk) {
            super(appfk, "BaseRecyclerViewAdapter" ,null);
        }

        @Override
        public void call() {
            Demo_BaseRecyclerViewAdapter.start(getAppFk());
        }
    }
}
