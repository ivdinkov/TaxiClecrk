/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Hours;

// TODO: Auto-generated Javadoc
/**
 * The Class HoursFragment.
 */
public class HoursFragment extends Fragment {

	/** The txt date. */
	private TextView txtDate;

	/** The date to be saved. */
	private String dateToBeSaved;

	/** The btn date picker. */
	private Button btnDatePicker;

	/** The btn date cancel. */
	private Button btnDateCancel;

	/** The btn save hours. */
	private Button btnSaveHours;

	/** The active. */
	private String active;

	/** The db. */
	private DatabaseHandler db;

	/** The exist. */
	private int exist;

	/** The time picker. */
	private TimePicker timePicker;

	/** The sdf. */
	private SimpleDateFormat sdf;

	/** The date to display. */
	private String dateToDisplay;

	/** The sdf db. */
	private SimpleDateFormat sdfDB;

	/** The shift. */
	private String shift;

	/** The m title. */
	private CharSequence mTitle;

	/** The nav menu titles. */
	private String[] navMenuTitles;

	/** The custom view. */
	public View customView;

	/**
	 * Instantiates a new hours fragment.
	 */
	public HoursFragment() {
		sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

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
		View rootView = inflater.inflate(R.layout.fragment_hours, container, false);

		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		txtDate = (TextView) rootView.findViewById(R.id.txtDate);
		// txtHoursToBeSaved = (EditText)
		// rootView.findViewById(R.id.hours_hours);
		btnDatePicker = (Button) rootView.findViewById(R.id.btnPickDate);
		btnDateCancel = (Button) rootView.findViewById(R.id.btnCanselHours);
		btnSaveHours = (Button) rootView.findViewById(R.id.btnSaveHours);
		timePicker = (TimePicker) rootView.findViewById(R.id.timePicker1);
		// lblHr = (TextView) rootView.findViewById(R.id.lblHours);
		setCurrentDate();
		shift = "8.00";
		timePicker.setCurrentHour(8);
		timePicker.setCurrentMinute(0);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				updateDisplay(hourOfDay, minute);
			}
		});
		/*
		 * Income date picker
		 */
		btnDatePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				DialogFragment picker = new DatePickerFragment();
				picker.show(getFragmentManager(), "datePicker");

			}
		});

		/*
		 * Cancel btn
		 */
		btnDateCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
				HomeFragment fragment = new HomeFragment();
				ft.replace(R.id.frame_container, fragment).commit();
				setTitle(navMenuTitles[0]);
			}
		});
		/*
		 * 
		 * Save Hours
		 */
		btnSaveHours.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				db = new DatabaseHandler(getActivity(), null);
				// hoursToBeSaved = txtHoursToBeSaved.getText().toString();
				exist = 1;
				List<Hours> hours = db.getAllHours();
				for (Hours cn : hours) {
					if (dateToDisplay.equals(cn.getDate())) {
						exist = 2;
					}
				}

				if (exist == 2) {
					Toast.makeText(getActivity(), "Record for " + dateToBeSaved + " allready exist", Toast.LENGTH_LONG).show();
				} else {
					showAlertDialog();
				}

			}

			private void showAlertDialog() {
				FragmentManager fm = getFragmentManager();
				MyDialog alertDialog = new MyDialog();
				alertDialog.show(fm, "fragment_alert");
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
					setTitle(navMenuTitles[0]);
					return true;
				} else {
					return false;
				}
			}
		});
		return rootView;
	}// end onCreate

	/**
	 * Update display.
	 *
	 * @param hourOfDay
	 *            the hour of day
	 * @param minute
	 *            the minute
	 */
	private void updateDisplay(int hourOfDay, int minute) {
		shift = String.valueOf(new StringBuilder().append(pad(hourOfDay)).append(".").append(pad(minute)));
	}

	/**
	 * Pad.
	 *
	 * @param c
	 *            the c
	 * @return the string
	 */
	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

	// Dialog
	/**
	 * The Class MyDialog.
	 */
	public class MyDialog extends DialogFragment {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle
		 * )
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
					active = "yes";
					db.addHours(new Hours(shift, dateToBeSaved, active));
					db.close();

					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
					HomeFragment fragment = new HomeFragment();
					ft.replace(R.id.frame_container, fragment).commit();
					setTitle(navMenuTitles[0]);
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

	// Set ActionBarTitle
	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(CharSequence title) {
		mTitle = title;
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(mTitle);
	}

	/*
	 * current date picker
	 */
	/**
	 * Sets the current date.
	 */
	private void setCurrentDate() {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.set(year, month, day);

		dateToDisplay = sdf.format(c.getTime());
		dateToBeSaved = sdfDB.format(c.getTime());

		txtDate.setText(dateToDisplay);
	}

	/*
	 * date picker dialog
	 */
	/**
	 * The Class DatePickerFragment.
	 */
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle
		 * )
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.
		 * widget.DatePicker, int, int, int)
		 */
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);// setting current year, preventing the
			// user to pickup different year
			c.set(y, month, day);

			dateToDisplay = sdf.format(c.getTime());
			dateToBeSaved = sdfDB.format(c.getTime());

			txtDate.setText(dateToDisplay);

		}

	}
}
