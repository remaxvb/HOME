package com.trm.trmclient.Model;

import java.util.ArrayList;

import android.util.Log;

import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Utils.FileUtil;
import com.trm.trmclient.View.IObserver;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class BaseModel implements ISubject {
	private final boolean DEBUG = true;
	private final String TAG = BaseModel.class.getSimpleName();
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
			iObserver.updateFromModel(dto);
			Log.d("Observerable:", "notify: " + iObserver.toString());
		}
	}

	public IDTO getDTO() {
		return dto;
	}
	
	public void setDTO(IDTO dto) {
		this.dto = dto;
	}

	/**
	 * Set DTO and save DTO to memory
	 * @param dto
	 * @param fileName
	 */
    public void saveData(IDTO dto, String fileName) {
//    	setDTO(dto);
    	FileUtil.saveObject(dto, fileName);
    }

    /**
     * Load DTO from memory
     * @param fileName
     * @return
     */
    public IDTO loadData(String fileName) {
        return (IDTO)FileUtil.readObject(fileName);
    }

}
