package lib.gg.y;

import android.content.Context;

import cx.sd.wea.AdManager;
import cx.sd.wea.nm.sp.SpotManager;
import cx.sd.wea.nm.sp.SpotRequestListener;
import cx.sd.wea.nm.vdo.VideoAdManager;
import cx.sd.wea.nm.vdo.VideoAdRequestListener;

/**
 * 配置<br>
 * Created by LiFuPing on 2018/6/22.
 */
public class Y_AdHolper {

    public static final void init(Context c, boolean log) {
        Context context = c.getApplicationContext();
        AdManager.getInstance(context).init("6e6856bb56d5563a", "210a85571da0ae21", log);
        SpotManager.getInstance(context).requestSpot(new SpotRequestListener() {
            @Override
            public void onRequestSuccess() {
            }

            @Override
            public void onRequestFailed(int i) {
            }
        });
    }

    public static final void onExit(Context c) {
        Context context = c.getApplicationContext();
        SpotManager.getInstance(context).onAppExit();
    }

}
