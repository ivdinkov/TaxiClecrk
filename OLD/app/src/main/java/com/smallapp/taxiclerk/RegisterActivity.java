/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

import org.json.JSONArray;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.User;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterActivity.
 */
public class RegisterActivity extends FragmentActivity {

	/** The btn register. */
	private Button btnRegister;
	
	/** The register email. */
	private EditText registerEmail;
	
	/** The register name. */
	private EditText registerName;
	
	/** The register pin. */
	private EditText registerPin;
	
	/** The email. */
	private String email;
	
	/** The name. */
	private String name = "user";
	
	/** The pin. */
	private String pin;
	
	/** The chk box terms. */
	private CheckBox chkBoxTerms;
	
	/** The terms. */
	private Boolean terms = false;
	
	/** The reg base1. */
	private EditText regBase1;
	
	/** The reg base2. */
	private EditText regBase2;
	
	/** The reg base3. */
	private EditText regBase3;
	
	/** The imei_number_device. */
	private String imei_number_device;
	
	/** The register_date. */
	private String register_date;
	
	/** The sdf. */
	private SimpleDateFormat sdf;

	// Creating JSON Parser object

	// products JSONArray
	/** The products. */
	JSONArray products = null;
	
	/** The res save remote. */
	protected boolean resSaveRemote;
	
	/** The db. */
	DatabaseHandler db;
	
	/** The m connection. */
	private Boolean mConnection;
	
	/** The txt terms. */
	private TextView txtTerms;
	
	/** The custom view. */
	public View customView;
	
	/** The Constant TEXT_REGEX. */
	private static final String TEXT_REGEX = "^[a-zA-Z ]*$";

    /** The Constant DDIGIT_REGEX */
    private static final String DIGIT_REGEX = "[0-9]+";

	/**
	 * Instantiates a new register activity.
	 */
	public RegisterActivity() {

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		//mConnection = checkConnection();
		// Importing all assets
		registerEmail = (EditText) findViewById(R.id.registerEmail);
		registerName = (EditText) findViewById(R.id.registerName);
		registerPin = (EditText) findViewById(R.id.registerPin);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		chkBoxTerms = (CheckBox) findViewById(R.id.checkbox_terms);
		txtTerms = (TextView) findViewById(R.id.terms);
		regBase1 = (EditText) findViewById(R.id.changeBase1);
		regBase2 = (EditText) findViewById(R.id.changeBase2);
		regBase3 = (EditText) findViewById(R.id.changeBase3);
		
		registerEmail.setHintTextColor(getResources().getColor(R.color.login_label_color));
		registerName.setHintTextColor(getResources().getColor(R.color.login_label_color));
		registerPin.setHintTextColor(getResources().getColor(R.color.login_label_color));
		chkBoxTerms.setHintTextColor(getResources().getColor(R.color.login_label_color));

		regBase1.setEnabled(false);
		regBase1.setClickable(false);
		regBase1.setFocusable(false);
		regBase2.setEnabled(false);
		regBase2.setClickable(false);
		regBase2.setFocusable(false);
		regBase3.setEnabled(false);
		regBase3.setClickable(false);
		regBase3.setFocusable(false);

		db = new DatabaseHandler(this, null);

		register_date = getCurrentDate();	
		
		chkBoxTerms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
                terms = chkBoxTerms.isChecked();
			}
		});
		
		txtTerms.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showAlertDialog();
			}
		});
		
		btnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				email = registerEmail.getText().toString();
				pin = registerPin.getText().toString();

				// Validating user input
				if (isValidEmail(email) && isValidName(name) && terms && isValidPin(pin)) {
					if (isValidName(name))
						regBase1.setBackgroundResource(R.drawable.edittext_login_box_base);
					if (isValidEmail(email))
						regBase2.setBackgroundResource(R.drawable.edittext_login_box_base);
					if (isValidPin(pin))
						regBase3.setBackgroundResource(R.drawable.edittext_login_box_base);
					/**
					 * register new user
					 */									
					db.addUser(new User(name, email, pin));
					Intent i = new Intent(getApplicationContext(), RegisterActivityStep2.class);
					startActivity(i);
					db.close();
					finish();

				} else {
					if (!terms) {
						Toast.makeText(RegisterActivity.this, "You have to accept the Terms", Toast.LENGTH_LONG).show();
					} else {
						if (!isValidName(name) || registerName == null) {
							regBase1.setBackgroundResource(R.drawable.error);
							Toast.makeText(RegisterActivity.this, "Invalid name", Toast.LENGTH_LONG).show();
						} else if (!isValidEmail(email) || registerEmail == null) {
							regBase2.setBackgroundResource(R.drawable.error);
							Toast.makeText(RegisterActivity.this, "Invalid email", Toast.LENGTH_LONG).show();
						} else {// ((!isValidPin(pin) || registerPin == null)) {
							regBase3.setBackgroundResource(R.drawable.error);
							Toast.makeText(RegisterActivity.this, "invalid Pin", Toast.LENGTH_LONG).show();
						}
					}
				}
			}// end onclick

		});// end onclicklistener
	}// end oncreate
	
	/**
	 * Show Terms Dialog.
	 */
	private void showAlertDialog() {
		FragmentManager fm = getSupportFragmentManager();
		MyDialog alertDialog = new MyDialog();
		alertDialog.show(fm, "fragment_alert");
	}
	
	/**
	 * Check for internet connection.
	 *
	 * @return the boolean
	 */
	private Boolean checkConnection() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		// For 3G check
		boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		// For WiFi Check
		boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        return is3g || isWifi;
    }


	/**
	 * Gets the current date.
	 *
	 * @return current date
	 */
	private String getCurrentDate() {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.set(year, month, day);

		sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		return register_date = sdf.format(c.getTime());
	}

	/**
	 * Email validity.
	 *
	 * @param target the target
	 * @return true, if is valid email
	 */
	public static boolean isValidEmail(String target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	}

	/**
	 * Name validity.
	 *
	 * @param target the target
	 * @return true, if is valid name
	 */
	public static boolean isValidName(String target) {
		if (target == null) {
			return false;
		} else {
            return Pattern.matches(TEXT_REGEX, target);
		}
	}

	/**
	 * Pin validity.
	 *
	 * @param target the target
	 * @return true, if is valid pin
	 */
	public static boolean isValidPin(String target) {
		if (target == null || !isDigit(target)) {
			return false;
		} else {
            return target.length() >= 5;
		}
	}

    private static boolean isDigit(String target) {
        return Pattern.matches(DIGIT_REGEX, target);
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
			Rect displayRectangle = new Rect();
			Window window = getActivity().getWindow();
			window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

			LayoutInflater inflater = getActivity().getLayoutInflater();
			final Dialog alertDialogBuilder = new Dialog(getActivity(), R.style.DialogTheme);

			customView = inflater.inflate(R.layout.terms_dialog, null);

			customView.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
			customView.setMinimumHeight((int) (displayRectangle.height() * 0.9f));
			
			TextView btnYes = (TextView) customView.findViewById(R.id.btnDialogYes);	
			TextView txtTerms = (TextView) customView.findViewById(R.id.txtTemrs);	
			txtTerms.setMovementMethod(new ScrollingMovementMethod());
			alertDialogBuilder.setContentView(customView);

			btnYes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					chkBoxTerms.setChecked(true);
					terms = true;
					alertDialogBuilder.cancel();
				}
			});
			
			return alertDialogBuilder;
		}
	}
}// end class
