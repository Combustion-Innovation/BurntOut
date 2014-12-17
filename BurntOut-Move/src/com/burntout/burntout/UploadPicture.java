package com.burntout.burntout;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import org.apache.*;




import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;



public class UploadPicture{
	String ServerResponse;
	String url;
	ArrayList<NameValuePair> nameValuePairs;
	Communicator comm;
	JSONObject response;
	String comment;
	String email;
	String picUrl;
	String pin_id;
	String lat;
	String lng;
	String gender;
	String type;
	
	public void setCommunicator (Communicator c)
	{
		comm = c;
	}

	
	
	public UploadPicture()
	{

	}

	
	
	public void executePosts(String ur, String picUrl,String email)
	{
		this.url = ur;
		
		this.picUrl = picUrl;
		this.email = email;
		
		new PostStuff().execute("D");
			
	}
	
	
	public void setoff()
	{
		comm.gotResponse(this.response);
		Log.d("Ddsdasdasd","Dasds");
	}
	
	public void silentRes()
	{
		comm.gotSilentResponse();
	}
	
private class PostStuff extends AsyncTask<String, Void, String> {
	   	
	
		
	@SuppressWarnings("deprecation")
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
	
		String d = params[0];
	    	
	    	 HttpClient httpclient = new DefaultHttpClient();
	    	    HttpPost httppost = new HttpPost(UploadPicture.this.url);
	    	      StringBuilder stringBuilder = new StringBuilder();
	    	    FileBody filebodyPic = new FileBody(new File(UploadPicture.this.picUrl));
	    	    StringBody myemail = null;
	    	    try {
	    	    	myemail = new StringBody(UploadPicture.this.email);
				} catch (UnsupportedEncodingException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	    	    
	    	    
	    	   // StringBody code = new StringBody(realtorCodeStr);

	    	    @SuppressWarnings("deprecation")
				MultipartEntity reqEntity = new MultipartEntity();
	    	    reqEntity.addPart("filename", filebodyPic);
	    	    reqEntity.addPart("email", myemail);
	    	    
	    
	    	    httppost.setEntity(reqEntity);

	    	    // DEBUG
	    	    System.out.println( "executing request " + httppost.getRequestLine( ) );
	    	    HttpResponse response = null;
				try {
					response = httpclient.execute( httppost );
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	    	    HttpEntity entity = response.getEntity( );
	    	
	            
	            if(response != null){
	            	
	            	
	            
	            }
	        
	       
	    	  httpclient.getConnectionManager( ).shutdown( );
			
	    	   try {
					entity.consumeContent( );
				} catch (IOException e) {
				// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	    return null;
	} 
	
    protected void onPostExecute(String result){
    	
    	UploadPicture.this.silentRes();
    	
    	//Post.this.comm.gotResponse("DD");
    
    
    	}

}


public String getUrl()
{
	return this.url;
}



public interface Communicator
{
		
	public void gotResponse(JSONObject s);
	public void gotSilentResponse();
	
}
    
 }