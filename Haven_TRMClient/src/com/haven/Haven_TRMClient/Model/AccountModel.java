package com.haven.Haven_TRMClient.Model;


import com.haven.Haven_TRMClient.Constants.JSONKey;
import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;
import com.haven.Haven_TRMClient.Controller.BussinessController.IAccountBusiness;
import com.haven.Haven_TRMClient.DTOs.AccountDTO;
import com.haven.Haven_TRMClient.RailsService.ServiceCaller;
import com.haven.Haven_TRMClient.View.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hieu.t.vo on 3/13/14.
 */
public class AccountModel extends BaseModel implements IAccountBusiness {
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
}
