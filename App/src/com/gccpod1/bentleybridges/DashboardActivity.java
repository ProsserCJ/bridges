package com.gccpod1.bentleybridges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DashboardActivity extends Activity {
	
	int numTestWidgets = 0;
	
	private DatabaseHelper DBHelper;
	  
  //Data for each chart
  private ArrayList<ArrayList<String>> 	bridgeStatus, 
  										nbi58DeckConditionRatings, 
  										postedBridges, 
  										structurallyDeficientDeckArea, 
  										structurallyDeficientNHSDeckArea,	
  										bridgeSufficiencyRatingDeckArea, 	
  										bridgeCondition,
  										nhsBridgeCondition, 				
  										deckAreaBridgeCondition, 			
  										deckAreaNHSBridgeCondition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);		
		// Show the Up button in the action bar.		
		setupActionBar();		 
		dbInit();		

	    //final ActionBar actionBar = getActionBar();
	    
	    // Specify that tabs should be displayed in the action bar.
	   /* actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

	    // Create a tab listener that is called when the user changes tabs.
	    ActionBar.TabListener tabListener = new ActionBar.TabListener() {
	        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // show the given tab
	        }

	        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // hide the given tab
	        }

	        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
	            // probably ignore this event
	        }
	    };
	    
	        actionBar.addTab(
	                actionBar.newTab()
	                        .setText(R.string.dashboardtab1)
	                        .setTabListener(tabListener));
	        actionBar.addTab(
	                actionBar.newTab()
	                        .setText(R.string.dashboardtab2)
	                        .setTabListener(tabListener)); */
	    

	    
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
	
	private void dbInit()
	{
		DBHelper = new DatabaseHelper(this);
	    
	    //Load data from database
	    bridgeStatus = getDataFromCursor(DBHelper.getBridgeStatus());     
		nbi58DeckConditionRatings = getDataFromCursor(DBHelper.getNBI58DeckConditionRatings());		
		postedBridges = getDataFromCursor(DBHelper.getPostedBridges()); 					
		structurallyDeficientDeckArea = getDataFromCursor(DBHelper.getStructurallyDeficientDeckArea());	
		structurallyDeficientNHSDeckArea = getDataFromCursor(DBHelper.getStructurallyDeficientNHSDeckArea());	
		bridgeSufficiencyRatingDeckArea = getDataFromCursor(DBHelper.getBridgeSufficiencyRatingDeckArea()); 	
		bridgeCondition = getDataFromCursor(DBHelper.getBridgeCondition());
		nhsBridgeCondition = getDataFromCursor(DBHelper.getNHSBridgeCondition()); 				
		deckAreaBridgeCondition = getDataFromCursor(DBHelper.getDeckAreaBridgeCondition()); 			
		deckAreaNHSBridgeCondition = getDataFromCursor(DBHelper.getDeckAreaNHSBridgeCondition());
	}
	
	public ArrayList<ArrayList<String>> getDataFromCursor(Cursor c){
		  ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		  
		  //add column headings
		  data.add(new ArrayList<String>(Arrays.asList(c.getColumnNames())));
		  
		  c.moveToFirst();
		  for (int i=0; i<c.getCount(); i++){
			  ArrayList<String> current = new ArrayList<String>();	
			  for (int j=0; j<c.getColumnCount(); j++){
				  try{current.add(c.getString(j));}
				  catch(Exception e){current.add("");}			
			  }
			 data.add(current);
			 c.moveToNext();
		  }
		  return data;
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		 //case R.id.action_addTestWidget:
	           // openSearch();
	     //       return true;

		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void addTestWidget(View view)
	{
		ViewGroup layout;
		
		numTestWidgets++;
		
		switch(numTestWidgets % 3)
		{
		case 1: layout = (ViewGroup) findViewById(R.id.dashboardCol1); break;
		case 2: layout = (ViewGroup) findViewById(R.id.dashboardCol2); break;
		case 0: layout = (ViewGroup) findViewById(R.id.dashboardCol3); break;
		default: layout = (ViewGroup) findViewById(R.id.dashboardCol1); break;
		}
		
		LinearLayout ll = new LinearLayout(this);
		ll.setBackgroundColor(Color.rgb(250,250,250));
		LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		llParams.setMargins(5, 10, 5, 10); // left top right bottom
		ll.setLayoutParams(llParams);
		//ll.setBackgroundResource(R.drawable.widgetbackground);
		
		TextView tv = new TextView(this);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		tv.setText("Hey this is a test!\n\nT E S T W I D G E T " + numTestWidgets + "\n\n");
		for(ArrayList<String> list : deckAreaNHSBridgeCondition)
			for(String s : list)
				tv.setText(tv.getText() + s + '\n');
		
		ll.addView(tv);
		layout.addView(ll);
		
	}

}
