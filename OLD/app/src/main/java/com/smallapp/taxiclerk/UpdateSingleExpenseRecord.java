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
 * The Class UpdateSingleExpenseRecord.
 */
public class UpdateSingleExpenseRecord extends Fragment {

	/** The db. */
	private DatabaseHandler db;

	/** The btn date picker. */
	private View btnDatePicker;

	/** The active. */
	private String active = "yes";

	/** The record id. */
	private int recordID;

	/** The txt date to update. */
	private TextView txtDateToUpdate;

	/** The btn date picker to update. */
	private Button btnDatePickerToUpdate;

	/** The txt expense amount to update. */
	private EditText txtExpenseAmountToUpdate;

	/** The txt expense notes to update. */
	private EditText txtExpenseNotesToUpdate;

	/** The btn expense type to update. */
	private Button btnExpenseTypeToUpdate;

	/** The btn update expense save. */
	private Button btnUpdateExpenseSave;

	/** The btn update expense cancel. */
	private Button btnUpdateExpenseCancel;

	/** The m title. */
	private CharSequence mTitle;

	/** The expense type to be saved. */
	private String expenseTypeToBeSaved;

	/** The payment type to be saved. */
	private String paymentTypeToBeSaved;

	/** The expense amount to be saved. */
	protected String expenseAmountToBeSaved;

	/** The notes to be saved. */
	protected String notesToBeSaved;

	/** The date to be saved. */
	protected String dateToBeSaved;

	/** The milage to be saved. */
	private String milageToBeSaved;

	/** The payment type to update. */
	private String paymentTypeToUpdate;

	/** The txt milage to update. */
	private EditText txtMilageToUpdate;

	/** The nav menu titles. */
	private String[] navMenuTitles;

	/** The odo milage. */
	private LinearLayout odoMilage;

	/** The sdf. */
	private SimpleDateFormat sdf;

	/** The sdf db. */
	private SimpleDateFormat sdfDB;

	/** The date to display. */
	private String dateToDisplay;

	/** The base1. */
	private EditText base1;

	/** The base2. */
	private EditText base2;

	/** The base3. */
	private EditText base3;

	/** The custom view. */
	public View customView;

	/** The root view. */
	private View rootView;

	/**
	 * Instantiates a new update single expense record.
	 * 
	 * @param recordID
	 *            the record id
	 */
	public UpdateSingleExpenseRecord(int recordID) {
		this.recordID = recordID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

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
			rootView = inflater.inflate(
					R.layout.fragment_update_single_expense_record_small,
					container, false);
		} else {
			// support for 400x800
			rootView = inflater.inflate(
					R.layout.fragment_update_single_expense_record, container,
					false);
		}

		sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
		txtDateToUpdate = (TextView) rootView
				.findViewById(R.id.txtDateExpenseToUpdate);
		btnDatePickerToUpdate = (Button) rootView
				.findViewById(R.id.btnPickDateExpenseToUpdate);
		txtExpenseAmountToUpdate = (EditText) rootView
				.findViewById(R.id.expenseAmountToUpdate);
		txtExpenseNotesToUpdate = (EditText) rootView
				.findViewById(R.id.notesExpenseToUpdate);
		odoMilage = (LinearLayout) rootView.findViewById(R.id.llOdoToUpdate);
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		txtMilageToUpdate = (EditText) rootView.findViewById(R.id.odoToUpdate);
		btnExpenseTypeToUpdate = (Button) rootView
				.findViewById(R.id.btnExpenseNameToUpdate);
		btnUpdateExpenseSave = (Button) rootView
				.findViewById(R.id.btnSaveExpenseToUpdate);
		btnUpdateExpenseCancel = (Button) rootView
				.findViewById(R.id.btnCancelExpenseToUpdate);
		final RadioButton rd1 = (RadioButton) rootView
				.findViewById(R.id.cashExpenseToUpdate);
		final RadioButton rd2 = (RadioButton) rootView
				.findViewById(R.id.cardExpenseToUpdate);
		final RadioButton rd3 = (RadioButton) rootView
				.findViewById(R.id.accExpenseToUpdate);
		base1 = (EditText) rootView.findViewById(R.id.expExpenseToUpdateBase);
		base2 = (EditText) rootView.findViewById(R.id.odoExpenseToUpdateBase);
		base3 = (EditText) rootView.findViewById(R.id.notesExpenseToUpdateBase);
		txtExpenseNotesToUpdate.setHintTextColor(getResources().getColor(
				R.color.login_label_color));

		base1.setEnabled(false);
		base1.setClickable(false);
		base1.setFocusable(false);
		base2.setEnabled(false);
		base2.setClickable(false);
		base2.setFocusable(false);
		base3.setEnabled(false);
		base3.setClickable(false);
		base3.setFocusable(false);

		/*
		 * 
		 * 
		 * Get all records from DB
		 */
		db = new DatabaseHandler(getActivity(), null);
		Expense single_expense_record = db.getSingleExpenseRecord(recordID);

		dateToDisplay = single_expense_record.getDate();
		txtDateToUpdate.setText(dateToDisplay);

		txtExpenseNotesToUpdate.setText(single_expense_record.getNotes());

		expenseAmountToBeSaved = single_expense_record.getAmount();

		txtExpenseAmountToUpdate.setText(single_expense_record.getAmount());

		milageToBeSaved = single_expense_record.getMilage();
		txtMilageToUpdate.setText(milageToBeSaved);

		expenseTypeToBeSaved = single_expense_record.getExpenseName();// diesel,
																		// misc...
		btnExpenseTypeToUpdate.setText(expenseTypeToBeSaved);

		paymentTypeToBeSaved = single_expense_record.getPaymentType();// cash or
																		// card...

		/*
		 * 
		 * 
		 * Setting visible off,if odometar reading !=0
		 */
		if (!expenseTypeToBeSaved.equals("Diesel"))
			odoMilage.setVisibility(View.GONE);

		/*
		 * 
		 * 
		 * Setting Radio Button
		 */

		if (paymentTypeToBeSaved.equals("Cash"))
			rd1.setChecked(true);
		else if (paymentTypeToBeSaved.equals("Card"))
			rd2.setChecked(true);
		else
			rd3.setChecked(true);
		/*
		 * 
		 * 
		 * Getting Radio
		 */

		rd1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				paymentTypeToBeSaved = (String) rd1.getText();
			}
		});
		rd2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				paymentTypeToBeSaved = (String) rd2.getText();
			}
		});
		rd3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				paymentTypeToBeSaved = (String) rd3.getText();
			}
		});

		/*
		 * 
		 * 
		 * Getting the expense type
		 */
		btnExpenseTypeToUpdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String[] items = view.getResources().getStringArray(
						R.array.expense_type);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(),
						android.R.layout.simple_spinner_dropdown_item, items);
				new AlertDialog.Builder(getActivity())
						.setTitle(R.string.expense_prompt)
						.setAdapter(adapter,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										btnExpenseTypeToUpdate
												.setText(items[which]);
										expenseTypeToBeSaved = items[which];

										if (items[which].equals("Diesel")) {
											odoMilage
													.setVisibility(View.VISIBLE);
											txtMilageToUpdate.setText("");
										} else {
											odoMilage.setVisibility(View.GONE);
											txtMilageToUpdate.setText("");
										}
										dialog.dismiss();
									}
								}).create().show();
			}
		});
		/*
		 * 
		 * 
		 * Income date picker
		 */
		btnDatePickerToUpdate.setOnClickListener(new OnClickListener() {
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
		txtExpenseAmountToUpdate.setRawInputType(Configuration.KEYBOARD_12KEY);
		txtExpenseAmountToUpdate.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				txtExpenseAmountToUpdate.post(new Runnable() {

					@Override
					public void run() {
						txtExpenseAmountToUpdate
								.setSelection(txtExpenseAmountToUpdate
										.getText().length());

					}
				});

				return false;
			}
		});
		txtExpenseAmountToUpdate.addTextChangedListener(new TextWatcher() {
			DecimalFormat dec = new DecimalFormat("0.00");

			@Override
			public void afterTextChanged(Editable s) {
                if (!s.toString().matches(
                        "^(\\d{1,3}(,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {

                    String userInput = ""
                            + s.toString().replaceAll("[^\\d]", "");

                    if (userInput.length() > 0) {

                        Float in = Float.parseFloat(userInput);
                        float percent = in / 100;

                        txtExpenseAmountToUpdate.setText(dec.format(percent));
                        expenseAmountToBeSaved = dec.format(percent);
                        txtExpenseAmountToUpdate
                                .setSelection(txtExpenseAmountToUpdate
                                        .getText().length());
                    }
                }
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				txtExpenseAmountToUpdate
						.setBackgroundResource(R.drawable.edittext_login_box_border);

			}
		});

		/*
		 * 
		 * 
		 * Notes
		 */
		InputFilter filter = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				for (int i = start; i < end; i++) {
					if (!Character.isLetterOrDigit(source.charAt(i))) {
						return " ";
					}
				}
				return null;
			}

		};
		txtExpenseNotesToUpdate.setFilters(new InputFilter[] { filter });
		/*
		 * 
		 * 
		 * Check milage
		 */
		if (String.valueOf(expenseTypeToBeSaved).equals("Diesel")) {
			odoMilage.setVisibility(View.VISIBLE);
		}

		/*
		 * Validate and insert
		 */
		btnUpdateExpenseSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				notesToBeSaved = txtExpenseNotesToUpdate.getText().toString();
				dateToBeSaved = reverseDate(dateToDisplay);
				milageToBeSaved = txtMilageToUpdate.getText().toString();

				if (expenseAmountToBeSaved.equals("0.00")) {
					Toast.makeText(getActivity(), "Incomplete details",
							Toast.LENGTH_LONG).show();
					base1.setBackgroundResource(R.drawable.error);
				} else {

					showAlertDialog();
				}
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

			private void showAlertDialog() {
				FragmentManager fm = getFragmentManager();
				MyDialog alertDialog = new MyDialog();
				alertDialog.show(fm, "fragment_alert");
			}
		});
		btnUpdateExpenseCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction ft = getFragmentManager()
						.beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_bottom,
						R.anim.slide_out_top);
				ExpSwitchFragment fragment = new ExpSwitchFragment();
				ft.replace(R.id.frame_container, fragment).commit();

			}
		});
		rootView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK) {
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_bottom,
							R.anim.slide_out_top);
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
			window.getDecorView()
					.getWindowVisibleDisplayFrame(displayRectangle);

			LayoutInflater inflater = getActivity().getLayoutInflater();
			final Dialog alertDialogBuilder = new Dialog(getActivity(),
					R.style.DialogTheme);

			customView = inflater.inflate(R.layout.dialog, null);

			customView.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
			// customView.setMinimumHeight((int) (displayRectangle.height() *
			// 0.9f));

			final TextView txtMsg = (TextView) customView
					.findViewById(R.id.txtDialogMsg1);
			final TextView txtMsg2 = (TextView) customView
					.findViewById(R.id.txtDialogMsg2);
			final TextView txtMsgEuro = (TextView) customView
					.findViewById(R.id.txtDialogEuro);
			TextView btnYes = (TextView) customView
					.findViewById(R.id.btnDialogYes);
			TextView btnNo = (TextView) customView
					.findViewById(R.id.btnDialogNo);

			txtMsg.setText(paymentTypeToBeSaved);
			txtMsg2.setText(expenseTypeToBeSaved);
			txtMsgEuro.setText(expenseAmountToBeSaved);
			alertDialogBuilder.setContentView(customView);
			btnYes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					db = new DatabaseHandler(getActivity(), null);
					db.updateExpense(recordID, expenseTypeToBeSaved,
							expenseAmountToBeSaved, dateToBeSaved,
							milageToBeSaved, paymentTypeToBeSaved,
							notesToBeSaved, active);
					db.close();
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();
					ft.setCustomAnimations(R.anim.slide_in_bottom,
							R.anim.slide_out_top);
					ExpSwitchFragment fragment = new ExpSwitchFragment();
					ft.replace(R.id.frame_container, fragment).commit();

					txtExpenseAmountToUpdate
							.setBackgroundResource(R.drawable.edittext_login_box_border);

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
	 * 
	 * 
	 * date picker dialog
	 */
	/**
	 * The Class DatePickerFragment.
	 */
	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

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

			sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
			sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

			dateToDisplay = sdf.format(c.getTime());
			dateToBeSaved = sdfDB.format(c.getTime());
			txtDateToUpdate.setText(dateToDisplay);

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
		((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
				mTitle);
	}

}// end fragment
