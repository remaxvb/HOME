package com.trm.trmclient.View;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.AccountDTO;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.Model.AccountModel;
import com.trm.trmclient.Utils.DialogBox;

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
    public void loadModel(IDTO dto) {

    }

    @Override
    public void handleError(ServiceEventConstant serviceType, int errorCode) {

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
	public void handleServiceResult(ServiceEventConstant serviceType,
			JSONObject data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDestroy() {
		AccountModel.getInstance().removeObserver(this);
		super.onDestroy();
	}
	
}