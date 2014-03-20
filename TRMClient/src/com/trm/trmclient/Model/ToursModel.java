package com.trm.trmclient.Model;

import org.json.JSONException;
import org.json.JSONObject;

import android.webkit.WebView.FindListener;

import com.trm.trmclient.Constants.JSONKey;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Controller.BussinessController.IToursBusiness;
import com.trm.trmclient.DTOs.AccountDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.DTOs.ToursDTO;
import com.trm.trmclient.Utils.ALog;
import com.trm.trmclient.Utils.RailsService.ServiceCaller;
import com.trm.trmclient.View.BaseActivity;
import com.trm.trmclient.View.IObserver;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class ToursModel extends BaseModel implements IToursBusiness,
		IServiceListener {
	
	@Override
	public void onServiceError(ServiceEventConstant serviceType, int errorCode) {
		// TODO Auto-generated method stub
		
	}

	private final boolean DEBUG = true;
	private final String TAG = ToursModel.class.getSimpleName();
	public String dataTextTour;
	private static ToursModel instance = null;

	public static ToursModel getInstance() {
		if (instance == null) {
			instance = new ToursModel();
		}
		return instance;
	}

	private ToursModel() {
		dto = loadData("ToursDTO.data");
		if(dto == null) {
			dto = new ToursDTO();
		}
	}

	@Override
	public boolean updateTourInfo(BaseActivity sender, TourDTO tourDTO) {
		JSONObject postData = new JSONObject();
		JSONObject userData = new JSONObject();
		

		AccountDTO accountDTO = (AccountDTO) AccountModel.getInstance()
				.getDTO();

		try {
			userData.putOpt(JSONKey.KEY_ID, accountDTO.userID);
			userData.putOpt(JSONKey.KEY_TOKEN, accountDTO.token);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject tourData = new JSONObject();

		try {
			tourData.accumulate(JSONKey.KEY_TOURID, tourDTO.id);
			tourData.accumulate(JSONKey.KEY_TITLE, tourDTO.title);
			tourData.accumulate(JSONKey.KEY_DEPARTUREDATE,
					tourDTO.departureDate);
			tourData.accumulate(JSONKey.KEY_MAXMEMMBER, tourDTO.maxMembers);
			tourData.accumulate(JSONKey.KEY_PRICEADULT, tourDTO.priceAdult);
			tourData.accumulate(JSONKey.KEY_PRICECHILDREN,
					tourDTO.priceChildren);
			tourData.accumulate(JSONKey.KEY_RETURNDATE, tourDTO.returnDate);
			tourData.accumulate(JSONKey.KEY_CURRENCY, tourDTO.currency);
			tourData.accumulate(JSONKey.KEY_IDMANAGER, tourDTO.managerId);
			postData.putOpt(JSONKey.KEY_USER, userData);
			postData.putOpt(JSONKey.KEY_TOUR, tourData);

			ALog.d(DEBUG, TAG, postData.toString());

		} catch (JSONException e) {
			return false;
		}
		ServiceCaller.getInstance().executeService((BaseActivity) sender,
				ServiceEventConstant.UPDATE_TOUR, true, postData,
				"Updating tour...");
		return true;
	}

	@Override
	public void onServiceResponse(ServiceEventConstant serviceType,
			JSONObject data) {
		// TODO: handle service result, save model data
		switch (serviceType) {
		case CREATE_TOUR:
			try {
				boolean result = data.getBoolean(JSONKey.KEY_SUCCESS);
				if (result == true) {
					TourDTO tourDTO = new TourDTO();
					JSONObject tourData = new JSONObject();
					tourData = data.getJSONObject(JSONKey.KEY_TOUR);
					tourDTO.parseFromJSON(tourData.toString());
					//Save new tour in array list
					ToursDTO toursDTO = (ToursDTO)dto;
					toursDTO.addTour(tourDTO);
					saveData(dto, "ToursDTO.data");
					ALog.d(DEBUG, TAG, tourDTO.toString());

					// Update data to view
					notifyObservers();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			notifyServiceResult(serviceType, data);
			// notifyServiceResult(serviceType, data);
			break;

		case UPDATE_TOUR:
			try {
				boolean result = data.getBoolean(JSONKey.KEY_SUCCESS);
				if (result == true) {
					TourDTO tourDTO = new TourDTO();
					JSONObject tourData = new JSONObject();
					tourData = data.getJSONObject(JSONKey.KEY_TOUR);
					tourDTO.parseFromJSON(tourData.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notifyServiceResult(serviceType, data);

			break;

		case DELETE_TOUR:

			try {
				boolean result = data.getBoolean(JSONKey.KEY_SUCCESS);
				TourDTO tourDTO = new TourDTO();
				JSONObject tourFlag = new JSONObject();
				tourFlag = data.getJSONObject(JSONKey.KEY_TOUR);
				tourDTO.parseFromJSON(tourFlag.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			notifyObservers();
			break;
		default:
			break;
		}

	}

	@Override
	public boolean createTour(BaseActivity sender, TourDTO tourDTO) {
		JSONObject postData = new JSONObject();
		AccountDTO accountDTO = (AccountDTO) AccountModel.getInstance()
				.getDTO();

		JSONObject userData = new JSONObject();
		try {
			userData.putOpt(JSONKey.KEY_ID, accountDTO.userID);
			userData.putOpt(JSONKey.KEY_TOKEN, accountDTO.token);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

		JSONObject tourData = new JSONObject();
		try {
			tourData.accumulate(JSONKey.KEY_TITLE, tourDTO.title);
			tourData.accumulate(JSONKey.KEY_DESTINATION, tourDTO.destination);
			tourData.accumulate(JSONKey.KEY_DEPARTUREDATE,
					tourDTO.departureDate);
			tourData.accumulate(JSONKey.KEY_MAXMEMMBER, tourDTO.maxMembers);
			tourData.accumulate(JSONKey.KEY_PRICEADULT, tourDTO.priceAdult);
			tourData.accumulate(JSONKey.KEY_PRICECHILDREN,
					tourDTO.priceChildren);
			tourData.accumulate(JSONKey.KEY_RETURNDATE, tourDTO.returnDate);
			tourData.accumulate(JSONKey.KEY_IDMANAGER, accountDTO.userID);
			tourData.accumulate(JSONKey.KEY_CURRENCY, tourDTO.currency);
			postData.putOpt(JSONKey.KEY_USER, userData);
			postData.putOpt(JSONKey.KEY_TOUR, tourData);

			ALog.d(DEBUG, TAG, postData.toString());

		} catch (JSONException e) {
			return false;
		}
		ServiceCaller.getInstance().executeService((BaseActivity) sender,
				ServiceEventConstant.CREATE_TOUR, true, postData,
				"Creating tour...");
		return true;
	}

	@Override
	public boolean deleteTour(BaseActivity sender, String tourId) {
		JSONObject postData = new JSONObject();
		JSONObject tourFlag = new JSONObject();
		try {
			tourFlag.accumulate(JSONKey.KEY_TOURID, tourId);
			postData.accumulate(JSONKey.KEY_TOUR, tourFlag);
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		ServiceCaller.getInstance()
				.executeService((BaseActivity) sender,
						ServiceEventConstant.DELETE_TOUR, true, postData,
						"Deleting...");

		return true;
	}
	
	private void notifyServiceResult(ServiceEventConstant serviceType,
			JSONObject data) {
		for (IObserver iObserver : observers) {
			iObserver.handleServiceResult(serviceType, data);
		}
	}

	@Override
	public void clearTours() {
		((ToursDTO)dto).clearTours();	
		saveData(dto, "ToursDTO.data");
	}
}
