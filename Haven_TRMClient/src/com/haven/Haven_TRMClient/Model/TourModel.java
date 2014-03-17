package com.haven.Haven_TRMClient.Model;


import com.haven.Haven_TRMClient.Controller.BussinessController.ITourBusiness;
import com.haven.Haven_TRMClient.DTOs.TourDTO;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class TourModel extends BaseModel implements ITourBusiness {
    private static TourModel instance;
    public static TourModel getInstance() {
        if(instance == null) {
            instance = new TourModel();
        }
        return instance;
    }

    @Override
    public boolean createTour(TourDTO tourDTO) {
        return false;
    }

    @Override
    public boolean updateTourInfo(TourDTO tourDTO) {
        return false;
    }
}

