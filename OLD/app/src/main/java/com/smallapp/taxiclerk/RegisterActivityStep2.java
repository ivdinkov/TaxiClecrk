/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Provider;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterActivityStep2.
 */
public class RegisterActivityStep2 extends Activity {
	
	/** The txt job pr2. */
	private EditText txtJobPr2;
	
	/** The base pr2. */
	private EditText basePr2;
	
	/** The txt job pr3. */
	private EditText txtJobPr3;
	
	/** The base pr3. */
	private EditText basePr3;
	
	/** The btn addpr2. */
	private Button btnAddpr2;
	
	/** The btn addpr3. */
	private Button btnAddpr3;
	
	/** The btn finish. */
	private Button btnFinish;
	
	/** The db. */
	private DatabaseHandler db;
	
	/** The active. */
	private String active = "yes";
	
	/** The pr3. */
	private String pr2, pr3;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.register_activity_step2);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		txtJobPr2 = (EditText) findViewById(R.id.txtProv2);
		basePr2 = (EditText) findViewById(R.id.base2);
		basePr2.setEnabled(false);
		basePr2.setClickable(false);
		basePr2.setFocusable(false);
		txtJobPr3 = (EditText) findViewById(R.id.txtProv3);
		basePr3 = (EditText) findViewById(R.id.base3);
		basePr3.setEnabled(false);
		basePr3.setClickable(false);
		basePr3.setFocusable(false);
		btnAddpr2 = (Button) findViewById(R.id.btnAddJobPr2);
		btnAddpr3 = (Button) findViewById(R.id.btnAddJobPr3);
		btnFinish = (Button) findViewById(R.id.btnFinishJobPr);

		db = new DatabaseHandler(this, null);

		// Button Finish Go to Main App
		btnFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		// Add Street Provider Default
		try {
			db.addProvider(new Provider("Street", active));
			db.addProvider(new Provider("0", "no"));
			db.addProvider(new Provider("0", "no"));

		} catch (Exception e) {
			Toast.makeText(RegisterActivityStep2.this, "Error: 000005", Toast.LENGTH_LONG).show();
		}

		// Add First Provider
		btnAddpr2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				pr2 = txtJobPr2.getText().toString();
				if (!pr2.isEmpty()) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
					db.updateProvider(2, pr2, active);
					btnAddpr2.setVisibility(View.GONE);
					Toast.makeText(RegisterActivityStep2.this, "Job Provider " + pr2 + " saved", Toast.LENGTH_LONG).show();
					txtJobPr2.setEnabled(false);
					txtJobPr2.setClickable(false);
					txtJobPr2.setFocusable(false);
					basePr2.setVisibility(View.GONE);
				}else{
					db.updateProvider(2,"", "no");
				}

			}

		});

		// Add Second Provider
		btnAddpr3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pr3 = txtJobPr3.getText().toString();
				if (!pr3.isEmpty()) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

					db.updateProvider(3, pr3, active);
					btnAddpr3.setVisibility(View.GONE);
					Toast.makeText(RegisterActivityStep2.this, "Job Provider " + pr3 + " saved", Toast.LENGTH_LONG).show();
					txtJobPr3.setEnabled(false);
					txtJobPr3.setClickable(false);
					txtJobPr3.setFocusable(false);
					basePr3.setVisibility(View.GONE);
				}else{
					db.updateProvider(3,"", "no");
				}

			}
		});
		db.close();
		super.onCreate(savedInstanceState);
	}
}
