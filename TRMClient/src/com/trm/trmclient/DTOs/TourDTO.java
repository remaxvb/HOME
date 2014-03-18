package com.trm.trmclient.DTOs;

import java.util.Date;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class TourDTO implements IDTO {
    
    public String title;
    public String destination;
    public String priceAdult;
    public String priceChildren;
    public String departureDate;
    public String returnDate;
    public String idManager;
    public int maxMembers;
    public String currency;

    @Override
    public String getJSONString() {
        return null;
    }

    @Override
    public void parseFromJSON(String data) {

    }


}
