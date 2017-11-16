/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smallapp.taxiclerk.adapter.ExpenseCustomAdapter;
import com.smallapp.taxiclerk.adapter.ExpenseCustomAdapter.UpdateExpRecord;
import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Expense;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpenseUpdateFragment.
 */
public class ExpenseUpdateFragment extends ListFragment implements UpdateExpRecord {

	/** The db. */
	protected DatabaseHandler db;
	
	/** The fragment manager. */
	FragmentManager fragmentManager;
	
	/** The Update single expense record. */
	private UpdateSingleExpenseRecord UpdateSingleExpenseRecord;
	
	/** The empty record. */
	private NoExpense emptyRecord;
	
	/** The custom view. */
	public View customView;
	
	/** The record id. */
	private int recordID;

	/**
	 * Instantiates a new expense update fragment.
	 */
	public ExpenseUpdateFragment() {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		db = new DatabaseHandler(getActivity(), null);
		List<Expense> expense = db.getAllExpense();
		db.close();
		if (!expense.isEmpty()) {
			View rootView = inflater.inflate(R.layout.fragment_exp_update, container, false);

			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

			ArrayAdapter<Expense> adapter = new ExpenseCustomAdapter(getActivity(), getModel(), this);
			setListAdapter(adapter);

			return rootView;
		} else {
			View rootView = inflater.inflate(R.layout.no_expense, container, false);
			LinearLayout ll = (LinearLayout) rootView.findViewById(R.id.list);
			ll.setVisibility(View.GONE);
			return rootView;
		}

	}

	// Getting all records
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	private List<Expense> getModel() {
		db = new DatabaseHandler(getActivity(), null);
		List<Expense> list = new ArrayList<Expense>();
		List<Expense> expense = db.getAllExpense();

		for (Expense cn : expense) {

			list.add(new Expense(Integer.valueOf(cn.getID()), cn.getExpenseName(), cn.getAmount(), cn.getDate(), cn.getMilage(), cn.getPaymentType(), cn.getNotes(), cn.getActive()));
		}

		db.close();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.smallapp.taxiclerk.adapter.ExpenseCustomAdapter.UpdateExpRecord#onRecordSelectUpdate(int, int)
	 */
	@Override
	public void onRecordSelectUpdate(final int recordID, int flag) {

		switch (flag) {
		case 1:
			this.recordID = recordID;
			// DELETE RECORD
			showAlertDialog();
			break;
		case 2:
			// UPDATE RECORD
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom);		
			UpdateSingleExpenseRecord = new UpdateSingleExpenseRecord(recordID);		
			ft.replace(R.id.frame_container, UpdateSingleExpenseRecord).commit();
		default:
			break;
		}
	}

	/**
	 * Show alert dialog.
	 */
	private void showAlertDialog() {
		FragmentManager fm = getFragmentManager();
		MyDialog alertDialog = new MyDialog();
		alertDialog.show(fm, "fragment_alert");
	}

	/**
	 * Do update.
	 *
	 * @param id the id
	 */
	public void doUpdate(int id) {
		db = new DatabaseHandler(getActivity(), null);
		db.deleteExpense(id);
		ArrayAdapter<Expense> adapter = new ExpenseCustomAdapter(getActivity(), getModel(), this);
		setListAdapter(adapter);
		db.close();
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

			LayoutInflater inflater = getActivity().getLayoutInflater();
			final Dialog alertDialogBuilder = new Dialog(getActivity(), R.style.DialogTheme);

			customView = inflater.inflate(R.layout.dialog_single_msg, null);
			final TextView txtMsg = (TextView) customView.findViewById(R.id.txtDialogMsg1);
			TextView dlgTitle = (TextView) customView.findViewById(R.id.dlgDelete);
			TextView btnYes = (TextView) customView.findViewById(R.id.btnDialogYes);
			TextView btnNo = (TextView) customView.findViewById(R.id.btnDialogNo);
			txtMsg.setText(R.string.are_you_sure);
			dlgTitle.setText(R.string.delete);
			alertDialogBuilder.setContentView(customView);
			btnYes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					doUpdate(recordID);
					db = new DatabaseHandler(getActivity(), null);
					List<Expense> list = new ArrayList<Expense>();
					List<Expense> expense = db.getAllExpense();
					if (expense.isEmpty()) {
						FragmentTransaction ft = getFragmentManager().beginTransaction();
						ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);		
						emptyRecord = new NoExpense();
						ft.replace(R.id.frame_container, emptyRecord).commit();
						
					}
					db.close();

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

}
