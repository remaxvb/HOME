package com.trm.trmclient.Model;


import com.trm.trmclient.Constants.JSONKey;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.Controller.BussinessController.IAccountBusiness;
import com.trm.trmclient.DTOs.AccountDTO;
import com.trm.trmclient.Utils.RailsService.ServiceCaller;
import com.trm.trmclient.View.BaseActivity;
import com.trm.trmclient.View.IObserver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hieu.t.vo on 3/13/14.
 */
public class AccountModel extends BaseModel implements IAccountBusiness, IServiceListener {
    private static AccountModel instance;

    public static AccountModel getInstance() {
        if (instance == null) {
            instance = new AccountModel();
        }
        return instance;
    }

    @Override
    public boolean signIn(BaseActivity sender, String email, String password) {
        JSONObject postData = new JSONObject();
        JSONObject userInfoJson = new JSONObject();
        try {
            userInfoJson.accumulate(JSONKey.KEY_EMAIL, email);
            userInfoJson.accumulate(JSONKey.KEY_PASSWORD, password);
            postData.accumulate(JSONKey.KEY_USER, userInfoJson);
        } catch (JSONException e) {
            return false;
        }
        ServiceCaller.getInstance().executeService((BaseActivity) sender, ServiceEventConstant.SIGN_IN, true, postData, "Sign in ...");
        return true;
    }

    @Override
    public boolean signUp(BaseActivity sender, AccountDTO accountDTO) {
        JSONObject postData = new JSONObject();
        JSONObject userInfoJson = new JSONObject();
        try {
            userInfoJson.accumulate(JSONKey.KEY_EMAIL, accountDTO.email);
            userInfoJson.accumulate(JSONKey.KEY_PASSWORD, accountDTO.password);
            userInfoJson.accumulate(JSONKey.KEY_FIRSTNAME, accountDTO.firstName);
            userInfoJson.accumulate(JSONKey.KEY_LASTNAME, accountDTO.lastName);
            userInfoJson.accumulate(JSONKey.KEY_ADDRESS, accountDTO.address);
            userInfoJson.accumulate(JSONKey.KEY_PHONE, accountDTO.phone);
            userInfoJson.accumulate(JSONKey.KEY_DATEOFBIRTH, accountDTO.dateOfBirth);
            userInfoJson.accumulate(JSONKey.KEY_GENDER, accountDTO.gender);
            postData.accumulate(JSONKey.KEY_USER, userInfoJson);
        } catch (JSONException e) {
            return false;
        }
        ServiceCaller.getInstance().executeService((BaseActivity) sender, ServiceEventConstant.SIGN_UP, true, postData, "Sign up ...");
        return true;
    }

    @Override
    public boolean updateUserInfo(BaseActivity sender, AccountDTO accountDTO) {
        return false;
    }

    @Override
    public boolean updateAvatar(BaseActivity sender, Object avatar) {
        return false;
    }

    @Override
    public void onServiceResponse(ServiceEventConstant serviceType, JSONObject data) {
        switch (serviceType) {
            //Service may make model change or not
            case SIGN_IN:
            case SIGN_UP:
                notifyServiceResult(serviceType, data);
                break;
            case CREATE_TOUR:
            	notifyServiceResult(serviceType, data);
            	break;
		default:
			break;
        }
    }


    private void notifyServiceResult(ServiceEventConstant serviceType, JSONObject data) {
    	for (IObserver iObserver : observers) {
            iObserver.handleServiceResult(serviceType, data);
        }
    }
    
    @Override
    public void onServiceError(ServiceEventConstant serviceType, int errorCode) {
        for (IObserver iObserver : observers) {
            iObserver.handleError(serviceType, errorCode);
        }
    }
}
