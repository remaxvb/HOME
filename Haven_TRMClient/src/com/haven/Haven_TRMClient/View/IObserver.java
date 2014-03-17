package com.haven.Haven_TRMClient.View;

import org.json.JSONObject;

import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;

/**
 * Created by hieu.t.vo on 3/13/14.
 */
public interface IObserver {
	/**
	 * Update model data
	 * @param o : Model data
	 */
	void updateView(Object o);

	/**
	 * Handle result when call service
	 * @param serviceType: Service type, in {@link ServiceEventConstant}
	 * @param data : JSON data from server
	 */
	void handleServiceEvent(ServiceEventConstant serviceType, JSONObject data);
}
