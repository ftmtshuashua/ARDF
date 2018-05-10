package com.lfp.androidrapiddevelopmentframework;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
import com.lfp.ardf.model.NotProguard;

import java.util.ArrayList;
import java.util.List;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;

/**
 * Created by LiFuPing on 2018/5/9.
 */
public class MainActivity extends AppCompatActivity {


    ListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRecyclerView = findViewById(R.id.view_ReyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter = new ListAdapter());


        List<Model> arrays = new ArrayList<>();
        arrays.add(new Model("Ttile 1"));
        arrays.add(new Model("Ttile 3"));
        arrays.add(new Model("Ttile 2"));
        arrays.add(new Model("Ttile 4"));
        arrays.add(new Model("Ttile 5"));
        arrays.add(new Model("Ttile 6"));

        mAdapter.setDataAndUpdata(arrays);
    }


    private static final class ListAdapter extends BaseRecyclerViewAdapter<Model> {
        @NonNull
        @Override
        public BaseViewHolder<Model> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new VHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_textview, parent, false));
        }

    }

    private static final class VHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Model> {
        TextView mTV_Info;

        public VHolder(View itemView) {
            super(itemView);
            mTV_Info = itemView.findViewById(R.id.view_Info);
        }

        @Override
        public void onUpdateUI(Model data) {
            mTV_Info.setText(data.Title);
        }
    }

    static final class Model implements NotProguard {
        String Title;

        public Model(String title) {
            Title = title;
        }
    }


}
