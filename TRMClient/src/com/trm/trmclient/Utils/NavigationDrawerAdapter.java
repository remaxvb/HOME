package com.trm.trmclient.Utils;

import java.util.ArrayList;

import com.trm.trmclient.Constants.NavigationConstant;
import com.trm.trmclient.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavigationDrawerAdapter extends BaseAdapter {

	private static NavigationDrawerAdapter instance = null;
	private ArrayList<NavigationDrawerItem> adapter;
	private LayoutInflater inflater;

	private NavigationDrawerAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
		this.adapter = new ArrayList<NavigationDrawerItem>();
		this.adapter.add(new NavigationDrawerItem(NavigationConstant.NAVIGATION_HOME,
				R.drawable.ic_launcher, "Home"));
		this.adapter.add(new NavigationDrawerItem(NavigationConstant.NAVIGATION_SIGNOUT,
				R.drawable.ic_launcher, "Sign Out"));
		this.adapter.add(new NavigationDrawerItem(NavigationConstant.NAVIGATION_EXIT,
				R.drawable.ic_launcher, "Exit"));
	}

	public static NavigationDrawerAdapter getInstance(Context context) {
		if (instance == null) {
			instance = new NavigationDrawerAdapter(context);
		}
		return instance;
	}

	@Override
	public int getCount() {
		return this.adapter.size();
	}

	@Override
	public NavigationDrawerItem getItem(int position) {
		return this.adapter.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.adapter.get(position).getId();
	}

	public int getPosition(NavigationDrawerItem item) {
		return this.adapter.indexOf(item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (this.adapter.get(position).hasParent()) {
			convertView = this.inflater.inflate(R.layout.navigation_subitem,
					null);
			final ImageView icon = (ImageView) convertView
					.findViewById(R.id.nav_subitem_icon);
			final TextView title = (TextView) convertView
					.findViewById(R.id.nav_subitem_title);
			final TextView counter = (TextView) convertView
					.findViewById(R.id.nav_subitem_counter);
			icon.setImageResource(this.adapter.get(position).getIconSrc());
			title.setText(this.adapter.get(position).getTitle());
			counter.setText(String.valueOf(this.adapter.get(position)
					.getCounter()));
			if (this.adapter.get(position).getCounter() > 0) {
				counter.setVisibility(View.VISIBLE);
			} else {
				counter.setVisibility(View.INVISIBLE);
			}
		} else {
			convertView = this.inflater.inflate(R.layout.navigation_item, null);
			final ImageView icon = (ImageView) convertView
					.findViewById(R.id.nav_item_icon);
			final TextView title = (TextView) convertView
					.findViewById(R.id.nav_item_title);
			icon.setImageResource(this.adapter.get(position).getIconSrc());
			title.setText(this.adapter.get(position).getTitle());
		}

		return convertView;
	}

	public void addItem(NavigationDrawerItem item) {
		if (!this.adapter.contains(item)) {
			if (item.hasParent()) {
				this.adapter.add(getPosition(item.getParent()) + 1, item);
			} else {
				this.adapter.add(item);
			}
		}
	}

	public void insertItem(int position, NavigationDrawerItem item) {
		this.adapter.add(position, item);
	}

}
