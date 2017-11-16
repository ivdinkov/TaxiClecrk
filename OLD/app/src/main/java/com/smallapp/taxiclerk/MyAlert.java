/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

// TODO: Auto-generated Javadoc
/**
 * The Class MyAlert.
 */
public class MyAlert extends AlertDialog {

/**
 * Instantiates a new my alert.
 *
 * @param context the context
 */
protected MyAlert(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

/* (non-Javadoc)
 * @see android.app.AlertDialog#onCreate(android.os.Bundle)
 */
@Override
protected void onCreate(Bundle savedInstanceState) {
	setContentView(R.layout.dialog);
	super.onCreate(savedInstanceState);
}
	
}
