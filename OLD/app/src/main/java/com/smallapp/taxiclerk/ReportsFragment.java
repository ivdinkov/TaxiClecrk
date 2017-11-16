/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportsFragment.
 */
public class ReportsFragment extends Fragment {

	/** The view pager. */
	private ViewPager viewPager;
	
	/** The view page title. */
	private ViewPager viewPageTitle;
	

	/**
	 * Instantiates a new reports fragment.
	 */
	public ReportsFragment() {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_report, container, false);
		viewPager = (ViewPager) rootView.findViewById(R.id.pager);
		//viewPageTitle = (ViewPager) rootView.findViewById(R.id.pageTitle);
		FragmentManager fm = getFragmentManager();
		
		viewPager.setAdapter(new MyAdapter(fm));

		return rootView;
	}

}

class MyAdapter extends FragmentStatePagerAdapter {

	private int _count = 3;

	public MyAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		if (arg0 == 0) {
			fragment = new ReportByDate();
		}
		if (arg0 == 1) {
			fragment = new ReportByExpenseType();
		}
		if (arg0 == 2) {
			fragment = new ReportFull();
		}
		
		
		return fragment;
	}

	@Override
	public int getCount() {
		return _count;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String n = new String();
		if(position == 0)
		{
			n = "By Income";
		}
		if(position == 1)
		{
			n = "By Expense";
		}
		if(position == 2)
		{
			n = "Full Report";
		}
		
		
		
		return n;
	}
}