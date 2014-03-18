package com.trm.trmclient.View;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Model.TourModel;
import com.trm.trmclient.Utils.DialogBox;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class DashboardActivity extends NavigationDrawerActivity {
    TextView txtTourTitle;
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.tour_activity_action, menu);
	    return super.onCreateOptionsMenu(menu);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourview);
        initActionBar();
        initNavigationDrawer();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.tour_action_new :
            	MainController.getInstance().handleSwitchActivity(ViewEventConstant.GOTO_CREATE_TOUR, this);
                return true;
            case R.id.tour_action_search:
            	MainController.getInstance().handleSwitchActivity(ViewEventConstant.GOT0_SEARCH_TOUR, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void loadModel(IDTO dto) {
        if(dto != null) {
            TourDTO tourDTO = (TourDTO) dto;
            txtTourTitle.setText(tourDTO.title);
        } else {
            txtTourTitle.setText("Tour model is null!");
            DialogBox.showMessage(this,"You have no tour", "Tour model is null!","OK");
        }
    }

    @Override
    public void handleError(ServiceEventConstant serviceType, int errorCode) {

    }

    @Override
    protected void onDestroy() {
        TourModel.getInstance().removeObserver(this);
        super.onDestroy();
    }

	@Override
	public void handleServiceResult(ServiceEventConstant serviceType,
			JSONObject data) {
		
	}

}