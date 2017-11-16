/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.User;

// TODO: Auto-generated Javadoc
/**
 * The Class PinChange.
 */
public class PinChange extends Fragment {
	
	/** The email. */
	private EditText email;
	
	/** The new pin. */
	private EditText newPin;
	
	/** The old pin. */
	private EditText oldPin;
	
	/** The btn change. */
	private Button btnChange;
	
	/** The db. */
	private DatabaseHandler db;
	
	/** The email base. */
	private TextView emailBase;
	
	/** The old pin base. */
	private EditText oldPinBase;
	
	/** The new pin base. */
	private EditText newPinBase;
	
	/** The _email. */
	private String _email = null;
	
	/** The _old pin. */
	private String _oldPin = null;
	
	/** The _new pin. */
	private String _newPin = null;
	
	/** The nav menu titles. */
	private String[] navMenuTitles;
	
	/** The m title. */
	private CharSequence mTitle;
	
	/** The origin_email. */
	private String origin_email;
	
	/** The origin_pin. */
	private String origin_pin;
	
	/** The confirm new pin. */
	private EditText confirmNewPin;
	
	/** The confirm new pin base. */
	private EditText confirmNewPinBase;
	
	/** The Constant TEXT_REGEX. */
	private static final String TEXT_REGEX = "^[a-zA-Z ]*$";

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.pin_change, container, false);
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		email = (EditText) rootView.findViewById(R.id.registeredEmail);
		emailBase = (TextView) rootView.findViewById(R.id.changeBase1);
		oldPin = (EditText) rootView.findViewById(R.id.oldPin);
		oldPinBase = (EditText) rootView.findViewById(R.id.changeBase2);
		newPin = (EditText) rootView.findViewById(R.id.newPin);
		newPinBase = (EditText) rootView.findViewById(R.id.changeBase3);
		confirmNewPin = (EditText) rootView.findViewById(R.id.confirmNewPin);
		confirmNewPinBase = (EditText) rootView.findViewById(R.id.changeBase4);
		btnChange = (Button) rootView.findViewById(R.id.btnChange);
		email.setHintTextColor(getResources().getColor(R.color.login_label_color));
		oldPin.setHintTextColor(getResources().getColor(R.color.login_label_color));
		newPin.setHintTextColor(getResources().getColor(R.color.login_label_color));
		confirmNewPin.setHintTextColor(getResources().getColor(R.color.login_label_color));
		
		
		db = new DatabaseHandler(getActivity(), null);

		User userInfo = db.getUser(1);
		origin_email = userInfo.getEmail();
		origin_pin = userInfo.getPin();
		
		btnChange.setOnClickListener(new View.OnClickListener() {

			private String _confirmNewPin;

			@Override
			public void onClick(View v) {
				_email = email.getText().toString();
				_oldPin = oldPin.getText().toString();
				_newPin = newPin.getText().toString();
				_confirmNewPin = confirmNewPin.getText().toString();
				// check email first
				if (_email != null && isValidEmail(_email) && _email.equals(origin_email)) {// &&
																							// isValidEmail(_email)
																							// &&
																							// _email.equals(origin_email)
					// check pin match
					if (_oldPin.equals(origin_pin)) {
						// check old pin match new pin
						if (_newPin.equals(_confirmNewPin) && isValidPin(_confirmNewPin)) {
							// change pin
							db.updateUser(new User(1, "user", _email, _newPin));
							FragmentTransaction ft = getFragmentManager().beginTransaction();
							ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
							SettingsFragment fragment = new SettingsFragment();
							ft.replace(R.id.frame_container, fragment).commit();	
							setTitle(navMenuTitles[5]);
						} else {
							confirmNewPinBase.setBackgroundResource(R.drawable.error);
							Toast.makeText(getActivity(), "Invalid PIN", Toast.LENGTH_LONG).show();
						}
					} else {
						oldPinBase.setBackgroundResource(R.drawable.error);
						Toast.makeText(getActivity(), "Invalid old PIN", Toast.LENGTH_LONG).show();
					}

				} else {
					emailBase.setBackgroundResource(R.drawable.error);
					Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_LONG).show();
				}
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
		return rootView;
	}

	/**
	 * Pin validity.
	 *
	 * @param target the target
	 * @return true, if is valid pin
	 */
	public static boolean isValidPin(String target) {
		if (target == null) {
			return false;
		} else {
            return target.length() >= 5;
		}
	}

	/**
	 * Email validity.
	 *
	 * @param target the target
	 * @return true, if is valid email
	 */
	public static boolean isValidEmail(String target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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
