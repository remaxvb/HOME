package com.haven.Haven_TRMClient.View;

import org.json.JSONObject;

import android.os.Bundle;
import android.widget.TextView;

import com.haven.Haven_TRMClient.Constants.ServiceEventConstant;
import com.haven.Haven_TRMClient.DTOs.TourDTO;
import com.haven.Haven_TRMClient.Model.TourModel;
import com.haven.Haven_TRMClient.R;
import com.haven.Haven_TRMClient.Utils.DialogBox;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class TourActivity extends BaseActivity {
    TextView txtTourTitle;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourview);
        initUIComponent();
        //Attach view to model
        TourModel.getInstance().registerObserver(this);
        //Get data from model
        TourModel.getInstance().notifyObservers();
    }

    private void initUIComponent() {
        txtTourTitle = (TextView) findViewById(R.id.txtTourTitle);
    }

    @Override
    public void updateView(Object o) {
        if(o != null) {
            TourDTO tourDTO = (TourDTO)o;
            txtTourTitle.setText(tourDTO.title);
        } else {
            txtTourTitle.setText("Tour model is null!");
            DialogBox.showMessage(this,"You have no tour", "Tour model is null!","OK");
        }
    }

    @Override
    protected void onDestroy() {
        TourModel.getInstance().removeObserver(this);
        super.onDestroy();
    }

	@Override
	public void handleServiceEvent(ServiceEventConstant serviceType,
			JSONObject data) {
		// TODO Auto-generated method stub
		
	}
}