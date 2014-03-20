package com.trm.trmclient.Utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class FragmentLayout extends Fragment {

	protected int layoutId;
	protected String layoutTitle;

	public void initFragment(int layoutId, String title) {
		this.layoutId = layoutId;
		this.layoutTitle = title;
	}

	public void setLayoutId(int layoutId) {
		this.layoutId = layoutId;
	}

	public void setLayoutTitle(String title) {
		this.layoutTitle = title;
	}

	public int getLayoutId() {
		return this.layoutId;
	}

	public String getLayoutTitle() {
		return this.layoutTitle;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(this.layoutId, container, false);
		return rootView;
	}

}
