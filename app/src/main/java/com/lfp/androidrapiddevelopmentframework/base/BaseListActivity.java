package com.lfp.androidrapiddevelopmentframework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;

/**
 * <pre>
 * desc:
 *      ListActivity
 *
 * function:
 *
 * Created by LiFuPing on 2018/7/26.
 * </pre>
 */
public abstract class BaseListActivity extends BaseActivity {
    ActionBarControl mActionBarControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycerview);
        mActionBarControl = new ActionBarControl(getActivity())
                .setfitsSystemWindows()
                .setTitle("ListActivity")
                .showBack()
                .setBackFinishActivity(getActivity())
        ;

        RecyclerView mRecyclerView = findViewById(R.id.view_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        onCreate(mRecyclerView);
    }

    protected abstract void onCreate(RecyclerView mRecyclerView)  ;

    protected ActionBarControl getActionBarControl() {
        return mActionBarControl;
    }


}
