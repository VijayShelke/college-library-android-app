package com.example.lib;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class RegisterFragment extends Fragment {
	
	
	
	EditText username;
	EditText fname;
	EditText lname;
	EditText rollno;
	EditText password;
	//EditText libno;
	User u = new User(); 
	public static final String ENDPOINT = "http://10.0.2.2:3000";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View v = inflater.inflate(R.layout.fragment_register_user, container,false);
		
		//TextView title = (TextView)v.findViewById(R.id.title);
		username = (EditText)v.findViewById(R.id.username);
		username.addTextChangedListener(new TextWatcher() {
			
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
		
		password = (EditText)v.findViewById(R.id.password);
		password.addTextChangedListener(new TextWatcher() {
			
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
		
		fname = (EditText)v.findViewById(R.id.firstname);
		fname.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				u.setFname(s.toString());
			
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
		lname = (EditText)v.findViewById(R.id.lastname);
		lname.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				u.setLname(s.toString());
			
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
		rollno = (EditText)v.findViewById(R.id.rollno);
		rollno.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				u.setRollno(s.toString());
			
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
		
		/*libno = (EditText)v.findViewById(R.id.libNo);
		libno.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				u.setLibno(s.toString());
			
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
		});*/
		
		Button RegisterButton = (Button)v.findViewById(R.id.register_button);
		RegisterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub	
				
				String myUrl = Uri.parse(ENDPOINT).buildUpon()
								.appendPath("users")
								.build()
								.toString();
				
				Log.d("Register Fragment","URL:"+myUrl);
				
				switch(v.getId()){
					case R.id.register_button: new RegisterUserTask().execute(myUrl);
										break;
										
					//case R.id.register_button: new 
				}
				
			}
	 
		//return super.onCreateView(inflater, container, savedInstanceState);
	
		});	
		return v ; 
	

}

	public class RegisterUserTask extends AsyncTask<String,Void,String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			//b.setTitle(putTitle.getText().toString());
			//b.setAuthor(putAuthor.getText().toString());
			//b.setPublication(putPubl.getText().toString());
			return new BookFetchr().Post_register(params[0],u);
		}
		
		/*@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			Toast.makeText(getActivity(), "Data Sent!", Toast.LENGTH_LONG).show();
			Intent i = new Intent(getActivity(),MainActivity.class);
			i.putExtra(MainActivity.EXTRA_REG_SUCCESS, result);
			startActivity(i);
		}*/
		
	}
	
}
