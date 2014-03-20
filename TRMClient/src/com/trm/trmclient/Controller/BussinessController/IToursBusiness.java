package com.trm.trmclient.Controller.BussinessController;

import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.View.BaseActivity;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public interface IToursBusiness {

	public boolean createTour(BaseActivity sender, TourDTO tourDTO);
	public boolean updateTourInfo(BaseActivity sender, TourDTO tourDTO);
	public boolean deleteTour(BaseActivity sender, String tourId);
	/**
	 * Clear tours on local
	 */
	public void clearTours();
}
