/*
 * Copyright (c) 2014. Small-app.com
 */

package com.smallapp.taxiclerk.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.smallapp.taxiclerk.FragA;
import com.smallapp.taxiclerk.FragB;
import com.smallapp.taxiclerk.FragC;

public class HomeAdapter extends FragmentStatePagerAdapter {

	private String fragmentNames[] = {"TODAY","WEEK","YEAR"};

	public HomeAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		Fragment fragment = null;
		if (arg0 == 0) {
			fragment = new FragA();
		}
		if (arg0 == 1) {
			fragment = new FragB();
		}
		if (arg0 == 2) {
			fragment = new FragC();
		}

		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String n = new String();
		if(position == 0)
		{
			n = fragmentNames[0];
		}
		if(position == 1)
		{
			n = fragmentNames[1];
		}
		if(position == 2)
		{
			n = fragmentNames[2];
		}
		return n;
	}
}
