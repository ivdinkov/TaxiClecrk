/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class SettingsFragment.
 */
public class SettingsFragment extends Fragment {

	/** The nav menu titles. */
	private String[] navMenuTitles;

	/** The m title. */
	private CharSequence mTitle;

	/** The prov3 active. */
	private String prov2Active, prov3Active;

	/** The set provider. */
	private TextView setProvider;

	/** The change pin. */
	private TextView changePin;

	/** The send feed. */
	private TextView sendFeed;

	/** The about. */
	private TextView about;
	/** The email_to. */
	private String email_to = "feedback@small-app.com";

	/**
	 * Instantiates a new settings fragment.
	 */
	public SettingsFragment() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		setProvider = (TextView) rootView.findViewById(R.id.txtSetJobProv);
		changePin = (TextView) rootView.findViewById(R.id.txtChangePin);
		sendFeed = (TextView) rootView.findViewById(R.id.txtSendFeed);
		about = (TextView) rootView.findViewById(R.id.txtAbout);

		setProvider.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				SettingsProviderFragment fragment = new SettingsProviderFragment();
				ft.replace(R.id.frame_container, fragment).commit();
			}
		});
		changePin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				PinChange fragment = new PinChange();
				ft.replace(R.id.frame_container, fragment).commit();
			}
		});
		sendFeed.setOnClickListener(new View.OnClickListener() {

			private Boolean mConnection;

			@Override
			public void onClick(View v) {
				mConnection = checkConnection();
				if (mConnection) {
					email(email_to, "TaxiClerk Feedback", "");
				} else {
					Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
				}
			}
		});
		about.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				AboutFragment fragment = new AboutFragment();
				ft.replace(R.id.frame_container, fragment).commit();
			}
		});

		return rootView;
	}

	/**
	 * Send Feedback.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param body
	 *            the body
	 */
	public void email(String to, String subject, String body) {
		Intent chooser;
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { to });
		intent.putExtra(Intent.EXTRA_SUBJECT, "");
		intent.putExtra(Intent.EXTRA_TEXT, body);
		intent.setType("message/rfc822");

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			startActivity(intent);
		} else {
			chooser = Intent.createChooser(intent, "Send Email");
			startActivity(chooser);
		}

	}

	/**
	 * Check for connection.
	 *
	 * @return the boolean
	 */
	private Boolean checkConnection() {
		ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
		boolean is3g = false;
		boolean isWifi = false;
		// For 3G check
		try {
			is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			// For WiFi Check
			isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		} catch (Exception e) {
			// TODO: handle exception
		}


        return is3g || isWifi;
    }
}
