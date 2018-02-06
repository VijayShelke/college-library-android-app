package com.example.lib;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class SearchableActivity extends FragmentActivity {
	
	private String query  ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_fragment);
		handleIntent(getIntent());
	}
	
	protected void onNewIntent(Intent intent){
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent){
		if(Intent.ACTION_SEARCH.equals(intent.getAction())){
			query = intent.getStringExtra(SearchManager.QUERY);
			if(query != null){
				Intent i = new Intent(SearchableActivity.this,MainActivity.class);
				i.putExtra(MainActivity.EXTRA_SEARCH_QUERY, query);
				startActivity(i);
			}
			/*
			FragmentManager fm = getSupportFragmentManager(); 
			
			Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
			
			if(fragment == null){
				fragment = SearchResultsFragment.newInstance(query);
				fm.beginTransaction()
				  .add(R.id.fragmentContainer,fragment)
				  .commit(); 
				Log.d("Create Fragment","Creating new Fragment with Query:"+query);
			}
			Log.d("handle Intent",query);
			*/
		}
	}
	
	/*protected Fragment createFragment(){
		Log.d("Create Fragment","Creating new Fragment with Query:"+query);
		return  SearchResultsFragment.newInstance(query);
		
	}*/

}

