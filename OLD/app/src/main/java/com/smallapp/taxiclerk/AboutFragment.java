/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class AboutFragment.
 */
public class AboutFragment extends Fragment {
	
	/** The nav menu titles. */
	private String[] navMenuTitles;
	
	/** The m title. */
	private CharSequence mTitle;

	private TextView verCode;




	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_about, container, false);
		
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		verCode  = (TextView) rootView.findViewById(R.id.txtVersionCode);
		
		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
		verCode.setText(getVersionCode());
		
		
		int v = 0;
	    try {
	        v = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionCode;
	    } catch (NameNotFoundException e) {
	    	e.printStackTrace();
	    }
	    //verName.setText(String.valueOf(v));
	    
		rootView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK) {
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
					SettingsFragment fragment = new SettingsFragment();
					ft.replace(R.id.frame_container, fragment).commit();	
					setTitle(navMenuTitles[5]);
					return true;
				} else {
					return false;
				}
			}
		});

		return rootView;
	}

    private String getVersionCode() {
        PackageInfo pInfo = null;
        try {
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo != null ? pInfo.versionName : null;
    }

    // Set ActionBarTitle
	/**
	 * Sets the title.
	 * test
	 * test
	 * @param title the new title
	 */
	public void setTitle(CharSequence title) {
		mTitle = title;
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(mTitle);
	}
}
