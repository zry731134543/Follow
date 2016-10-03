package com.example.wo;

 
import com.example.followme.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Historcal extends Activity {
	private TextView View;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.history);
 	
	}
	

}
