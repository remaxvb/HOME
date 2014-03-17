package com.haven.Haven_TRMClient.Controller;

import android.content.Intent;

import com.haven.Haven_TRMClient.Common.ActionEvent;
import com.haven.Haven_TRMClient.Constants.ActionEventConstant;
import com.haven.Haven_TRMClient.View.BaseActivity;
import com.haven.Haven_TRMClient.View.SignInActivity;
import com.haven.Haven_TRMClient.View.SignUpActivity;
import com.haven.Haven_TRMClient.View.TourActivity;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class ViewController {
    public void handleSwitchActivity(ActionEvent e) {
        //Handle change view here
        BaseActivity base = (BaseActivity) e.sender;
        if (e.action == ActionEventConstant.GOTO_TOURACTIVITY) {
            Intent t = new Intent(base, TourActivity.class);
            base.startActivity(t);
            return;
        }

        if (e.action == ActionEventConstant.GOTO_SIGN_UP) {
            Intent t = new Intent(base, SignUpActivity.class);
            base.startActivity(t);
            return;
        }
        
        if (e.action == ActionEventConstant.GOTO_SIGN_IN) {
            Intent t = new Intent(base, SignInActivity.class);
            base.startActivity(t);
            return;
        }
        
        
    }
}
