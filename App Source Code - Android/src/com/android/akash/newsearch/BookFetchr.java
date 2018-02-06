package com.example.lib;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class BookFetchr {

		byte[] getURLBytes(String urlspec)throws IOException{
				
				URL url = new URL(urlspec);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				
				try {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					InputStream in = conn.getInputStream();
					
					if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
						return null;
					}
					
					int bytesRead = 0  ; 
					byte[] buffer = new byte[1024];
					while((bytesRead = in.read(buffer))>0){
						out.write(buffer,0,bytesRead);
						
					}
					out.close();
					return out.toByteArray();
					
				}finally{
					conn.disconnect();
				}
		}
		
		public String getURL(String urlspec) throws IOException{
			return  new  String(getURLBytes(urlspec));
			
		}

		public String Post_auth(String url ,User u){
				
			InputStream inputstream = null;
			String result = "";
			String json = "";
			try{
				
				HttpClient httpClient = new DefaultHttpClient();
				
				HttpPost httpPost = new HttpPost(url);
			
				
				
				
				JSONObject jObj = new JSONObject();
				jObj.accumulate("username",u.getUsername() );
				
				//jObj.accumulate("firstname",u.getFname());
				//jObj.accumulate("lastname",u.getLname());
				//jObj.accumulate("rollno",u.getRollno());
				jObj.accumulate("password",u.getPassword());
				
				json = jObj.toString();
				
				
				//List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
				//namevaluepairs.add(new BasicNameValuePair("username",u.getUsername()));
				//namevaluepairs.add(new BasicNameValuePair("password",u.getPassword()));
				StringEntity se = new StringEntity(json);
				
				httpPost.setEntity(se);
				 
				httpPost.setHeader("Accept","application/json");
				httpPost.setHeader("Content-type","application/json");
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				
				
				inputstream = httpResponse.getEntity().getContent();
				
				
				if(inputstream != null){
					result = convertInputStreamToString(inputstream);
							
				}
				else{
					result = "Did not work!";
				}
				
				
			}catch(Exception e){
				Log.d("InputStream",e.getLocalizedMessage());
			}
			return result;
		}
		
		public String Post_register(String url ,User u){
			
			InputStream inputstream = null;
			String result = "";
			String json = "";
			try{
				
				HttpClient httpClient = new DefaultHttpClient();
				
				HttpPost httpPost = new HttpPost(url);
			
				
				
				
				JSONObject jObj = new JSONObject();
				jObj.accumulate("username",u.getUsername() );
				
				jObj.accumulate("firstname",u.getFname());
				jObj.accumulate("lastname",u.getLname());
				jObj.accumulate("rollno",u.getRollno());
				jObj.accumulate("password",u.getPassword());
				jObj.accumulate("libno",u.getLibno());
				json = jObj.toString();
				
				
				//List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);
				//namevaluepairs.add(new BasicNameValuePair("username",u.getUsername()));
				//namevaluepairs.add(new BasicNameValuePair("password",u.getPassword()));
				StringEntity se = new StringEntity(json);
				
				httpPost.setEntity(se);
				 
				httpPost.setHeader("Accept","application/json");
				httpPost.setHeader("Content-type","application/json");
				
				HttpResponse httpResponse = httpClient.execute(httpPost);
				
				inputstream = httpResponse.getEntity().getContent();
				
				
				if(inputstream != null){
					result = convertInputStreamToString(inputstream);
							
				}
				else{
					result = "Did not work!";
				}
				
				
			}catch(Exception e){
				Log.d("InputStream",e.getLocalizedMessage());
			}
			return result;
		}
		
		 private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		        String line = "";
		        String result = "";
		        while((line = bufferedReader.readLine()) != null)
		            result += line;
		 
		        inputStream.close();
		        return result;
		 
		
		 }	

		 
}