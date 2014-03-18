package com.trm.trmclient.View;

import org.json.JSONObject;

import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.DTOs.IDTO;

/**
 * Created by hieu.t.vo on 3/13/14.
 */
public interface IObserver {
	/**
	 * Update model data
	 * @param o : Model data
	 */
	void loadModel(IDTO dto);

    void handleServiceResult(ServiceEventConstant serviceType, JSONObject data);

    void handleError(ServiceEventConstant serviceType, int errorCode);

}
