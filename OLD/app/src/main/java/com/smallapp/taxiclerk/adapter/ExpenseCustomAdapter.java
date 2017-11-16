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
import com.smallapp.taxiclerk.sqlite.Expense;

// TODO: Auto-generated Javadoc
/**
 * The Class ExpenseCustomAdapter.
 */
public class ExpenseCustomAdapter extends ArrayAdapter<Expense> {

	/** The list. */
	private final List<Expense> list;
	
	/** The context. */
	private final Activity context;
	

	/** The update exp callback. */
	protected UpdateExpRecord updateExpCallback;

	/**
	 * The Interface UpdateExpRecord.
	 */
	public interface UpdateExpRecord {
		
		/**
		 * On record select update.
		 *
		 * @param recordID the record id
		 * @param flag the flag
		 */
		public void onRecordSelectUpdate(int recordID, int flag);
	}

	/**
	 * Instantiates a new expense custom adapter.
	 *
	 * @param context the context
	 * @param list the list
	 * @param updateExpCallback the update exp callback
	 */
	public ExpenseCustomAdapter(Activity context, List<Expense> list, UpdateExpRecord updateExpCallback) {
		super(context, R.layout.lv_each_expense, list);
		this.context = context;
		this.list = list;
		this.updateExpCallback = updateExpCallback;
			}

	/**
	 * The Class ViewHolder.
	 */
	static class ViewHolder {
		
		/** The expense_id. */
		protected TextView expense_id;
		
		/** The expense_name. */
		protected TextView expense_name;
		
		/** The expense_amount. */
		protected TextView expense_amount;
		
		/** The expense_date. */
		protected TextView expense_date;
		
		/** The expense_milage. */
		protected TextView expense_milage;
		
		/** The expense_type. */
		protected TextView expense_type;
		
		/** The expense_note. */
		protected TextView expense_note;
		
		/** The btn exp edit. */
		protected ImageButton btnExpEdit;
		
		/** The btn exp delete. */
		protected ImageButton btnExpDelete;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
			View view = null;
			if (convertView == null) {
				LayoutInflater inflator = context.getLayoutInflater();

				view = inflator.inflate(R.layout.lv_each_expense, null);
				final ViewHolder viewHolder = new ViewHolder();

				viewHolder.expense_id = (TextView) view.findViewById(R.id.expense_id);
				viewHolder.expense_date = (TextView) view.findViewById(R.id.expense_date);
				viewHolder.expense_name = (TextView) view.findViewById(R.id.expense_name);
				viewHolder.expense_amount = (TextView) view.findViewById(R.id.expense_amount);
				viewHolder.expense_type = (TextView) view.findViewById(R.id.expense_type);
				viewHolder.expense_milage = (TextView) view.findViewById(R.id.expense_milage);

				viewHolder.expense_note = (TextView) view.findViewById(R.id.expense_note);
				viewHolder.btnExpEdit = (ImageButton) view.findViewById(R.id.btnExpenseEdit);
				viewHolder.btnExpEdit.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String msg = (String) viewHolder.expense_id.getText();
						int flag = 2;// 2 EDIT RECORD
						try {
							updateExpCallback.onRecordSelectUpdate(Integer.valueOf(msg), flag);
						} catch (ClassCastException e) {
							throw new ClassCastException(context.toString() + " must implement DeleteRecord");
						}

					}
				});
				viewHolder.btnExpDelete = (ImageButton) view.findViewById(R.id.btnExpenseDelete);
				viewHolder.btnExpDelete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						String msg = (String) viewHolder.expense_id.getText();
						int flag = 1;// 1 DELETE RECORD
						try {
							updateExpCallback.onRecordSelectUpdate(Integer.valueOf(msg), flag);
						} catch (ClassCastException e) {
							throw new ClassCastException(context.toString() + " must implement DeleteRecord");
						}

						// db.updateIncome(msg);
					}

				});

				view.setTag(viewHolder);

			} else {
				view = convertView;
				// ((ViewHolder)
				// view.getTag()).checkbox.setTag(list.get(position));
			}

			ViewHolder holder = (ViewHolder) view.getTag();
			holder.expense_id.setText(String.valueOf(list.get(position).getID()));
			holder.expense_date.setText(list.get(position).getDate());
			holder.expense_name.setText(list.get(position).getExpenseName());
			holder.expense_amount.setText(list.get(position).getAmount());
			holder.expense_type.setText(list.get(position).getPaymentType());
			holder.expense_note.setText(list.get(position).getNotes());
			holder.expense_milage.setText(list.get(position).getMilage());
			return view;
		}
	

}