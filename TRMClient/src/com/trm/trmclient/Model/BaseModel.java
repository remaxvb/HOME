package com.trm.trmclient.Model;

import java.net.PortUnreachableException;
import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.View.IObserver;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class BaseModel implements ISubject {
	protected ArrayList<IObserver> observers;
	protected IDTO dto;

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
			iObserver.loadModel(dto);
			Log.d("Observerable:", "notify: " + iObserver.toString());
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
