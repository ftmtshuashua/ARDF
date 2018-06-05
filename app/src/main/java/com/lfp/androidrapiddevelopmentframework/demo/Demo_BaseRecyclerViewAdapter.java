package com.lfp.androidrapiddevelopmentframework.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.base.BaseActivity;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
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
public class Demo_BaseRecyclerViewAdapter extends BaseActivity implements View.OnClickListener {

    public static final void start(IAppFramework appfk) {
        Intent intent = new Intent(appfk.getContext(), Demo_BaseRecyclerViewAdapter.class);
        appfk.startActivity(intent);
    }

    ListAdapter mAdapter;
    TextView mTV_Info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastUtil.show(R.string.activity_recyclerview_info);
        setContentView(R.layout.demo_recycleradapter);

        Toolbar toolbar = findViewById(R.id.view_Toolbar);
        toolbar.setTitle("RecyclerView Adapter");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.arrow_left_white);


        findViewById(R.id.view_Set3).setOnClickListener(this);
        findViewById(R.id.view_Set5).setOnClickListener(this);
        findViewById(R.id.view_Add3).setOnClickListener(this);
        findViewById(R.id.view_Add5).setOnClickListener(this);
        findViewById(R.id.view_Insert1).setOnClickListener(this);
        findViewById(R.id.view_Insert2).setOnClickListener(this);
        findViewById(R.id.view_Move).setOnClickListener(this);

        mTV_Info = findViewById(R.id.view_Info);

        RecyclerView mRecyclerView = findViewById(R.id.view_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.setAdapter(mAdapter = new ListAdapter());
        mAdapter.setOnAdapterDataChange(mOnDataChange);
//        mAdapter.setFlag(ListAdapter.FLAG_DISABLE_ITEM_ANIMATOR); /*禁用Item动画*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_Set3:
                mAdapter.setAndUpdata(buildData("Set(3)", 3));
                break;
            case R.id.view_Set5:
                mAdapter.setAndUpdata(buildData("Set(5)", 5));
                break;
            case R.id.view_Add3:
                mAdapter.addAndUpdata(buildData("Add(3)", 3));
                break;
            case R.id.view_Add5:
                mAdapter.addAndUpdata(buildData("Add(5)", 5));
                break;
            case R.id.view_Insert1:
                mAdapter.insertAndUpdata(1, new Model(MessageFormat.format("{0} _ {1}", "Inset(1)", 0)));
                break;
            case R.id.view_Insert2:
                mAdapter.insertAndUpdata(1, buildData("Inset(2)", 2));
                break;
            case R.id.view_Move:
                mAdapter.moveAndUpdata(1, 3);
                break;
        }
    }

    BaseRecyclerViewAdapter.OnAdapterDataChange mOnDataChange = new BaseRecyclerViewAdapter.OnAdapterDataChange<Model>() {
        @Override
        public void onChange(BaseRecyclerViewAdapter<Model> adapter) {
            mTV_Info.setText(MessageFormat.format("data count：{0}", adapter.getItemCount()));
        }
    };

    private static final class ListAdapter extends BaseRecyclerViewAdapter<Model> {
        @NonNull
        @Override
        public BaseViewHolder<Model> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_recycleradapter_listitem, parent, false));
        }

    }

    private static final class VHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Model> implements View.OnClickListener {
        TextView mTV_Info;

        public VHolder(View itemView) {
            super(itemView);
            mTV_Info = itemView.findViewById(R.id.view_Info);
            mTV_Info.setOnClickListener(this);
            itemView.findViewById(R.id.view_Delet).setOnClickListener(this);
        }

        @Override
        public void onUpdateUI(Model data) {
            mTV_Info.setText(data.Title);
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

    static final class Model {
        String Title;

        public Model(String title) {
            Title = title;
        }
    }


    /*Demo入口*/
    public static final class Demo extends DemoEntrance {
        IAppFramework appfk;

        public Demo(IAppFramework appfk) {
            super("RecyclerViewAdapter");
            this.appfk = appfk;
        }

        @Override
        public void enter() {
            Demo_BaseRecyclerViewAdapter.start(appfk);
        }
    }

    /*构建数据*/
    List<Model> buildData(String tag, int count) {
        List<Model> arrays = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arrays.add(new Model(MessageFormat.format("{0} _ {1}", tag, i)));
        }
        return arrays;
    }


}
