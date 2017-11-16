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
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportByDate.
 */
public class ReportByDate extends Fragment {

	/** The db. */
	private DatabaseHandler db;
	
	/** The prov selected. */
	private String provSelected;
	
	/** The end date to search. */
	private String startDateToSearch, endDateToSearch;
	
	/** The sdf. */
	private SimpleDateFormat sdf;
	
	/** The txt end date income. */
	private TextView txtStartDateIncome, txtEndDateIncome;
	
	/** The btn search income. */
	private Button btnPickStartDateIncome, btnPickEndDateIncome, btnSearchIncome;
	
	/** The start date to display. */
	public String startDateToDisplay;
	
	/** The end date to display. */
	public String endDateToDisplay;

	/**
	 * Instantiates a new report by date.
	 */
	public ReportByDate() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.report_by_date, container, false);

		txtStartDateIncome = (TextView) rootView.findViewById(R.id.txtStartDateIncome);
		txtEndDateIncome = (TextView) rootView.findViewById(R.id.txtEndDateIncome);
		btnPickStartDateIncome = (Button) rootView.findViewById(R.id.btnPickStartDateIncome);
		btnPickEndDateIncome = (Button) rootView.findViewById(R.id.btnPickEndDateIncome);
		btnSearchIncome = (Button) rootView.findViewById(R.id.btnGenerateFullReport);
		RadioButton rbStreetIncome = (RadioButton) rootView.findViewById(R.id.rbStreetIncome);
		final RadioButton rbIncomeAll = (RadioButton) rootView.findViewById(R.id.rbIncomeAll);
		final RadioButton rbStreet = (RadioButton) rootView.findViewById(R.id.rbStreetIncome);
		final RadioButton rbIncome2 = (RadioButton) rootView.findViewById(R.id.rbIncome2);
		final RadioButton rbIncome3 = (RadioButton) rootView.findViewById(R.id.rbIncome3);

		btnPickStartDateIncome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putInt("DATE", 1);

				DialogFragment newFragment = new DatePickerFragment();
				newFragment.setArguments(bundle);

				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		btnPickEndDateIncome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putInt("DATE", 2);

				DialogFragment newFragment = new DatePickerFragment();
				newFragment.setArguments(bundle);

				newFragment.show(getFragmentManager(), "datePicker");

			}
		});

		/*
		 * 
		 * 
		 * Displaying Providers if any
		 */
		db = new DatabaseHandler(getActivity(), null);
		List<Provider> prov = db.getAllProviders();
		int provCount = db.getProviderCount();

		if (provCount == 1) {
			rbIncomeAll.setVisibility(View.GONE);
			rbIncome2.setVisibility(View.GONE);
			rbIncome3.setVisibility(View.GONE);
			provSelected = "Street";
		} else {
			rbIncomeAll.setVisibility(View.VISIBLE);
			rbIncomeAll.setChecked(true);
			rbStreetIncome.setChecked(false);
			provSelected = "All";
			for (Provider cn : prov) {

				if (cn.getID() == 2 && cn.getActive().equals("yes")) {
					rbIncome2.setVisibility(View.VISIBLE);
					rbIncome2.setText(cn.getName());
				}
				if (cn.getID() == 3 && cn.getActive().equals("yes")) {
					rbIncome3.setVisibility(View.VISIBLE);
					rbIncome3.setText(cn.getName());
				}
			}
			db.close();
		}

		/*
		 * Getting Radio Provider Type
		 */

		rbIncomeAll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				provSelected = (String) rbIncomeAll.getText();
			}
		});
		rbStreet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				provSelected = (String) rbStreet.getText();
			}
		});

		rbIncome2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				provSelected = (String) rbIncome2.getText();
			}
		});

		rbIncome3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				provSelected = (String) rbIncome3.getText();
			}
		});

		// Searching for dates
		btnSearchIncome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int z = 0;
				int x = 0;
				try {
					x = Integer.valueOf(startDateToSearch.replaceAll("[^0-9]", ""));
					z = Integer.valueOf(endDateToSearch.replaceAll("[^0-9]", ""));

				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if (startDateToSearch == null || endDateToSearch == null) {
					Toast.makeText(getActivity(), "Please select Date", Toast.LENGTH_LONG).show();
				} else if (z < x) {
					Toast.makeText(getActivity(), "End Date can't be before Start Date", Toast.LENGTH_LONG).show();
				} else {
					// Log.d("Date",provSelected+startDateToSearch+endDateToSearch);
					ReportByIncomeResult fragment = new ReportByIncomeResult(provSelected, startDateToDisplay, endDateToDisplay, startDateToSearch, endDateToSearch);
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);							
					ft.replace(R.id.frame_container, fragment).commit();
				}

			}
		});

		return rootView;
	}

	/*
	 * date picker dialog
	 */
	/**
	 * The Class DatePickerFragment.
	 */
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		/** The Constant START_DATE. */
		static final int START_DATE = 1;
		
		/** The Constant END_DATE. */
		static final int END_DATE = 2;

		/** The m chosen date. */
		private int mChosenDate;

		/** The cur. */
		int cur = 0;
		
		/** The sdf db. */
		private SimpleDateFormat sdfDB;

		/* (non-Javadoc)
		 * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
		 */
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {

			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			Bundle bundle = this.getArguments();
			if (bundle != null) {
				mChosenDate = bundle.getInt("DATE", 1);
			}

			switch (mChosenDate) {

			case START_DATE:
				cur = START_DATE;
				return new DatePickerDialog(getActivity(), this, year, month, day);

			case END_DATE:
				cur = END_DATE;
				return new DatePickerDialog(getActivity(), this, year, month, day);

			}
			return null;
		}

		/* (non-Javadoc)
		 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
		 */
		@Override
		public void onDateSet(DatePicker datePicker, int year, int month, int day) {
			Calendar c = Calendar.getInstance();
			//int y = c.get(Calendar.YEAR);
			// setting current year, preventing the
			// user to pickup different year
			
			
			c.set(year, month, day);

			if (cur == START_DATE) {
				sdf = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
				sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

				startDateToSearch = sdfDB.format(c.getTime());
				
				startDateToDisplay = sdf.format(c.getTime());
				txtStartDateIncome.setText(startDateToDisplay);

			} else {
				sdf = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
				sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

				endDateToSearch = sdfDB.format(c.getTime());

				endDateToDisplay = sdf.format(c.getTime());
				txtEndDateIncome.setText(endDateToDisplay);
			}
		}

	}

}
