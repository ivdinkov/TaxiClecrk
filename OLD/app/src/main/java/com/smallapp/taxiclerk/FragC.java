/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class FragC.
 */
public class FragC extends Fragment {
	// table names
	/** The Constant TABLE_EXPENSE. */
	private static final String TABLE_EXPENSE = "expense";

	/** The Constant TABLE_INCOME. */
	private static final String TABLE_INCOME = "income";

	/** The Constant TABLE_HOURS. */
	private static final String TABLE_HOURS = "hours";

	/** The Constant KEY_INC_TYPE. */
	private static final String KEY_INC_TYPE = "income_type";

	/** The Constant KEY_INC_DATE. */
	private static final String KEY_INC_DATE = "income_date";

	/** The Constant KEY_INC_AMOUNT. */
	private static final String KEY_INC_AMOUNT = "income_amount";

	/** The Constant KEY_INC_ACTIVE. */
	private static final String KEY_INC_ACTIVE = "income_active";

	/** The Constant KEY_AMOUNT. */
	private static final String KEY_AMOUNT = "amount";

	/** The Constant KEY_DATE. */
	private static final String KEY_DATE = "date";

	/** The Constant KEY_ACTIVE. */
	private static final String KEY_ACTIVE = "active";
	// Hours table
	/** The Constant KEY_HOURS. */
	private static final String KEY_HOURS = "hours";

	/** The Constant KEY_HOURS_DATE. */
	private static final String KEY_HOURS_DATE = "hours_date";

	/** The Constant KEY_HOURS_ACTIVE. */
	private static final String KEY_HOURS_ACTIVE = "hours_active";

	/** The db. */
	private DatabaseHandler db;

	/** The cash total year. */
	private String cashTotalYear;

	/** The acc total year. */
	private String accTotalYear;

	/** The exp total year. */
	private String expTotalYear;

	/** The to take home year. */
	private String toTakeHomeYear;

	/** The hr year. */
	private String hrYear;

	/** The current YEAR */
	private String currentYear;

	/** The in. */
	private Float in = null;

	/** The euro hr year. */
	private String euroHrYear;

	/** The sql. */
	private String sql;

	/** The txt cash total. */
	private TextView txtCashTotal;

	/** The txt acc total. */
	private TextView txtAccTotal;

	/** The txt exp total. */
	private TextView txtExpTotal;

	/** The txt to take home total. */
	private TextView txtToTakeHomeTotal;

	/** The txt hr year. */
	private TextView txtHrYear;

	/** The txt avg year. */
	private TextView txtAvgYear;

	/** The txt year total. */
	private TextView txtYearTotal;

	/** The year total. */
	private String yearTotal;

	/** The year jobs. */
	private int yearJobs;

	/** The txt year jobs. */
	private TextView txtYearJobs;

	private LinearLayout chart;
    
    private String euro_sign;
    private View rootView;

    /**
	 * Instantiates a new frag c.
	 */
	public FragC() {

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
            rootView = inflater.inflate(R.layout.frag_by_year_small, container, false);
        }else{
            //support for 400x800
            rootView = inflater.inflate(R.layout.frag_by_year, container, false);
        }

		

		txtCashTotal = (TextView) rootView.findViewById(R.id.yearCash);
		txtAccTotal = (TextView) rootView.findViewById(R.id.yearAcc);
		txtExpTotal = (TextView) rootView.findViewById(R.id.yearExp);
		txtYearTotal = (TextView) rootView.findViewById(R.id.yearTotal);
		txtHrYear = (TextView) rootView.findViewById(R.id.yearHr);
		txtAvgYear = (TextView) rootView.findViewById(R.id.yearAvg);
		txtToTakeHomeTotal = (TextView) rootView.findViewById(R.id.yearPocket);
		txtYearJobs = (TextView) rootView.findViewById(R.id.yearJobs);
		chart = (LinearLayout) rootView.findViewById(R.id.drawing_year);
        euro_sign = getResources().getString(R.string.euro_sign);
		DecimalFormat dec = new DecimalFormat("0.00");
		db = new DatabaseHandler(getActivity(), null);

		currentYear = getCurrentYear();

		/*
		 * 
		 * 
		 * Total Income Outcome
		 * 
		 * 
		 * All cash
		 */
		sql = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_TYPE + " LIKE 'Cash' AND " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE
				+ " LIKE '" + currentYear + "%'";

		cashTotalYear = db.getAllIncomeTotal(sql);
		txtCashTotal.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(cashTotalYear))));

		/*
		 * 
		 * All Account
		 */
		sql = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_TYPE + " LIKE 'Account' AND " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE
				+ " LIKE '" + currentYear + "%'";
		accTotalYear = db.getAllIncomeTotal(sql);
		txtAccTotal.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(accTotalYear))));

		/*
		 * 
		 * All Expenses
		 */
		sql = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " LIKE '" + currentYear + "%'";
		expTotalYear = db.getAllIncomeTotal(sql);
		txtExpTotal.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(expTotalYear))));

		/*
		 * 
		 * All Hr
		 */
		sql = "SELECT SUM(CAST(" + KEY_HOURS + " as DECIMAL(9,2))) FROM " + TABLE_HOURS + " WHERE " + KEY_HOURS_ACTIVE + " LIKE 'yes' AND " + KEY_HOURS_DATE + " LIKE '" + currentYear + "%'";
		hrYear = db.getAllIncomeTotal(sql);
		in = Float.parseFloat(hrYear);
		hrYear = dec.format(in);
		hrYear = correctTheTime(String.valueOf(hrYear));
		if (hrYear != null)
			txtHrYear.setText(hrYear);
		else {
			txtHrYear.setText("0");
			hrYear = "0";
		}

		/*
		 * 
		 * All jobs for week
		 */
		yearJobs = db.allJobsForYear(getCurrentYear());
		txtYearJobs.setText(String.valueOf(yearJobs));
		/*
		 * 
		 * Total Year
		 */
		yearTotal = String.valueOf(Double.valueOf(cashTotalYear) + Double.valueOf(accTotalYear));
		in = (float) 0;
		in = Float.parseFloat(yearTotal);
		txtYearTotal.setText(euro_sign + dec.format(in));
		/*
		 * 
		 * Take home after expenses
		 */

		toTakeHomeYear = String.valueOf((Double.valueOf(cashTotalYear) + Double.valueOf(accTotalYear)) - Double.valueOf(expTotalYear));
		in = (float) 0;
		in = Float.parseFloat(toTakeHomeYear);
		txtToTakeHomeTotal.setText(euro_sign + dec.format(in));

		/*
		 * 
		 * Euro per Hour
		 */

		if (Double.valueOf(hrYear) != 0) {
			euroHrYear = String.valueOf(Double.valueOf(toTakeHomeYear) / Double.valueOf(hrYear));
			in = (float) 0;
			in = Float.parseFloat(euroHrYear);
			if (Double.valueOf(toTakeHomeYear) != 0)
				txtAvgYear.setText(euro_sign + dec.format(in));
			else
				txtAvgYear.setText(euro_sign+" 0.00");
		} else
			txtAvgYear.setText(euro_sign+" 0.00");

		sql = "";
		db.close();

		/************************** Year Chart ******************************/
		String[] months = getMonths();
		String[][] incomeResult = new String[12][2];
		String[] expenseResult = new String[12];
		// Retrieving income(cash/acc)
		for (int i = 0; i < 12; i++) {
			String res_cash = db.getYearlyIncome(months[i], "Cash");
			String res_acc = db.getYearlyIncome(months[i], "Account");

			if (res_cash != null)
				incomeResult[i][0] = res_cash;
			else
				incomeResult[i][0] = "0.00000001";

			if (res_acc != null)
				incomeResult[i][1] = res_acc;
			else
				incomeResult[i][1] = "0.00000001";

		}
		// Retrieve expenses
		for (int m = 0; m < months.length; m++) {
			String res_exp = db.getEarlyExpense(months[m]);

			if (res_exp != null)
				expenseResult[m] = res_exp;
			else
				expenseResult[m] = "0.00000001";
			//Log.d("I exp", String.valueOf(expenseResult[m]));
		}

		DrawChartYear cw = new DrawChartYear(getActivity(), incomeResult, expenseResult);
		chart.addView(cw);
		/********************************************************************/

		return rootView;
	}

	private String[] getMonths() {
		String[] months = new String[12];

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.UK);
		Calendar c = Calendar.getInstance(Locale.UK);
		c.get(Calendar.YEAR);
		String y = sdf.format(c.getTime());

		for (int i = 1; i < 13; i++) {
			StringBuilder sb = new StringBuilder();
			sb.append(y);
			sb.append("-");
			if (i < 10) {
				sb.append("0");
			}
			sb.append(i);
			months[i - 1] = sb.toString();
			sb.setLength(0);
		}
		return months;
	}

	/**
	 * Correct the time.
	 *
	 * @param valueOf
	 *            the value of
	 * @return the string
	 */
	private String correctTheTime(String valueOf) {
		String res = null;
		String hr;
		String min;
		String str = new String(valueOf);
		String x = str.replaceAll("[^0-9]", "");

		if (x.length() > 3) {
			hr = x.substring(0, 2);
			min = x.substring(2, x.length());
		} else {
			hr = x.substring(0, 1);
			min = x.substring(1, x.length());
		}

		/*
		 * 
		 * Modify minutes to match 60min format
		 */
		int a = Integer.valueOf(min) - 60;
		int b = Integer.valueOf(hr);

		if (a > 0) {
			if (a < 10) {
				min = "0" + String.valueOf(a);
			} else {
				min = String.valueOf(a);
			}
			b = b + 1;
		}
		res = String.valueOf(b) + "." + min;

		return res;
	}

	/**
	 * Get current date.
	 *
	 * @return the string
	 */
	private String getCurrentYear() {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);

		return String.valueOf(year);
	}
}
