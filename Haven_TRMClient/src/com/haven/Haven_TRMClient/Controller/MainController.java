package com.haven.Haven_TRMClient.Controller;


import com.haven.Haven_TRMClient.Model.AccountModel;
import com.haven.Haven_TRMClient.Model.TourModel;
import org.json.JSONObject;

import android.util.Log;

import com.haven.Haven_TRMClient.Common.ActionEvent;
import com.haven.Haven_TRMClient.Constants.ActionEventConstant;
import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;
import com.haven.Haven_TRMClient.DTOs.AccountDTO;
import com.haven.Haven_TRMClient.DTOs.TourDTO;
import com.haven.Haven_TRMClient.RailsService.ServiceCaller;
import com.haven.Haven_TRMClient.View.BaseActivity;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class MainController implements ServiceCaller.ServiceCallback{
    private static MainController instance = new MainController();

    private ViewController viewController;


    public static synchronized MainController getInstance() {
        return instance;
    }

    private MainController() {
        viewController = new ViewController();

        ServiceCaller.getInstance().setServiceCallback(this);
    }

    public void handleSwitchActivity(ActionEventConstant actionEvent, Object sender) {
            viewController.handleSwitchActivity(new ActionEvent(actionEvent, sender));
    }

    public void handleServiceEvent(ServiceEventConstant serviceType, Object data, Object sender) {
        if(serviceType == ServiceEventConstant.SIGN_IN) {
            //Call sign in here
            AccountDTO accountDTO = (AccountDTO) data;
            AccountModel.getInstance().signIn((BaseActivity) sender, accountDTO.email, accountDTO.password);
            return;
        }

        if(serviceType == ServiceEventConstant.SIGN_UP) {
        	AccountDTO accountDTO = (AccountDTO) data;
            AccountModel.getInstance().signUp((BaseActivity) sender, accountDTO);
        }

        if(serviceType == ServiceEventConstant.CREATE_TOUR) {
            //Call create tour here
            TourModel.getInstance().createTour((TourDTO) data);
            return;
        }
    }

    @Override
    public void onReceiveResponse(ServiceEventConstant serviceType, JSONObject data) {
        if(serviceType == ServiceEventConstant.SIGN_IN) {
            Log.d("SIGN_IN", data.toString());
            AccountModel.getInstance().onResponse(serviceType, data);
            return;
        }
        
        if(serviceType == ServiceEventConstant.SIGN_UP) {
            Log.d("SIGN_UP", data.toString());
            AccountModel.getInstance().onResponse(serviceType, data);
            return;
        }
    }

    @Override
    public void onError(ServiceEventConstant serviceType, Object data) {

    }
}
