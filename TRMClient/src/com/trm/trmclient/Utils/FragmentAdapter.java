package com.trm.trmclient.Utils;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
	private ArrayList<FragmentLayout> adapter;

	public FragmentAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		adapter = new ArrayList<FragmentLayout>();
	}

	@Override
	public Fragment getItem(int position) {
		return this.adapter.get(position);
	}

	@Override
	public int getCount() {
		return this.adapter.size();
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return this.adapter.get(position).getLayoutTitle();
    }
	
	public void addLayout(FragmentLayout fragmentLayout) {
		this.adapter.add(fragmentLayout);
	}

}
