package com.trm.trmclient.View;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.R;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.Model.AccountModel;

/**
 * 
 * @author chien.nguyen
 *
 */
public class WelcomeActivity extends BaseActivity implements View.OnClickListener {
	
	// Define all components
		private Button btnSignIn;
		private Button btnSignUp;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		getActionBar().hide();
		// Attach view to model
		initUIComponent();
	}
	
	public void initUIComponent(){
			btnSignIn = (Button) findViewById(R.id.btnWelcomeSignIn);
			btnSignUp = (Button) findViewById(R.id.btnWelcomeSignUp);
			btnSignIn.setOnClickListener(this);
			btnSignUp.setOnClickListener(this);
	}

	@Override
	public void loadModel(IDTO dto) {
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnWelcomeSignIn: {
			MainController.getInstance().handleSwitchActivity(
					ViewEventConstant.GOTO_SIGN_IN, this);
		}

			break;
		case R.id.btnWelcomeSignUp: {
			MainController.getInstance().handleSwitchActivity(
					ViewEventConstant.GOTO_SIGN_UP, this);
		}
			break;
		}
	}


}
