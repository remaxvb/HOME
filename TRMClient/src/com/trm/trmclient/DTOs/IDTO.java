package com.trm.trmclient.DTOs;

import java.io.Serializable;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public interface IDTO extends Serializable {
    public String getJSONString();
    public void parseFromJSON(String data);
}
