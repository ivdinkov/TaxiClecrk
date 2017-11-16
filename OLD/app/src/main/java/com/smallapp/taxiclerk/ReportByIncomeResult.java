/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.DecimalFormat;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportByIncomeResult.
 */
public class ReportByIncomeResult extends Fragment {

	/** The Constant TABLE_INCOME. */
	private static final String TABLE_INCOME = "income";
	// Income Table Column names
	/** The Constant KEY_ID_INCOME. */
	private static final String KEY_ID_INCOME = "income_id";
	
	/** The Constant KEY_INC_DATE. */
	private static final String KEY_INC_DATE = "income_date";
	
	/** The Constant KEY_INC_TYPE. */
	private static final String KEY_INC_TYPE = "income_type";
	
	/** The Constant KEY_INC_AMOUNT. */
	private static final String KEY_INC_AMOUNT = "income_amount";
	
	/** The Constant KEY_INC_NOTES. */
	private static final String KEY_INC_NOTES = "income_notes";
	
	/** The Constant KEY_INC_ACTIVE. */
	private static final String KEY_INC_ACTIVE = "income_active";
	
	/** The Constant KEY_INC_PROVIDER. */
	private static final String KEY_INC_PROVIDER = "income_provider";
	
	/** The total v2. */
	private Double totalV2 = 0.0;
	
	/** The _end date. */
	private String _startDate, _endDate;
	
	/** The txt end date. */
	private TextView txtStartDate, txtEndDate;
	
	/** The end date. */
	private String provName, startDate, endDate;
	
	/** The txt total v2. */
	private TextView txtNameV1, txtJobsV1, txtCashV1, txtAccV1, txtTotalV1, txtTotalV2;
	
	/** The txt prov2 name acc v2. */
	private TextView txtNameV2, txtJobsV2, txtCashV2, txtProv1CashV2, txtProv2CashV2, txtProvName, txtProv1NameCashV2, txtProv2NameCashV2, txtProv1AccV2, txtProv1NameAccV2, txtProv2AccV2,
			txtProv2NameAccV2;
	
	/** The total v1. */
	private String nameV1, jobsV1, cashV1, accV1, totalV1;
	
	/** The prov2 name acc v2. */
	private String nameV2, jobsV2, cashV2, prov1CashV2, prov2CashV2, prov1NameCashV2, prov2NameCashV2, prov1AccV2, prov1NameAccV2, prov2AccV2, prov2NameAccV2;
	
	/** The db. */
	private DatabaseHandler db;
	
	/** The in. */
	private float in;
	
	/** The btn back. */
	private Button btnBack;
	
	/** The view2. */
	private LinearLayout view1, view2;
	
	/** The pr5. */
	private LinearLayout pr1, pr2, pr3, pr4, pr5;
	
	/** The e date. */
	private String eDate;
	
	/** The s date. */
	private String sDate;
	
	/** The _start date d. */
	private String _startDateD;
	
	/** The _end date d. */
	private String _endDateD;

	/**
	 * Instantiates a new report by income result.
	 *
	 * @param pName the name
	 * @param startDateDisplay the start date display
	 * @param endDateDisplay the end date display
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public ReportByIncomeResult(String pName, String startDateDisplay,String endDateDisplay,String startDate, String endDate) {
		this.provName = pName;
		this._startDate = startDate;
		this._endDate = endDate;
		this._startDateD = startDateDisplay;
		this._endDateD = endDateDisplay;

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.by_income_result, container, false);
		btnBack = (Button) rootView.findViewById(R.id.btnBack);
		// Dates
		txtStartDate = (TextView) rootView.findViewById(R.id.startDate);
		txtEndDate = (TextView) rootView.findViewById(R.id.endDate);

		// id view1
		view1 = (LinearLayout) rootView.findViewById(R.id.view1);
		txtNameV1 = (TextView) rootView.findViewById(R.id.name_view1);
		txtJobsV1 = (TextView) rootView.findViewById(R.id.jobs_view1);
		txtCashV1 = (TextView) rootView.findViewById(R.id.reportByIncomeCashView1);
		txtAccV1 = (TextView) rootView.findViewById(R.id.reportByIncomeAccView1);
		txtTotalV1 = (TextView) rootView.findViewById(R.id.reportByExpTotal);

		// id view2
		view2 = (LinearLayout) rootView.findViewById(R.id.view2);
		txtNameV2 = (TextView) rootView.findViewById(R.id.name_view2);
		txtJobsV2 = (TextView) rootView.findViewById(R.id.jobs_view2);

		txtProvName = (TextView) rootView.findViewById(R.id.prov_name);
		txtCashV2 = (TextView) rootView.findViewById(R.id.reportByIncomeCashStreet);
		txtProv1CashV2 = (TextView) rootView.findViewById(R.id.reportByIncomeCashProv1);
		txtProv1NameCashV2 = (TextView) rootView.findViewById(R.id.prov1_name);
		txtProv2CashV2 = (TextView) rootView.findViewById(R.id.reportByIncomeCashProv2);
		txtProv2NameCashV2 = (TextView) rootView.findViewById(R.id.prov2_name);

		txtProv1AccV2 = (TextView) rootView.findViewById(R.id.reportByIncomeAccProv1);
		txtProv1NameAccV2 = (TextView) rootView.findViewById(R.id.prov1_name_acc);
		txtProv2AccV2 = (TextView) rootView.findViewById(R.id.reportByIncomeAccProv2);
		txtProv2NameAccV2 = (TextView) rootView.findViewById(R.id.prov2_name_acc);
		txtTotalV2 = (TextView) rootView.findViewById(R.id.reportByIncomeTotalView2);

		pr1 = (LinearLayout) rootView.findViewById(R.id.pr1);
		pr2 = (LinearLayout) rootView.findViewById(R.id.pr2);
		pr3 = (LinearLayout) rootView.findViewById(R.id.pr3);
		pr4 = (LinearLayout) rootView.findViewById(R.id.pr4);
		pr5 = (LinearLayout) rootView.findViewById(R.id.pr5);

		pr1.setVisibility(View.GONE);
		pr2.setVisibility(View.GONE);
		pr3.setVisibility(View.GONE);
		pr4.setVisibility(View.GONE);
		pr5.setVisibility(View.GONE);
		/*
		 * 
		 * txtProv1NameCashV2 = txtProv1NameAccV2 txtProv2NameCashV2 =
		 * txtProv2NameAccV2
		 */
		
		txtStartDate.setText(_startDateD);
		txtEndDate.setText(_endDateD);

		DecimalFormat dec = new DecimalFormat("0.00");
		db = new DatabaseHandler(getActivity(), null);

		if (!provName.equals("All")) {

			view2.setVisibility(View.GONE);
			txtNameV1.setText(provName);

			jobsV1 = String.valueOf(db.allJobsForPeriodProvider(_startDate, _endDate, provName));
			txtJobsV1.setText(jobsV1);

			String sqlAllCash = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '" + _startDate
					+ "' AND " + KEY_INC_DATE + " <= '" + _endDate + "' AND " + KEY_INC_TYPE + " LIKE 'cash' AND " + KEY_INC_PROVIDER + " LIKE '" + provName + "'";
			cashV1 = db.getAllIncomeTotal(sqlAllCash);
			in = 0;
			in = Float.parseFloat(cashV1);
			txtCashV1.setText(dec.format(in));

			String sqlAllAcc = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '" + _startDate
					+ "' AND " + KEY_INC_DATE + " <= '" + _endDate + "' AND " + KEY_INC_TYPE + " LIKE 'account' AND " + KEY_INC_PROVIDER + " LIKE '" + provName + "'";
			accV1 = db.getAllIncomeTotal(sqlAllAcc);
			in = 0;
			in = Float.parseFloat(accV1);
			txtAccV1.setText(dec.format(in));

			in = 0;
			totalV1 = String.valueOf(Double.valueOf(cashV1) + Double.valueOf(accV1));
			in = Float.parseFloat(totalV1);
			txtTotalV1.setText(dec.format(in));

		} else {

			view1.setVisibility(View.GONE);
			txtNameV2.setText(provName);

			jobsV2 = String.valueOf(db.allJobsForPeriod(_startDate, _endDate));
			txtJobsV2.setText(jobsV2);
			// get all active providers
			List<Provider> prov = db.getAllProviders();
			// get all cash records
			for (Provider rec : prov) {
				if (rec.getID() == 1) {
					// get record
					String sqlAllCash = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '"
							+ _startDate + "' AND " + KEY_INC_DATE + " <= '" + _endDate + "' AND " + KEY_INC_TYPE + " LIKE 'cash' AND " + KEY_INC_PROVIDER + " LIKE '" + rec.getName() + "'";
					cashV2 = db.getAllIncomeTotal(sqlAllCash);

					// set values
					txtProvName.setText(rec.getName());
					in = 0;
					in = Float.parseFloat(cashV2);
					totalV2 = totalV2 + in;
					txtCashV2.setText(dec.format(in));

				}
				if (rec.getID() == 2) {
					pr1.setVisibility(View.VISIBLE);
					// get record
					String sqlAllCash = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '"
							+ _startDate + "' AND " + KEY_INC_DATE + " <= '" + _endDate + "' AND " + KEY_INC_TYPE + " LIKE 'cash' AND " + KEY_INC_PROVIDER + " LIKE '" + rec.getName() + "'";
					prov1CashV2 = db.getAllIncomeTotal(sqlAllCash);

					// set values
					txtProv1NameCashV2.setText(rec.getName());
					in = 0;
					in = Float.parseFloat(prov1CashV2);
					totalV2 = totalV2 + in;
					txtProv1CashV2.setText(dec.format(in));

				}
				if (rec.getID() == 3) {
					pr2.setVisibility(View.VISIBLE);
					// get record
					String sqlAllCash = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '"
							+ _startDate + "' AND " + KEY_INC_DATE + " <= '" + _endDate + "' AND " + KEY_INC_TYPE + " LIKE 'cash' AND " + KEY_INC_PROVIDER + " LIKE '" + rec.getName() + "'";
					prov2CashV2 = db.getAllIncomeTotal(sqlAllCash);

					// set values
					txtProv2NameCashV2.setText(rec.getName());
					in = 0;
					in = Float.parseFloat(prov2CashV2);
					totalV2 = totalV2 + in;
					txtProv2CashV2.setText(dec.format(in));

				}

			}
			// get all acc records
			for (Provider rec : prov) {
				if (rec.getID() == 2) {
					pr3.setVisibility(View.VISIBLE);
					pr4.setVisibility(View.VISIBLE);
					// get record
					String sqlAllCash = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '"
							+ _startDate + "' AND " + KEY_INC_DATE + " <= '" + _endDate + "' AND " + KEY_INC_TYPE + " LIKE 'account' AND " + KEY_INC_PROVIDER + " LIKE '" + rec.getName() + "'";
					prov1AccV2 = db.getAllIncomeTotal(sqlAllCash);

					// set values
					txtProv1NameAccV2.setText(rec.getName());
					in = 0;
					in = Float.parseFloat(prov1AccV2);
					totalV2 = totalV2 + in;
					txtProv1AccV2.setText(dec.format(in));

				}
				if (rec.getID() == 3) {
					pr3.setVisibility(View.VISIBLE);
					pr5.setVisibility(View.VISIBLE);
					// get record
					String sqlAllCash = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '"
							+ _startDate + "' AND " + KEY_INC_DATE + " <= '" + _endDate + "' AND " + KEY_INC_TYPE + " LIKE 'account' AND " + KEY_INC_PROVIDER + " LIKE '" + rec.getName() + "'";
					prov2AccV2 = db.getAllIncomeTotal(sqlAllCash);

					// set values
					txtProv2NameAccV2.setText(rec.getName());
					in = 0;
					in = Float.parseFloat(prov2AccV2);
					totalV2 = totalV2 + in;
					txtProv2AccV2.setText(dec.format(in));

				}
			}
			// total
			txtTotalV2.setText(String.valueOf(dec.format(totalV2)));
		}
		db.close();

		btnBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
				ReportsFragment fragment = new ReportsFragment();				
				ft.replace(R.id.frame_container, fragment).commit();
			}
		});
		return rootView;
	}

	
}
