package com.example.sj;

import java.util.ArrayList;

import java.util.List;

import com.example.followme.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.widget.RadioGroup;

public class MainActivity extends FragmentActivity {
	private RadioGroup rgs;
	private List<Fragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_mian);
		//实例化radiogroup
		rgs = (RadioGroup) findViewById(R.id.radioGroup);
		fragments = new ArrayList<Fragment>();
     
		//分别添加3个fragment
		fragments.add(new FragmentPedometer());
		fragments.add(new FragmentDanmu());
		fragments.add(new FragmentWo());
		new FragmentAdapter(this, fragments, R.id.Fragment, rgs);

	}

}
