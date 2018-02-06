package com.example.lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

//public class MainActivity extends ActionBarActivity {

public class MainActivity extends FragmentActivity implements SearchView.OnQueryTextListener{

	private SearchView mSearchView ; //added now 
	//private EditText mUsername;
	//private EditText mPassword;
//	private TextView mStatusView ; //added now 
	public static final String EXTRA_SEARCH_QUERY = " com.android.akash.newsearch.search_query";
	public static final String EXTRA_AUTH_ID = "com.android.akash.newsearch.auth_id";
	public static final String EXTRA_AUTH_SUCCESS = "com.android.akash.newsearch.auth_success";
	public static final String EXTRA_LOGOUT = "com.android.akash.newsearch.logout";
	
	public static final String MYPREF = "com.android.akash.newsearch.pref";
	public static String TAG_USER = "username";
	public static String TAG_USER_ID = "_id";
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	
	static SharedPreferences pref ;
	public static Boolean check_login = false;
	//private LinearLayout linearLayout;
	FragmentManager fm = getSupportFragmentManager(); 
	
	Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		
		setContentView(R.layout.activity_fragment);
		
		
		
		//check_login = pref.getBoolean("islogin", false);
	//mStatusView = (TextView)findViewById(R.id.status);
		String query = getIntent().getStringExtra(EXTRA_SEARCH_QUERY);
		Log.d("Intent from search","Got query");
		if(query != null){
			
		
		if(fragment == null){
			fragment = SearchResultsFragment.newInstance(query);
			fm.beginTransaction()
			  .add(R.id.fragmentContainer,fragment)
			  .commit(); 
			Log.d("Create Fragment","Creating new Fragment with Query:"+query);
		}
	
		
			//mUsername = (EditText)findViewById(R.id.username);
			
		
		}
		
		String id =(String)getIntent().getStringExtra(BookFragment.EXTRA_BOOK_ID);
		if(id != null){
			
			if(fragment == null){
				fragment = BookFragment.newInstance(id);
				fm.beginTransaction()
				  .add(R.id.fragmentContainer,fragment)
				  .commit(); 
				Log.d("Create Fragment","Creating new Fragment with Query:"+query);
			}
		}
		
		
		String auth = (String)getIntent().getStringExtra(EXTRA_AUTH_ID);
		if(auth != null){
			
			if(fragment == null){
				fragment = new RegisterFragment();
				fm.beginTransaction()
				  .add(R.id.fragmentContainer,fragment)
				  .commit(); 
				Log.d("Register","Registering");
				
			}
		}
		
		
		final String result = (String)getIntent().getStringExtra(EXTRA_AUTH_SUCCESS);
		if(result != null ){
			
				
			
				if(fragment == null){
					fragment = new HomeFragment();
					fm.beginTransaction()
					  .add(R.id.fragmentContainer,fragment)
					  .commit(); 
					Log.d("Login Success","Going to home");
					
					
				}
				onAttachFragment(fragment);
			
				
				try{
					
					JSONObject user = new JSONObject(result);
					
					String curr_user = user.getString(TAG_USER);
					String curr_user_id = user.getString(TAG_USER_ID);
					
					
					SharedPreferences.Editor editor = pref.edit();
					
					editor.putString("username", curr_user);
					editor.putString("_id", curr_user_id);
					editor.putBoolean("islogin", true);
					
					editor.commit();
					check_login = pref.getBoolean("islogin", false);
					Log.d("Shared Pref","Pref:"+pref.getBoolean("islogin", false));
					
					/*JSONArray borrow = user.getJSONArray(CurrentIssuedFragment.TAG_BORROW);
					
					
					//editor.
					Set<String> defaulter  = new HashSet<String>();
					
					if(pref.getStringSet("defaultbooks", null) != null){
						editor.remove("defaultbooks");
						editor.commit();
					}
					
					for(int i=0; i < borrow.length() ; i++){
						JSONObject b = borrow.getJSONObject(i);
						
						String ret = b.getString(CurrentIssuedFragment.TAG_RET);
						String bookId = b.getString(CurrentIssuedFragment.TAG_ID);
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
						
						Date d = sdf.parse(ret);
						
						Date t = new Date();
						
						String t1 = sdf.format(t);
						
						Date today = sdf.parse(t1);
						
						if(d.after(today)){
							
							//editor.putString("default",bookId);
							//editor.commit();
							defaulter.add(bookId);
						}
						
						
					}
					
					if(!defaulter.isEmpty()){
					
						editor.putStringSet("defaultbooks", defaulter);
						editor.commit();
					}
				}*/
				}
				catch(JSONException e){
					e.printStackTrace();
				} 
				//catch (ParseException e) {
					// TODO Auto-generated catch block
				//	e.printStackTrace();
				//}
				
				
				
		}
		
		if(fragment == null){
			fragment = new HomeFragment();
			fm.beginTransaction()
			  .add(R.id.fragmentContainer,fragment)
			  .commit(); 
			
		}
		onAttachFragment(fragment);
	
	//	String logout = null;
		//logout = (String)getIntent().getStringExtra(EXTRA_LOGOUT);
		//Log.d("handle Intent",query);
		//linearLayout = (LinearLayout)findViewById(R.id.frame_container);
		//check_login = pref.getBoolean("islogin", false);
		Log.d("Drawer Layout","Reached till start");
		mTitle = mDrawerTitle = getTitle();
		
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
		mDrawerList = (ListView)findViewById(R.id.list_slidermenu);
		
		navDrawerItems = new ArrayList<NavDrawerItem>();
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0,  -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1,  -1)));
		
		
		if(!check_login){
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2,  -1)));
		Log.d("Drawer Layout","Result is  null" + result);
		}
		
		else{
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[8], navMenuIcons.getResourceId(7,  -1)));
		}
		
		if(check_login){
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3,  -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4,  -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5,  -1)));
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6,  -1)));
		}
		
		navMenuIcons.recycle();
		adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
		mDrawerList.setAdapter(adapter);
		Log.d("Drawer Layout","setting adapter");
		
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, 
												R.string.app_name, R.string.app_name){
			
			public void onDrawerClosed(View view){
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View view){
				super.onDrawerOpened(view);
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		
		mDrawerList.setOnItemClickListener(new SlideMenuCLickListener());
		
		
		//displayView(0);
			
		
	}
	
	
	@Override
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
		pref = getSharedPreferences(MYPREF, 0);
		
		check_login=pref.getBoolean("islogin", false);
		Log.d("on attached","login :"+check_login);
		
		
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
  pref = getSharedPreferences(MYPREF, 0);
		
		check_login=pref.getBoolean("islogin", false);
		Log.d("on start","login"+check_login);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume(); 
		pref = getSharedPreferences(MYPREF, 0);
		
		check_login=pref.getBoolean("islogin", false);
		Log.d("on resume","login"+check_login);
	
	}
	private class SlideMenuCLickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			displayView(position);
		}
		
	}
	
	private void displayView(int position){
		Fragment fragment = null;
		Boolean flag = false ;
		switch(position){
		
		case 0:
			fragment = new HomeFragment();
			break;
		
			case 1:
				fragment = new BrowseFragment();
				break;
				
			case 2:
				if(!check_login){
					fragment = new AuthenticateFragment();
				}
				else{
					fragment = new HomeFragment();
					flag = true;
				}
					break;
					
			case 4:
				fragment = new HistoryFragment();
				break;
			case 5:
				fragment = new CurrentIssuedFragment();
				break;
				
		}
		
		if(fragment != null){
			fm.beginTransaction()
				.replace(R.id.fragmentContainer, fragment)
				.commit();
			mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            onAttachFragment(fragment);
		}
		else{
			Log.e("MainActivity","Error in creating fragment");
		}
		if(flag){
			
			SharedPreferences.Editor editor = pref.edit();
			editor.clear();
			editor.commit();
			flag = false;
			check_login = false;
			Log.d("check","value:"+check_login);
			Log.d("Log out","Logging out");
			Intent i = new Intent(this,MainActivity.class);
			i.putExtra(EXTRA_LOGOUT, "Logging out");
			startActivity(i);
		}
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);
		 
	   // SearchManager searchManager =
		 //          (SearchManager)getSystemService(Context.SEARCH_SERVICE);
		 
		
		// SearchView searchView =
		  //          (SearchView) menu.findItem(R.id.search).getActionView();
		   
		 //searchView.setSearchableInfo(
		   //         searchManager.getSearchableInfo(new ComponentName(this,SearchableActivity.class)));
		    
		    //searchView.setIconifiedByDefault(false);
		 
		MenuItem  searchItem = menu.findItem(R.id.search) ;
		mSearchView  = (SearchView)searchItem.getActionView();
		setupSearchView(searchItem); 
		return true;
	}
	
	 private void setupSearchView(MenuItem searchItem) {

	        if (isAlwaysExpanded()) {
	            mSearchView.setIconifiedByDefault(false);
	        } else {
	            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
	                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	        }

	        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	        if (searchManager != null) {
	           //List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();
	        
	           SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
	           
	           /*for (SearchableInfo inf : searchables) {
	                if (inf.getSuggestAuthority() != null
	                        && inf.getSuggestAuthority().startsWith("applications")) {
	                    info = inf;
	                }
	            }*/
	            mSearchView.setSearchableInfo(info);
	        }

	        mSearchView.setOnQueryTextListener(this);
	    }

	    public boolean onQueryTextChange(String newText) {
	     //   mStatusView.setText("Query = " + newText);
	        return false;
	    }

	    public boolean onQueryTextSubmit(String query) {
	        //mStatusView.setText("Query = " + query + " : submitted");
	        return false;
	    }

	    public boolean onClose() {
	      //  mStatusView.setText("Closed!");
	        return false;
	    }

	    protected boolean isAlwaysExpanded() {
	        return false;
	    }
	


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		
		  if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		 
		boolean drawerOpen  = mDrawerLayout.isDrawerOpen(mDrawerList);
		/*if(drawerOpen == true){
			Log.d("Drawer Open", "True");
		}
		else if(drawerOpen == false){
			Log.d("Drawer Open","False");
		}
		else{
			Log.d("Drawer Open","ND");
		}*/
			
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
		
		
	}
	
	@Override
	public void setTitle(CharSequence title){
		mTitle  = title ;
		getActionBar().setTitle(mTitle);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState){
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	
}
