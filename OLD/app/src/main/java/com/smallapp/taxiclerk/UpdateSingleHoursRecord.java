/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.app.Dialog;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Hours;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateSingleHoursRecord.
 */
public class UpdateSingleHoursRecord extends Fragment {

	/** The record id. */
	private int recordID;
	
	/** The db. */
	private DatabaseHandler db;
	
	/** The txt date. */
	private TextView txtDate;
	
	/** The txt hours. */
	private EditText txtHours;
	
	/** The btn hours. */
	private Button btnHours;
	
	/** The btn cancel. */
	private Button btnCancel;
	
	/** The date to be saved. */
	private String dateToBeSaved;
	
	/** The hours to be saved. */
	private String hoursToBeSaved;
	
	/** The fragment manager. */
	private FragmentManager fragmentManager;
	
	/** The frag update hours record. */
	private HoursUpdateFragment fragUpdateHoursRecord;
	
	/** The fragment container. */
	private int fragmentContainer;
	
	/** The date to display. */
	private String dateToDisplay;
	
	/** The shift. */
	private String shift;
	
	/** The time picker. */
	private TimePicker timePicker;
	
	/** The custom view. */
	public View customView;
	
	/** The m title. */
	private CharSequence mTitle;
	
	/** The nav menu titles. */
	private String[] navMenuTitles;

	/**
	 * Instantiates a new update single hours record.
	 *
	 * @param recordID the record id
	 */
	public UpdateSingleHoursRecord(int recordID) {
		this.recordID = recordID;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_update_single_hours_record, container, false);
		// Preventing softkey pad overlap
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		// init db connetion
		db = new DatabaseHandler(getActivity(), null);
		Hours single_hours_record = db.getSingleHoursRecord(recordID);

		txtDate = (TextView) rootView.findViewById(R.id.txtDateToUpdate);
		dateToDisplay = single_hours_record.getDate();
		txtDate.setText(dateToDisplay);
		shift = single_hours_record.getHours();
		btnHours = (Button) rootView.findViewById(R.id.btnSaveHoursToUpdate);
		btnCancel = (Button) rootView.findViewById(R.id.btnCanselHoursToUpdate);
		timePicker = (TimePicker) rootView.findViewById(R.id.timePickerToUpdate);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		setShiftTime(shift);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				updateDisplay(hourOfDay, minute);
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
				HoursSwitchFragment fragment = new HoursSwitchFragment();
				ft.replace(R.id.frame_container, fragment).commit();

			}
		});
		btnHours.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dateToBeSaved = reverseDate(dateToDisplay);
				showAlertDialog();
			}

			private void showAlertDialog() {
				FragmentManager fm = getFragmentManager();
				MyDialog alertDialog = new MyDialog();
				alertDialog.show(fm, "fragment_alert");
			}

			private String reverseDate(String a) {

				String str = new String(a);
				String x = str.replaceAll("[^0-9]", "");
				String day = x.substring(0, 2);
				String month = x.substring(2, x.length() - 4);
				String year = x.substring(4, x.length());
				String ready = year + "-" + month + "-" + day;
				return ready;
			}

		});
		rootView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK) {
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
					HomeFragment fragment = new HomeFragment();
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

	// Dialog
	/**
	 * The Class MyDialog.
	 */
	public class MyDialog extends DialogFragment {

		/* (non-Javadoc)
		 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			Rect displayRectangle = new Rect();
			Window window = getActivity().getWindow();
			window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

			LayoutInflater inflater = getActivity().getLayoutInflater();
			final Dialog alertDialogBuilder = new Dialog(getActivity(), R.style.DialogTheme);

			customView = inflater.inflate(R.layout.dialog_empty, null);

			customView.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
			// customView.setMinimumHeight((int) (displayRectangle.height() *
			// 0.9f));
			final TextView txtMsg = (TextView) customView.findViewById(R.id.txtDialogMsg1);
			final TextView txtMsg2 = (TextView) customView.findViewById(R.id.txtDialogMsg2);

			TextView btnYes = (TextView) customView.findViewById(R.id.btnDialogYes);
			TextView btnNo = (TextView) customView.findViewById(R.id.btnDialogNo);
			txtMsg.setText("Shift duration  ");
			txtMsg2.setText(shift);

			alertDialogBuilder.setContentView(customView);
			btnYes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					db = new DatabaseHandler(getActivity(), null);

					db.updateHours(recordID, shift, dateToBeSaved);

					db.close();
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
					HoursSwitchFragment fragment = new HoursSwitchFragment();
					ft.replace(R.id.frame_container, fragment).commit();
					alertDialogBuilder.cancel();

				}
			});
			btnNo.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					alertDialogBuilder.cancel();

				}
			});
			return alertDialogBuilder;
		}
	}

	/**
	 * Sets the shift time.
	 *
	 * @param a the new shift time
	 */
	private void setShiftTime(String a) {
		String hr, min;
		String str = new String(a);
		String x = str.replaceAll("[^0-9]", "");
		if (x.length() > 3) {
			hr = x.substring(0, 2);
			min = x.substring(2, x.length());
		} else {
			hr = x.substring(0, 1);
			min = x.substring(1, x.length());
		}

		// Log.d("Time", hr + min);
		timePicker.setCurrentHour(Integer.valueOf(hr));
		timePicker.setCurrentMinute(Integer.valueOf(min));
	}

	/**
	 * Update display.
	 *
	 * @param hourOfDay the hour of day
	 * @param minute the minute
	 */
	private void updateDisplay(int hourOfDay, int minute) {
		shift = String.valueOf(new StringBuilder().append(pad(hourOfDay)).append(".").append(pad(minute)));
	}

	/**
	 * Pad.
	 *
	 * @param c the c
	 * @return the string
	 */
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
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
