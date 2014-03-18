package com.trm.trmclient.Controller;

import android.content.Intent;

import com.trm.trmclient.Common.ActionEvent;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.View.BaseActivity;
import com.trm.trmclient.View.CreateTourActivity;
import com.trm.trmclient.View.SignInActivity;
import com.trm.trmclient.View.SignUpActivity;
import com.trm.trmclient.View.DashboardActivity;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class ViewController {
    public void handleSwitchActivity(ActionEvent e) {
        //Handle change view here
        BaseActivity base = (BaseActivity) e.sender;
        if (e.action == ViewEventConstant.GOTO_DASHBOARD) {
            Intent t = new Intent(base, DashboardActivity.class);
            base.startActivity(t);
            return;
        }

        if (e.action == ViewEventConstant.GOTO_SIGN_UP) {
            Intent t = new Intent(base, SignUpActivity.class);
            base.startActivity(t);
            return;
        }
        
        if (e.action == ViewEventConstant.GOTO_SIGN_IN) {
            Intent t = new Intent(base, SignInActivity.class);
            base.startActivity(t);
            return;
        }
        
        if (e.action == ViewEventConstant.GOTO_CREATE_TOUR) {
            Intent t = new Intent(base, CreateTourActivity.class);
            base.startActivity(t);
            return;
        }
        
        
    }
}
