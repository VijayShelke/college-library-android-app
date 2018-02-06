package com.example.lib;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends ListFragment {

	
	SharedPreferences pref;
	String name;
	public static final String TAG_TITLE = "title";
	public static final String TAG_AUTHOR ="author";
	public static final String TAG_ID ="_id";
	public static final String TAG_publication="publication";
	public static final String TAG_INFO = "SearchResultsFragment";
	public static final String ENDPOINT = "http://10.0.2.2:3000";
	public static final String TAG_AVL = "available";
	private static Boolean flag = false;
	ArrayList<Book> BookList ;//add a model instead of hashmap 
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Book b = ((BookAdapter)getListAdapter()).getItem(position);
		
		Intent i = new Intent(getActivity(),MainActivity.class);
		i.putExtra(BookFragment.EXTRA_BOOK_ID, b.getId());
		startActivity(i);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity();
		BookList = new ArrayList<Book>();
		//View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_book_list, null);
		
		
		
		//text.setText("Most Issued");
		//String query = (String)getArguments().getCharSequence(TAG_QUERY);
		
		//if(query != null){
			
			/*Uri.Builder builder = new  Uri.Builder();
			builder.scheme("http").authority("10.0.2.2:3000")
			.appendPath("books")
			.appendPath("search")
			.appendQueryParameter("q", query);
		*/
		//	String en_query = Uri.parse(query).toString();
		String myUrl = Uri.parse(ENDPOINT).buildUpon()
					.appendPath("books")
				.appendPath("mostissued")
				//	.appendQueryParameter("q", en_query)
					.build().toString(); 
			
		
		
			new FetchItemsTask().execute(myUrl);
		
		
		
		
	}
	
private class BookAdapter extends ArrayAdapter<Book>{
		
		public BookAdapter(ArrayList<Book> books){
			super(getActivity(),0,books);
		}
		
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			if(convertView == null){
				convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_book_list, null);
			}
			
			if(!flag){
				TextView text = (TextView)convertView.findViewById(R.id.most_issued);
				//text.setText("Most Issued");
				flag = true;
			}
			
			Book b = getItem(position);
			Log.d("Book Adapter","title:"+b.getTitle());
			
			TextView title = (TextView)convertView.findViewById(R.id.book_title);
			title.setText(b.getTitle());
			TextView author =(TextView)convertView.findViewById(R.id.book_author);
			author.setText(b.getAuthor());
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
							for(int i=0 ; i < books.length() ; i++) {
								JSONObject c = books.getJSONObject(i);
						
								String title  =  c.getString(TAG_TITLE);
								String author = c.getString(TAG_AUTHOR);
								String publ = c.getString(TAG_publication);
								String id = c.getString(TAG_ID);
								
								//avl added
								int  avl = c.getInt(TAG_AVL);
								
								Book b = new Book(title,author,publ,id,avl);
								
								BookCatalog.get(getActivity()).addBook(b);
								
								
						
								//BookList.add(b);
							}
					
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
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	    pref = getActivity().getSharedPreferences(MainActivity.MYPREF, 0);
		//name = pref.getString("username", "xyz");
		//Log.d("Shared Pref",name);
	}
	

	
	/*@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_home, container,false);
		
		
		
		//TextView msg = (TextView)v.findViewById(R.id.display_message);
		//msg.setText("Hello,"+pref.getString("username", "User"));
		
		return v;
	}*/
	
}

