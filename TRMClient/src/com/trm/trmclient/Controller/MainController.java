package com.trm.trmclient.Controller;


import org.json.JSONObject;

import com.trm.trmclient.Common.ActionEvent;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.DTOs.AccountDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Model.AccountModel;
import com.trm.trmclient.Model.TourModel;
import com.trm.trmclient.Utils.ALog;
import com.trm.trmclient.Utils.RailsService.ServiceCaller;
import com.trm.trmclient.View.BaseActivity;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class MainController implements ServiceCaller.ServiceCallback{
	private static boolean DEBUG = true;
	private static String TAG = MainController.class.getSimpleName();
    private static MainController instance = new MainController();

    private ViewController viewController;


    public static synchronized MainController getInstance() {
        return instance;
    }

    private MainController() {
        viewController = new ViewController();

        ServiceCaller.getInstance().setServiceCallback(this);
    }

    public void handleSwitchActivity(ViewEventConstant actionEvent, Object sender) {
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
    	ALog.d(DEBUG, TAG, "Receive: " + data.toString());
        switch (serviceType) {
            case SIGN_IN:
            case SIGN_UP:
                AccountModel.getInstance().onServiceResponse(serviceType, data);
                break;
            case CREATE_TOUR:
            	TourModel.getInstance().onServiceResponse(serviceType, data);
                break;
        }

    }

    @Override
    public void onError(ServiceEventConstant serviceType, int errorCode) {
        switch (serviceType) {
            case SIGN_IN:
            case SIGN_UP:
                AccountModel.getInstance().onServiceError(serviceType, errorCode);
                break;
            case CREATE_TOUR:
            	TourModel.getInstance().onServiceError(serviceType, errorCode);
                break;
        }
    }
}
