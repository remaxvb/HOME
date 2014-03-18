package com.trm.trmclient.View;

import com.trm.trmclient.R;
import com.trm.trmclient.Utils.NavigationDrawerAdapter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public abstract class NavigationDrawerActivity extends BaseActivity {

	protected DrawerLayout drawerLayout;
	protected ActionBarDrawerToggle actionBarToggle;
	protected ListView navigationDrawer;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected void initActionBar() {
		getActionBar().setTitle("Home");
		this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		this.actionBarToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle("Home");
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("Menu");
				super.onDrawerOpened(drawerView);
			}
		};
		this.drawerLayout.setDrawerListener(actionBarToggle);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	protected void initNavigationDrawer() {
		this.navigationDrawer = (ListView) findViewById(R.id.navigation_drawer_list);
		this.navigationDrawer.setAdapter(NavigationDrawerAdapter
				.getInstance(this));
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		actionBarToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (actionBarToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
