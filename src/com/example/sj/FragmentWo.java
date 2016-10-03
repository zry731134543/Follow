package com.example.sj;
import com.example.followme.R;
import com.example.wo.Exercise;
import com.example.wo.Goals;
import com.example.wo.Historcal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

 
/**
 * @author shijun
 * 这是wo的碎片
 *
 */
public class FragmentWo extends Fragment implements OnClickListener{
	
    private View view;
    private LinearLayout exerciseLayout;
    private LinearLayout goalsLayout;
    private LinearLayout HistoryLayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.wo, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}
	private void init() {
		exerciseLayout=(LinearLayout) view.findViewById(R.id.exercise);
		goalsLayout=(LinearLayout) view.findViewById(R.id.goals);
		HistoryLayout=(LinearLayout) view.findViewById(R.id.History);
//		settingLayout=(LinearLayout) view.findViewById(R.id.setting);
		
		exerciseLayout.setOnClickListener(this);
		goalsLayout.setOnClickListener(this);
		HistoryLayout.setOnClickListener(this);
//		settingLayout.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		    case R.id.History:
		    	 Intent intent = new Intent(getActivity(), Historcal.class);
		    	 startActivity(intent);
		    	 break;
		    case R.id.exercise:
		    	 Intent intent1 = new Intent(getActivity(), Exercise.class);
		    	 startActivity(intent1);
		    	 break;
		    case R.id.goals:
		    	 Intent intent2 = new Intent(getActivity(),Goals.class);
		    	 startActivity(intent2);
		    	 break;
		}
		
		
	}
    
	 
 
	 

}
