package com.trm.trmclient.View;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.JSONKey;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.AccountDTO;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.Model.AccountModel;
import com.trm.trmclient.Utils.DialogBox;
import com.trm.trmclient.Utils.EmailValidator;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class SignInActivity extends BaseActivity implements
		View.OnClickListener {

	private Button btnSignIn;
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
		txtEmail = (EditText) findViewById(R.id.txtSignInEmail);
		txtPassword = (EditText) findViewById(R.id.txtSignInPassword);
	}

	@Override
	protected void onDestroy() {
		AccountModel.getInstance().removeObserver(this);
		super.onDestroy();
	}

	@Override
	public void onClick(View view) {
		if (view.equals(btnSignIn)) {
			String email = txtEmail.getText().toString().trim();
			// Check email valid
			if (!(new EmailValidator().validate(email))) {
				DialogBox.showMessage(this, "Wrong input",
						"Wrong email format", "OK");
				return;
			}

			// TODO: Check password valid here

			AccountDTO accountDTO = new AccountDTO();
			accountDTO.email = email;
			accountDTO.password = txtPassword.getText().toString().trim();
			MainController.getInstance().handleServiceEvent(
					ServiceEventConstant.SIGN_IN, accountDTO, this);
			return;
		}

		if (view.equals(txtSignUp)) {
			MainController.getInstance().handleSwitchActivity(
					ViewEventConstant.GOTO_SIGN_UP, this);
			return;
		}
	}

	@Override
	public void updateFromModel(final IDTO dto) {
		//
	}

	@Override
	public void handleError(ServiceEventConstant serviceType, int errorCode) {
		DialogBox.showMessage(this, "Error", "Error with error code: "
				+ errorCode, "OK");
	}

	@Override
	public void handleServiceResult(ServiceEventConstant serviceType,
			JSONObject data) {
		if (serviceType == ServiceEventConstant.SIGN_IN) {
			boolean result = false;
			String message = null;

			try {
				result = data.getBoolean(JSONKey.KEY_SUCCESS);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (result == true) {
				MainController.getInstance().handleSwitchActivity(
						ViewEventConstant.GOTO_DASHBOARD, this);
			} else {
				try {
					message = data.getString(JSONKey.KEY_MESSAGE);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				DialogBox.showMessage(this, "Error", message, "OK");
			}
		}
	}
}