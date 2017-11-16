/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smallapp.taxiclerk.adapter.HomeAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class HomeFragment.
 */
public class HomeFragment extends Fragment {

	/** The view pager. */
	private ViewPager viewPager;
	
	

	/**
	 * Instantiates a new home fragment.
	 */
	public HomeFragment() {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		viewPager = (ViewPager) rootView.findViewById(R.id.pager);		
		FragmentManager fm = getFragmentManager();		
		viewPager.setAdapter(new HomeAdapter(fm));

		return rootView;
	}

}

