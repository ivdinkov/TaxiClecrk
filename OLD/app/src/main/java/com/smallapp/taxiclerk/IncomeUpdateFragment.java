/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smallapp.taxiclerk.adapter.IncomeCustomAdapter;
import com.smallapp.taxiclerk.adapter.IncomeCustomAdapter.UpdateRecord;
import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Income;

// TODO: Auto-generated Javadoc
/**
 * The Class IncomeUpdateFragment.
 */
public class IncomeUpdateFragment extends ListFragment implements UpdateRecord {

	/** The db. */
	protected DatabaseHandler db;
	// private Button btnUpdate;
	/** The empty record. */
	private NoIncome emptyRecord;
	
	/** The fragment manager. */
	FragmentManager fragmentManager;
	// private IncomeFragment fragIncomeRecord;
	/** The Update single income record fragment. */
	private Fragment UpdateSingleIncomeRecordFragment;
	
	/** The custom view. */
	public View customView;
	
	/** The record id. */
	private int recordID;
	
	/** The frag update income record. */
	private UpdateSingleIncomeRecord fragUpdateIncomeRecord;

	/**
	 * Instantiates a new income update fragment.
	 */
	public IncomeUpdateFragment() {
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		db = new DatabaseHandler(getActivity(), null);
		List<Income> income = db.getAllIncome();
		db.close();
		if (!income.isEmpty()) {
			View rootView = inflater.inflate(R.layout.fragment_inc_update, container, false);
			// create an array of Strings, that will be put to our ListActivity
			ArrayAdapter<Income> adapter = new IncomeCustomAdapter(getActivity(), getModel(), this);
			setListAdapter(adapter);
			
			db.close();
			return rootView;
		} else {
			View rootView = inflater.inflate(R.layout.no_income, container, false);
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
	private List<Income> getModel() {
		db = new DatabaseHandler(getActivity(), null);
		List<Income> list = new ArrayList<Income>();
		List<Income> income = db.getAllIncome();

		for (Income cn : income) {
			list.add(new Income(Integer.valueOf(cn.getID()), cn.getDate(), cn.getIncType(), cn.getAmount(), cn.getNote(), cn.getActive(), cn.getProvider()));
		}
		db.close();
		return list;
	}

	/* (non-Javadoc)
	 * @see com.smallapp.taxiclerk.adapter.IncomeCustomAdapter.UpdateRecord#onRecordSelectUpdate(int, int)
	 */
	@Override
	public void onRecordSelectUpdate(final int recordID, int flag) {
		this.recordID = recordID;
		switch (flag) {
		case 1:
			// DELETE RECORD
			showAlertDialog();
			break;
		case 2:
			// UPDATE RECORD
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom);			
			fragUpdateIncomeRecord = new UpdateSingleIncomeRecord(recordID);
			ft.replace(R.id.frame_container, fragUpdateIncomeRecord).commit();
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
					List<Income> income = db.getAllIncome();
					if (income.isEmpty()) {
						
						FragmentTransaction ft = getFragmentManager().beginTransaction();
						ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);			
						emptyRecord = new NoIncome();
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

	/**
	 * Do update.
	 *
	 * @param id the id
	 */
	public void doUpdate(int id) {
		db = new DatabaseHandler(getActivity(), null);
		db.deleteIncome(id);
		ArrayAdapter<Income> adapter = new IncomeCustomAdapter(getActivity(), getModel(), this);
		setListAdapter(adapter);
		db.close();
	}

}
