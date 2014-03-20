package com.trm.trmclient.View;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.Model.MembersModel;

public class MemberActivity extends NavigationDrawerActivity {
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.member_activity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.member);
		initActionBar();
		initNavigationDrawer();
		initUIComponent();	
		MembersModel.getInstance().registerObserver(this);
	}

	private void initUIComponent() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.member_invite:			
			//TODO: Call invite function here
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void updateFromModel(IDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleServiceResult(ServiceEventConstant serviceType,
			JSONObject data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void handleError(ServiceEventConstant serviceType, int errorCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		MembersModel.getInstance().removeObserver(this);		
		super.onDestroy();
	}
	
	
	

}
