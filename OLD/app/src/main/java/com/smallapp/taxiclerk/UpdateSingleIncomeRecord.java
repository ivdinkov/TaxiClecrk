/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Income;
import com.smallapp.taxiclerk.sqlite.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateSingleIncomeRecord.
 */
public class UpdateSingleIncomeRecord extends Fragment {

	/** The db. */
	private DatabaseHandler db;
	
	/** The btn date picker. */
	private View btnDatePicker;
	
	/** The txt date. */
	private TextView txtDate;
	
	/** The txt income amount. */
	private EditText txtIncomeAmount;
	
	/** The date to be saved. */
	private String dateToBeSaved;
	
	/** The income amount to be saved. */
	private String incomeAmountToBeSaved = null;

	/** The notes to be saved. */
	private String notesToBeSaved;
	
	/** The active. */
	private String active = "yes";

	/** The txt notes. */
	private EditText txtNotes;
	
	/** The btn income. */
	private Button btnIncome;
	
	/** The btn cancel. */
	private Button btnCancel;
	
	/** The record id. */
	private int recordID;
	
	/** The type to be saved. */
	protected String typeToBeSaved;
	
	/** The radio selected text. */
	private String radioSelectedText;
	
	/** The type. */
	private String type;
	
	/** The date to display. */
	private String dateToDisplay;
	
	/** The sdf. */
	private SimpleDateFormat sdf;
	
	/** The sdf db. */
	private SimpleDateFormat sdfDB;
	
	/** The rd provider. */
	private String rdProvider;
	
	/** The pr3. */
	private RadioButton pr3;
	
	/** The pr2. */
	private RadioButton pr2;
	
	/** The rd street. */
	private RadioButton rdStreet;
	
	/** The inc base. */
	private EditText incBase;
	
	/** The radio income group. */
	private RadioGroup radioIncomeGroup;
	
	/** The custom view. */
	private View customView;
	
	/** The root view. */
	private View rootView;
	
	/** The nav menu titles. */
	private String[] navMenuTitles;
	
	/** The m title. */
	private CharSequence mTitle;

	/**
	 * Instantiates a new update single income record.
	 *
	 * @param recordID the record id
	 */
	public UpdateSingleIncomeRecord(int recordID) {
		this.recordID = recordID;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		int measuredWidth = 0;
		int measuredHeight = 0;
		Point size = new Point();
		WindowManager w = getActivity().getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			w.getDefaultDisplay().getSize(size);

			measuredWidth = size.x;
			measuredHeight = size.y;
		} else {
			Display d = w.getDefaultDisplay();
			measuredWidth = d.getWidth();
			measuredHeight = d.getHeight();
		}
		if (Integer.valueOf(measuredHeight) < 800) {
			// support for 320x480
			rootView = inflater.inflate(R.layout.fragment_update_single_income_record_small, container, false);
		} else {
			// support for 400x800
			rootView = inflater.inflate(R.layout.fragment_update_single_income_record, container, false);
		}

		// View rootView =
		// inflater.inflate(R.layout.fragment_update_single_income_record,
		// container, false);
		// Preventing softkey pad overlap
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		txtDate = (TextView) rootView.findViewById(R.id.txtDateToUpdate);
		btnDatePicker = rootView.findViewById(R.id.btnPickDateToUpdate);
		txtIncomeAmount = (EditText) rootView.findViewById(R.id.incomeAmountToUpdate);

		txtNotes = (EditText) rootView.findViewById(R.id.notesToUpdate);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		radioIncomeGroup = (RadioGroup) rootView.findViewById(R.id.radioIncomeTypeToUpdate);
		final RadioButton rd1 = (RadioButton) rootView.findViewById(R.id.inc_cashToUpdate);
		final RadioButton rd2 = (RadioButton) rootView.findViewById(R.id.inc_accToUpdate);
		btnIncome = (Button) rootView.findViewById(R.id.btnUpdateIncomeToUpdate);
		btnCancel = (Button) rootView.findViewById(R.id.btnCanselToUpdate);
		rdStreet = (RadioButton) rootView.findViewById(R.id.prStreetToUpdate);
		pr2 = (RadioButton) rootView.findViewById(R.id.pr2ToUpdate);
		pr3 = (RadioButton) rootView.findViewById(R.id.pr3ToUpdate);
		incBase = (EditText) rootView.findViewById(R.id.incomeBaseToUpdate);

		txtNotes.setHintTextColor(getResources().getColor(R.color.login_label_color));
		incBase.setEnabled(false);
		incBase.setClickable(false);
		incBase.setFocusable(false);

		db = new DatabaseHandler(getActivity(), null);
		Income single_income_record = db.getSingleIncomeRecord(recordID);

		dateToDisplay = single_income_record.getDate();
		txtDate.setText(dateToDisplay);

		type = single_income_record.getIncType();
		radioSelectedText = type;
		// Setting radio
		if (type.equals("Cash"))
			rd1.setChecked(true);
		else
			rd2.setChecked(true);
		txtIncomeAmount.setText(single_income_record.getAmount());
		incomeAmountToBeSaved = single_income_record.getAmount();

		txtNotes.setText(single_income_record.getNote());
		rdProvider = single_income_record.getProvider();
		// set Radio Provider

		List<Provider> prov = db.getAllProviders();
		for (Provider cn : prov) {
			// String log = "Id: " + cn.getID() + ", provider:" + cn.getName() +
			// ", provider_active:" + cn.getActive();
			if (rdProvider.equals("Street"))
				radioIncomeGroup.setVisibility(View.GONE);
			if (cn.getID() == 2) {
				pr2.setVisibility(View.VISIBLE);

				pr2.setText(cn.getName());
				if (rdProvider.equals(cn.getName()))
					pr2.setChecked(true);
				// radioIncomeGroup.setVisibility(View.VISIBLE);
			}

			if (cn.getID() == 3) {
				pr3.setVisibility(View.VISIBLE);

				pr3.setText(cn.getName());
				if (rdProvider.equals(cn.getName()))
					pr3.setChecked(true);
				// radioIncomeGroup.setVisibility(View.VISIBLE);
			}

			db.close();
		}

		/*
		 * 
		 * 
		 * Getting Radio
		 */
		rd1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioSelectedText = (String) rd1.getText();
			}
		});
		rd2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioSelectedText = (String) rd2.getText();
			}
		});
		/*
		 * 
		 * Provider Radio
		 */
		/*
		 * Getting Radio Provider Type
		 */

		rdStreet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioIncomeGroup.setVisibility(View.GONE);
				rdProvider = (String) rdStreet.getText();
			}
		});

		pr2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioIncomeGroup.setVisibility(View.VISIBLE);
				rdProvider = (String) pr2.getText();
			}
		});

		pr3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioIncomeGroup.setVisibility(View.VISIBLE);
				rdProvider = (String) pr3.getText();
			}
		});

		/*
		 * 
		 * 
		 * Cancel btn
		 */
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
				IncSwitchFragment fragment = new IncSwitchFragment();
				ft.replace(R.id.frame_container, fragment).commit();
				setTitle(navMenuTitles[0]);
			}
		});
		/*
		 * 
		 * 
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
		 * 
		 * 
		 * Income amount
		 */
		txtIncomeAmount.setRawInputType(Configuration.KEYBOARD_12KEY);
		txtIncomeAmount.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				txtIncomeAmount.post(new Runnable() {

					@Override
					public void run() {
						txtIncomeAmount.setSelection(txtIncomeAmount.getText().length());

					}
				});

				return false;
			}
		});
		txtIncomeAmount.addTextChangedListener(new TextWatcher() {
			DecimalFormat dec = new DecimalFormat("0.00");

			@Override
			public void afterTextChanged(Editable s) {
                if (!s.toString().matches("^(\\d{1,3}(,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    if (userInput.length() > 0) {
                        Float in = Float.parseFloat(userInput);
                        float percent = in / 100;
                        txtIncomeAmount.setText(dec.format(percent));
                        incomeAmountToBeSaved = dec.format(percent);
                        txtIncomeAmount.setSelection(txtIncomeAmount.getText().length());
                    }
                }
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				txtIncomeAmount.setBackgroundResource(R.drawable.edittext_login_box_border);

			}
		});

		/*
		 * 
		 * 
		 * Notes
		 */
		InputFilter filter = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				for (int i = start; i < end; i++) {
					if (!Character.isLetterOrDigit(source.charAt(i))) {
						return " ";
					}
				}
				return null;
			}

		};

		txtNotes.setFilters(new InputFilter[] { filter });

		/*
		 * 
		 * 
		 * Validate and insert
		 */
		btnIncome.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				notesToBeSaved = txtNotes.getText().toString();

				dateToBeSaved = reverseDate(dateToDisplay);

				if (incomeAmountToBeSaved.equals("0.00")) {
					incBase.setBackgroundResource(R.drawable.error);
					Toast.makeText(getActivity(), "Incomplete details", Toast.LENGTH_LONG).show();
				} else if (radioSelectedText.equals("Account") && rdProvider.equals("Street")) {
					Toast.makeText(getActivity(), "You can't have Account from Street", Toast.LENGTH_LONG).show();
				} else {
					showAlertDialog();
				}
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
					setTitle(navMenuTitles[0]);
					return true;
				} else {
					return false;
				}
			}
		});
		return rootView;
	}// end oncreate

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

			customView = inflater.inflate(R.layout.dialog, null);

			customView.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
			// customView.setMinimumHeight((int) (displayRectangle.height() *
			// 0.9f));

			final TextView txtMsg = (TextView) customView.findViewById(R.id.txtDialogMsg1);
			final TextView txtMsg2 = (TextView) customView.findViewById(R.id.txtDialogMsg2);
			final TextView txtMsgEuro = (TextView) customView.findViewById(R.id.txtDialogEuro);
			TextView btnYes = (TextView) customView.findViewById(R.id.btnDialogYes);
			TextView btnNo = (TextView) customView.findViewById(R.id.btnDialogNo);
			txtMsg.setText(radioSelectedText);
			txtMsg2.setText(rdProvider);
			txtMsgEuro.setText(incomeAmountToBeSaved);
			alertDialogBuilder.setContentView(customView);
			btnYes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					db = new DatabaseHandler(getActivity(), null);

					// db.addIncome(new Income(dateToBeSaved, radioSelectedText,
					// incomeAmountToBeSaved, notesToBeSaved, active,
					// rdProvider));
					db.updateIncome(recordID, dateToBeSaved, radioSelectedText, incomeAmountToBeSaved, notesToBeSaved, active, rdProvider);

					db.close();

					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
					IncSwitchFragment fragment = new IncSwitchFragment();				
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

	/*
	 * date picker dialog
	 */
	/**
	 * The Class DatePickerFragment.
	 */
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

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

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		/* (non-Javadoc)
		 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
		 */
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			Calendar c = Calendar.getInstance();
			int y = c.get(Calendar.YEAR);// setting current year, preventing the
			// user to pickup different year
			c.set(y, month, day);

			sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
			sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

			dateToDisplay = sdf.format(c.getTime());
			dateToBeSaved = sdfDB.format(c.getTime());

			txtDate.setText(dateToDisplay);

		}

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
}// end fragment
