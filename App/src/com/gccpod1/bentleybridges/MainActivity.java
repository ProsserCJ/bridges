package com.gccpod1.bentleybridges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//___________________________________________________________________________
		//-------------------------------------------------------------------------
		//		ListView
		
		
		// Populate the ListView
	    final ListView listview = (ListView) findViewById(R.id.listview);
	    String[] values = new String[] { 
	    	getString(R.string.mainmenu1),
	    	getString(R.string.mainmenu2)
	    	};
	    
	    final ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < values.length; ++i) 
	    {
	      list.add(values[i]);
	    }
	    
	    final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
	    listview.setAdapter(adapter);
		
	    
	    listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
	    {
	      @Override
	      public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
	        //final String item = (String) parent.getItemAtPosition(position); // this is commented out 
	        //view.animate().setDuration(2000).alpha(0)
	        //    .withEndAction(new Runnable() {
	        //      @Override
	        //      public void run() {
	        //        list.remove(item);
	        //        adapter.notifyDataSetChanged();
	        //        view.setAlpha(1);
	        //     }
	        //    }); 
	    	  
	    	  
		        switch(position) // Which item they clicked on in the list 
		        {
		        case 0: startActivity(new Intent(MainActivity.this, HelloWorldActivity.class)); break; // go to that activity
	
		        case 1: startActivity(new Intent(MainActivity.this, LoginActivity.class)); break;
	
		        default: break;
		        }
	        
	      	}
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private class StableArrayAdapter extends ArrayAdapter<String> 
	  {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) 
	    {
	      super(context, textViewResourceId, objects);
	      
	      for (int i = 0; i < objects.size(); ++i) 
	        mIdMap.put(objects.get(i), i);
	    }

	    @Override
	    public long getItemId(int position) 
	    {
	      String item = getItem(position);
	      return mIdMap.get(item);
	    }

	    @Override
	    public boolean hasStableIds() 
	    {
	      return true;
	    }

	  }

}
