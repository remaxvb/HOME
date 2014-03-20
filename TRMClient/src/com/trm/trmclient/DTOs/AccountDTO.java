package com.trm.trmclient.DTOs;

import java.io.Serializable;

import com.trm.trmclient.Constants.JSONKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hieu.t.vo on 3/13/14.
 */
public class AccountDTO implements IDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String userID;
    public String token;
    public String firstName;
    public String lastName;
    public String email;
    public String address;
    public String phone;
    public String dateOfBirth;
    public boolean gender;
    public String avatarFileName;
    public String password;

    @Override
    public String getJSONString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSONKey.KEY_ID, userID);
            jsonObject.putOpt(JSONKey.KEY_TOKEN, token);
            jsonObject.putOpt(JSONKey.KEY_FIRSTNAME, firstName);
            jsonObject.putOpt(JSONKey.KEY_LASTNAME, lastName);
            jsonObject.putOpt(JSONKey.KEY_EMAIL, email);
            jsonObject.putOpt(JSONKey.KEY_ADDRESS, address);
            jsonObject.putOpt(JSONKey.KEY_PHONE, phone);
            jsonObject.putOpt(JSONKey.KEY_DATEOFBIRTH, dateOfBirth);
            jsonObject.putOpt(JSONKey.KEY_GENDER, gender);
            jsonObject.putOpt(JSONKey.KEY_AVATARFILENAME, avatarFileName);
            jsonObject.putOpt(JSONKey.KEY_PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public void parseFromJSON(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            userID = jsonObject.getString(JSONKey.KEY_ID);
            token = jsonObject.getString(JSONKey.KEY_TOKEN);
            firstName = jsonObject.getString(JSONKey.KEY_FIRSTNAME);
            lastName = jsonObject.getString(JSONKey.KEY_LASTNAME);
            email = jsonObject.getString(JSONKey.KEY_EMAIL);
            address = jsonObject.getString(JSONKey.KEY_ADDRESS);
            phone = jsonObject.getString(JSONKey.KEY_PHONE);
            dateOfBirth = jsonObject.getString(JSONKey.KEY_DATEOFBIRTH);
            gender = jsonObject.getBoolean(JSONKey.KEY_GENDER);
            avatarFileName = jsonObject.getString(JSONKey.KEY_AVATARFILENAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
