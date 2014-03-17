package com.haven.Haven_TRMClient.Controller.BussinessController;

import com.haven.Haven_TRMClient.DTOs.TourDTO;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public interface ITourBusiness {
	public boolean createTour(TourDTO tourDTO);

	public boolean updateTourInfo(TourDTO tourDTO);
}
