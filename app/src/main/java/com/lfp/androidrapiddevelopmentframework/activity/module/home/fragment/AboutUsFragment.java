package com.lfp.androidrapiddevelopmentframework.activity.module.home.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfp.androidrapiddevelopmentframework.R;
import com.lfp.androidrapiddevelopmentframework.api.Apiserver;
import com.lfp.androidrapiddevelopmentframework.base.BaseFragment;
import com.lfp.androidrapiddevelopmentframework.demo.Demo_WebActivity;
import com.lfp.androidrapiddevelopmentframework.event.IEvent;
import com.lfp.androidrapiddevelopmentframework.net.ParseOkHttpResponse;
import com.lfp.androidrapiddevelopmentframework.net.UnifyRespond;
import com.lfp.androidrapiddevelopmentframework.util.ActionBarControl;
import com.lfp.ardf.adapter.BaseRecyclerViewAdapter;
import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.model.NotProguard;
import com.lfp.ardf.module.net.OkHttpRequest;
import com.lfp.ardf.util.ApkUtil;
import com.lfp.ardf.util.ScreenUtil;
import com.lfp.ardf.util.ToastUtil;
import com.lfp.ardf.util.ViewUtil;

/**
 * 关于<br/>
 * Created by LiFuPing on 2018/6/22.
 */
public class AboutUsFragment extends BaseFragment {

    public static final Fragment newInstance() {
        return new AboutUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_aboutus, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new ActionBarControl(view).setfitsSystemWindows()
                .setTitle("关于我们");


        RecyclerView mRecyclerView = view.findViewById(R.id.view_RecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ListAdapter mAdapter = new ListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        /*数据*/
        mAdapter.addData(
                new WebMenu(getAppFk(), "给个赞", "http://3888.group/ardf/issues")
                        .setTopLineHeight(ScreenUtil.dip2px(15))
                        .setIco(R.mipmap.about_zan)
        );
        mAdapter.addData(
                new WebMenu(getAppFk(), "分享", "http://3888.group/ardf/issues")
                        .setIco(R.mipmap.about_share)
        );
        mAdapter.addData(
                new WebMenu(getAppFk(), "问题反馈", "http://3888.group/ardf/issues")
                        .setIco(R.mipmap.about_tip)
        );
        mAdapter.addData(
                new ExamineVersion(getAppFk())
                        .setTopLineHeight(ScreenUtil.dip2px(15))
                        .setIco(R.mipmap.about_version)
        );
        mAdapter.notifyDataSetChanged();
    }


    private static final class ListAdapter extends BaseRecyclerViewAdapter<IEvent> {

        @NonNull
        @Override
        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new InfoMenuHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_aboutus_listitem_infomenu, parent, false));
        }
    }

    private static final class InfoMenuHolder extends BaseRecyclerViewAdapter.BaseViewHolder<InfoMenu> implements View.OnClickListener {
        ImageView mIV_Ico;
        TextView mTV_Title, mTV_Info, mTV_Hint;
        View mV_ClickTag;

        View mV_Root;

        public InfoMenuHolder(View itemView) {
            super(itemView);
            mIV_Ico = itemView.findViewById(R.id.view_Ico);
            mTV_Title = itemView.findViewById(R.id.view_Title);
            mTV_Info = itemView.findViewById(R.id.view_Info);
            mTV_Hint = itemView.findViewById(R.id.view_Hint);
            mV_ClickTag = itemView.findViewById(R.id.view_ClickTag);
            mV_Root = itemView.findViewById(R.id.layout_Root);
            mV_Root.setOnClickListener(this);
        }

        @Override
        public void onUpdateUI(InfoMenu data) {
            FrameLayout.LayoutParams mParams = (FrameLayout.LayoutParams) mV_Root.getLayoutParams();
            mParams.setMargins(0, data.getTopLineHeight(), 0, 0);

            ViewUtil.setVisibility(mTV_Info, TextUtils.isEmpty(data.getInfo()) ? View.GONE : View.VISIBLE);
            ViewUtil.setVisibility(mTV_Hint, TextUtils.isEmpty(data.getHint()) ? View.GONE : View.VISIBLE);
            ViewUtil.setVisibility(mIV_Ico, data.getIco() == 0 ? View.INVISIBLE : View.VISIBLE);

            mTV_Title.setText(data.getTitle());
            if (!TextUtils.isEmpty(data.getInfo())) mTV_Info.setText(data.getInfo());
            if (!TextUtils.isEmpty(data.getHint())) mTV_Info.setText(data.getHint());
            if (data.getIco() != 0) mIV_Ico.setImageResource(data.getIco());
        }

        @Override
        public void onClick(View v) {
            getSaveData().call();
        }
    }


    /*------------ 菜单样式 -------------*/
    private abstract static class InfoMenu implements IEvent {
        int res_ico;
        String txt_title;
        String txt_info;
        String txt_hint;
        int color_hint;

        IAppFramework mAppFk;

        int top_line_height;/*顶部分割线高度*/


        public InfoMenu(IAppFramework appfk, String title) {
            super();
            mAppFk = appfk;
            setTitle(title);
            top_line_height = ScreenUtil.dip2px(1);
        }

        public InfoMenu setIco(int res_ico) {
            this.res_ico = res_ico;
            return this;
        }

        public InfoMenu setTitle(String txt_title) {
            this.txt_title = txt_title;
            return this;
        }

        public InfoMenu setInfo(String txt_info) {
            this.txt_info = txt_info;
            return this;
        }

        public InfoMenu setHint(String txt_hint) {
            this.txt_hint = txt_hint;
            return this;
        }

        public InfoMenu setColorHint(int color_hint) {
            this.color_hint = color_hint;
            return this;
        }

        public int getIco() {
            return res_ico;
        }

        public String getTitle() {
            return txt_title;
        }

        public String getInfo() {
            return txt_info;
        }

        public String getHint() {
            return txt_hint;
        }

        public int getColorHint() {
            return color_hint;
        }

        public int getTopLineHeight() {
            return top_line_height;
        }

        public InfoMenu setTopLineHeight(int top_line_height) {
            this.top_line_height = top_line_height;
            return this;
        }

        public IAppFramework getAppFk() {
            return mAppFk;
        }
    }

    private static final class WebMenu extends InfoMenu {
        String url;


        public WebMenu(IAppFramework appfk, String title, String url) {
            super(appfk, title);
            this.url = url;
        }


        @Override
        public void call() {
            Demo_WebActivity.Config config = new Demo_WebActivity.Config(url);
            config.setTitle(getTitle());
            Demo_WebActivity.start(getAppFk(), config);
        }
    }

    private static final class ExamineVersion extends InfoMenu {

        public ExamineVersion(IAppFramework appfk) {
            super(appfk, "检查更新");
        }

        @Override
        public void call() {
            OkHttpRequest request = Apiserver.getVersion();
            request.setRequestListener(
                    new UnifyRespond<OkHttpRequest>(getAppFk()) {
                        @Override
                        public void onComplete(OkHttpRequest request) {
                            ParseOkHttpResponse pr = new ParseOkHttpResponse(request);

                            if (pr.isSuccess(true)) {
                                final Version version = pr.getData(Version.class);
                                if (ApkUtil.getVersionCode() < version.c) {
                                    new AlertDialog.Builder(getAppFk().getContext())
                                            .setTitle("发现新版本")
                                            .setMessage(version.m) // 这里是版本更新信息
                                            .setNegativeButton("马上升级",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(version.d));
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            getAppFk().getContext().startActivity(intent);
                                                            // ps：这里示例点击“马上升级”按钮之后简单地调用系统浏览器进行新版本的下载，
                                                            // 但强烈建议开发者实现自己的下载管理流程，这样可以获得更好的用户体验。
                                                        }
                                                    })
                                            .setPositiveButton("下次再说",
                                                    new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();
                                                        }
                                                    }).create().show();
                                } else {
                                    ToastUtil.show("当前版本已经是最新版!");
                                }
                            }
                        }
                    });
            request.start();
        }

        private static final class Version implements NotProguard {
            int c;
            String v;
            String d;
            String m;
        }

    }

}
