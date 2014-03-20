package com.trm.trmclient.View;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.trm.trmclient.R;
import com.trm.trmclient.Constants.JSONKey;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Model.ToursModel;
import com.trm.trmclient.Utils.DialogBox;

public class UpdateTourActivity extends BaseActivity implements View.OnClickListener {
	private EditText edtidTour, edtAdult, edtChildren,
			edtdepartureDate, edtCurrency, edtreturnDate, edtmaxMembers, edtManager;
	private Button btnUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.update_tour);
		// Attach view to model
		ToursModel.getInstance().registerObserver(this);
		initUIComponent();
	}

	public void initUIComponent() {

		edtidTour = (EditText) findViewById(R.id.edtTourId);
		edtAdult = (EditText) findViewById(R.id.edtTourAdultPrice);
		edtChildren = (EditText) findViewById(R.id.edtTourChildPrice);
		edtCurrency = (EditText) findViewById(R.id.edtCurrency);
		edtdepartureDate = (EditText) findViewById(R.id.edtDepatureDate);
		edtreturnDate = (EditText) findViewById(R.id.edtReturnDate);
		edtmaxMembers = (EditText) findViewById(R.id.edtMaxMember);
		edtManager = (EditText) findViewById(R.id.edtManager);
		btnUpdate = (Button) findViewById(R.id.btnUpdateTour);
		btnUpdate.setOnClickListener((android.view.View.OnClickListener) this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(btnUpdate)) {
			TourDTO tourDTO = new TourDTO();
			tourDTO.id = edtidTour.getText().toString().trim();
			tourDTO.priceAdult = edtAdult.getText().toString().trim();
			tourDTO.priceChildren = edtChildren.getText().toString().trim();
			tourDTO.departureDate = edtdepartureDate.getText().toString().trim();
			tourDTO.currency = edtCurrency.getText().toString().trim();
			tourDTO.returnDate = edtreturnDate.getText().toString().trim();
			tourDTO.maxMembers = edtmaxMembers.getText()
					.toString().trim();
			tourDTO.managerId = edtManager.getText().toString().trim();

			MainController.getInstance().handleServiceEvent(
					ServiceEventConstant.UPDATE_TOUR, tourDTO, this);
			return;
		}
	}

	public void loadModel(IDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateFromModel(IDTO dto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleServiceResult(ServiceEventConstant serviceType,
			JSONObject data) {
		// TODO Auto-generated method stub
		if (serviceType == ServiceEventConstant.UPDATE_TOUR) {
			boolean result = false;
			String message = null;
			try {
				result = data.getBoolean(JSONKey.KEY_SUCCESS);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (result == true) {
				DialogBox.showMessage(this, "Update", "Success", "OK");
			} else {
				try {
					message = data.getString(JSONKey.KEY_MESSAGE);
				} catch (JSONException e) {
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

}