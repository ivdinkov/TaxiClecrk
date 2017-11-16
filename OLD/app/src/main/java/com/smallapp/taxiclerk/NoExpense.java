/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.smallapp.taxiclerk.R;

// TODO: Auto-generated Javadoc
/**
 * The Class NoExpense.
 */
public class NoExpense extends Fragment{
	
	/**
	 * Instantiates a new no expense.
	 */
	public NoExpense() {
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.no_expense, container, false);
		
		return rootView;
	}
}
