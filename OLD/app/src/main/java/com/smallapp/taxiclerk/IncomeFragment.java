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
 * The Class IncomeFragment.
 */
public class IncomeFragment extends Fragment {

	
	
	/** The db. */
	private DatabaseHandler db;
	
	/** The btn date picker. */
	private View btnDatePicker;
	
	/** The date text. */
	private TextView dateText;
	
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
	
	/** The txt hours. */
	private EditText txtHours;
	
	/** The txt notes. */
	private EditText txtNotes;
	
	/** The btn income. */
	private Button btnIncome;
	
	/** The btn cancel. */
	private Button btnCancel;
	
	/** The radio selected text. */
	private String radioSelectedText = "Cash";
	
	/** The date to display. */
	private String dateToDisplay;
	
	/** The sdf. */
	private SimpleDateFormat sdf;
	
	/** The sdf db. */
	private SimpleDateFormat sdfDB;
	
	/** The m title. */
	private CharSequence mTitle;
	
	/** The nav menu titles. */
	private String[] navMenuTitles;
	
	/** The rd provider. */
	private String rdProvider;
	
	/** The rd street. */
	private RadioButton rdStreet;
	
	/** The rd p r2. */
	private RadioButton rdPR2;
	
	/** The rd p r3. */
	private RadioButton rdPR3;
	
	/** The income base. */
	private TextView incomeBase;
	
	/** The income note base. */
	private TextView incomeNoteBase;
	
	/** The radio income group. */
	private RadioGroup radioIncomeGroup;
	
	/** The custom view. */
	public View customView;
	
	/** The root view. */
	private View rootView;

	/**
	 * Instantiates a new income fragment.
	 */
	public IncomeFragment() {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		
		int measuredWidth = 0;
		int measuredHeight = 0;
		Point size = new Point();
		WindowManager w = getActivity().getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
		    w.getDefaultDisplay().getSize(size);

		    measuredWidth = size.x;
		    measuredHeight = size.y;
		}
		else
		{
		    Display d = w.getDefaultDisplay();
		    measuredWidth = d.getWidth();
		    measuredHeight = d.getHeight();
		}
		if(Integer.valueOf(measuredHeight)<800){
			//support for 320x480
			rootView = inflater.inflate(R.layout.fragment_income_small, container, false);
		}else{
			//support for 400x800
			rootView = inflater.inflate(R.layout.fragment_income, container, false);
		}
		
		
		// Preventing softkey pad overlap
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		dateText = (TextView) rootView.findViewById(R.id.txtDate);
		setCurrentDate();
		btnDatePicker = rootView.findViewById(R.id.btnPickDate);
		txtIncomeAmount = (EditText) rootView.findViewById(R.id.incomeAmount);
		txtHours = (EditText) rootView.findViewById(R.id.hours);
		txtNotes = (EditText) rootView.findViewById(R.id.notes);
		btnIncome = (Button) rootView.findViewById(R.id.btnUpdateIncome);
		btnCancel = (Button) rootView.findViewById(R.id.btnCansel);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		radioIncomeGroup = (RadioGroup) rootView.findViewById(R.id.radioIncomeType);
		rdStreet = (RadioButton) rootView.findViewById(R.id.prStreet);
		rdPR2 = (RadioButton) rootView.findViewById(R.id.pr2);
		rdPR3 = (RadioButton) rootView.findViewById(R.id.pr3);
		incomeBase = (TextView) rootView.findViewById(R.id.incomeBase);
		incomeNoteBase = (TextView) rootView.findViewById(R.id.inc_note_base);

		txtNotes.setHintTextColor(getResources().getColor(R.color.login_label_color));
		incomeBase.setEnabled(false);
		incomeBase.setClickable(false);
		incomeBase.setFocusable(false);
		incomeNoteBase.setEnabled(false);
		incomeNoteBase.setClickable(false);
		incomeNoteBase.setFocusable(false);
		rdProvider = "Street";


		radioIncomeGroup.setVisibility(View.GONE);

		/*
		 * 
		 * 
		 * Displaying Providers if any
		 */
		db = new DatabaseHandler(getActivity(), null);
        List<Provider> prov = db.getAllProviders();
		for (Provider cn : prov) {
			if (cn.getID() == 2) {
				//if (cn.getActive().equals("yes")) {
					rdPR2.setVisibility(View.VISIBLE);
					rdPR2.setText(cn.getName());

				//} else {
				//	rdPR2.setVisibility(View.GONE);
				//}
			}
			if (cn.getID() == 3) {
				//if (cn.getActive().equals("yes")) {
					rdPR3.setVisibility(View.VISIBLE);
					rdPR3.setText(cn.getName());

				//} else {
				//	rdPR3.setVisibility(View.GONE);
				//}
			}
			// Writing Contacts to log
			// Log.d("T", log);
			db.close();
		}

		/*
		 * Getting Radio Provider Type
		 */

		rdStreet.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				rdProvider = (String) rdStreet.getText();
				radioIncomeGroup.setVisibility(View.GONE);
			}
		});

		rdPR2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				rdProvider = (String) rdPR2.getText();
				radioIncomeGroup.setVisibility(View.VISIBLE);
			}
		});

		rdPR3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				rdProvider = (String) rdPR3.getText();
				radioIncomeGroup.setVisibility(View.VISIBLE);
			}
		});
		/*
		 * Getting Radio Payment Type
		 */
		final RadioButton rdcash = (RadioButton) rootView.findViewById(R.id.inc_cash);
		rdcash.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioSelectedText = (String) rdcash.getText();
			}
		});
		final RadioButton rdacc = (RadioButton) rootView.findViewById(R.id.inc_acc);
		rdacc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioSelectedText = (String) rdacc.getText();
			}
		});

		/*
		 * Cancel btn
		 */
		btnCancel.setOnClickListener(new View.OnClickListener() {
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
		 * Income amount
		 */
		txtIncomeAmount.setRawInputType(Configuration.KEYBOARD_12KEY);
		txtIncomeAmount.setText("0.00");
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
		 * Hours
		 */
		txtHours.setRawInputType(Configuration.KEYBOARD_12KEY);

		/*
		 * 
		 * 
		 * Notes
		 */
		InputFilter filter = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				for (int i = start; i < end; i++) {
					if (!Character.isLetterOrDigit(source.charAt(i)) ) {
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

				if (incomeAmountToBeSaved == null) {
					if (incomeAmountToBeSaved == null) {
						incomeBase.setBackgroundResource(R.drawable.error);
						Toast.makeText(getActivity(), "Incomplete details", Toast.LENGTH_LONG).show();
					}
				} else if (radioSelectedText.equals("Account") && rdProvider.equals("Street")) {
					Toast.makeText(getActivity(), "You can't have Account from Street", Toast.LENGTH_LONG).show();
				} else {

					showAlertDialog();
				}
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

	/**
	 * Show alert dialog.
	 */
	private void showAlertDialog() {
		FragmentManager fm = getFragmentManager();
		MyDialog alertDialog = new MyDialog();
		alertDialog.show(fm, "fragment_alert");
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

		sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

		dateToDisplay = sdf.format(c.getTime());
		dateToBeSaved = sdfDB.format(c.getTime());

		dateText.setText(dateToDisplay);
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
			int y = c.get(Calendar.YEAR);
			// setting current year, preventing the
			// user to pickup different year
			c.set(y, month, day);

			sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

			dateToDisplay = sdf.format(c.getTime());
			dateToBeSaved = sdfDB.format(c.getTime());

			dateText.setText(dateToDisplay);

		}

	}

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
			//customView.setMinimumHeight((int) (displayRectangle.height() * 0.9f));

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

					db.addIncome(new Income(dateToBeSaved, radioSelectedText, incomeAmountToBeSaved, notesToBeSaved, active, rdProvider));
					db.close();
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
					HomeFragment fragment = new HomeFragment();				
					ft.replace(R.id.frame_container, fragment).commit();
					setTitle(navMenuTitles[0]);
					txtHours.setBackgroundResource(R.drawable.edittext_login_box_border);					
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
}// end fragment
