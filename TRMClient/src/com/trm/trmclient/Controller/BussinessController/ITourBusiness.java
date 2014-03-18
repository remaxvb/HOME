package com.trm.trmclient.Controller.BussinessController;

import com.trm.trmclient.DTOs.TourDTO;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public interface ITourBusiness {
	public boolean createTour(TourDTO tourDTO);

	public boolean updateTourInfo(TourDTO tourDTO);
}
