package com.gccpod1.bentleybridges;

import java.util.ArrayList;
import java.util.Arrays;

import org.achartengine.GraphicalView;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class GraphAdapter extends BaseAdapter {
    private Context context;
    private GraphFactory graphFactory;
    
    public GraphAdapter(Context c) {
        context = c;
        graphFactory = new GraphFactory(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    	GraphicalView graphView = null;
        if (convertView == null) 
        {         	
        	graphView = graphFactory.getView_nbi58DeckConditionRatings();	

        	graphView.setLayoutParams(new GridView.LayoutParams(400, 400));
        	graphView.setPadding(0,0,0,0);
        } 
        else 
        {
        	graphView = (GraphicalView) convertView;
        }        

        return graphView;
    }

	@Override
	public int getCount() {
		return 10;
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
