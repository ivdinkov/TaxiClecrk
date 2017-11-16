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
 * The Class ExpSwitchFragment.
 */
public class ExpSwitchFragment extends Fragment {
	
	/** The fragment container. */
	protected int fragmentContainer;	
	
	/** The fragment. */
	protected IncomeUpdateFragment fragment;
	
	/** The fragment manager. */
	protected FragmentManager fragmentManager;
	
	/** The frag update expense record. */
	private ExpenseUpdateFragment fragUpdateExpenseRecord;

	/**
	 * Instantiates a new exp switch fragment.
	 */
	public ExpSwitchFragment() {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.expense_switch, container, false);

		fragmentManager = getFragmentManager();

		fragUpdateExpenseRecord = new ExpenseUpdateFragment();

		fragmentContainer = R.id.frag_expense_container;

		fragmentManager.beginTransaction().add(fragmentContainer, fragUpdateExpenseRecord).commit();

		return rootView;
	}

}