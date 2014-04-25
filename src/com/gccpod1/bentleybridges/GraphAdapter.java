package com.gccpod1.bentleybridges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.achartengine.GraphicalView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class GraphAdapter extends BaseAdapter {
    private Context context;
    private GraphFactory graphFactory;
    private int numGraphs = 0;
    private int width, height;
    Scanner input;
    ArrayList<GraphicalView> graphs;
    
    public GraphAdapter(Context c) {
        context = c;
        graphFactory = new GraphFactory(context); 
        graphs = new ArrayList<GraphicalView>();
        refresh();
    }

    public View getView(int position, View convertView, ViewGroup parent) {    	
    	GraphicalView graphView = graphs.get(position);      	 
    	
    	graphView.setLayoutParams(new GridView.LayoutParams(width, height));
    	graphView.setPadding(25,25,25,25);

        return graphView;
    }
    
    public void refresh()
    {
    	graphs.clear();
    	
        try {
        	input = new Scanner(context.getAssets().open("graph_order.txt"));
		} catch (IOException e) {			
			e.printStackTrace();
			input = null;
		}         

        width = input.nextInt();
        height = input.nextInt();
        input.nextLine(); input.nextLine();        

    	for (numGraphs = 0; input.hasNextLine(); numGraphs++){
    		graphs.add(graphFactory.parse(input.nextLine()));    	
    	}
    }

	@Override
	public int getCount() {
		return numGraphs;
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

