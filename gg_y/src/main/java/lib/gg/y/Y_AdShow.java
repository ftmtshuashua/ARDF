package lib.gg.y;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lfp.ardf.framework.I.IAppFramework;
import com.lfp.ardf.framework.util.SimpleLifeCycleObserve;

import cx.sd.wea.nm.sp.SplashViewSettings;
import cx.sd.wea.nm.sp.SpotListener;
import cx.sd.wea.nm.sp.SpotManager;

/**
 * 展示<br/>
 * Created by LiFuPing on 2018/6/22.
 */
public class Y_AdShow {
    Context context;

    public Y_AdShow(IAppFramework appFk) {
        context = appFk.getContext();
        appFk.registeredObserve(new SimpleLifeCycleObserve() {
            @Override
            public void onDestroy() {
                super.onDestroy();
                SpotManager.getInstance(context).onDestroy();
            }
        });

    }

    /*开屏*/
    public final void show(ViewGroup splashViewContainer, Class targetClass) {
        SplashViewSettings splashViewSettings = new SplashViewSettings();
        splashViewSettings.setTargetClass(targetClass);
        splashViewSettings.setSplashViewContainerAndLayoutParams(splashViewContainer, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        SpotManager.getInstance(context).showSplash(context, splashViewSettings, new SpotListener() {
            @Override
            public void onShowSuccess() {
            }

            @Override
            public void onShowFailed(int i) {
//                cx.sd.wea.nm.cm.ErrorCode
            }

            @Override
            public void onSpotClosed() {
            }

            @Override
            public void onSpotClicked(boolean b) {
            }
        });
    }

}
