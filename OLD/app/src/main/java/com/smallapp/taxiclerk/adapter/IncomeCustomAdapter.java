/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smallapp.taxiclerk.R;
import com.smallapp.taxiclerk.sqlite.Income;

// TODO: Auto-generated Javadoc
/**
 * The Class IncomeCustomAdapter.
 */
public class IncomeCustomAdapter extends ArrayAdapter<Income> {

	/** The list. */
	private final List<Income> list;
	
	/** The context. */
	private final Activity context;

	/** The update callback. */
	protected UpdateRecord updateCallback;

	/**
	 * The Interface UpdateRecord.
	 */
	public interface UpdateRecord {
		
		/**
		 * On record select update.
		 *
		 * @param recordID the record id
		 * @param flag the flag
		 */
		public void onRecordSelectUpdate(int recordID, int flag);
	}

	/**
	 * Instantiates a new income custom adapter.
	 *
	 * @param context the context
	 * @param list the list
	 * @param updateCallback the update callback
	 */
	public IncomeCustomAdapter(Activity context, List<Income> list, UpdateRecord updateCallback) {

		super(context, R.layout.lv_each_income, list);
		this.context = context;
		this.list = list;
		this.updateCallback = updateCallback;
	}

	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {
		
		/** The income_id. */
		protected TextView income_id;
		
		/** The date. */
		protected TextView date;
		
		/** The type. */
		protected TextView type;
		
		/** The amount. */
		protected TextView amount;
		
		/** The hours. */
		protected TextView hours;
		
		/** The note. */
		protected TextView note;
		
		/** The provider. */
		protected TextView provider;
		
		/** The btn edit. */
		protected ImageButton btnEdit;
		
		/** The btn delete. */
		protected ImageButton btnDelete;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			if (list.isEmpty()) {
				view = inflator.inflate(R.layout.no_income, null);
			} else {

				view = inflator.inflate(R.layout.lv_each_income, null);
				final ViewHolder viewHolder = new ViewHolder();
				viewHolder.income_id = (TextView) view.findViewById(R.id.income_id);
				viewHolder.date = (TextView) view.findViewById(R.id.income_date);
				viewHolder.type = (TextView) view.findViewById(R.id.income_type);
				viewHolder.amount = (TextView) view.findViewById(R.id.income_amount);
				viewHolder.note = (TextView) view.findViewById(R.id.income_notes);
				viewHolder.provider = (TextView) view.findViewById(R.id.income_provider);
				viewHolder.btnEdit = (ImageButton) view.findViewById(R.id.btnIncomeEdit);
				viewHolder.btnEdit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String msg = (String) viewHolder.income_id.getText();
						int flag = 2;// 2 EDIT RECORD
						try {
							updateCallback.onRecordSelectUpdate(Integer.valueOf(msg), flag);
						} catch (ClassCastException e) {
							throw new ClassCastException(context.toString() + " must implement DeleteRecord");
						}

					}
				});
				viewHolder.btnDelete = (ImageButton) view.findViewById(R.id.btnIncomeDelete);
				viewHolder.btnDelete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String msg = (String) viewHolder.income_id.getText();
						int flag = 1;// 1 DELETE RECORD
						try {
							updateCallback.onRecordSelectUpdate(Integer.valueOf(msg), flag);
						} catch (ClassCastException e) {
							throw new ClassCastException(context.toString() + " must implement DeleteRecord");
						}

						// db.updateIncome(msg);
					}

				});

				view.setTag(viewHolder);
			}
		} else {
			view = convertView;
		}
		ViewHolder holder = (ViewHolder) view.getTag();

		holder.income_id.setText(String.valueOf(list.get(position).getID()));
		holder.date.setText(list.get(position).getDate());
		holder.type.setText(list.get(position).getIncType());
		holder.amount.setText(list.get(position).getAmount());
		holder.provider.setText(list.get(position).getNote());
		holder.note.setText(list.get(position).getProvider());

		return view;
	}

}