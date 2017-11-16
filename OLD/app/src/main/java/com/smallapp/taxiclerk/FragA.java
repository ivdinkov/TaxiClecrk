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
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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
 * The Class FragA.
 */
public class FragA extends Fragment {
    // table names
    /**
     * The Constant TABLE_EXPENSE.
     */
    private static final String TABLE_EXPENSE = "expense";

    /**
     * The Constant TABLE_INCOME.
     */
    private static final String TABLE_INCOME = "income";

    /**
     * The Constant TABLE_HOURS.
     */
    private static final String TABLE_HOURS = "hours";
    // income
    /**
     * The Constant KEY_INC_DATE.
     */
    private static final String KEY_INC_DATE = "income_date";

    /**
     * The Constant KEY_INC_TYPE.
     */
    private static final String KEY_INC_TYPE = "income_type";

    /**
     * The Constant KEY_INC_AMOUNT.
     */
    private static final String KEY_INC_AMOUNT = "income_amount";

    /**
     * The Constant KEY_INC_ACTIVE.
     */
    private static final String KEY_INC_ACTIVE = "income_active";
    // expenses
    /**
     * The Constant KEY_AMOUNT.
     */
    private static final String KEY_AMOUNT = "amount";

    /**
     * The Constant KEY_DATE.
     */
    private static final String KEY_DATE = "date";

    /**
     * The Constant KEY_ACTIVE.
     */
    private static final String KEY_ACTIVE = "active";
    // Hours table
    /**
     * The Constant KEY_HOURS.
     */
    private static final String KEY_HOURS = "hours";

    /**
     * The Constant KEY_HOURS_DATE.
     */
    private static final String KEY_HOURS_DATE = "hours_date";

    /**
     * The Constant KEY_HOURS_ACTIVE.
     */
    private static final String KEY_HOURS_ACTIVE = "hours_active";
    private static final String TAG = "TC";
    
    /**
     * The db.
     */
    private DatabaseHandler db;

    /**
     * The today.
     */
    private String today;

    /**
     * The today total.
     */
    private String todayTotal;

    /**
     * The cash total today.
     */
    private String cashTotalToday;

    /**
     * The acc total today.
     */
    private String accTotalToday;

    /**
     * The exp total today.
     */
    private String expTotalToday;

    /**
     * The to take home today.
     */
    private String toTakeHomeToday;

    /**
     * The today jobs.
     */
    private int todayJobs;

    /**
     * The hr today.
     */
    private String hrToday;

    /**
     * The in.
     */
    private Float in = null;

    /**
     * The euro hr day.
     */
    private String euroHrDay;

    /**
     * The sql.
     */
    private String sql;

    /**
     * The txt total today.
     */
    private TextView txtTotalToday;

    /**
     * The txt cash today.
     */
    private TextView txtCashToday;

    /**
     * The txt acc today.
     */
    private TextView txtAccToday;

    /**
     * The txt exp today.
     */
    private TextView txtExpToday;

    /**
     * The txt to take home.
     */
    private TextView txtToTakeHome;

    /**
     * The txt hr today.
     */
    private TextView txtHrToday;

    /**
     * The txt avg day.
     */
    private TextView txtAvgDay;

    /**
     * The txt jobs.
     */
    private TextView txtJobs;

    private LinearLayout pie;

    private String euro_sign;
    private View rootView;


    /**
     * Instantiates a new frag a.
     */
    public FragA() {

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
            /////
        }
        else
        {
            Display d = w.getDefaultDisplay();
            measuredWidth = d.getWidth();
            measuredHeight = d.getHeight();
        }
        if(Integer.valueOf(measuredHeight)<800){
            //support for 320x480
            rootView = inflater.inflate(R.layout.frag_by_day_small, container, false);
        }else{
            //support for 400x800
            rootView = inflater.inflate(R.layout.frag_by_day, container, false);
        }


        txtTotalToday = (TextView) rootView.findViewById(R.id.todayTotal);
        txtCashToday = (TextView) rootView.findViewById(R.id.todayCash);
        txtAccToday = (TextView) rootView.findViewById(R.id.todayAcc);
        txtExpToday = (TextView) rootView.findViewById(R.id.todayExp);
        txtToTakeHome = (TextView) rootView.findViewById(R.id.todayPocket);
        txtHrToday = (TextView) rootView.findViewById(R.id.todayHr);
        txtJobs = (TextView) rootView.findViewById(R.id.todayJobs);
        txtAvgDay = (TextView) rootView.findViewById(R.id.todayAvg);
        pie = (LinearLayout) rootView.findViewById(R.id.drawing);
        euro_sign = getResources().getString(R.string.euro_sign);
        DecimalFormat dec = new DecimalFormat("0.00");
        db = new DatabaseHandler(getActivity(), null);
        today = getTodayDate();



		/*
         * 
		 * All cash income for today
		 */
        sql = "";
        sql = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_TYPE + " LIKE 'Cash' AND " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE
                + " LIKE '" + today + "'";
        cashTotalToday = db.getAllIncomeTotal(sql);
        txtCashToday.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(cashTotalToday))));

        
        //Log.i(TAG,);
        
        
		/*
		 * 
		 * All Account for today
		 */
        sql = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_TYPE + " LIKE 'Account' AND " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE
                + " LIKE '" + today + "'";
        accTotalToday = db.getAllIncomeTotal(sql);
        txtAccToday.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(accTotalToday))));

		/*
		 * 
		 * All Expenses for today
		 */
        sql = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " LIKE '" + today + "'";
        expTotalToday = db.getAllIncomeTotal(sql);
        txtExpToday.setText(euro_sign + String.valueOf(dec.format(Float.valueOf(expTotalToday))));

		/*
		 * 
		 * All Hours for today
		 */
        sql = "SELECT SUM(CAST(" + KEY_HOURS + " as DECIMAL(9,2))) FROM " + TABLE_HOURS + " WHERE " + KEY_HOURS_ACTIVE + " LIKE 'yes' AND " + KEY_HOURS_DATE + " LIKE '" + today + "'";
        hrToday = db.getAllIncomeTotal(sql);
        hrToday = correctTheTime(String.valueOf(dec.format(Double.valueOf(hrToday))));
        if (hrToday != null)
            txtHrToday.setText(hrToday);
        else {
            txtHrToday.setText("0");
            hrToday = "0";
        }

		/*
		 * 
		 * 
		 * All Jobs Today
		 */
        todayJobs = db.allJobsForDate(today);
        txtJobs.setText(String.valueOf(todayJobs));

		/*
		 * 
		 * Take home after expenses
		 */

        toTakeHomeToday = String.valueOf((Double.valueOf(cashTotalToday) + Double.valueOf(accTotalToday)) - Double.valueOf(expTotalToday));
        in = (float) 0;
        in = Float.parseFloat(toTakeHomeToday);
        txtToTakeHome.setText(euro_sign + dec.format(in));
		/*
		 * 
		 * Total Today
		 */
        todayTotal = String.valueOf(Double.valueOf(cashTotalToday) + Double.valueOf(accTotalToday));
        in = (float) 0;
        in = Float.parseFloat(todayTotal);
       txtTotalToday.setText(euro_sign + dec.format(in));
		/*
		 * 
		 * Euro per Hour
		 */
        if (Double.valueOf(hrToday) != 0) {
            euroHrDay = String.valueOf(Double.valueOf(toTakeHomeToday) / Double.valueOf(hrToday));
            in = (float) 0;
            in = Float.parseFloat(euroHrDay);
            if (Double.valueOf(toTakeHomeToday) != 0)
                txtAvgDay.setText(euro_sign + dec.format(in));
            else
                txtAvgDay.setText(euro_sign+" 0.00");
        } else
            txtAvgDay.setText(euro_sign+" 0.00");

        sql = "";
        db.close();

        /******************** PIECHART *******************/

        float e = 0;
        float a = 0;
        float c = 0;

        if (Float.valueOf(expTotalToday) == 0)
            e = 0.000001f;
        else
            e = Float.valueOf(expTotalToday);

        if (Float.valueOf(accTotalToday) == 0)
            a = 0.000001f;
        else
            a = Float.valueOf(accTotalToday);

        if (Float.valueOf(cashTotalToday) == 0)
            c = 0.000001f;
        else
            c = Float.valueOf(cashTotalToday);

        DrawPie s = new DrawPie(getActivity(), e, a, c);

        pie.addView(s);

        /************************************************/

        return rootView;
    }

    /**
     * Correct the time.
     *
     * @param valueOf the value of
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
            res = String.valueOf(b) + "." + min;
        } else {
            // leave it the same
            res = hr + "." + min;
        }
        // Log.d("Time2", res);
        return res;
    }

    /**
     * Gets the today date.
     *
     * @return the today date
     */
    private String getTodayDate() {
        String date = null;
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        date = sdf.format(c.getTime());
        c.clear();
        return date;
    }

}
