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
import com.smallapp.taxiclerk.sqlite.Hours;

// TODO: Auto-generated Javadoc
/**
 * The Class HoursCustomAdapter.
 */
public class HoursCustomAdapter extends ArrayAdapter<Hours> {

	/** The list. */
	private final List<Hours> list;
	
	/** The context. */
	private final Activity context;

	/** The update hours callback. */
	protected UpdateHoursRecord updateHoursCallback;

	/**
	 * The Interface UpdateHoursRecord.
	 */
	public interface UpdateHoursRecord {
		
		/**
		 * On record select update.
		 *
		 * @param recordID the record id
		 * @param flag the flag
		 */
		public void onRecordSelectUpdate(int recordID, int flag);
	}

	/**
	 * Instantiates a new hours custom adapter.
	 *
	 * @param context the context
	 * @param list the list
	 * @param updateCallback the update callback
	 */
	public HoursCustomAdapter(Activity context, List<Hours> list, UpdateHoursRecord updateCallback) {
		super(context, R.layout.lv_each_hours, list);
		this.context = context;
		this.list = list;
		this.updateHoursCallback = updateCallback;
	}

	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {
		
		/** The id. */
		protected TextView id;
		
		/** The date. */
		protected TextView date;
		
		/** The hours. */
		protected TextView hours;
		
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
				view = inflator.inflate(R.layout.lv_each_hours, null);
				final ViewHolder viewHolder = new ViewHolder();

				viewHolder.id = (TextView) view.findViewById(R.id.hours_id);
				viewHolder.hours = (TextView) view.findViewById(R.id.hours);
				viewHolder.date = (TextView) view.findViewById(R.id.hours_date);
				viewHolder.btnEdit = (ImageButton) view.findViewById(R.id.btnHoursEdit);
				viewHolder.btnEdit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String msg = (String) viewHolder.id.getText();
						int flag = 2;// 2 EDIT RECORD
						try {
							updateHoursCallback.onRecordSelectUpdate(Integer.valueOf(msg), flag);
						} catch (ClassCastException e) {
							throw new ClassCastException(context.toString() + " must implement DeleteRecord");
						}

					}
				});
				viewHolder.btnDelete = (ImageButton) view.findViewById(R.id.btnHoursDelete);
				viewHolder.btnDelete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String msg = (String) viewHolder.id.getText();
						int flag = 1;// 1 DELETE RECORD
						try {
							updateHoursCallback.onRecordSelectUpdate(Integer.valueOf(msg), flag);
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
			// ((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();

		holder.id.setText(String.valueOf(list.get(position).getID()));
		holder.hours.setText(list.get(position).getHours());
		holder.date.setText(list.get(position).getDate());

		return view;
	}

}