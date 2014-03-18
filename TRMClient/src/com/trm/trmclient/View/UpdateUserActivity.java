package com.trm.trmclient.View;

import org.json.JSONObject;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.DTOs.AccountDTO;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.Utils.DialogBox;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter.ViewBinder;


public class UpdateUserActivity extends BaseActivity implements OnClickListener {
	private EditText edtlastName, edtfirstName, edtemail, edtphone, edtdateOfBirth, edtAddress;
	private Button btnUpdate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_user);
		init();
	}
	public void init(){
		edtAddress=(EditText)findViewById(R.id.edtaddress);
		edtdateOfBirth=(EditText)findViewById(R.id.edtbirthDay);
		edtemail=(EditText)findViewById(R.id.edtemail);
		edtfirstName=(EditText)findViewById(R.id.edtfirstName);
		edtlastName=(EditText)findViewById(R.id.edtlastName);
		edtphone=(EditText)findViewById(R.id.edtphone);
		btnUpdate=(Button)findViewById(R.id.btnupdate);
		btnUpdate.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.equals(btnUpdate)){
			AccountDTO accountDTO= new AccountDTO();
			String lastName, firstName, email, phone,address,birthDay;
			lastName=edtlastName.getText().toString().trim();
			firstName=edtfirstName.getText().toString().trim();
			email=edtemail.getText().toString().trim();
			phone=edtphone.getText().toString().trim();
			address=edtAddress.getText().toString().trim();
			birthDay=edtdateOfBirth.getText().toString().trim();
			accountDTO.lastName=lastName;
			accountDTO.firstName=firstName;
			accountDTO.address=address;
			accountDTO.email=email;
			accountDTO.phone=phone;
			accountDTO.dateOfBirth=birthDay;
		}
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
		DialogBox.showMessage(this, "Error", "Error with error code: "
				+ errorCode, "OK");
	}

	
}
