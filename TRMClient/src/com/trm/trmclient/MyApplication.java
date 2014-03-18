package com.trm.trmclient;

import android.app.Application;

import com.trm.trmclient.Controller.MainController;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initController();
    }

    private void initController() {
        MainController.getInstance();
    }
}
