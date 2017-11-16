/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class SettingsProviderFragment.
 */
public class SettingsProviderFragment extends Fragment {
	
	/** The txt prov2. */
	private EditText txtProv2;
	
	/** The txt prov3. */
	private EditText txtProv3;
	
	/** The base2. */
	private EditText base2;
	
	/** The base3. */
	private EditText base3;
	
	/** The btn update prov2. */
	private Button btnUpdateProv2;
	
	/** The btn update prov3. */
	private Button btnUpdateProv3;
	
	/** The db. */
	private DatabaseHandler db;
	
	/** The btn update finish. */
	private Button btnUpdateFinish;
	
	/** The prov to update2. */
	private String provToUpdate2;
	
	/** The prov to update3. */
	private String provToUpdate3;
	
	/** The nav menu titles. */
	private String[] navMenuTitles;
	
	/** The m title. */
	private CharSequence mTitle;
	
	/** The prov2saved. */
	private String prov2saved;
	
	/** The prov3saved. */
	private String prov3saved;

	/** The prov3 active. */
	private String prov2Active, prov3Active;

	/**
	 * Instantiates a new settings provider fragment.
	 */
	public SettingsProviderFragment() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings_provider, container, false);

		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		txtProv2 = (EditText) rootView.findViewById(R.id.txtProv2ToUpdate);
		txtProv3 = (EditText) rootView.findViewById(R.id.txtProv3ToUpdate);
		base2 = (EditText) rootView.findViewById(R.id.baseToUpdate2);
		base3 = (EditText) rootView.findViewById(R.id.baseToUpdate3);
		btnUpdateProv2 = (Button) rootView.findViewById(R.id.btnAddJobPr2ToUpdate);
		btnUpdateProv3 = (Button) rootView.findViewById(R.id.btnAddJobPr3ToUpdate);
		btnUpdateFinish = (Button) rootView.findViewById(R.id.btnFinishJobPrToUpdate);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		base2.setEnabled(false);
		base2.setClickable(false);
		base2.setFocusable(false);

		base3.setEnabled(false);
		base3.setClickable(false);
		base3.setFocusable(false);

		// Populating edit provider fields
		db = new DatabaseHandler(getActivity(), null);
		final List<Provider> prov = db.getProvidersState();

		for (Provider cn : prov) {
			if (cn.getID() == 2) {

				prov2Active = cn.getActive();

				if (prov2Active.equals("yes")) {

					btnUpdateProv2.setText("Delete");
					base2.setVisibility(View.GONE);
					txtProv2.setText(cn.getName());
					txtProv2.setEnabled(false);
					txtProv2.setClickable(false);
					txtProv2.setFocusable(false);

				}
				if (prov2Active.equals("no")) {

					btnUpdateProv2.setText("Add");
					base2.setVisibility(View.VISIBLE);
					txtProv2.setText("");
					txtProv2.setEnabled(true);
					txtProv2.setClickable(true);
					txtProv2.setFocusable(true);

				}

			}

			if (cn.getID() == 3) {

				prov3Active = cn.getActive();

				if (prov3Active.equals("yes")) {

					btnUpdateProv3.setText("Delete");
					base3.setVisibility(View.GONE);
					txtProv3.setText(cn.getName());
					txtProv3.setEnabled(false);
					txtProv3.setClickable(false);
					txtProv3.setFocusable(false);

				}
				if (prov3Active.equals("no")) {

					btnUpdateProv3.setText("Add");
					base3.setVisibility(View.VISIBLE);
					txtProv3.setText("");
					txtProv3.setEnabled(true);
					txtProv3.setClickable(true);
					txtProv3.setFocusable(true);

				}

			}


		}

		// handling Update Buttons
		btnUpdateProv2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				/*
				 * 
				 * 
				 * Handling Provider 2
				 */
				if (prov2Active.equals("yes")) {
					db.updateProvider(2, "", "no");

					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
					SettingsFragment fragment = new SettingsFragment();
					ft.replace(R.id.frame_container, fragment).commit();	
					setTitle(navMenuTitles[5]);
					InputMethodManager imanager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imanager.hideSoftInputFromWindow(txtProv2.getWindowToken(), 0);

				}
				if (prov2Active.equals("no")) {
					provToUpdate2 = txtProv2.getText().toString();
					if (provToUpdate2.isEmpty()) {
						Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_LONG).show();
					} else
						db.updateProvider(2, provToUpdate2, "yes");
					getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
					SettingsProviderFragment fragment = new SettingsProviderFragment();
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
					InputMethodManager imanager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imanager.hideSoftInputFromWindow(txtProv2.getWindowToken(), 0);
				}



			}
		});
		btnUpdateProv3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				/*
				 * 
				 * 
				 * Handling Provider 3
				 */
				if (prov3Active.equals("yes")) {
					db.updateProvider(3, "", "no");

					SettingsProviderFragment fragment = new SettingsProviderFragment();
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
					InputMethodManager imanager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imanager.hideSoftInputFromWindow(txtProv3.getWindowToken(), 0);
				}
				if (prov3Active.equals("no")) {
					provToUpdate3 = txtProv3.getText().toString();
					if (provToUpdate3.isEmpty()) {
						Toast.makeText(getActivity(), "Please enter name", Toast.LENGTH_LONG).show();
					} else
						db.updateProvider(3, provToUpdate3, "yes");
					getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
					SettingsProviderFragment fragment = new SettingsProviderFragment();
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
					InputMethodManager imanager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
					imanager.hideSoftInputFromWindow(txtProv3.getWindowToken(), 0);
				}
				db.close();
			}
		});
		btnUpdateFinish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
				SettingsFragment fragment = new SettingsFragment();
				ft.replace(R.id.frame_container, fragment).commit();	
				setTitle(navMenuTitles[5]);
			}
		});
		rootView.setFocusableInTouchMode(true);
		rootView.requestFocus();
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
        db.close();
		return rootView;
	}

	// Set ActionBarTitle
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(CharSequence title) {
		mTitle = title;
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(mTitle);
	}
}
