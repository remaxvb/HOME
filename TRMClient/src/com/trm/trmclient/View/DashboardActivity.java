package com.trm.trmclient.View;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;

import com.trm.trmclient.R;
import com.trm.trmclient.Constants.ServiceEventConstant;
import com.trm.trmclient.Constants.ViewEventConstant;
import com.trm.trmclient.Controller.MainController;
import com.trm.trmclient.DTOs.IDTO;
import com.trm.trmclient.DTOs.TourDTO;
import com.trm.trmclient.Model.AccountModel;
import com.trm.trmclient.Model.ToursModel;
import com.trm.trmclient.Utils.FragmentAdapter;
import com.trm.trmclient.Utils.TourFragmentLayout;
import com.trm.trmclient.Utils.ToursListAdapter;

/**
 * Created by hieu.t.vo on 3/14/14.
 */
public class DashboardActivity extends BaseActivity {
	TextView txtTourTitle;
	AccountModel accountModel;

	// private ArrayAdapter<TourDTO> adapter;

	// private FragmentAdapter fragmentAdapter;
	// private ViewPager viewPager;

	// Fragment Layout for tours
	// private TourFragmentLayout pendingTours;
	// private TourFragmentLayout activeTours;
	// private TourFragmentLayout finishedTours;

	// Adapter for tours
	// private ToursListAdapter pendingToursAdapter;
	// private ToursListAdapter activeToursAdapter;
	// private ToursListAdapter finishedToursAdapter;

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
		setContentView(R.layout.active_tour_card);
		// initActionBar();
		// initNavigationDrawer();
		initUIComponent();
		// Attach view to model
		// ToursModel.getInstance().registerObserver(this);
		// Get data from model
		// ToursModel.getInstance().notifyObservers();
		// txtTourTitle.setText(dataText);
	}

	private void initUIComponent() {
		// txtTourTitle = (TextView) findViewById(R.id.txtTourTitle);
		Card card = new Card(this);
		CardHeader header = new CardHeader(getBaseContext());
		card.addCardHeader(header);
		CardView cardView = (CardView) findViewById(R.id.carddemo);
		cardView.setCard(card);

		// pendingTours = new TourFragmentLayout();
		// // activeTours = new TourFragmentLayout();
		// // finishedTours = new TourFragmentLayout();
		// pendingTours.initFragment(R.layout.tours_pending_list, "Pending");
		// // activeTours.initFragment(R.layout.tours_active_list, "Active");
		// // finishedTours.initFragment(R.layout.tours_finished_list,
		// "Finished");
		// //
		// // pendingToursAdapter = new
		// ToursListAdapter(pendingTours.getActivity());
		// // activeToursAdapter = new
		// ToursListAdapter(activeTours.getActivity());
		// // finishedToursAdapter = new
		// ToursListAdapter(finishedTours.getActivity());
		// //
		// fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
		// fragmentAdapter.addLayout(pendingTours);
		// // fragmentAdapter.addLayout(activeTours);
		// // fragmentAdapter.addLayout(finishedTours);
		//
		// viewPager = (ViewPager) findViewById(R.id.home_paper);
		// viewPager.setAdapter(fragmentAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.tour_action_new:
			MainController.getInstance().handleSwitchActivity(
					ViewEventConstant.GOTO_CREATE_TOUR, this);
			return true;
		case R.id.tour_action_update:
			MainController.getInstance().handleSwitchActivity(
					ViewEventConstant.GOTO_UPDATE_TOUR, this);
			return true;
		case R.id.tour_action_search:
			MainController.getInstance().handleSwitchActivity(
					ViewEventConstant.GOTO_MEMBER, this);
			return true;

		case R.id.tour_action_clear:
			ToursModel.getInstance().clearTours();
			Toast.makeText(this, "Clear tours", Toast.LENGTH_LONG).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void updateFromModel(IDTO dto) {
		// if(dto != null) {
		// TourDTO tourDTO = (TourDTO) dto;
		// txtTourTitle.setText(tourDTO.title);
		// } else {
		// txtTourTitle.setText("Tour model is null!");
		// DialogBox.showMessage(this,"You have no tour",
		// "Tour model is null!","OK");
		// }
	}

	@Override
	public void handleError(ServiceEventConstant serviceType, int errorCode) {

	}

	@Override
	protected void onDestroy() {
		ToursModel.getInstance().removeObserver(this);
		super.onDestroy();
	}

	@Override
	public void handleServiceResult(ServiceEventConstant serviceType,
			JSONObject data) {

	}

	public void UpdateUI() {

	}

}