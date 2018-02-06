package com.example.lib;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryFragment extends ListFragment {
	
	public static final String ENDPOINT = "http://10.0.2.2:3000";

	public static final String TAG_CATEGORIES = "categories";
	public static final String TAG_TITLE = "title";
	public static final String TAG_AUTHOR ="author";
	public static final String TAG_ID ="book_id";
	public static final String TAG_publication="publication";
	public static final String TAG_HISTORY = "history";
	public static final String TAG_RET = "Return_date";
	public static final String TAG_INFO = "CurrentIssuedFragment";
	public static final String TAG_AVL = "available";
    public static String userid ;
	
	//ArrayList<String> categoryList = new ArrayList<String>();
	//HashSet<String> hs = new HashSet<String>();
	SharedPreferences pref;
	ArrayList<Book> BookList ;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		pref = getActivity().getSharedPreferences(MainActivity.MYPREF, 0);
		userid =pref.getString("_id", null);
		Log.d("Browse Result Fragment","Login username:"+pref.getString("username","xyz"));
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity();
		
		String myUrl = Uri.parse(ENDPOINT).buildUpon()
						.appendPath("history")
						.appendPath(userid)
						.build()
						.toString();
		
		new FetchItemsTask().execute(myUrl);
		
		
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		 Book  b= (Book) getListAdapter().getItem(position);
		
		Intent i = new Intent(getActivity(),MainActivity.class);
		i.putExtra(BookFragment.EXTRA_BOOK_ID, b.getId());
		startActivity(i);
		
	}
	
private class BookAdapter extends ArrayAdapter<Book>{
		
		public BookAdapter(ArrayList<Book> books){
			super(getActivity(),0,books);
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_history, null);
			}
			
			Book b = getItem(position);
			Log.d("Book Adapter","title:"+b.getTitle());
			TextView title = (TextView)convertView.findViewById(R.id.book_title);
			title.setText(b.getTitle());
			TextView author =(TextView)convertView.findViewById(R.id.book_author);
			author.setText(b.getAuthor());
			TextView publ = (TextView)convertView.findViewById(R.id.book_publ);
			publ.setText(b.getPublication());
			
			//TextView late = (TextView)convertView.findViewById(R.id.late);
			
			//if(b.Islate()){
				//late.setText("Please return this book");
			//}
			//return super.getView(position, convertView, parent);
			return convertView;
		}
		
	}
	
public class FetchItemsTask extends AsyncTask<String, Void, Void>{
		
		protected Void doInBackground(String...params){
			
			String url = params[0] ; 
			
			try{
				String result = new BookFetchr().getURL(url);
				BookCatalog.get(getActivity()).clearList();
				Log.i(TAG_INFO, "Fetch contents of URL:"+result);
				
				if (result != null){
					
					try{
							JSONArray books = new JSONArray(result);
							
							for(int j = 0 ; j < books.length() ; j++){
								JSONObject c = books.getJSONObject(j);
						        
								JSONArray b = c.getJSONArray(TAG_HISTORY);
								
								for(int i = 0 ; i < b.length() ;i++){
                                    
									JSONObject a = b.getJSONObject(i); 								
									String title  =  a.getString(TAG_TITLE);
									String author = a.getString(TAG_AUTHOR);
									String publ = a.getString(TAG_publication);
									String id = a.getString(TAG_ID);
									//int avl = c.getInt(TAG_AVL);
									//String ret = a.getString(TAG_RET);
								
									/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
									
									Date d = sdf.parse(ret);
									Log.d("ret","ret :"+d);
									
									Date t = new Date();
									
									
									String t1 = sdf.format(t);
									
									Log.d("t1","t1:"+t1);
									
									
									Date today = sdf.parse(t1);
									
								    Log.d("today","today:"+today);
									
									
							        
									Boolean booklate = false;
									if(d.before(today)){
										booklate = true ; 
									}
									
									Log.d("Date","today:"+today+"d:"+d+"due:"+d.before(today));
									*/
									
									Book book = new Book(title,author,publ,id);
								
									BookCatalog.get(getActivity()).addBook(book);
								
								}
							}
						
								//BookList.add(b);
							
					
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
			
			
			BookList = BookCatalog.get(getActivity()).getBooks();
			
			BookAdapter adapter = new BookAdapter(BookList);
			//ListAdapter  adapter = new SimpleAdapter(getActivity(), BookList, R.layout.fragment_book_list,
				//	new String[] {TAG_TITLE,TAG_AUTHOR}, new int[] {R.id.book_title,R.id.book_author});
		
			setListAdapter(adapter);
		}
	}
}
