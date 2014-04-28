package com.gccpod1.bentleybridges;

import java.util.ArrayList;


import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Activity {
	int numTestWidgets = 0;
	CheckboxListAdapter dataAdapter = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);		
		
		// Show the Up button in the action bar.		
		//setupActionBar();
		
		setUpTabs();
							
		displayDashboardContent();	
		
		displayManagerContent();
	    
	}

	private void setUpTabs() {
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
	}

	private void displayDashboardContent() {
		GridView gridView = (GridView) findViewById(R.id.gridview);
		gridView.setAdapter(new GraphAdapter(this));
		gridView.setOnItemClickListener(null);
		gridView.setVerticalSpacing(25);
	}
	
	 private void displayManagerContent() {
		 
		  //Array list of countries
		  ArrayList<WidgetItem> widgetItemList = new ArrayList<WidgetItem>();
		  widgetItemList.add(new WidgetItem("1", "NBI 59 Superstructure Condition Ratings", false));
		  widgetItemList.add(new WidgetItem("2", "NBI 60 Substructure Condition Ratings", false));
		  widgetItemList.add(new WidgetItem("3", "NBI 62 Culvert Condition Ratings", false));
		  widgetItemList.add(new WidgetItem("4", "NHS Bridge Condition", false));
		  widgetItemList.add(new WidgetItem("5", "Deck Area Bridge Condition", false));
		  widgetItemList.add(new WidgetItem("6", "Deck Area NHS Bridge Condition", false));
		  widgetItemList.add(new WidgetItem("7", "Structurally Deficient Deck Area", false));
		  widgetItemList.add(new WidgetItem("8", "Structurally Deficient NHS Deck Area", false));
		  widgetItemList.add(new WidgetItem("9", "Bridge Sufficiency Rating Deck Area", false));
		  widgetItemList.add(new WidgetItem("10","NBI 58 Deck Condition Ratings", false));
		  widgetItemList.add(new WidgetItem("11","Posted Bridges", false));
		  widgetItemList.add(new WidgetItem("12", "Bridge Status", false));
		  widgetItemList.add(new WidgetItem("13", "Bridge Condition", false));
		  
		  ListView listView = (ListView) findViewById(R.id.listView1);
		  dataAdapter = new CheckboxListAdapter(this, R.layout.widget_item_info, widgetItemList);
		  listView.setAdapter(dataAdapter);
		 
		  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				  // When clicked, show a toast with the TextView text
				  WidgetItem item = (WidgetItem) parent.getItemAtPosition(position);
				  Toast.makeText(getApplicationContext(), "Clicked on Row: " + item.getContent() + ", Code: " + item.getCode(), 
						  Toast.LENGTH_LONG).show();}
		  });
		  
		  listView.setVerticalScrollBarEnabled(true);
	 }
	
	private class CheckboxListAdapter extends ArrayAdapter<WidgetItem> {

		private ArrayList<WidgetItem> widgetItemList;

		public CheckboxListAdapter(Context context, int textViewResourceId, ArrayList<WidgetItem> widgList) {
			super(context, textViewResourceId, widgList);
			this.widgetItemList = new ArrayList<WidgetItem>();
			this.widgetItemList.addAll(widgList);
		}

		private class ViewHolder {
			TextView code;
			CheckBox name;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			Log.v("ConvertView", String.valueOf(position));

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.widget_item_info, null);

				holder = new ViewHolder();
				holder.code = (TextView) convertView.findViewById(R.id.code);
				holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
				convertView.setTag(holder);

				holder.name.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						CheckBox cb = (CheckBox) v;
						WidgetItem item = (WidgetItem) cb.getTag();
						Toast.makeText(getApplicationContext(),
								"Clicked on Checkbox: " + cb.getText() + " is "
										+ cb.isChecked(), Toast.LENGTH_LONG).show();
						item.setSelected(cb.isChecked());
					}
				});
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			WidgetItem widgetItem = widgetItemList.get(position);
			//holder.code.setText(" (" + widgetItem.getCode() + ")"); //UNCOMMENT this to view the widget codes
			holder.code.setText("");
			holder.name.setText("    " + widgetItem.getContent());
			holder.name.setChecked(widgetItem.isSelected());
			holder.name.setTag(widgetItem);

			return convertView;

		}

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
