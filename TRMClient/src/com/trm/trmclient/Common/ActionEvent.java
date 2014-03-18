package com.trm.trmclient.Common;

import com.trm.trmclient.Constants.ViewEventConstant;

/**
 * Created by Hieu on 3/15/14.
 */
public class ActionEvent {
    public ActionEvent(ViewEventConstant actionEvent, Object sender) {
        this.action = actionEvent;
        this.sender = sender;
    }

    public ViewEventConstant action;
    public Object modelData;
    public Object viewData;
    public Object userData;
    public Object sender;
}
