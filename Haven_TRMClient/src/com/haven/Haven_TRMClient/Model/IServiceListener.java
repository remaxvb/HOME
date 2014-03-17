package com.haven.Haven_TRMClient.Model;

import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;
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
    void onResponse(ServiceEventConstant serviceType, JSONObject data);
}
