package com.haven.Haven_TRMClient.Common;

import com.haven.Haven_TRMClient.Constants.ActionEventConstant;

/**
 * Created by Hieu on 3/15/14.
 */
public class ActionEvent {
    public ActionEvent(ActionEventConstant actionEvent, Object sender) {
        this.action = actionEvent;
        this.sender = sender;
    }

    public ActionEventConstant action;
    public Object modelData;
    public Object viewData;
    public Object userData;
    public Object sender;
}
