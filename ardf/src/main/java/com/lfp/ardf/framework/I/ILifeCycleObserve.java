package com.lfp.ardf.framework.I;

import android.content.Intent;
import android.os.Bundle;

/**
 * 生命周期订阅
 * Created by LiFuPing on 2017/9/4.
 */

public interface ILifeCycleObserve {
    void onDestroy();

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onActivityResult(int requestCode, int resultCode, Intent data);


}
