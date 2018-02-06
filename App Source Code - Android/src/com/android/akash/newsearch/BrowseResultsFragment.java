package com.example.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class BrowseResultsFragment extends ListFragment {
	
	
	public static final String ENDPOINT = "http://10.0.2.2:3000";
	public static final String TAG_INFO= "BrowseResultsFragment";
	public static final String TAG_CATEGORIES = "categories";

	
	ArrayList<String> categoryList = new ArrayList<String>();
	HashSet<String> hs = new HashSet<String>();
	SharedPreferences pref;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		pref = getActivity().getSharedPreferences(MainActivity.MYPREF, 0);
		Log.d("Browse Result Fragment","Login username:"+pref.getString("username","xyz"));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity();
		
		String myUrl = Uri.parse(ENDPOINT).buildUpon()
						.appendPath("books")
						.build()
						.toString();
		
		new FetchItemsTask().execute(myUrl);
		
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		String cat = (String) getListAdapter().getItem(position);
		
		Intent i = new Intent(getActivity(),MainActivity.class);
		i.putExtra(MainActivity.EXTRA_SEARCH_QUERY, cat);
		startActivity(i);
		
	}
	
public class FetchItemsTask extends AsyncTask<String, Void, Void>{
		
		protected Void doInBackground(String...params){
			
			String url = params[0] ; 
			
			try{
				String result = new BookFetchr().getURL(url);
				Log.i(TAG_INFO, "Fetch contents of URL:"+result);
				
				if (result != null){
					
					try{
							JSONArray books = new JSONArray(result);
							for(int i=0 ; i < books.length() ; i++) {
								JSONObject c = books.getJSONObject(i);
								JSONArray cats = c.getJSONArray(TAG_CATEGORIES);
								if(cats != null){
								for(int j=0; j < cats.length() ;j++)
								{
									String name = cats.get(j).toString();
									Log.d("Async Task", "Array String "+name);
									if(name != null){
										categoryList.add(name);
									}
								}
								}
							
							}
							hs.addAll(categoryList);
							categoryList.clear();
							categoryList.addAll(hs);
							
					
					}
					catch(JSONException e){
					e.printStackTrace();
				}
			}
			else{
				Log.e("Service Handler"	,"Couldn't get any data");
			}
			
		}
		catch(IOException ioe){
			Log.e(TAG_INFO,"Failed to fetch URL:"+ioe);
		}
		
			
			
		return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_category_list
						,R.id.category, categoryList);
			
		
			setListAdapter(adapter);
		}

}
}


