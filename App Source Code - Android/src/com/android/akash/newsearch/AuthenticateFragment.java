package com.example.lib;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressWarnings("unused")
public class AuthenticateFragment extends Fragment {
	
	private User u = new User(); 
	EditText putUsername ;
	EditText putPassword;
	//SharedPreferences pref;
	public static final String ENDPOINT = "http://10.0.2.2:3000";
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_authenticate, container,false);
		
		//TextView title = (TextView)v.findViewById(R.id.title);
		putUsername = (EditText)v.findViewById(R.id.username);
		putUsername.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				u.setUsername(s.toString());
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			
				
			}
		});
		//TextView author = (TextView)v.findViewById(R.id.author);
		putPassword = (EditText)v.findViewById(R.id.password);
		putPassword.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				u.setPassword(s.toString());
			
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
				
			}
		});
	
		
		Button addBookButton = (Button)v.findViewById(R.id.login);
		addBookButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				
				String myUrl = Uri.parse(ENDPOINT).buildUpon()
								.appendPath("login")
								.build()
								.toString();
				
				switch(v.getId()){
					case R.id.login: new PostUserTask().execute(myUrl);
							
										break;
						
						
						
										
					
										
					//case R.id.register_button: new 
				}
				
			}
			
		});

		Button addRegisterButton = (Button) v.findViewById(R.id.register_button);
		addRegisterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch(v.getId()){
				case R.id.register_button: Intent i = new Intent(getActivity(),MainActivity.class);
										   String id = "authenticate_fragment";
										   i.putExtra(MainActivity.EXTRA_AUTH_ID,id);
										   startActivity(i);
										break; 
				}
			}
		});
		
	
	
		
		//Log.d("Shared Pref",pref.getString("username", "xyz"));
		
		
		return v ; 
	}
	
	public class PostUserTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			//b.setTitle(putTitle.getText().toString());
			//b.setAuthor(putAuthor.getText().toString());
			//b.setPublication(putPubl.getText().toString());
			String res =  new BookFetchr().Post_auth(params[0],u);
			Log.d("Post Auth","Response received:"+res);
			
			return res ;
		
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			Toast.makeText(getActivity(), "Data Sent!", Toast.LENGTH_LONG).show();
			Intent i = new Intent(getActivity(),MainActivity.class);
			i.putExtra(MainActivity.EXTRA_AUTH_SUCCESS, result);
			startActivity(i);
			
		}
		
	}
}
