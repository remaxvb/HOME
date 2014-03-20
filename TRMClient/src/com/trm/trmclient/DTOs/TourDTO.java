package com.trm.trmclient.DTOs;

import org.json.JSONException;
import org.json.JSONObject;

import com.trm.trmclient.Constants.JSONKey;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class TourDTO implements IDTO {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String id;
    public String title;
    public String destination;
    public String priceAdult;
    public String priceChildren;
    public String departureDate;
    public String returnDate;
    public String managerId;
    public String maxMembers;
    public String currency;
    public String flag;
    @Override
    public String getJSONString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt(JSONKey.KEY_TOURID, id );
            jsonObject.putOpt(JSONKey.KEY_DEPARTUREDATE, departureDate);
            jsonObject.putOpt(JSONKey.KEY_TITLE, title);
            jsonObject.putOpt(JSONKey.KEY_DESTINATION, destination);
            jsonObject.putOpt(JSONKey.KEY_PRICEADULT, priceAdult);
            jsonObject.putOpt(JSONKey.KEY_PRICECHILDREN, priceChildren);
            jsonObject.putOpt(JSONKey.KEY_RETURNDATE, returnDate);
            jsonObject.putOpt(JSONKey.KEY_MAXMEMMBER, maxMembers);
            jsonObject.putOpt(JSONKey.KEY_CURRENCY, currency);
            jsonObject.putOpt(JSONKey.KEY_FLAG, flag);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    @Override
    public void parseFromJSON(String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            id = jsonObject.getString(JSONKey.KEY_ID);
            title= jsonObject.getString(JSONKey.KEY_TITLE);
            departureDate= jsonObject.getString(JSONKey.KEY_DEPARTUREDATE);
            destination= jsonObject.getString(JSONKey.KEY_DESTINATION);
            priceAdult= jsonObject.getString(JSONKey.KEY_PRICEADULT);
            priceChildren= jsonObject.getString(JSONKey.KEY_PRICECHILDREN);
            returnDate= jsonObject.getString(JSONKey.KEY_RETURNDATE);
            maxMembers= jsonObject.getString(JSONKey.KEY_MAXMEMMBER);
            currency= jsonObject.getString(JSONKey.KEY_CURRENCY);
            flag = jsonObject.getString(JSONKey.KEY_FLAG);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
