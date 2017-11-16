/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// TODO: Auto-generated Javadoc
/**
 * The Class HoursSwitchFragment.
 */
public class HoursSwitchFragment extends Fragment {
	
	/** The fragment container. */
	protected int fragmentContainer;
	
	/** The frag update hours record. */
	private Fragment fragUpdateHoursRecord;	
	
	/** The fragment. */
	protected HoursUpdateFragment fragment;
	
	/** The fragment manager. */
	protected FragmentManager fragmentManager;

	/**
	 * Instantiates a new hours switch fragment.
	 */
	public HoursSwitchFragment() {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.hours_switch, container, false);

		fragmentManager = getFragmentManager();

		fragUpdateHoursRecord = new HoursUpdateFragment();

		fragmentContainer = R.id.frag_hours_container;

		fragmentManager.beginTransaction().add(fragmentContainer, fragUpdateHoursRecord).commit();

		return rootView;
	}

}