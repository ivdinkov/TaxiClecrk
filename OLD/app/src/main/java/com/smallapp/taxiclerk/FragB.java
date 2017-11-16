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
 * The Class FragB.
 */
public class FragB extends Fragment {
	// table names
	/** The Constant TABLE_EXPENSE. */
	private static final String TABLE_EXPENSE = "expense";

	/** The Constant TABLE_INCOME. */
	private static final String TABLE_INCOME = "income";

	/** The Constant TABLE_HOURS. */
	private static final String TABLE_HOURS = "hours";

	/** The Constant KEY_INC_DATE. */
	private static final String KEY_INC_DATE = "income_date";

	/** The Constant KEY_INC_TYPE. */
	private static final String KEY_INC_TYPE = "income_type";

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

	/** The cash total week. */
	private String cashTotalWeek;

	/** The acc total week. */
	private String accTotalWeek;

	/** The exp total week. */
	private String expTotalWeek;

	/** The to take home week. */
	private String toTakeHomeWeek;

	/** The total week. */
	private String totalWeek;

	/** The start week day. */
	private String startWeekDay;

	/** The end week day. */
	private String endWeekDay;

	/** The hr week. */
	private String hrWeek;

	/** The week jobs. */
	private int weekJobs;

	/** The in. */
	private Float in = null;

	/** The euro hr week. */
	private String euroHrWeek;

	/** The sql. */
	private String sql;

	/** The txt week total. */
	private TextView txtWeekTotal;

	/** The txt week pocket. */
	private TextView txtWeekPocket;

	/** The txt week cash. */
	private TextView txtWeekCash;

	/** The txt week acc. */
	private TextView txtWeekAcc;

	/** The txt week exp. */
	private TextView txtWeekExp;

	/** The txt week hr. */
	private TextView txtWeekHr;

	/** The txt week avg. */
	private TextView txtWeekAvg;

	/** The txt week jobs. */
	private TextView txtWeekJobs;

	private LinearLayout chart;
    
    private String euro_sign;
    private View rootView;

    /**
	 * Instantiates a new frag b.
	 */
	public FragB() {

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
            rootView = inflater.inflate(R.layout.frag_by_week_small, container, false);
        }else{
            //support for 400x800
            rootView = inflater.inflate(R.layout.frag_by_week, container, false);
        }



		txtWeekTotal = (TextView) rootView.findViewById(R.id.weekTotal);
		txtWeekPocket = (TextView) rootView.findViewById(R.id.weekPocket);
		txtWeekCash = (TextView) rootView.findViewById(R.id.weekCash);
		txtWeekAcc = (TextView) rootView.findViewById(R.id.weekAcc);
		txtWeekExp = (TextView) rootView.findViewById(R.id.weekExp);
		txtWeekHr = (TextView) rootView.findViewById(R.id.weekHr);
		txtWeekAvg = (TextView) rootView.findViewById(R.id.weekAvg);
		txtWeekJobs = (TextView) rootView.findViewById(R.id.weekJobs);
		chart = (LinearLayout) rootView.findViewById(R.id.drawing_week);
        euro_sign = getResources().getString(R.string.euro_sign);
		startWeekDay = startWeekDay();
		endWeekDay = endWeekDay();

		DecimalFormat dec = new DecimalFormat("0.00");
		db = new DatabaseHandler(getActivity(),null);

		/*
		 * 
		 * 
		 * All Current Week Income Outcome
		 * 
		 * 
		 * Cash Week
		 */
		sql = "";
		sql = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_TYPE + " LIKE 'Cash'" + " AND "
				+ KEY_INC_DATE + " BETWEEN '" + startWeekDay + "' AND '" + endWeekDay + "'";

		cashTotalWeek = db.getAllIncomeTotal(sql);
		// Log.d("T",cashTotalWeek);
		txtWeekCash.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(cashTotalWeek))));

		/*
		 * 
		 * Acc Week
		 */
		sql = "";
		sql = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_TYPE + " LIKE 'Account'" + " AND "
				+ KEY_INC_DATE + " >= '" + startWeekDay + "' AND " + KEY_INC_DATE + " <= '" + endWeekDay + "'";

		accTotalWeek = db.getAllIncomeTotal(sql);
		txtWeekAcc.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(accTotalWeek))));

		/*
		 * 
		 * Expenses Week
		 */
		sql = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '" + startWeekDay + "' AND " + KEY_DATE
				+ " <= '" + endWeekDay + "'";
		expTotalWeek = db.getAllIncomeTotal(sql);
		txtWeekExp.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(expTotalWeek))));
		/*
		 * 
		 * Total Week
		 */
		totalWeek = String.valueOf((Double.valueOf(cashTotalWeek) + Double.valueOf(accTotalWeek)));
		txtWeekTotal.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(totalWeek))));
		/*
		 * 
		 * All jobs for week
		 */
		weekJobs = db.allJobsForPeriod(startWeekDay, endWeekDay);
		txtWeekJobs.setText(String.valueOf(weekJobs));
		/*
		 * 
		 * Hr Week
		 */
		sql = "SELECT SUM(CAST(" + KEY_HOURS + " as DECIMAL(9,2))) FROM " + TABLE_HOURS + " WHERE " + KEY_HOURS_ACTIVE + " LIKE 'yes' AND " + KEY_HOURS_DATE + " >= '" + startWeekDay + "' AND "
				+ KEY_HOURS_DATE + " <= '" + endWeekDay + "'";
		hrWeek = db.getAllIncomeTotal(sql);
		in = Float.parseFloat(hrWeek);
		hrWeek = dec.format(in);
		hrWeek = correctTheTime(String.valueOf(hrWeek));
		if (hrWeek != null)
			txtWeekHr.setText(hrWeek);
		else {
			txtWeekHr.setText("0");
			hrWeek = "0";
		}

		/*
		 * 
		 * Take home after expenses
		 */
		toTakeHomeWeek = String.valueOf((Double.valueOf(cashTotalWeek) + Double.valueOf(accTotalWeek)) - Double.valueOf(expTotalWeek));
		in = (float) 0;
		in = Float.parseFloat(toTakeHomeWeek);
		txtWeekPocket.setText(euro_sign + dec.format(in));

		/*
		 * 
		 * Euro per Hour
		 */

		if (Double.valueOf(hrWeek) != 0) {
			euroHrWeek = String.valueOf(Double.valueOf(toTakeHomeWeek) / Double.valueOf(hrWeek));
			in = (float) 0;
			in = Float.parseFloat(euroHrWeek);
			if (Double.valueOf(toTakeHomeWeek) != 0)
				txtWeekAvg.setText(euro_sign + dec.format(in));
			else
				txtWeekAvg.setText(euro_sign+" 0.00");
		} else
			txtWeekAvg.setText(euro_sign+" 0.00");

		sql = "";
		db.close();

		/***************************** WEEKCHART ***********************/
		
		String[] sevenDays = allDaysOfWeek();
		String[][] incomeResult = new String[7][2];
		String[] expenseResult = new String[7];

		// Retrieve income pairs(cash/account) for week days
		for (int d = 0; d < sevenDays.length; d++) {
			String cash="Cash";
			String res_cash = db.getDailyIncome(sevenDays[d],cash);
			String acc="Account";
			String res_acc = db.getDailyIncome(sevenDays[d],acc);
			
			if(res_cash!=null)
				incomeResult[d][0] = res_cash;	
			else
				incomeResult[d][0] = "0.00000001";
			
			
			if(res_acc!=null)
				incomeResult[d][1] = res_acc;	
			else
				incomeResult[d][1] = "0.00000001";
			
		}
		//Retrieve expenses
		for (int d = 0; d < sevenDays.length; d++) {
			String res_exp = db.getDailyExpense(sevenDays[d]);
			
			if(res_exp!=null)
				expenseResult[d] = res_exp;	
			else
				expenseResult[d] = "0.00000001";
		}
		
		DrawChartWeek cw = new DrawChartWeek(getActivity(), incomeResult,expenseResult);
		chart.addView(cw);
		
		/***************************************************************/
		return rootView;
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

	private String[] allDaysOfWeek() {

		String[] weekDays = new String[7];
		Calendar c = Calendar.getInstance(Locale.UK);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);

		for (int i = 0; i < 7; i++) {
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);
			weekDays[i] = sdf.format(c.getTime());
			
		}

		return weekDays;
	}

	/**
	 * Start week day.
	 *
	 * @return the string
	 */
	private String startWeekDay() {
		Calendar c = Calendar.getInstance(Locale.UK);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
		String date = sdf.format(c.getTime());

		return date;
	}

	/**
	 * End week day.
	 *
	 * @return the string
	 */
	private String endWeekDay() {
		Calendar c = Calendar.getInstance(Locale.UK);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
		String date = sdf.format(c.getTime());
		// Log.d("SunB", date);
		return date;
	}

}
