package com.haven.Haven_TRMClient.View;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.haven.Haven_TRMClient.R;
import com.haven.Haven_TRMClient.Constants.ActionEventConstant;
import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;
import com.haven.Haven_TRMClient.Controller.MainController;
import com.haven.Haven_TRMClient.DTOs.AccountDTO;
import com.haven.Haven_TRMClient.Model.AccountModel;
import com.haven.Haven_TRMClient.Utils.DialogBox;

/**
 * Created by Hieu on 3/16/14.
 */
public class SignUpActivity extends BaseActivity implements View.OnClickListener {
    private Button btnSignUp;
    private EditText edtEmail, edtPassword, edtRepeatPassword;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        AccountModel.getInstance().registerObserver(this);
        initUIComponent();
    }

    private void initUIComponent() {
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        edtEmail = (EditText) findViewById(R.id.txtEmail);
        edtPassword = (EditText) findViewById(R.id.txtPassword);
        edtRepeatPassword = (EditText) findViewById(R.id.txtRepeatPassword);
    }

    @Override
    public void updateView(Object o) {

    }

    @Override
    public void onClick(View view) {
        if(view.equals(btnSignUp)) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.email = edtEmail.getText().toString().trim();
            accountDTO.password = edtPassword.getText().toString().trim();
            MainController.getInstance().handleServiceEvent(ServiceEventConstant.SIGN_UP, accountDTO, this);
            return;
        }
    }

	@Override
	public void handleServiceEvent(ServiceEventConstant serviceType,
			JSONObject data) {
		if(serviceType == ServiceEventConstant.SIGN_UP) {
			try {
				boolean result = data.getBoolean("success");
				if (result == true) {
					MainController.getInstance().handleSwitchActivity(
                            ActionEventConstant.GOTO_SIGN_IN, this);
				} else {
					DialogBox.showMessage(this, "Sign up error", data.getString("message"), "OK");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}