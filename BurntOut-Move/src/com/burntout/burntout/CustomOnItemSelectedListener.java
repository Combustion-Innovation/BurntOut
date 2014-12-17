package com.burntout.burntout;
 
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
 
public class CustomOnItemSelectedListener implements OnItemSelectedListener {
	
	
 
  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
	
		String thisItem = parent.getItemAtPosition(pos).toString();
  }
 
  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
  }
  
  
 
}