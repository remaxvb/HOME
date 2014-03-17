package com.haven.Haven_TRMClient.View;

import com.haven.Haven_TRMClient.Constants.JSONKey;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haven.Haven_TRMClient.R;
import com.haven.Haven_TRMClient.Constants.ActionEventConstant;
import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;
import com.haven.Haven_TRMClient.Controller.MainController;
import com.haven.Haven_TRMClient.DTOs.AccountDTO;
import com.haven.Haven_TRMClient.Model.AccountModel;
import com.haven.Haven_TRMClient.Utils.DialogBox;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class SignInActivity extends BaseActivity implements
		View.OnClickListener {

	private Button btnSignIn, btnUpdate;
	private EditText txtPassword, txtEmail;
	private TextView txtSignUp;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_in);
		// Attach view to model
		AccountModel.getInstance().registerObserver(this);
		initUIComponent();
	}

	private void initUIComponent() {
		btnSignIn = (Button) findViewById(R.id.btnSignIn);
		btnSignIn.setOnClickListener(this);

		txtSignUp = (TextView) findViewById(R.id.txtSignUp);
		txtSignUp.setOnClickListener(this);

		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
	}

	@Override
	public void updateView(Object o) {

	}

	@Override
	protected void onDestroy() {
		AccountModel.getInstance().removeObserver(this);
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		if (view.equals(btnSignIn)) {
			AccountDTO accountDTO = new AccountDTO();
			accountDTO.email = txtEmail.getText().toString().trim();
			accountDTO.password = txtPassword.getText().toString().trim();
			MainController.getInstance().handleServiceEvent(
					ServiceEventConstant.SIGN_IN, accountDTO, this);
			return;
		}

		if (view.equals(txtSignUp)) {
			MainController.getInstance().handleSwitchActivity(
                    ActionEventConstant.GOTO_SIGN_UP, this);
			return;
		}
	}

	@Override
	public void handleServiceEvent(ServiceEventConstant serviceType,
			JSONObject data) {
		if (serviceType == ServiceEventConstant.SIGN_IN) {
			try {
				boolean result = data.getBoolean(JSONKey.KEY_SUCCESS);
				if (result == true) {
					MainController.getInstance().handleSwitchActivity(
                            ActionEventConstant.GOTO_TOURACTIVITY, this);
				} else {
					DialogBox.showMessage(this, "Error", data.getString("errors"), "OK");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}