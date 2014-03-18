package com.trm.trmclient.Model;

import com.trm.trmclient.Constants.ServiceEventConstant;

import org.json.JSONObject;

/**
 * Created by hieu.t.vo on 3/17/14.
 */
public interface IServiceListener {
    /**
     * Handle service event
     * @param serviceType : Type of service
     * @param data : JSONObject data response from server
     */
    void onServiceResponse(ServiceEventConstant serviceType, JSONObject data);

    void onServiceError(ServiceEventConstant serviceType, int errorCode);
}
