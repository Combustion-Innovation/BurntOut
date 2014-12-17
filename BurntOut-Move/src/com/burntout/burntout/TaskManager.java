package com.burntout.burntout;

import android.widget.Button;
import android.widget.FrameLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

public class TaskManager {
	
	public boolean clickable;
	
	public View view;
	
	public TaskManager(View v) {
		
		this.view = v;
		this.clickable = true;
		
	}
	
	
	public void setClickable() {
		this.clickable = true;
		this.view.setClickable(true);
		
	}
	
	public void unsetClickable() {
		this.clickable = false;
		this.view.setClickable(false);
	}

	
	
	
	
}
