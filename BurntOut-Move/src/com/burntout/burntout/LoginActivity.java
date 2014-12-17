package com.burntout.burntout;





import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;





import com.facebook.*;
import com.facebook.android.Facebook;
import com.facebook.model.*;
import com.facebook.widget.*;
import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.*;


 
public class LoginActivity extends Activity implements Post.Communicator {
	 RelativeLayout main;
	 ImageView myImage;
	 ProgressDialog pm;
	 Post login;
	
	 ArrayList<EditText>inputs;
	 Facebook facebook;
	 private PendingAction pendingAction = PendingAction.NONE;
	 private GraphUser user;
	 
	 String username;
    String birthday;
    String firstname;
    String lastname;
    String picture;
    String picurl;
    String gender;
    String age;
    String fbid;
    String login_lat;
    String login_lng;
    String device;
    String email;
    String token;
    private LoginButton facebookButton;
    private TextView facebookLoginStatus;
    RelativeLayout mainrelativelayout;
    int isFBLogin;
    boolean isLoggedIn;
    boolean initialLogin = true;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.login_screen);
			
			isLoggedIn = false;
			
			pm = null;
			
			
			
			
			
			mainrelativelayout = (RelativeLayout)findViewById(R.id.mainView);
			
			facebookButton = (LoginButton) findViewById(R.id.authButton);
			facebookButton.setReadPermissions(Arrays.asList("basic_info","email"));
			
			facebookButton.setUserInfoChangedCallback(new LoginButton.UserInfoChangedCallback() {
	            @Override
	            public void onUserInfoFetched(GraphUser user) {
	            			LoginActivity.this.user = user;
	            			
	    					
	                updateUI();
	                // It's possible that we were waiting for this.user to be populated in order to post a
	                // status update.
	           
	            }
			});   
	            
	            
	            if(checkLoginType() == 1)
	            {
	            	Session.openActiveSession(this,  true, new Session.StatusCallback() {
	    				
	    				@Override
	    				public void call(Session session, SessionState state, Exception exception) {
	    					// TODO Auto-generated method stub
	    					if(session.isOpened()) {
	    						
	    						Request.newMeRequest(session,  new Request.GraphUserCallback() {
	    							
	    							@Override
	    							public void onCompleted(GraphUser user, Response response) {
	    								if(user != null) {
	    									// TODO Auto-generated method stub
	    									String fname = user.getFirstName();
	    									String lname = user.getLastName();
	    									String email = (String) user.asMap().get("email");
	    									String fbID = user.getId();
	    									picture = "https://graph.facebook.com/" + user.getId() + "/picture";
	    									isFBLogin = 1;
	    								}
	    								
	    							}
	    						}).executeAsync();
	    					}
	    					
	    				}
	    			});
	            }
	            
	            
			
	            
	        
			
			
	
			
			//Debugging activity redirect
			
			/*
			Intent intent = new Intent(this, DebugActivity.class);
			startActivity(intent);
			*/
			
			bye();
			int[] ids = {R.id.email, R.id.password};
			inputs = new ArrayList<EditText>(ids.length);
			
			for(int i=0;i<ids.length;i++)
			{
				EditText b =  (EditText)findViewById(ids[i]);
				inputs.add(b);
			}

			fixLayout();
			
			checkIfLoggedIn();
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void bye(){
	
	     main  = (RelativeLayout)findViewById(R.id.mainView);
		 myImage = new ImageView(this);
		 myImage.setImageResource(R.drawable.splash);
		 myImage.setScaleType(ScaleType.FIT_XY);
		 int wid =   (int) (this.getResources().getDisplayMetrics().widthPixels );
		 int height =   (int) (this.getResources().getDisplayMetrics().heightPixels);
		 main.addView(myImage);
		 main.bringChildToFront(myImage);
		 myImage.getLayoutParams().height = height;
		 myImage.getLayoutParams().width = wid;
		 new AlertDialog.Builder(this)
		  	.setTitle("Don't Text While Driving")
		  	.setMessage("Please pull over before using this app")
		  	.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					new Handler().postDelayed(new Runnable() {
						  
						
			            @Override
			            public void run() {
			    
			            		
							main.removeView(myImage);
							checkIfLoggedIn();
			            	
			            }
			        }, 3000);
					
				}
			})
		  	.show();
		  
		 
		 
		
		  
	}
	
	

		
		public void fixLayout()
		{
			 int wid =   (int) (this.getResources().getDisplayMetrics().widthPixels * .92);
			int[]	inputs = {R.id.password,R.id.email};
			for(int i=0;i<inputs.length;i++)
			{
				
				EditText b =  (EditText)findViewById(inputs[i]);
				b.getLayoutParams().width = wid;
				
				
			}
			
			facebookButton.getLayoutParams().width = wid;
			Button ba = (Button)findViewById(R.id.loginbutton);
			ba.getLayoutParams().width = wid;
	
		}	
	
		
	
		
		public void forgotPass(View v)
		{
			Intent i = new Intent(this,ForgotPasswordActivity.class);
		
			this.startActivity(i);
			
		}
		
		
		public void tryToLogin(View v)
		{
				String email = inputs.get(0).getText().toString();
				String password = inputs.get(1).getText().toString();
				isFBLogin = 0;
				loginBurnt(email,password);
		}
		
		
		public void signUpScreen(View v)
		{
			
			Intent i = new Intent(this,SignUpActivity.class);
			   this.overridePendingTransition(R.anim.stay,
		               R.anim.slide_up_in);
			this.startActivityForResult(i, 1);
		}
		
		
		public void loginBurnt(String email, String password)
		{
			//if(!isLoggedIn) {
				if(validated())
				{
				
				pm = new ProgressDialog(this);
				pm.show();
		
				login = new Post();
				login.setCommunicator(this);
				
		
				 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		         nameValuePairs.add(new BasicNameValuePair("email", email));
		         nameValuePairs.add(new BasicNameValuePair("password",password));
		         login.executePosts(nameValuePairs,"http://combustioninnovation.com/production/Goodyear/php/login.php");
				}
			//}
			
			
		}
		
		
		
		@Override
		public void gotResponse(JSONObject s) {
			// TODO Auto-generated method stub
			
			String status;
			try {
				status = s.getString("status");
				if(status.equals("one"))
				{
				
				//	Toast.makeText(this, "JESUS",Toast.LENGTH_LONG).show();
					
					SharedPreferences sharedPref = ((Activity) this).getPreferences(Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("fname", s.getString("fname"));
					String em = inputs.get(0).getText().toString();
					String pass = inputs.get(1).getText().toString();
				
					editor.putString("email", em);
					editor.putString("password", pass);
					
					Log.d("fblogin", Integer.toString(isFBLogin));
					editor.putString("isFB", Integer.toString(isFBLogin));
				
					editor.commit();
					
					//Log.d("isFB", s.getString("logintype"));
			//		 Toast.makeText(this, em, Toast.LENGTH_LONG).show();
					isLoggedIn = true;
					
					
					
					Intent i = new Intent(this,ProfileActivity.class);
					
					 i.putExtra("fname", s.getString("fname"));
					 i.putExtra("lname", s.getString("lname"));
					 i.putExtra("email", s.getString("email"));
					 i.putExtra("picture", s.getString("picture"));
					 i.putExtra("logintype", Integer.toString(isFBLogin));
					 
					 
					 
					 
				//	 Toast.makeText(this, s.getString("picture"),Toast.LENGTH_LONG).show();
					 String password = inputs.get(1).getText().toString();
					 i.putExtra("password",password);
					   this.overridePendingTransition(R.anim.stay,
				               R.anim.slide_up_in);
					this.startActivityForResult(i, 3);
				}
				else {
					if(!initialLogin) {
						Toast.makeText(this, "Incorrect Password" , Toast.LENGTH_LONG).show();
					}
				
					
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(pm!=null)
			{
				pm.dismiss();
				pm = null;
			}
			initialLogin = false;
		}

		
		
		public boolean validated(){
			
			String email = inputs.get(0).getText().toString();
			String password = inputs.get(1).getText().toString();
			return true;
		}
		

	
public int checkLoginType()
{
	
	Context context =this; 
	SharedPreferences sharedPref = context.getSharedPreferences(
	  getString(R.string.pref), Context.MODE_PRIVATE);

	String ltype = sharedPref.getString("logintype","0");
	
	return Integer.parseInt(ltype);

}



public void checkIfLoggedIn()
{
	Context context =this; 
	SharedPreferences sharedPref = context.getSharedPreferences(
	  getString(R.string.pref), Context.MODE_PRIVATE);

	String email = sharedPref.getString("email", "no");
	String password = sharedPref.getString("password", "no");
	
	//Toast.makeText(this,email,Toast.LENGTH_LONG).show();
	if(!password.equals("no"))
	{
			//saved credentials
		
		
		loginBurnt(email,password);
	}

} 


  




protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
	
    if (requestCode == 1) {
        if(resultCode == RESULT_OK){
            String email=data.getStringExtra("email");
            String password=data.getStringExtra("password");
            loginBurnt(email,password);
        }
        if (resultCode == RESULT_CANCELED) {
            //Write your code if there's no result
        }
    }
    else if(requestCode == 2)
    {
    	   if(resultCode == RESULT_OK){
               String email=data.getStringExtra("email");
               String password=data.getStringExtra("password");
               inputs.get(1).setText(password);
               inputs.get(0).setText(email);
               loginBurnt(email,password);
           }
           if (resultCode == RESULT_CANCELED) {
               //Write your code if there's no result
           }
    }
    else if(requestCode == 3) {
    	
    	if(resultCode == RESULT_OK) {
    		
    		Session.getActiveSession().closeAndClearTokenInformation();
    		Session.setActiveSession(null);
    		isFBLogin =0;
    		
    	}
    	else
    	{
    		isLoggedIn = false;
    		
    		
    	
    	}
    	
    	facebookButton.setEnabled(true);
    	SharedPreferences sharedPref = this.getSharedPreferences(
				  getString(R.string.pref), Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		
		editor.clear();
		editor.commit();
    }
    else
    {
    	  super.onActivityResult(requestCode, resultCode, data);
  	     Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

    }
}//onActivityResult

private Session.StatusCallback callback = new Session.StatusCallback() {
    @Override
    public void call(Session session, SessionState state, Exception exception) {
     onSessionStateChange(session, state, exception);
     
     String token = session.getAccessToken();
     Log.d("mytoken",token);
    }
};
private enum PendingAction {
    NONE,
    POST_PHOTO,
    POST_STATUS_UPDATE
}
private UiLifecycleHelper uiHelper;






private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
    @Override
    public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
        Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
    }

    @Override
    public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
        Log.d("HelloFacebook", "Success!");
    }
};
private void onSessionStateChange(Session session, SessionState state, Exception exception) {
    if (pendingAction != PendingAction.NONE &&
            (exception instanceof FacebookOperationCanceledException ||
            exception instanceof FacebookAuthorizationException)) {
            new AlertDialog.Builder(LoginActivity.this)
                .setTitle("cancel")
                .setMessage("said no")
                .setPositiveButton("cool", null)
                .show();
   
        } else if (state == SessionState.OPENED_TOKEN_UPDATED) {

    }
    
}

private void updateUI() {
    Session session = Session.getActiveSession();
    boolean enableButtons = (session != null && session.isOpened());

    		
    if (enableButtons && user != null) {
    	picurl = "https://graph.facebook.com/";
    	
    //	 facebookLoginStatus.setText("Logout of Facebook");
    	 firstname  = user.getFirstName();
    	 lastname = user.getLastName();
    	 birthday = user.getBirthday();
    	 gender = (String) user.asMap().get("gender");
    	 fbid = user.getId();
    	 username = user.getUsername();
    	 picture = picurl + user.getId() + "/picture?width=400";
    	 device = "Android";
    	 email = (String) user.asMap().get("email");
    //	 Toast.makeText(this, email, Toast.LENGTH_LONG).show();
    	 
    	 ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
    	 nameValuePairs.add(new BasicNameValuePair("email", email));
    	 nameValuePairs.add(new BasicNameValuePair("fname", firstname));
    	 nameValuePairs.add(new BasicNameValuePair("lname", lastname));
    	 nameValuePairs.add(new BasicNameValuePair("birthday", birthday));
    	 nameValuePairs.add(new BasicNameValuePair("gender", gender));
    	 nameValuePairs.add(new BasicNameValuePair("fbid", user.getId()));
    	 nameValuePairs.add(new BasicNameValuePair("picture", picture));
    	 
    	 Post fbLogin = new Post();
    	 fbLogin.setCommunicator(this);
    	 
    	 fbLogin.executePosts(nameValuePairs, "http://combustioninnovation.com/production/Goodyear/php/loginFB.php");
    	 
    	 
    	 
    	 
    }
}

private void logout(){
    // clear any user information
    isLoggedIn = false;
    
    // find the active session which can only be facebook in my app
    Session session = Session.getActiveSession();
    // run the closeAndClearTokenInformation which does the following
    // DOCS : Closes the local in-memory Session object and clears any persistent 
    // cache related to the Session.
    session.closeAndClearTokenInformation();
    
    session.isClosed();
    // return the user to the login screen
    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    // make sure the user can not access the page after he/she is logged out
    // clear the activity stack
    finish();
}



}


