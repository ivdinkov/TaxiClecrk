/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportByExpenseType.
 */
public class ReportByExpenseType extends Fragment {
	
	/** The sdf db. */
	private SimpleDateFormat sdfDB;
	
	/** The sdf. */
	private SimpleDateFormat sdf;
	
	/** The start date to search. */
	private String startDateToSearch;
	
	/** The start date to display. */
	private String startDateToDisplay;
	
	/** The end date to search. */
	private String endDateToSearch;
	
	/** The end date to display. */
	private String endDateToDisplay;
	
	/** The btn spinner. */
	private Button btnSpinner;
	
	/** The expense type to be searched. */
	private String expenseTypeToBeSearched;
	
	/** The txt start date exp. */
	private TextView txtStartDateExp;
	
	/** The txt end date exp. */
	private TextView txtEndDateExp;
	
	/** The btn pick start date exp. */
	private Button btnPickStartDateExp;
	
	/** The btn pick end date exp. */
	private Button btnPickEndDateExp;
	
	/** The btn search exp. */
	private Button btnSearchExp;

	/**
	 * Instantiates a new report by expense type.
	 */
	public ReportByExpenseType() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.report_by_expense_type, container, false);

		txtStartDateExp = (TextView) rootView.findViewById(R.id.txtStartDateExp);
		txtEndDateExp = (TextView) rootView.findViewById(R.id.txtEndDateExp);
		btnPickStartDateExp = (Button) rootView.findViewById(R.id.btnPickStartDateExp);
		btnPickEndDateExp = (Button) rootView.findViewById(R.id.btnPickEndDateExp);
		btnSearchExp = (Button) rootView.findViewById(R.id.btnSearchByExp);
		btnSpinner = (Button) rootView.findViewById(R.id.btnExpenseType);

		/*
		 * 
		 * Pick dates to search
		 */
		btnPickStartDateExp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putInt("DATE", 1);

				DialogFragment newFragment = new DatePickerFragment();
				newFragment.setArguments(bundle);

				newFragment.show(getFragmentManager(), "datePicker");
			}
		});

		btnPickEndDateExp.setOnClickListener(new View.OnClickListener() {
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
		 * Getting the expense type
		 */
		btnSpinner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String[] items = view.getResources().getStringArray(R.array.expense_type_report);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
				new AlertDialog.Builder(getActivity()).setTitle(R.string.expense_prompt).setAdapter(adapter, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// btnSpinner.setBackgroundResource(R.drawable.edittext_login_box_border);
						btnSpinner.setText(items[which]);
						expenseTypeToBeSearched = items[which];

						dialog.dismiss();
					}
				}).create().show();
			}
		});
		/*
		 * 
		 * Search
		 */
		// Searching for dates
		btnSearchExp.setOnClickListener(new View.OnClickListener() {

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
					if (expenseTypeToBeSearched == null) {
						Toast.makeText(getActivity(), "Please select expense type", Toast.LENGTH_LONG).show();
					} else {
						ReportByExpenseResult fragment = new ReportByExpenseResult(expenseTypeToBeSearched, startDateToDisplay, endDateToDisplay, startDateToSearch, endDateToSearch);
						FragmentTransaction ft = getFragmentManager().beginTransaction();
						ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);								
						ft.replace(R.id.frame_container, fragment).commit();

					}
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
				txtStartDateExp.setText(startDateToDisplay);

			} else {
				sdf = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
				sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

				endDateToSearch = sdfDB.format(c.getTime());

				endDateToDisplay = sdf.format(c.getTime());
				txtEndDateExp.setText(endDateToDisplay);
			}
		}

	}

}
