package com.trm.trmclient.View;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Model.AccountModel;

/**
 * 
 * Created by chien.nguyen Mar-18-2014
 * 
 */

public class CreateTourActivity extends BaseActivity implements OnClickListener {

	EditText edtDestination, edtTourName, edtPriceAdult, edtPriceChildren,
			edtDepartureDate, edtReturnDate, edtMaxMembers;
	Button btnCreate;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_tour);
		// Attach view to model
		AccountModel.getInstance().registerObserver(this);
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
		// TODO Auto-generated method stub
		TourDTO tourDTO = new TourDTO();
		tourDTO.departureDate = edtDepartureDate.getText().toString().trim();
		tourDTO.destination = edtDestination.getText().toString().trim();
		tourDTO.maxMembers = (Integer.parseInt(edtMaxMembers.getText()
				.toString().trim()));
		tourDTO.priceAdult = edtPriceAdult.getText().toString().trim();
		tourDTO.priceChildren = edtPriceChildren.getText().toString().trim();
		tourDTO.returnDate = edtReturnDate.getText().toString().trim();
		tourDTO.title = edtTourName.getText().toString().trim();
		MainController.getInstance().handleServiceEvent(
				ServiceEventConstant.CREATE_TOUR, tourDTO, this);
	}

}
