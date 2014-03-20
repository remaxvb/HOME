package com.trm.trmclient.View;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.JSONKey;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Model.AccountModel;
import com.trm.trmclient.Model.ToursModel;
import com.trm.trmclient.Utils.DialogBox;

/**
 * 
 * Created by chien.nguyen Mar-18-2014
 * 
 */

public class CreateTourActivity extends BaseActivity implements OnClickListener {

	EditText edtDestination, edtTourName, edtPriceAdult, edtPriceChildren,
			edtDepartureDate, edtReturnDate, edtMaxMembers;
	Button btnCreate;
	ToursModel tourModel;
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_tour);
		// Attach view to model
		ToursModel.getInstance().registerObserver(this);
		initUIComponent();
	}

	private void initUIComponent() {
		// TODO Auto-generated method stub
		edtTourName = (EditText) findViewById(R.id.txtTourName);
		edtDestination = (EditText) findViewById(R.id.txtTourLocation);
		edtPriceAdult = (EditText) findViewById(R.id.txtTourAdultPrice);
		edtPriceChildren = (EditText) findViewById(R.id.txtTourChildPrice);
		edtDepartureDate = (EditText) findViewById(R.id.txtDepatureDate);
		edtReturnDate = (EditText) findViewById(R.id.txtReturnDate);
		edtMaxMembers = (EditText) findViewById(R.id.txtMaxMember);
		btnCreate = (Button) findViewById(R.id.btnCreateTour);
		btnCreate.setOnClickListener(this);
	}

	@Override
	public void updateFromModel(IDTO dto) {
		// TODO Auto-generated method stub
		
		
		
	}

	@Override
	public void handleServiceResult(ServiceEventConstant serviceType,
			JSONObject data) {
		// TODO Auto-generated method stub
		if (serviceType == ServiceEventConstant.CREATE_TOUR) {
			boolean result = false;
			String message = null;

			try {
				result = data.getBoolean(JSONKey.KEY_SUCCESS);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (result == true) {
//				MainController.getInstance().handleSwitchActivity(
//						ViewEventConstant.GOTO_DASHBOARD, this);
				DialogBox.showMessage(this, "Create tour", "Create tour successfully", "OK");
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

	@Override
	public void handleError(ServiceEventConstant serviceType, int errorCode) {
		// TODO Auto-generated method stub
		DialogBox.showMessage(this, "Error", "Error with error code: "
				+ errorCode, "OK");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		TourDTO tourDTO = new TourDTO();
		tourDTO.departureDate = edtDepartureDate.getText().toString().trim();
		tourDTO.destination = edtDestination.getText().toString().trim();
		tourDTO.maxMembers = edtMaxMembers.getText().toString().trim();
		tourDTO.priceAdult = edtPriceAdult.getText().toString().trim();
		tourDTO.priceChildren = edtPriceChildren.getText().toString().trim();
		tourDTO.returnDate = edtReturnDate.getText().toString().trim();
		tourDTO.title = edtTourName.getText().toString().trim();
		MainController.getInstance().handleServiceEvent(
				ServiceEventConstant.CREATE_TOUR, tourDTO, this);
	}

}
