package com.gccpod1.bentleybridges;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.achartengine.GraphicalView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

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
    	if (position > numGraphs)
    		return null;
    	GraphicalView graphView = graphs.get(position);      	 
    	
    	graphView.setLayoutParams(new GridView.LayoutParams(width, height));
    	graphView.setPadding(25,25,25,25);

        return graphView;
    }
    
    private String getWidgetConfig() {
		
		String temp = "";
		  FileInputStream fin;
			try {
				fin = context.openFileInput("widgetConfig.dat");
				int c;
				while( (c = fin.read()) != -1){
				   temp = temp + Character.toString((char)c);
				}
				//string temp contains all the data of the file.
				fin.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return temp;
	}
    
    public void refresh()
    {
    	graphs.clear();
    	
    	String conf = getWidgetConfig();
    	
    	
    	width = 400;
    	height = 350;
    	
    	String[] widgetIds = conf.split(" ");
		  final String[] EMPTY_STRING_ARRAY = new String[0];
		  List<String> list = new ArrayList<String>(Arrays.asList(widgetIds));
		  list.removeAll(Arrays.asList(""));
		  widgetIds = list.toArray(EMPTY_STRING_ARRAY);

		  numGraphs = widgetIds.length;
		  
		  for (int i = 0; i < numGraphs; i++)
			  graphs.add(graphFactory.parse(widgetIds[i]));
		  
    	
        /*try {
        	input = new Scanner(context.getAssets().open("graph_order.txt"));
		} catch (IOException e) {
			e.printStackTrace();
			input = null;
		}         
		
		
        width = input.nextInt();
        height = input.nextInt();
        input.nextLine(); input.nextLine();        

    	for (numGraphs = 0; input.hasNextLine(); numGraphs++){
    		//graphs.add(graphFactory.parse(input.nextLine()));   
    		input.nextLine();
    	} */
    	
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

