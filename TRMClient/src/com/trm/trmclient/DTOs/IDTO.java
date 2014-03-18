package com.trm.trmclient.DTOs;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public interface IDTO {
    public String getJSONString();
    public void parseFromJSON(String data);
}
