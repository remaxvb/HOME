package com.haven.Haven_TRMClient;

import android.app.Application;
import com.haven.Haven_TRMClient.Controller.MainController;

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
