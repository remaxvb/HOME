package com.trm.trmclient.Utils;

import java.util.ArrayList;

import com.trm.trmclient.R;
import com.trm.trmclient.DTOs.TourDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ToursListAdapter extends BaseAdapter {

	private ArrayList<TourDTO> adapter;
	private LayoutInflater inflater;
	
	
	public ToursListAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.adapter = new ArrayList<TourDTO>();
	}
	
	@Override
	public int getCount() {
		return this.adapter.size();
	}

	@Override
	public TourDTO getItem(int position) {
		return this.adapter.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = this.inflater.inflate(R.layout.tour_item, null);
		final TextView tourTitle = (TextView) convertView.findViewById(R.id.tour_title_text);
		tourTitle.setText(this.adapter.get(position).title);
		final TextView tourDeparture = (TextView) convertView.findViewById(R.id.tour_startdate);
		tourDeparture.setText(this.adapter.get(position).departureDate);
		final TextView tourMaxMembers = (TextView) convertView.findViewById(R.id.tour_member_total);
		tourMaxMembers.setText(this.adapter.get(position).maxMembers);
		final TextView tourAdultPrice = (TextView) convertView.findViewById(R.id.tour_budget);
		tourAdultPrice.setText(this.adapter.get(position).priceAdult);
		return convertView;
	}

	public void addTour(TourDTO tour) {
		this.adapter.add(tour);
	}
	
	public void setAdapter(ArrayList<TourDTO> adapter) {
		this.adapter = adapter;
	}
	
}
