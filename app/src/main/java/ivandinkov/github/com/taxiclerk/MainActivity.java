package ivandinkov.github.com.taxiclerk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.google.firebase.auth.FirebaseAuth;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
				implements NavigationView.OnNavigationItemSelectedListener,
				HomeFragment.OnFragmentInteractionListener,
				DayFragment.OnFragmentInteractionListener,
				MonthFragment.OnFragmentInteractionListener,
				YearFragment.OnFragmentInteractionListener,
				IncomeFragment.OnFragmentInteractionListener,
				ExpenseFragment.OnFragmentInteractionListener,
				ReportFragment.OnFragmentInteractionListener,
				SettingsFragment.OnFragmentInteractionListener,
				NewIncomeFragment.OnFragmentInteractionListener,
				NewExpenseFragment.OnFragmentInteractionListener,
				JobProviderFragment.OnFragmentInteractionListener,
				ExpenseAddFragment.OnFragmentInteractionListener,
				TrainFragment.OnFragmentInteractionListener


{
	private static final String TAG = "TC";
	private FirebaseAuth firebaseAuth;
	private String[] navMenuTitles;
	public static final String tc_prefs = "shift_state";
	private SharedPreferences sharedPreferences;
	private SwitchCompat timeSwitch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Stetho.initializeWithDefaults(this);
		
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		firebaseAuth = FirebaseAuth.getInstance();
		
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
						this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.setDrawerListener(toggle);
		toggle.syncState();
		
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
		
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(1);
			
		}
		
		// load nav menu titles
		navMenuTitles = getResources().getStringArray(R.array.nav_bar_titles);
		if (readShiftState().compareTo("on") == 0) {
			setTitle(navMenuTitles[1]);
		} else {
			setTitle(navMenuTitles[2]);
		}
	}
	
	private String readShiftState() {
		sharedPreferences = getSharedPreferences(tc_prefs, this.MODE_PRIVATE);
		return sharedPreferences.getString("shift", "");
	}
	
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		switch (position) {
			case 1:
				fragment = new HomeFragment();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				ft.replace(R.id.main_fragment_container, fragment, "home").commit();
				break;
			case 2:
				// Show all Jobs
				fragment = new IncomeFragment();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				ft.replace(R.id.main_fragment_container, fragment, "income").commit();
				break;
			case 3:
				// Show All expenses
				fragment = new ExpenseFragment();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				ft.replace(R.id.main_fragment_container, fragment, "expense").commit();
				break;
			case 4:
				// Show Reports
				fragment = new ReportFragment();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				ft.replace(R.id.main_fragment_container, fragment, "reports").commit();
				break;
			case 5:
				// Show Settings
				fragment = new SettingsFragment();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				ft.replace(R.id.main_fragment_container, fragment, "settings").commit();
				break;
			case 6:
				// Show train fragment
				fragment = new TrainFragment();
				ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				ft.replace(R.id.main_fragment_container, fragment, "settings").commit();
				break;
			case 7:
				// Send feedback
				sendEmailFeedback();
				break;
			case 8:
				// Sign Out App
				finish();
				firebaseAuth.signOut();
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				
				break;
			
			default:
				break;
		}
	}
	
	private void sendEmailFeedback() {
		if (!checkConnection()) {
			Toast.makeText(this, "Please check your connection!", Toast.LENGTH_LONG).show();
		} else {
			Intent chooser;
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ivdinkov@gmail.com"});
			intent.putExtra(Intent.EXTRA_SUBJECT, "Taxi Clerk feedback");
			intent.putExtra(Intent.EXTRA_TEXT, "");
			intent.setType("message/rfc822");
			
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				startActivity(intent);
			} else {
				chooser = Intent.createChooser(intent, "Send Email");
				startActivity(chooser);
			}
		}
	}
	
	private Boolean checkConnection() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
		if (activeNetwork != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			// Disable Android back button
			displayView(1);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		// initialize switch
		timeSwitch = (SwitchCompat) menu.findItem(R.id.switchID).getActionView().findViewById(R.id.switchCompat);
		if (readShiftState().compareTo("on") == 0) {
			timeSwitch.setChecked(true);
		} else {
			timeSwitch.setChecked(false);
		}
		
		timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (timeSwitch.isChecked()) {
					// change title
					setTitle(navMenuTitles[1]);
					// save to shared prefs
					savePrefs("shift", "on");
					// start new shift and update the DB
					startNewShift(generateNewShiftId(), getCurrentTime());
				} else {
					// change title
					setTitle(navMenuTitles[2]);
					// save to shared prefs
					savePrefs("shift", "off");
					// end shift and update end shift time  in the DB
					endCurrentShift(readShiftId(), getCurrentTime());
				}
			}
		});
		return true;
	}
	
	private void endCurrentShift(String shiftId, String currentTime) {
		// end current shift and update the DB
		DB db = new DB(this, null);
		db.updateShift(shiftId, currentTime);
		// update shiftId in sharedPrefs
		savePrefs("shiftId", "0");
		db.close();
	}
	
	private void startNewShift(String shiftId, String currentTime) {
		// save the new shift in DB
		DB db = new DB(this, null);
		if (db.startNewShift(shiftId, currentTime)) {
			// new shift create success => save shiftId to sharedPrefs
			savePrefs("shiftId", shiftId);
		} else {
			Log.d(TAG, "Was unable to save the new shift");
		}
		db.close();
	}
	
	private String getCurrentTime() {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		c.set(year, month, day);
		
		SimpleDateFormat sdfDB = new SimpleDateFormat("ddMMyyyyHHmm", Locale.ENGLISH);
		
		return sdfDB.format(c.getTime()).toString();
	}
	
	private String generateNewShiftId() {
		final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123465798abcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		int len = 20;
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}
	
	private String readShiftId() {
		sharedPreferences = getSharedPreferences(tc_prefs, this.MODE_PRIVATE);
		return sharedPreferences.getString("shiftId", "");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.add_job) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			Fragment fragment = new NewIncomeFragment();
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
			ft.replace(R.id.main_fragment_container, fragment, "new fare").commit();
			return true;
		} else if (id == R.id.add_expense) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			Fragment fragment = new NewExpenseFragment();
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
			ft.replace(R.id.main_fragment_container, fragment, "new expense").commit();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		
		if (id == R.id.nav_home) {
			displayView(1);
		} else if (id == R.id.nav_jobs) {
			displayView(2);
		} else if (id == R.id.nav_expenses) {
			displayView(3);
		} else if (id == R.id.nav_reports) {
			displayView(4);
		} else if (id == R.id.nav_settings) {
			displayView(5);
		} else if (id == R.id.nav_trains) {
			displayView(6);
		} else if (id == R.id.nav_feedback) {
			displayView(7);
		} else if (id == R.id.nav_logout) {
			displayView(8);
		}
		
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		
		return true;
	}
	
	@Override
	public void onFragmentInteraction(Uri uri) {
		
	}
	
	@Override
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(title);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		// save shift status
		savePrefs("shift", readShiftState());
		savePrefs("shiftId", readShiftId());
	}
	
	
	private void savePrefs(String key, String value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.apply();
	}
}
