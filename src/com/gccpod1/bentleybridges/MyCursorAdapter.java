package com.gccpod1.bentleybridges;


import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;

public class MyCursorAdapter extends BaseAdapter {
    private Context context;  
    ArrayList<ArrayList<String>> data;
    
    MyCursorAdapter(Context _context, Cursor _cursor)
    {
    	context = _context;
    	data = getDataFromCursor(_cursor);
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
	public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv = new TextView(context);
			switch(position)
			{
			case 0: tv.setText("Asset Code\n"); break;
			case 1: tv.setText("58: Deck\n"); break;
			case 2: tv.setText("59: Super\n"); break;
			case 3: tv.setText("60: Sub\n"); break;
			case 4: tv.setText("62: Culvert\n"); break;
			case 5: tv.setText("Posting\nStatus"); break;
			case 6: tv.setText("Sufficiency Rating"); break;
			case 7: tv.setText("Status\n"); break;			 
			
			default:
				int i = 0;
				while (position >= data.get(0).size())
				{
					position -= data.get(0).size();
					i++;
				}
				int j = position;
				
				//Map integers to bridge status
				if ((position%7) == 0)
				{
					try{
						switch(Integer.parseInt(data.get(i).get(j)))
						{
						case 0: tv.setText("OK"); break;
						case 1: tv.setText("SD"); break;
						case 2: tv.setText("FO"); break;
						default: tv.setText(data.get(i).get(j));
						}
					}
					catch(Exception e){tv.setText(data.get(i).get(j));}
				}				
				else tv.setText(data.get(i).get(j));	
				break;
			}
			

			
			return tv;	
	}
    
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size() * data.get(0).size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

    
}

