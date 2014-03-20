package com.trm.trmclient.DTOs;

import java.util.ArrayList;

public class ToursDTO implements IDTO {
	private static final long serialVersionUID = 1L;

	ArrayList<TourDTO> tourList;

	public ToursDTO() {
		tourList = new ArrayList<TourDTO>();
	}

	public boolean addTour(TourDTO tourDTO) {
		tourList.add(tourDTO);
		return true;
	}

	public TourDTO getTour(String tourID) {
		for(TourDTO tourDTO : tourList) {
			if(tourDTO.id.equals(tourID)) {
				return tourDTO;
			}
		}
		return null;
	}

	public boolean removeTour(TourDTO tourDTO) {
		tourList.remove(tourDTO);
		return true;
	}

	public boolean clearTours() {
		tourList.clear();
		return true;
	}

	@Override
	public String getJSONString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void parseFromJSON(String data) {
		// TODO Auto-generated method stub

	}

}
