/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Expense;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpensesFragment.
 */
public class ExpensesFragment extends Fragment {

	/** The btn date picker. */
	private Button btnDatePicker;
	
	/** The btn spinner. */
	private Button btnSpinner;
	
	/** The btn new expense. */
	private Button btnNewExpense;
	
	/** The btn cancel new expence. */
	private Button btnCancelNewExpence;
	
	/** The date text. */
	private TextView dateText;
	
	/** The txt expense amount. */
	private EditText txtExpenseAmount;
	
	/** The txt milage. */
	private EditText txtMilage;
	
	/** The expense type to be saved. */
	private String expenseTypeToBeSaved = null;
	
	/** The date to be saved. */
	private String dateToBeSaved = null;
	
	/** The notes to be saved. */
	private String notesToBeSaved = null;
	
	/** The active. */
	private String active = "yes";
	
	/** The expense amount. */
	private String expenseAmount = null;
	
	/** The ml. */
	private String ml = null;
	
	/** The db. */
	private DatabaseHandler db;
	
	/** The date to display. */
	private String dateToDisplay;
	
	/** The sdf db. */
	private SimpleDateFormat sdfDB;
	
	/** The sdf. */
	private SimpleDateFormat sdf;

	/** The radio selected text. */
	private String radioSelectedText = "Cash";
	

	
	/** The ll odo. */
	private LinearLayout llOdo;
	
	
	
	/** The notes. */
	private EditText notes;
	
	/** The nav menu titles. */
	private String[] navMenuTitles;
	
	/** The m title. */
	private CharSequence mTitle;
	
	/** The exp base. */
	private EditText expBase;
	
	/** The note base. */
	private EditText noteBase;
	
	/** The odo base. */
	private EditText odoBase;
	
	/** The custom view. */
	public View customView;
	
	/** The root view. */
	private View rootView;

	/**
	 * Instantiates a new expenses fragment.
	 */
	public ExpensesFragment() {
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
			rootView = inflater.inflate(R.layout.fragment_expenses_small, container, false);
		}else{
			//support for 400x800
			rootView = inflater.inflate(R.layout.fragment_expenses, container, false);
		}
				
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

		dateText = (TextView) rootView.findViewById(R.id.txtDateExp);
		setCurrentDate();
		dateText.setText(dateToDisplay);
		btnSpinner = (Button) rootView.findViewById(R.id.btnExpenseName);
		btnDatePicker = (Button) rootView.findViewById(R.id.btnPickDate);
		txtExpenseAmount = (EditText) rootView.findViewById(R.id.expenseAmount);
		btnNewExpense = (Button) rootView.findViewById(R.id.btnNewExpense);
		btnCancelNewExpence = (Button) rootView.findViewById(R.id.btnCancelNewExpense);
		notes = (EditText) rootView.findViewById(R.id.notesExpense);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		llOdo = (LinearLayout) rootView.findViewById(R.id.llOdo);
		txtMilage = (EditText) rootView.findViewById(R.id.odoExp);
		expBase = (EditText) rootView.findViewById(R.id.expenseBase);
		noteBase = (EditText) rootView.findViewById(R.id.notesExpenseBaseToUpdate);
		odoBase = (EditText) rootView.findViewById(R.id.odoExpenseBase);
		llOdo.setVisibility(View.GONE);

		notes.setHintTextColor(getResources().getColor(R.color.login_label_color));
		noteBase.setEnabled(false);
		noteBase.setClickable(false);
		noteBase.setFocusable(false);
		expBase.setEnabled(false);
		expBase.setClickable(false);
		expBase.setFocusable(false);
		odoBase.setEnabled(false);
		odoBase.setClickable(false);
		odoBase.setFocusable(false);

		/*
		 * Getting Radio
		 */

		final RadioButton rd1 = (RadioButton) rootView.findViewById(R.id.cashExpense);
		rd1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioSelectedText = (String) rd1.getText();
			}
		});
		final RadioButton rd2 = (RadioButton) rootView.findViewById(R.id.cardExpense);
		rd2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioSelectedText = (String) rd2.getText();
			}
		});
		final RadioButton rd3 = (RadioButton) rootView.findViewById(R.id.accExpense);
		rd3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				radioSelectedText = (String) rd3.getText();
			}
		});

		/*
		 * Getting the expense type
		 */
		btnSpinner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String[] items = view.getResources().getStringArray(R.array.expense_type);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
				new AlertDialog.Builder(getActivity()).setTitle(R.string.expense_prompt).setAdapter(adapter, new DialogInterface.OnClickListener() {

					private TextView lblOdometar;

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// btnSpinner.setBackgroundResource(R.drawable.edittext_login_box_border);
						btnSpinner.setText(items[which]);
						expenseTypeToBeSaved = items[which];

						txtMilage = (EditText) rootView.findViewById(R.id.odoExp);
						lblOdometar = (TextView) rootView.findViewById(R.id.lblOdometar);

						if (items[which].equals("Diesel")) {
							llOdo.setVisibility(View.VISIBLE);
							// txtMilage.setVisibility(View.VISIBLE);
							// lblOdometar.setVisibility(View.VISIBLE);
							txtMilage.setText("");
						} else {
							// txtMilage.setVisibility(View.GONE);
							// lblOdometar.setVisibility(View.GONE);
							llOdo.setVisibility(View.GONE);
						}
						dialog.dismiss();
					}
				}).create().show();
			}
		});

		/*
		 * Date expense was made picker
		 */
		btnDatePicker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				DialogFragment picker = new DatePickerFragment();

				picker.show(getFragmentManager(), "datePicker");

			}
		});

		/*
		 * Expense amount
		 */
		txtExpenseAmount.setRawInputType(Configuration.KEYBOARD_12KEY);
		txtExpenseAmount.setText("0.00");
		txtExpenseAmount.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				txtExpenseAmount.post(new Runnable() {

					@Override
					public void run() {
						txtExpenseAmount.setSelection(txtExpenseAmount.getText().length());

					}
				});

				return false;
			}
		});
		txtExpenseAmount.addTextChangedListener(new TextWatcher() {
			DecimalFormat dec = new DecimalFormat("0.00");

			@Override
			public void afterTextChanged(Editable s) {
                if (!s.toString().matches("^(\\d{1,3}(,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    if (userInput.length() > 0) {
                        Float in = Float.parseFloat(userInput);
                        float percen = in / 100;
                        // txtExpenseAmount.removeTextChangedListener(this);
                        txtExpenseAmount.setText(dec.format(percen));
                        expenseAmount = dec.format(percen);
                        txtExpenseAmount.setSelection(txtExpenseAmount.getText().length());
                    }
                }
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				txtExpenseAmount.setBackgroundResource(R.drawable.edittext_login_box_border);

			}
		});
		/*
		 * Notes
		 */
		notes = (EditText) rootView.findViewById(R.id.notesExpense);
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

		notes.setFilters(new InputFilter[] { filter });

		/*
		 * Enter expense in database
		 */
		btnNewExpense.setOnClickListener(new OnClickListener() {

			private String mlDialogDisplay;

			@Override
			public void onClick(View v) {

				if (expenseAmount == null || expenseTypeToBeSaved == null) {

					if (expenseTypeToBeSaved == null)
						btnSpinner.setBackgroundResource(R.drawable.error_spiner);
					if (expenseAmount == null)
						expBase.setBackgroundResource(R.drawable.error);

					Toast.makeText(getActivity(), "Incomplete details", Toast.LENGTH_LONG).show();
				} else {
					// get notes
					notesToBeSaved = notes.getText().toString();
					// getting milage
					txtMilage = (EditText) rootView.findViewById(R.id.odoExp);
					if (txtMilage.getVisibility() == View.VISIBLE) {
						ml = txtMilage.getText().toString();
					} else
						ml = "";					
					
					if (!ml.isEmpty())
						mlDialogDisplay = ml + " ml/km";
					else
						mlDialogDisplay = "";
					
					showAlertDialog();
				}

			}

			private void showAlertDialog() {
				FragmentManager fm = getFragmentManager();
				MyDialog alertDialog = new MyDialog();
				alertDialog.show(fm, "fragment_alert");
			}

		});
		btnCancelNewExpence.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
				HomeFragment fragment = new HomeFragment();				
				ft.replace(R.id.frame_container, fragment).commit();
				setTitle(navMenuTitles[0]);
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

			customView = inflater.inflate(R.layout.dialog, null);

			customView.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
			//customView.setMinimumHeight((int) (displayRectangle.height() * 0.9f));
			
			final TextView txtMsg = (TextView) customView.findViewById(R.id.txtDialogMsg1);
			final TextView txtMsg2 = (TextView) customView.findViewById(R.id.txtDialogMsg2);
			final TextView txtMsgEuro = (TextView) customView.findViewById(R.id.txtDialogEuro);
			TextView btnYes = (TextView) customView.findViewById(R.id.btnDialogYes);
			TextView btnNo = (TextView) customView.findViewById(R.id.btnDialogNo);

			txtMsg.setText(radioSelectedText);
			txtMsg2.setText(expenseTypeToBeSaved);
			txtMsgEuro.setText(expenseAmount);
			alertDialogBuilder.setContentView(customView);
			btnYes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					db = new DatabaseHandler(getActivity(), null);

					db.addExpense(new Expense(expenseTypeToBeSaved, expenseAmount, dateToBeSaved, ml, radioSelectedText, notesToBeSaved, active));
					db.close();
					FragmentTransaction ft = getFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
					HomeFragment fragment = new HomeFragment();				
					ft.replace(R.id.frame_container, fragment).commit();
					setTitle(navMenuTitles[0]);
					txtExpenseAmount.setBackgroundResource(R.drawable.edittext_login_box_border);

					
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
	 * @param title the new title
	 */
	public void setTitle(CharSequence title) {
		mTitle = title;
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(mTitle);
	}

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

			sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

			dateToDisplay = sdf.format(c.getTime());
			dateToBeSaved = sdfDB.format(c.getTime());

			dateText.setText(dateToDisplay);
		}

	}

}// end class 