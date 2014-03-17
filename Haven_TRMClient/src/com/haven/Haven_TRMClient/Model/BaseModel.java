package com.haven.Haven_TRMClient.Model;

import java.net.PortUnreachableException;
import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;
import com.haven.Haven_TRMClient.DTOs.IDTO;
import com.haven.Haven_TRMClient.View.IObserver;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class BaseModel implements ISubject, IServiceListener {
	private ArrayList<IObserver> observers;
	private IDTO dto;

	public BaseModel() {
		observers = new ArrayList<IObserver>();
	}

	@Override
	public void registerObserver(IObserver iob) {
		observers.add(iob);
		Log.d("Observerable:", "add: " + iob.toString());
	}

	@Override
	public void removeObserver(IObserver iob) {
		observers.remove(iob);
		Log.d("Observerable:", "remove: " + iob.toString());
	}

	@Override
	public void notifyObservers() {
		for (IObserver iObserver : observers) {
			iObserver.updateView(dto);
			Log.d("Observerable:", "notify: " + iObserver.toString());
		}
	}

	@Override
	public void onResponse(ServiceEventConstant serviceType,
                           JSONObject data) {
		for (IObserver iObserver : observers) {
			iObserver.handleServiceEvent(serviceType, data);
		}
	}

	public void setData(IDTO dto) {
		this.dto = dto;
	}

    public void saveData(IDTO dto) {

    }

    public IDTO loadData() {
        return null;
    }
}
