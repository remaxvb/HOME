package com.trm.trmclient.Utils;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.content.Context;

import com.trm.trmclient.R;

public class TourFragmentLayout extends FragmentLayout {

	private ToursListAdapter tourAdapter;
	private ListView tourList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = super.onCreateView(inflater, container,
				savedInstanceState);
		  
//		if (tourAdapter==null) {
//			this.tourAdapter = new ToursListAdapter(getActivity());
//		}
//		tourList = (ListView) rootView.findViewById(R.id.display_list);
//		tourList.setAdapter(tourAdapter);
		return rootView;
	}
	
	public void setAdapter(ToursListAdapter adapter) {
		this.tourAdapter = adapter;
	}

}
