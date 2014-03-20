package com.gccpod1.bentleybridges;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DashboardActivity extends Activity {
	
	int numTestWidgets = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		// Show the Up button in the action bar.
		setupActionBar();
		

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
		
		TextView tv = new TextView(this);
		tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		tv.setText("Hey this is a test!" + numTestWidgets);
		layout.addView(tv);
		
	}

}
