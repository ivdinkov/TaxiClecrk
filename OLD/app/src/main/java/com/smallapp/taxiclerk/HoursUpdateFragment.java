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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smallapp.taxiclerk.adapter.HoursCustomAdapter;
import com.smallapp.taxiclerk.adapter.HoursCustomAdapter.UpdateHoursRecord;
import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Hours;

// TODO: Auto-generated Javadoc
/**
 * The Class HoursUpdateFragment.
 */
public class HoursUpdateFragment extends ListFragment implements UpdateHoursRecord {

	// private HoursFragment fragHoursRecord;
	/** The db. */
	private DatabaseHandler db;

	/** The Update single hours record fragment. */
	private UpdateSingleHoursRecord UpdateSingleHoursRecordFragment;

	/** The empty record. */
	private NoHours emptyRecord;

	/** The record id. */
	private int recordID;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.ListFragment#onCreateView(android.view.LayoutInflater
	 * , android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		db = new DatabaseHandler(getActivity(), null);
		List<Hours> hours = db.getAllHours();
		db.close();
		if (!hours.isEmpty()) {
			View rootView = inflater.inflate(R.layout.fragment_hours_update, container, false);
			// create an array of Strings, that will be put to our ListActivity

			ArrayAdapter<Hours> adapter = new HoursCustomAdapter(getActivity(), getModel(), this);
			setListAdapter(adapter);

			return rootView;
		} else {
			View rootView = inflater.inflate(R.layout.no_hours, container, false);
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
	private List<Hours> getModel() {
		db = new DatabaseHandler(getActivity(), null);
		List<Hours> list = new ArrayList<Hours>();
		List<Hours> hours = db.getAllHours();

		for (Hours cn : hours) {
			list.add(new Hours(Integer.valueOf(cn.getID()), cn.getHours(), cn.getDate(), cn.getActive()));

		}

		db.close();
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.smallapp.taxiclerk.adapter.HoursCustomAdapter.UpdateHoursRecord#
	 * onRecordSelectUpdate(int, int)
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
			UpdateSingleHoursRecordFragment = new UpdateSingleHoursRecord(recordID);
			ft.replace(R.id.frame_container, UpdateSingleHoursRecordFragment).commit();
		default:
			break;
		}

	}

	/**
	 * Do update.
	 *
	 * @param id
	 *            the id
	 */
	public void doUpdate(int id) {
		db = new DatabaseHandler(getActivity(), null);
		db.deleteHours(id);
		ArrayAdapter<Hours> adapter = new HoursCustomAdapter(getActivity(), getModel(), this);
		setListAdapter(adapter);
		db.close();
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

		/** The custom view. */
		private View customView;

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle
		 * )
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

				private NoHours noHours;

				@Override
				public void onClick(View v) {
					doUpdate(recordID);
					db = new DatabaseHandler(getActivity(), null);
					List<Hours> list = new ArrayList<Hours>();
					List<Hours> hours = db.getAllHours();

					if (hours.isEmpty()) {
						FragmentTransaction ft = getFragmentManager().beginTransaction();
						ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
						emptyRecord = new NoHours();
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
