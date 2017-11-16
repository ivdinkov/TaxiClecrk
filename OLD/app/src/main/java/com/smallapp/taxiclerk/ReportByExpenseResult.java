/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.DecimalFormat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportByExpenseResult.
 */
public class ReportByExpenseResult extends Fragment {
	// DB
	// Table name
	/** The Constant TABLE_EXPENSE. */
	private static final String TABLE_EXPENSE = "expense";
	// Expense Table Column names
	/** The Constant KEY_ID_EXPENSE. */
	private static final String KEY_ID_EXPENSE = "expense_id";
	
	/** The Constant KEY_EXPENSE_NAME. */
	private static final String KEY_EXPENSE_NAME = "expense_name";
	
	/** The Constant KEY_AMOUNT. */
	private static final String KEY_AMOUNT = "amount";
	
	/** The Constant KEY_DATE. */
	private static final String KEY_DATE = "date";
	
	/** The Constant KEY_MILAGE. */
	private static final String KEY_MILAGE = "milage";
	
	/** The Constant KEY_PAYMENT_TYPE. */
	private static final String KEY_PAYMENT_TYPE = "payment_type";
	
	/** The Constant KEY_NOTES. */
	private static final String KEY_NOTES = "notes";
	
	/** The Constant KEY_ACTIVE. */
	private static final String KEY_ACTIVE = "active";

	/** The btn back. */
	private Button btnBack;
	
	/** The _expense type. */
	private String _expenseType;
	
	/** The _end date d. */
	private String _startDateD, _endDateD;
	
	/** The _end date s. */
	private String _startDateS, _endDateS;
	
	/** The txt exp name. */
	private TextView txtExpName;
	
	/** The txt start date. */
	private TextView txtStartDate;
	
	/** The txt end date. */
	private TextView txtEndDate;
	
	/** The txt exp cash. */
	private TextView txtExpCash;
	
	/** The txt exp card. */
	private TextView txtExpCard;
	
	/** The txt exp bank acc. */
	private TextView txtExpBankAcc;
	
	/** The txt exp total. */
	private TextView txtExpTotal;
	
	/** The db. */
	private DatabaseHandler db;
	
	/** The cash exp. */
	private String cashExp;
	
	/** The in. */
	private float in;
	
	/** The dec. */
	private DecimalFormat dec;
	
	/** The card exp. */
	private String cardExp;
	
	/** The bank exp. */
	private String bankExp;
	
	/** The total exp. */
	private String totalExp;
	

	/**
	 * Instantiates a new report by expense result.
	 *
	 * @param expenseTypeToBeSearched the expense type to be searched
	 * @param startDateToDisplay the start date to display
	 * @param endDateToDisplay the end date to display
	 * @param startDateToSearch the start date to search
	 * @param endDateToSearch the end date to search
	 */
	public ReportByExpenseResult(String expenseTypeToBeSearched, String startDateToDisplay, String endDateToDisplay, String startDateToSearch, String endDateToSearch) {
		this._expenseType = expenseTypeToBeSearched;
		this._endDateD = endDateToDisplay;
		this._startDateD = startDateToDisplay;
		this._endDateS = endDateToSearch;
		this._startDateS = startDateToSearch;
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.by_expense_result, container, false);

		// Dates
		txtStartDate = (TextView) rootView.findViewById(R.id.startDateRepExp);
		txtEndDate = (TextView) rootView.findViewById(R.id.endDateRepExp);
		txtExpName = (TextView) rootView.findViewById(R.id.name_expense);
		// Expense Result Fields
		txtExpCash = (TextView) rootView.findViewById(R.id.reportByExpenseCash);
		txtExpCard = (TextView) rootView.findViewById(R.id.reportByExpenseAcc);
		txtExpBankAcc = (TextView) rootView.findViewById(R.id.reportByExpenseBankAcc);
		txtExpTotal = (TextView) rootView.findViewById(R.id.reportByExpTotal);
		btnBack = (Button) rootView.findViewById(R.id.btnBack);

		dec = new DecimalFormat("0.00");
		/*
		 * 
		 * Show dates
		 */
		txtStartDate.setText(_startDateD);
		txtEndDate.setText(_endDateD);
		/*
		 * 
		 * Set Expense Name
		 */
		txtExpName.setText(_expenseType);
		/*
		 * 
		 * Go Back
		 */
		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ReportsFragment fragment = new ReportsFragment();
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);						
				ft.replace(R.id.frame_container, fragment).commit();
			}
		});
		/*
		 * *
		 * Search for Expenses
		 */
		// new db Object
		db = new DatabaseHandler(getActivity(), null);

		if (_expenseType.equals("All")) {
			/*
			 * 
			 * search for cash expense
			 */
			String sqlCashExp = null;
			sqlCashExp = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '" + _startDateS + "' AND "
					+ KEY_DATE + " <= '" + _endDateS + "' AND " + KEY_PAYMENT_TYPE + " LIKE 'Cash'";
			cashExp = db.doSql(sqlCashExp);
			in = 0;
			in = Float.parseFloat(cashExp);
			txtExpCash.setText(dec.format(in));

			/*
			 * 
			 * search for card expense
			 */
			String sqlCardExp = null;
			sqlCardExp = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '" + _startDateS + "' AND "
					+ KEY_DATE + " <= '" + _endDateS + "' AND " + KEY_PAYMENT_TYPE + " LIKE 'Card'";
			cardExp = db.doSql(sqlCardExp);
			in = 0;
			in = Float.parseFloat(cardExp);
			txtExpCard.setText(dec.format(in));

			/*
			 * 
			 * search for bank expense
			 */
			String sqlBankExp = null;
			sqlBankExp = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '" + _startDateS + "' AND "
					+ KEY_DATE + " <= '" + _endDateS + "' AND " + KEY_PAYMENT_TYPE + " LIKE 'Bank Acc'";
			bankExp = db.doSql(sqlBankExp);
			in = 0;
			in = Float.parseFloat(bankExp);
			txtExpBankAcc.setText(dec.format(in));
			/*
			 * 
			 * Set total
			 */
			totalExp = String.valueOf(Double.valueOf(cashExp) + Double.valueOf(cardExp) + Double.valueOf(bankExp));
			in = 0;
			in = Float.parseFloat(totalExp);
			txtExpTotal.setText(dec.format(in));
			
			

		} else {

			/*
			 * 
			 * search for cash expense
			 */
			String sqlCashExp = null;
			sqlCashExp = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '" + _startDateS + "' AND "
					+ KEY_DATE + " <= '" + _endDateS + "' AND " + KEY_PAYMENT_TYPE + " LIKE 'Cash' AND " + KEY_EXPENSE_NAME + " LIKE '" + _expenseType + "'";
			cashExp = db.doSql(sqlCashExp);
			in = 0;
			in = Float.parseFloat(cashExp);
			txtExpCash.setText(dec.format(in));

			/*
			 * 
			 * search for card expense
			 */
			String sqlCardExp = null;
			sqlCardExp = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '" + _startDateS + "' AND "
					+ KEY_DATE + " <= '" + _endDateS + "' AND " + KEY_PAYMENT_TYPE + " LIKE 'Card' AND " + KEY_EXPENSE_NAME + " LIKE '" + _expenseType + "'";
			cardExp = db.doSql(sqlCardExp);
			in = 0;
			in = Float.parseFloat(cardExp);
			txtExpCard.setText(dec.format(in));

			/*
			 * 
			 * search for bank expense
			 */
			String sqlBankExp = null;
			sqlBankExp = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '" + _startDateS + "' AND "
					+ KEY_DATE + " <= '" + _endDateS + "' AND " + KEY_PAYMENT_TYPE + " LIKE 'Bank Acc' AND " + KEY_EXPENSE_NAME + " LIKE '" + _expenseType + "'";
			bankExp = db.doSql(sqlBankExp);
			in = 0;
			in = Float.parseFloat(bankExp);
			txtExpBankAcc.setText(dec.format(in));

			String f = String.valueOf(Double.valueOf(cashExp) + Double.valueOf(cardExp) + Double.valueOf(bankExp));
			in = 0;
			in = Float.parseFloat(f);
			txtExpTotal.setText(dec.format(in));
		}

		db.close();
		return rootView;
	}
}
