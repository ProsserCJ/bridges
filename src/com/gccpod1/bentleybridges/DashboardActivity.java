package com.gccpod1.bentleybridges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalActivity;
import org.achartengine.GraphicalView;

import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class DashboardActivity extends Activity {
		int numTestWidgets = 0;
		private GraphFactory gf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);		
		// Show the Up button in the action bar.		
		//setupActionBar();

		
		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new GraphAdapter(this));
		gridView.setOnItemClickListener(null);
		gridView.setVerticalSpacing(25);	
		
		TabHost th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		TabSpec spec = th.newTabSpec("arbitraryTag1");
		spec.setContent(R.id.Dashboard);
		spec.setIndicator("Dashboard");
		th.addTab(spec);
		
		spec = th.newTabSpec("arbitraryTag2");
		spec.setContent(R.id.Manager);
		spec.setIndicator("Manager");
		th.addTab(spec);
							
		
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
	
	protected void onResume()
	{
		super.onResume();	
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
		ViewGroup layout = (ViewGroup) findViewById(R.id.Manager);

		LinearLayout ll = new LinearLayout(this);
		ll.setBackgroundColor(Color.rgb(250,250,250));
		LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		llParams.setMargins(5, 10, 5, 10); // left top right bottom
		ll.setLayoutParams(llParams);		
		
		TextView tv = new TextView(this);
		tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		tv.setText("Hey this is a test!\n\nT E S T W I D G E T " + numTestWidgets + "\n\n");

		ll.addView(tv);
		layout.addView(ll);		
	}
	
	public void addGraph(View graph)
	{	


/*		ViewGroup.LayoutParams llParams = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	
		graph.addView(graph, llParams);
		layout.addView(view);*/
		
	}

}
