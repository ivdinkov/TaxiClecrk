/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.User;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginActivity.
 */
public class LoginActivity extends Activity {

	/** The btn login. */
	Button btnLogin;

	/** The login email. */
	EditText loginEmail;

	/** The login pin. */
	EditText loginPin;

	/** The count. */
	int count = 0;

	/** The txt login base. */
	private EditText txtLoginBase;

	/** The prefs. */
	private SharedPreferences prefs;

	/** The db. */
	private DatabaseHandler db;

	

	/** The register_date. */
	public String register_date;

	/** The email. */
	public String email;

	/** The restored text. */
	private String restoredText;

    private TextView verCode;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DatabaseHandler(this, null);

		setContentView(R.layout.activity_login);

		btnLogin = (Button) findViewById(R.id.btnLogin);
		prefs = this.getSharedPreferences("taxiclerk", Context.MODE_PRIVATE);
		txtLoginBase = (EditText) findViewById(R.id.loginBase);
		loginPin = (EditText) findViewById(R.id.loginPin);
        verCode = (TextView) findViewById(R.id.version_number);

        verCode.setText("v"+getVersionCode());

		loginPin.setHintTextColor(getResources().getColor(R.color.login_label_color));
		// check if this is the first time app runs
		if (!checkUserIfAny()) {
			Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
			startActivity(i);
			finish();
		} else {
			restoredText = prefs.getString("logged", null);
			if (restoredText != null) {
				if (restoredText.equals("yes")) {
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
			/*
			 * 
			 * Generate login page and check user
			 */

			btnLogin.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {

					String pin = loginPin.getText().toString();

					int i = db.Login(pin);
					db.close();
					if (i == 1) {
                        // Store prefs
						SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
						editor.putString("logged", "yes");
						editor.apply();

                        //log time shift started
                        logUserActivity();

                        // go to main activity
						Intent intent = new Intent(getApplicationContext(), MainActivity.class);
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(LoginActivity.this, "Incorrect Login", Toast.LENGTH_LONG).show();
					}
				}
			});
		}
	}// end oncreate

    private void logUserActivity() {
        db = new DatabaseHandler(this, null);

        db.close();
    }

    private String getVersionCode() {
        PackageInfo pInfo = null;
        try {
           pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pInfo != null ? pInfo.versionName : null;
    }


    /**
	 * Check if registerd before.
	 *
	 * @return true, if successful
	 */
	private boolean checkUserIfAny() {
		/* *
		 * Check if user record exist else go to register activity
		 */
		db = new DatabaseHandler(this, null);
		List<User> users = db.getAllUsers();
		int count = users.size();
		db.close();
        return count != 0;
    }

	/**
	 * onStop save user state.
	 */
	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putString("logged", "yes");
		editor.apply();
	}

    /**
     * Gets the current date.
     *
     * @return current date
     */
    private String getCurrentDateTime() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return register_date = sdf.format(c.getTime());
    }
}// end class
