package com.trm.trmclient.Model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.trm.trmclient.Constants.JSONKey;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Controller.BussinessController.ITourBusiness;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Utils.ALog;
import com.trm.trmclient.Utils.RailsService.ServiceCaller;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class TourModel extends BaseModel implements ITourBusiness,
		IServiceListener {
	private final boolean DEBUG = true;
	private final String TAG = TourModel.class.getSimpleName();
	
	ArrayList<TourDTO> tourList;
	
	private static TourModel instance;

	public static TourModel getInstance() {
		if (instance == null) {
			instance = new TourModel();
		}
		return instance;
	}

	private TourModel() {
		tourList = new ArrayList<TourDTO>();
	}
	
	@Override
	public boolean createTour(TourDTO tourDTO) {
		JSONObject postData = new JSONObject();
		
		JSONObject userData = new JSONObject();
		try {
			userData.putOpt(JSONKey.KEY_ID, "1");
			userData.putOpt(JSONKey.KEY_TOKEN, "hsdfjdsla9323");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		
		JSONObject tourData = new JSONObject();
		try {
			tourData.accumulate(JSONKey.KEY_TITLE, tourDTO.title);
			tourData.accumulate(JSONKey.KEY_DESTINATION,
					tourDTO.destination);
			tourData.accumulate(JSONKey.KEY_DEPARTUREDATE,
					tourDTO.departureDate);
			tourData.accumulate(JSONKey.KEY_MAXMEMMBER, tourDTO.maxMembers);
			tourData.accumulate(JSONKey.KEY_PRICEADULT, tourDTO.priceAdult);
			tourData.accumulate(JSONKey.KEY_PRICECHILDREN,
					tourDTO.priceChildren);
			tourData.accumulate(JSONKey.KEY_RETURNDATE, tourDTO.returnDate);
			postData.putOpt(JSONKey.KEY_USER, userData);
			postData.putOpt(JSONKey.KEY_TOUR, tourData);
			
			ALog.d(DEBUG, TAG, postData.toString());

		} catch (Exception e) {
			return false;
		}
		ServiceCaller.getInstance().executeService(null,
				ServiceEventConstant.CREATE_TOUR, true, postData,
				"Creating tour...");
		return true;
	}

	@Override
	public boolean updateTourInfo(TourDTO tourDTO) {
		return false;
	}

	@Override
	public void onServiceResponse(ServiceEventConstant serviceType,
			JSONObject data) {
		// TODO: handle service result, save model data
		if(serviceType == ServiceEventConstant.CREATE_TOUR) {
			
		}
	}

	@Override
	public void onServiceError(ServiceEventConstant serviceType, int errorCode) {
		
	}
}
