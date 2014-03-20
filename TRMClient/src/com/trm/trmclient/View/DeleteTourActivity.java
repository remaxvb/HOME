package com.trm.trmclient.View;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.JSONKey;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Model.ToursModel;
import com.trm.trmclient.Utils.DialogBox;

/**
 * Created by hoang.l.nguyen on 3/19/14.
 */
public class DeleteTourActivity extends BaseActivity implements View.OnClickListener {
    private Button btnDelete;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_tour);

        ToursModel.getInstance().registerObserver(this);

        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void updateFromModel(IDTO dto) {

    }

    @Override
    public void handleServiceResult(ServiceEventConstant serviceType, JSONObject data) {
        if (serviceType == ServiceEventConstant.DELETE_TOUR){
            boolean result = false;
            String message = null;
            try {
                result = data.getBoolean(JSONKey.KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (result == true){
                DialogBox.showMessage(this,"Delete tour", "Xong roi ku", "OK");
            } else {
                try {
                    message = data.getString(JSONKey.KEY_MESSAGE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DialogBox.showMessage(this, "Error",message, "OK" );
            }

        }

    }

    @Override
    public void handleError(ServiceEventConstant serviceType, int errorCode) {

    }

    @Override
    public void onClick(View v) {
        TourDTO tourDTO = new TourDTO();
        tourDTO.id = "1";
        tourDTO.flag = "1";
        MainController.getInstance().handleServiceEvent(ServiceEventConstant.DELETE_TOUR, tourDTO, this);

    }


}