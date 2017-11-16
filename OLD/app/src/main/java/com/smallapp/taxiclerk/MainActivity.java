/*
 * Copyright (c) 2014. Small-app.com
 */

package com.smallapp.taxiclerk;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.smallapp.taxiclerk.adapter.NavDrawerListAdapter;
import com.smallapp.taxiclerk.model.NavDrawerItem;

// TODO: Auto-generated Javadoc
/*
 * Extending  ActionBarActivity instead of ActionBar(API>=11) for older version support 
 */
/**
 * The Class MainActivity.
 */
public class MainActivity extends ActionBarActivity {

	/** The m drawer layout. */
	private DrawerLayout mDrawerLayout;
	
	/** The m drawer list. */
	private ListView mDrawerList;
	
	/** The m drawer toggle. */
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	/** The m drawer title. */
	private CharSequence mDrawerTitle;

	// used to store app title
	/** The m title. */
	private CharSequence mTitle;

	// slide menu items
	/** The nav menu titles. */
	private String[] navMenuTitles;
	
	/** The nav menu icons. */
	private TypedArray navMenuIcons;

	/** The nav drawer items. */
	private ArrayList<NavDrawerItem> navDrawerItems;
	
	/** The adapter. */
	private NavDrawerListAdapter adapter;
	
	/** The selected list link. */
	public int selectedListLink = 7;
	
	/** The frag income record. */
	private IncomeFragment fragIncomeRecord;
	
	/** The frag exp record. */
	private ExpensesFragment fragExpRecord;
	
	/** The frag hr record. */
	private HoursFragment fragHrRecord;
	
	/** The drawer open. */
	private Boolean drawerOpen = false;
	
	/** The Constant FRAGMENT_ID. */
	static final String FRAGMENT_ID = "fragmentID";
	
	/** The Constant TAXICLERK_NAME. */
	private static final String TAXICLERK_NAME = null;
	
	/** The frag id. */
	int fragID;
	
	/** The m silent mode. */
	private boolean mSilentMode;

	//private InterstitialAd interstitial;

	// private ImageButton menuHandle;

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		// menuHandle = (ImageButton) findViewById(R.id.menuHandle);
		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<>();

		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],navMenuIcons.getResourceId(0, -1)));
		// Income
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
		// Expenses
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
		// Timing
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
		// Reports
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
		// Settings
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
		// Exit
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);

		/*
		 * Using getSupportActionBar() instead getActionBar()(API>=11) for older
		 * version support
		 */

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			@Override
			public void onDrawerClosed(View view) {
				getSupportActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				supportInvalidateOptionsMenu();
				drawerOpen = false;
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getSupportActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				supportInvalidateOptionsMenu();
				drawerOpen = true;
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}



    /**
     * This class makes the ad request and loads the ad.
     */

    public static class AdFragment extends Fragment {

        private AdView mAdView;

        public AdFragment() {
        }

        @Override
        public void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);

            // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
            // values/strings.xml.
            mAdView = (AdView) getView().findViewById(R.id.adView);

            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            //AdRequest.DEVICE_ID_EMULATOR
            AdRequest adRequest = new AdRequest.Builder()
                    //.addTestDevice("E6528E6BF313C0F2DF0E560047CCE11D")
                    .build();

            // Start loading the ad in the background.
           mAdView.loadAd(adRequest);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_ad, container, false);
        }

        /** Called when leaving the activity */
        @Override
        public void onPause() {
            if (mAdView != null) {
                mAdView.pause();
            }
            super.onPause();
        }

        /** Called when returning to the activity */
        @Override
        public void onResume() {
            super.onResume();
            if (mAdView != null) {
                mAdView.resume();
            }
        }

        /** Called before the activity is destroyed */
        @Override
        public void onDestroy() {
            if (mAdView != null) {
                mAdView.destroy();
            }
            super.onDestroy();
        }

    }

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Always call the superclass so it can restore the view hierarchy
		super.onRestoreInstanceState(savedInstanceState);

		fragID = savedInstanceState.getInt(FRAGMENT_ID);
		displayView(fragID);

	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save the user's current state

		savedInstanceState.putInt(FRAGMENT_ID, fragID);

		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}


	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		
		/* (non-Javadoc)
		 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// display view for selected nav drawer item
			selectedListLink = position;
			displayView(position);
		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		int s = item.getItemId();
		// if(s == R.id.action_settings) return true;
		// return super.onOptionsItemSelected(item);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (s == R.id.add_job) {
			ft.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom);
			fragIncomeRecord = new IncomeFragment();
			ft.replace(R.id.frame_container, fragIncomeRecord).commit();
			mDrawerList.setItemChecked(1, true);
			mDrawerList.setSelection(1);
			setTitle(navMenuTitles[1]);
			return true;
		} else if (s == R.id.add_expense) {
			ft.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom);
			fragExpRecord = new ExpensesFragment();
			ft.replace(R.id.frame_container, fragExpRecord).commit();
			mDrawerList.setItemChecked(2, true);
			mDrawerList.setSelection(2);
			setTitle(navMenuTitles[2]);
			return true;
		} else if (s == R.id.add_shift) {
			ft.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom);
			fragHrRecord = new HoursFragment();
			ft.replace(R.id.frame_container, fragHrRecord).commit();
			mDrawerList.setItemChecked(3, true);
			mDrawerList.setSelection(3);
			setTitle(navMenuTitles[3]);
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	/* (non-Javadoc)
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.add_job).setVisible(!drawerOpen);
		menu.findItem(R.id.add_expense).setVisible(!drawerOpen);
		menu.findItem(R.id.add_shift).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/*
	 * 
	 * Diplaying fragment view for selected nav drawer list item
	 */
	/**
	 * Display view.
	 *
	 * @param position the position
	 */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			ft.replace(R.id.frame_container, fragment, "home").commit();
			fragID = 0;
			break;
		case 1:
			fragment = new IncSwitchFragment();
			ft.replace(R.id.frame_container, fragment, "income").commit();
			fragID = 1;
			break;
		case 2:
			fragment = new ExpSwitchFragment();
			ft.replace(R.id.frame_container, fragment, "expense").commit();
			fragID = 2;
			break;
		case 3:
			fragment = new HoursSwitchFragment();
			ft.replace(R.id.frame_container, fragment, "hours").commit();
			fragID = 3;
			break;
		case 4:
			fragment = new ReportsFragment();
			ft.replace(R.id.frame_container, fragment, "reports").commit();
			fragID = 4;
			break;
		case 5:
			fragment = new SettingsFragment();
			ft.replace(R.id.frame_container, fragment, "settings").commit();
			fragID = 5;
			break;
		case 6:
			// Exit
			SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
			editor.putString("logged", "no");
			editor.apply();
			finish();
			break;

		default:
			break;
		}

		if (fragment != null) {
			// FragmentManager fragmentManager = getSupportFragmentManager();
			// fragmentManager.beginTransaction().replace(R.id.frame_container,
			// fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
			// save win title

		} else {

			finish();

		}
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#setTitle(java.lang.CharSequence)
	 */
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 *
	 * @param savedInstanceState the saved instance state
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onConfigurationChanged(android.content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		if (drawerOpen)
			mDrawerLayout.closeDrawers();
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top);
		HomeFragment fragment = new HomeFragment();
		ft.replace(R.id.frame_container, fragment).commit();
		setTitle(navMenuTitles[0]);
	}

	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
		editor.putString("logged", "yes");
		editor.apply();
	}

}
