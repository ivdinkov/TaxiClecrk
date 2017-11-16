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
 * The Class IncSwitchFragment.
 *
 */
public class IncSwitchFragment extends Fragment {
	
	/** The fragment container. */
	protected int fragmentContainer;
	
	/** The frag update income record. */
	private Fragment fragUpdateIncomeRecord;	
	
	/** The fragment. */
	protected IncomeUpdateFragment fragment;
	
	/** The fragment manager. */
	protected FragmentManager fragmentManager;

	/**
	 * Instantiates a new inc switch fragment.
     *
	 */
	public IncSwitchFragment() {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.income_switch, container, false);

		fragmentManager = getFragmentManager();

		fragUpdateIncomeRecord = new IncomeUpdateFragment();

		fragmentContainer = R.id.frag_container;

		fragmentManager.beginTransaction().add(fragmentContainer, fragUpdateIncomeRecord).commit();

		return rootView;
	}

}