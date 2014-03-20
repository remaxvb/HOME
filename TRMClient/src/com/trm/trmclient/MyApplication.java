package com.trm.trmclient;

import android.app.Application;
import android.content.Context;

import com.trm.trmclient.Controller.MainController;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class MyApplication extends Application {
	private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initController();
    }
    
    public static Context getMyApplicationContext() {
    	return instance.getApplicationContext();
    }

    private void initController() {
        MainController.getInstance();
    }
}
