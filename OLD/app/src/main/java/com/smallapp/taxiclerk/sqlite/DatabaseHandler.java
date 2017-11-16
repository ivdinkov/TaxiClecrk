/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

// TODO: Auto-generated Javadoc
/**
 * The Class DatabaseHandler.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables

	// Database Version
	/** The Constant DATABASE_VERSION. */
	private static final int DATABASE_VERSION = 1;
	// Database Name
	/** The Constant DATABASE_NAME. */
	private static final String DATABASE_NAME = "taxi_book_keeper";
	// Table names
	/** The Constant TABLE_USER. */
	private static final String TABLE_USER = "user";

	/** The Constant TABLE_TRIAL. */
	private static final String TABLE_TRIAL = "trial";

	/** The Constant TABLE_EXPENSE. */
	private static final String TABLE_EXPENSE = "expense";

	/** The Constant TABLE_INCOME. */
	private static final String TABLE_INCOME = "income";

	/** The Constant TABLE_HOURS. */
	private static final String TABLE_HOURS = "hours";

	/** The Constant TABLE_JOB_PROVIDER. */
	private static final String TABLE_JOB_PROVIDER = "job_provider";

	// User Activity
	/** The Constant KEY_TRIAL_ID. */
	private static final String KEY_TRIAL_ID = "id";

	/** The Constant KEY_TRIAL_IMEI. */
	private static final String KEY_TRIAL_IMEI = "imei";

	/** The Constant KEY_TRIAL_TIME. */
	private static final String KEY_TRIAL_TIME = "trial_time";

	/** The Constant KEY_TRIAL_LAST_LOGIN_TIME. */
	private static final String KEY_TRIAL_LAST_LOGIN_TIME = "last_login_time";

	/** The Constant KEY_TRIAL_SUCCESS. */
	private static final String KEY_TRIAL_SUCCESS = "trial_success";

	/** The Constant KEY_TRIAL_EXPIRED. */
	private static final String KEY_TRIAL_EXPIRED = "trial_expired";

	/** The Constant KEY_TRIAL_EMAIL. */
	private static final String KEY_TRIAL_EMAIL = "trial_email";

	// Contacts Table Columns names
	/** The Constant KEY_ID. */
	private static final String KEY_ID = "id";

	/** The Constant KEY_NAME. */
	private static final String KEY_NAME = "name";

	/** The Constant KEY_EMAIL. */
	private static final String KEY_EMAIL = "email";

	/** The Constant KEY_PIN. */
	private static final String KEY_PIN = "pin";

	/** The Constant KEY_LOGGED. */
	private static final String KEY_LOGGED = "logged";

	// Expense Table Column names
	/** The Constant KEY_ID_EXPENSE. */
	private static final String KEY_ID_EXPENSE = "expense_id";

	/** The Constant KEY_EXPENSE_NAME. */
	private static final String KEY_EXPENSE_NAME = "expense_name";

	/** The Constant KEY_AMOUNT. */
	private static final String KEY_AMOUNT = "amount";

	/** The Constant KEY_DATE. */
	private static final String KEY_DATE = "date";

	/** The Constant KEY_MILAGE. */
	private static final String KEY_MILAGE = "milage";

	/** The Constant KEY_PAYMENT_TYPE. */
	private static final String KEY_PAYMENT_TYPE = "payment_type";

	/** The Constant KEY_NOTES. */
	private static final String KEY_NOTES = "notes";

	/** The Constant KEY_ACTIVE. */
	private static final String KEY_ACTIVE = "active";

	// Income Table Column names
	/** The Constant KEY_ID_INCOME. */
	private static final String KEY_ID_INCOME = "income_id";

	/** The Constant KEY_INC_DATE. */
	private static final String KEY_INC_DATE = "income_date";

	/** The Constant KEY_INC_TYPE. */
	private static final String KEY_INC_TYPE = "income_type";

	/** The Constant KEY_INC_AMOUNT. */
	private static final String KEY_INC_AMOUNT = "income_amount";

	/** The Constant KEY_INC_NOTES. */
	private static final String KEY_INC_NOTES = "income_notes";

	/** The Constant KEY_INC_ACTIVE. */
	private static final String KEY_INC_ACTIVE = "income_active";

	/** The Constant KEY_INC_PROVIDER. */
	private static final String KEY_INC_PROVIDER = "income_provider";

	// Hours table
	/** The Constant KEY_ID_HOURS. */
	private static final String KEY_ID_HOURS = "hours_id";

	/** The Constant KEY_HOURS. */
	private static final String KEY_HOURS = "hours";

	/** The Constant KEY_HOURS_DATE. */
	private static final String KEY_HOURS_DATE = "hours_date";

	/** The Constant KEY_HOURS_ACTIVE. */
	private static final String KEY_HOURS_ACTIVE = "hours_active";

	// Provider table
	/** The Constant KEY_PR_ID. */
	private static final String KEY_PR_ID = "provider_id";

	/** The Constant KEY_PR_NAME. */
	private static final String KEY_PR_NAME = "provider_name";

	/** The Constant KEY_PR_ACTIVE. */
	private static final String KEY_PR_ACTIVE = "provider_active";

	/** The Constant CREATE_USER_TABLE. */
	private static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PIN
			+ " TEXT," + KEY_LOGGED + " TEXT)";

	/** The Constant CREATE_EXPENSE_TABLE. */
	private static final String CREATE_EXPENSE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_EXPENSE + "(" + KEY_ID_EXPENSE + " INTEGER PRIMARY KEY," + KEY_EXPENSE_NAME + " TEXT," + KEY_AMOUNT
			+ " TEXT," + KEY_DATE + " TEXT," + KEY_MILAGE + " TEXT, " + KEY_PAYMENT_TYPE + " TEXT," + KEY_NOTES + " TEXT," + KEY_ACTIVE + " TEXT)";

	/** The Constant CREATE_INCOME_TABLE. */
	private static final String CREATE_INCOME_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INCOME + "(" + KEY_ID_INCOME + " INTEGER PRIMARY KEY," + KEY_INC_DATE + " TEXT," + KEY_INC_TYPE + " TEXT,"
			+ KEY_INC_AMOUNT + " TEXT," + KEY_INC_NOTES + " TEXT," + KEY_INC_ACTIVE + " TEXT," + KEY_INC_PROVIDER + " TEXT)";

	/** The Constant CREATE_HOURS_TABLE. */
	private static final String CREATE_HOURS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_HOURS + "(" + KEY_ID_HOURS + " INTEGER PRIMARY KEY," + KEY_HOURS + " TEXT," + KEY_HOURS_ACTIVE + " TEXT,"
			+ KEY_HOURS_DATE + " TEXT)";

	/** The Constant CREATE_JOB_PROVIDER_TABLE. */
	private static final String CREATE_JOB_PROVIDER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_JOB_PROVIDER + "(" + KEY_PR_ID + " INTEGER PRIMARY KEY," + KEY_PR_NAME + " TEXT," + KEY_PR_ACTIVE
			+ " TEXT)";

	/** The Constant CREATE_TRIAL_TABLE. */
	private static final String CREATE_TRIAL_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TRIAL + "(" + KEY_TRIAL_ID + " INTEGER PRIMARY KEY," + KEY_TRIAL_IMEI + " TEXT," + KEY_TRIAL_TIME + " TEXT,"
			+ KEY_TRIAL_LAST_LOGIN_TIME + " TEXT," + KEY_TRIAL_EXPIRED + " TEXT," + KEY_TRIAL_SUCCESS + " TEXT," + KEY_TRIAL_EMAIL + " TEXT)";

	/**
	 * Instantiates a new database handler.
	 *
	 * @param context
	 *            the context
     *
	 */
	public DatabaseHandler(Context context, CursorFactory factory) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

	// Expense Sql
	/**
	 * Do sql.
	 *
	 * @param sql
	 *            the sql
	 * @return the string
	 */
	public String doSql(String sql) {
		String result = null;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				result = String.valueOf(cursor.getDouble(0));
			} while (cursor.moveToNext());
		}
		db.close();
		cursor.close();
		return result;
	}

	// onCreate
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			db.execSQL(CREATE_USER_TABLE);
			db.execSQL(CREATE_TRIAL_TABLE);
			db.execSQL(CREATE_EXPENSE_TABLE);
			db.execSQL(CREATE_INCOME_TABLE);
			db.execSQL(CREATE_HOURS_TABLE);
			db.execSQL(CREATE_JOB_PROVIDER_TABLE);
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}

	}

	// onOpen App
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onOpen(android.database.sqlite
	 * .SQLiteDatabase)
	 */
	@Override
	public void onOpen(SQLiteDatabase db) {
		try {
			db.execSQL(CREATE_USER_TABLE);
			db.execSQL(CREATE_TRIAL_TABLE);
			db.execSQL(CREATE_EXPENSE_TABLE);
			db.execSQL(CREATE_INCOME_TABLE);
			db.execSQL(CREATE_HOURS_TABLE);
			db.execSQL(CREATE_JOB_PROVIDER_TABLE);
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}
	}

	// Upgrading database
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite
	 * .SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed

		try {

			db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_TRIAL);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOURS);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOB_PROVIDER);
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}

		// Create tables again
		onCreate(db);
	}

	// Login in DB
	/**
	 * Login.
	 *
	 * @param pin
	 *            the pin
	 * @return the int
	 */
	public int Login(String pin) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] selectionArgs = new String[] { pin };
		try {
			int i = 0;
			Cursor c = null;
			c = db.rawQuery("select * from " + TABLE_USER + " where pin=?", selectionArgs);
			c.moveToFirst();
			i = c.getCount();
			c.close();
			return i;
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}
		db.close();
		return 0;
	}

	// Adding new contact
	/**
	 * Adds the user.
	 *
	 * @param user
	 *            the user
	 */
	public void addUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName()); // Contact Name
		values.put(KEY_EMAIL, user.getEmail()); // Contact Name
		values.put(KEY_PIN, user.getPin()); // Contact Phone

		// Inserting Row
		db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection
	}

	// Updating single contact
	/**
	 * Update user.
	 *
	 * @param user
	 *            the user
	 * @return the int
	 */
	public int updateUser(User user) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, user.getName());
		values.put(KEY_EMAIL, user.getEmail());
		values.put(KEY_PIN, user.getPin());

		// updating row
		return db.update(TABLE_USER, values, KEY_ID + " = ?", new String[] { String.valueOf(user.getID()) });
		// db.close();
	}

	// Getting single contact
	/**
	 * Gets the user.
	 *
	 * @param id
	 *            the id
	 * @return the user
	 */
	public User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_USER, new String[] { KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PIN }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		User user = new User(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
		db.close();
		// return user
		return user;

	}

	// Getting contacts Count
	/**
	 * Gets the user count.
	 *
	 * @return the user count
	 */
	public int getUserCount() {
		String countQuery = "SELECT  * FROM " + TABLE_USER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		db.close();
		// return count
		return cursor.getCount();
	}

	// Getting All Contacts
	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	public List<User> getAllUsers() {
		List<User> contactList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				User contact = new User();
				contact.setID(Integer.parseInt(cursor.getString(0)));
				contact.setName(cursor.getString(1));
				contact.setEmail(cursor.getString(2));
				contact.setPin(cursor.getString(3));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.close();
		// return contact list
		return contactList;
	}

	/*
	 * 
	 * 
	 * I N C O M E
	 */
	// Adding new income
	/**
	 * Adds the income.
	 *
	 * @param income
	 *            the income
	 */
	public void addIncome(Income income) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.clear();
		values.put(KEY_INC_DATE, income.getDate());
		values.put(KEY_INC_TYPE, income.getIncType());
		values.put(KEY_INC_AMOUNT, income.getAmount());
		values.put(KEY_INC_NOTES, income.getNote());
		values.put(KEY_INC_ACTIVE, income.getActive());
		values.put(KEY_INC_PROVIDER, income.getProvider());

		// Inserting Row
		try {
			db.insert(TABLE_INCOME, null, values);
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}

		db.close(); // Closing database connection
	}

	// Updating single income entry
	/**
	 * Update income.
	 *
	 * @param id
	 *            the id
	 * @param income_date
	 *            the income_date
	 * @param income_type
	 *            the income_type
	 * @param income_amount
	 *            the income_amount
	 * @param income_note
	 *            the income_note
	 * @param income_active
	 *            the income_active
	 * @param income_provider
	 *            the income_provider
	 */
	public void updateIncome(int id, String income_date, String income_type, String income_amount, String income_note, String income_active, String income_provider) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_INC_DATE, income_date);
		values.put(KEY_INC_TYPE, income_type);
		values.put(KEY_INC_AMOUNT, income_amount);

		values.put(KEY_INC_NOTES, income_note);
		values.put(KEY_INC_ACTIVE, income_active);
		values.put(KEY_INC_PROVIDER, income_provider);

		db.update(TABLE_INCOME, values, KEY_ID_INCOME + " = ?", new String[] { String.valueOf(id) });
		db.close();
	}

	// Getting All Incomes
	/**
	 * Gets the all income.
	 *
	 * @return the all income
	 */
	public List<Income> getAllIncome() {
		List<Income> incomeList = new ArrayList<Income>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_ID_INCOME + " ,SUBSTR(income_date,9,2)||'-'||SUBSTR(income_date,6,2)||'-'||SUBSTR(income_date,1,4) 'date'," + KEY_INC_TYPE + "," + KEY_INC_AMOUNT + ","
				+ KEY_INC_NOTES + "," + KEY_INC_ACTIVE + "," + KEY_INC_PROVIDER + " FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes' " + " ORDER BY " + KEY_ID_INCOME + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Income inc = new Income();
				inc.setID(Integer.parseInt(cursor.getString(0)));
				inc.setDate(cursor.getString(1));
				inc.setIncType(cursor.getString(2));
				inc.setAmount(cursor.getString(3));
				inc.setNote(cursor.getString(4));
				inc.setActive(cursor.getString(5));
				inc.setProvider(cursor.getString(6));
				// Adding contact to list
				incomeList.add(inc);
			} while (cursor.moveToNext());
		}
		db.close();
		// return income list
		return incomeList;
	}

	// Get All IncomeTotal
	/**
	 * Gets the all income total.
	 *
	 * @param sqlCashToday
	 *            the sql cash today
	 * @return the all income total
	 */
	public String getAllIncomeTotal(String sqlCashToday) {
		String cashTotal = null;
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(sqlCashToday, null);
		if (cursor.moveToFirst()) {
			do {
				cashTotal = String.valueOf(cursor.getDouble(0));
			} while (cursor.moveToNext());
		}
		db.close();
		cursor.close();
		return cashTotal;
	}

	/**
	 * Get Income WeekChart
	 * 
	 */
	public String getDailyIncome(String date, String type) {

		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) AS inc FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes'  AND " + KEY_INC_DATE + " LIKE '" + date
				+ "'  AND " + KEY_INC_TYPE + " LIKE '" + type + "'";

		Cursor cursor = db.rawQuery(query, null);
		int i = 0;
		String weekDays = null;
		if (cursor.moveToFirst()) {
			do {
				weekDays = String.valueOf(cursor.getDouble(0));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return weekDays;
	}

	public String getYearlyIncome(String date, String type) {

		SQLiteDatabase db = this.getReadableDatabase();

		String query = "SELECT SUM(CAST(" + KEY_INC_AMOUNT + " as DECIMAL(9,2))) AS inc FROM " + TABLE_INCOME + " WHERE " + KEY_INC_ACTIVE + " LIKE 'yes'  AND " + KEY_INC_DATE + " LIKE '" + date
				+ "%'  AND " + KEY_INC_TYPE + " LIKE '" + type + "'";

		Cursor cursor = db.rawQuery(query, null);
		int i = 0;
		String months = null;
		if (cursor.moveToFirst()) {
			do {
				months = String.valueOf(cursor.getDouble(0));
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return months;
	}

	/**
	 * Gets the single income record.
	 *
	 * @param id
	 *            the id
	 * @return the single income record
	 */
	public Income getSingleIncomeRecord(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_INCOME, new String[] { "SUBSTR(income_date,9,2)||'-'||SUBSTR(income_date,6,2)||'-'||SUBSTR(income_date,1,4) 'date'", KEY_INC_TYPE, KEY_INC_AMOUNT,
				KEY_INC_NOTES, KEY_INC_ACTIVE, KEY_INC_PROVIDER }, KEY_ID_INCOME + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Income income = new Income(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
		// return single income
		db.close();
		return income;
	}

	// Delete all income records f provider
	/**
	 * Delete income of provider.
	 *
	 * @param provName
	 *            the prov name
	 */
	public void deleteIncomeOfProvider(String provName) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_INC_ACTIVE, "no");
		db.update(TABLE_INCOME, values, KEY_INC_PROVIDER + " = ?", new String[] { String.valueOf(provName) });
		db.close();
	}

	// Deleting single income entry
	/**
	 * Delete income.
	 *
	 * @param id
	 *            the id
	 */
	public void deleteIncome(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_INC_ACTIVE, "no");
		db.update(TABLE_INCOME, values, KEY_ID_INCOME + " = ?", new String[] { String.valueOf(id) });
		db.close();
	}

	/*
	 * 
	 * 
	 * E X P E N S E
	 */
	// Adding new expense
	/**
	 * Adds the expense.
	 *
	 * @param expense
	 *            the expense
	 */
	public void addExpense(Expense expense) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.clear();
		values.put(KEY_EXPENSE_NAME, expense.getExpenseName());
		values.put(KEY_AMOUNT, expense.getAmount());
		values.put(KEY_DATE, expense.getDate());
		values.put(KEY_MILAGE, expense.getMilage());
		values.put(KEY_PAYMENT_TYPE, expense.getPaymentType());
		values.put(KEY_NOTES, expense.getNotes());
		values.put(KEY_ACTIVE, expense.getActive());
		// Inserting Row
		try {
			db.insert(TABLE_EXPENSE, null, values);
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}

		db.close(); // Closing database connection
	}

	// Getting All Expenses
	/**
	 * Gets the all expense.
	 *
	 * @return the all expense
	 */
	public List<Expense> getAllExpense() {
		List<Expense> expenseList = new ArrayList<Expense>();

		// Select All Query
		String selectQuery = "SELECT " + KEY_ID_EXPENSE + "," + KEY_EXPENSE_NAME + "," + KEY_AMOUNT + "," + " SUBSTR(date,9,2)||'-'||SUBSTR(date,6,2)||'-'||SUBSTR(date,1,4) 'date'," + KEY_MILAGE
				+ "," + KEY_PAYMENT_TYPE + "," + KEY_NOTES + "," + KEY_ACTIVE + " FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' " + " ORDER BY " + KEY_ID_EXPENSE + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Expense ex = new Expense();
				ex.setID(Integer.parseInt(cursor.getString(0)));
				ex.setExpenseName(cursor.getString(1));
				ex.setAmount(cursor.getString(2));
				ex.setDate(cursor.getString(3));
				ex.setMilage(cursor.getString(4));
				ex.setPaymentType(cursor.getString(5));
				ex.setNotes(cursor.getString(6));
				ex.setActive(cursor.getString(7));
				// Adding contact to list
				expenseList.add(ex);
			} while (cursor.moveToNext());
		}
		db.close();
		// return expense list
		return expenseList;
	}

	/**
	 * Gets the all misc expense.
	 *
	 * @return the all expense
	 */
	public List<Expense> getAllExpenseForType(String startDate,String endDate,String type) {
		List<Expense> expenseList = new ArrayList<Expense>();

		// Select All Query
		String selectQuery = "SELECT " + KEY_ID_EXPENSE + "," + KEY_EXPENSE_NAME + "," + KEY_AMOUNT + "," + " SUBSTR(date,9,2)||'-'||SUBSTR(date,6,2)||'-'||SUBSTR(date,1,4) 'date'," + KEY_MILAGE
				+ "," + KEY_PAYMENT_TYPE + "," + KEY_NOTES + "," + KEY_ACTIVE + " FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_EXPENSE_NAME + " LIKE '"+type+"' AND "
				+ KEY_DATE + " >= '" + startDate + "' AND " + KEY_DATE + " <= '" + endDate + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Expense ex = new Expense();
				ex.setID(Integer.parseInt(cursor.getString(0)));
				ex.setExpenseName(cursor.getString(1));
				ex.setAmount(cursor.getString(2));
				ex.setDate(cursor.getString(3));
				ex.setMilage(cursor.getString(4));
				ex.setPaymentType(cursor.getString(5));
				ex.setNotes(cursor.getString(6));
				ex.setActive(cursor.getString(7));
				// Adding contact to list
				expenseList.add(ex);
			} while (cursor.moveToNext());
		}
		db.close();
		// return expense list
		return expenseList;
	}

	public String getDailyExpense(String date) {
		String result = null;
		String sql = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " LIKE '" + date + "'";

		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				result = String.valueOf(cursor.getDouble(0));
			} while (cursor.moveToNext());
		}
		db.close();
		cursor.close();

		return result;

	}

	public String getEarlyExpense(String date) {
		String result = null;
		String sql = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM " + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " LIKE '" + date + "%'";
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(sql, null);
		if (cursor.moveToFirst()) {
			do {
				result = String.valueOf(cursor.getDouble(0));
			} while (cursor.moveToNext());
		}
		db.close();
		cursor.close();

		return result;
	}

    /**
     *
     * M I L E A G E
     *
     */
	public int getMaxColumnData( String startDate,String endDate) {

		SQLiteDatabase db = this.getReadableDatabase();
	    final SQLiteStatement stmt = db.compileStatement("SELECT MAX("+KEY_MILAGE+") FROM "+TABLE_EXPENSE + " WHERE "+ KEY_MILAGE +" <> '' AND "+ KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '"
                + startDate + "' AND " + KEY_DATE + " <= '" + endDate + "'");

	    return (int) stmt.simpleQueryForLong();
	}
	
	public int getMinColumnData( String startDate,String endDate) {

		SQLiteDatabase db = this.getReadableDatabase();
	    final SQLiteStatement stmt = db.compileStatement("SELECT MIN("+KEY_MILAGE+") FROM "+TABLE_EXPENSE + " WHERE "+ KEY_MILAGE +" <> '' AND "+ KEY_ACTIVE + " LIKE 'yes' AND " + KEY_DATE + " >= '"
                + startDate + "' AND " + KEY_DATE + " <= '" + endDate + "'");
	    return (int) stmt.simpleQueryForLong();
	}

	
	// Getting single income
	/**
	 * Gets the single expense record.
	 *
	 * @param id
	 *            the id
	 * @return the single expense record
	 */
	public Expense getSingleExpenseRecord(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_EXPENSE, new String[] { KEY_EXPENSE_NAME, KEY_AMOUNT, "SUBSTR(date,9,2)||'-'||SUBSTR(date,6,2)||'-'||SUBSTR(date,1,4) 'date'", KEY_MILAGE, KEY_PAYMENT_TYPE,
				KEY_NOTES, KEY_ACTIVE }, KEY_ID_EXPENSE + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Expense expense = new Expense(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
		// return single income
		db.close();
		return expense;
	}

	// Deleting single income entry
	/**
	 * Delete expense.
	 *
	 * @param id
	 *            the id
	 */
	public void deleteExpense(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_ACTIVE, "no");
		db.update(TABLE_EXPENSE, values, KEY_ID_EXPENSE + " = ?", new String[] { String.valueOf(id) });
		db.close();
	}

	// Update Expense
	/**
	 * Update expense.
	 *
	 * @param recordID
	 *            the record id
	 * @param expenseTypeToBeSaved
	 *            the expense type to be saved
	 * @param expenseAmountToBeSave
	 *            the expense amount to be save
	 * @param dateToBeSaved
	 *            the date to be saved
	 * @param milageToBeSaved
	 *            the milage to be saved
	 * @param paymentToBeSaved
	 *            the payment to be saved
	 * @param notesToBeSaved
	 *            the notes to be saved
	 * @param active
	 *            the active
	 */
	public void updateExpense(int recordID, String expenseTypeToBeSaved, String expenseAmountToBeSave, String dateToBeSaved, String milageToBeSaved, String paymentToBeSaved, String notesToBeSaved,
			String active) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_EXPENSE_NAME, expenseTypeToBeSaved);
		values.put(KEY_AMOUNT, expenseAmountToBeSave);
		values.put(KEY_DATE, dateToBeSaved);
		values.put(KEY_MILAGE, milageToBeSaved);
		values.put(KEY_PAYMENT_TYPE, paymentToBeSaved);
		values.put(KEY_NOTES, notesToBeSaved);
		values.put(KEY_ACTIVE, active);

		db.update(TABLE_EXPENSE, values, KEY_ID_EXPENSE + " = ?", new String[] { String.valueOf(recordID) });
		db.close();
	}

	/*
	 * 
	 * 
	 * H O U R S
	 */
	// Get All Hours
	/**
	 * Gets the all hours.
	 *
	 * @return the all hours
	 */
	public List<Hours> getAllHours() {
		List<Hours> hoursList = new ArrayList<Hours>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_ID_HOURS + "," + KEY_HOURS + "," + KEY_HOURS_ACTIVE + ","
				+ "SUBSTR(hours_date,9,2)||'-'||SUBSTR(hours_date,6,2)||'-'||SUBSTR(hours_date,1,4) 'date' FROM " + TABLE_HOURS + " WHERE " + KEY_HOURS_ACTIVE + " LIKE 'yes' " + " ORDER BY "
				+ KEY_ID_HOURS + " DESC";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Hours inc = new Hours();
				inc.setID(Integer.parseInt(cursor.getString(0)));
				inc.setHours(cursor.getString(1));
				inc.setActive(cursor.getString(2));
				inc.setDate(cursor.getString(3));
				// Adding contact to list
				hoursList.add(inc);
			} while (cursor.moveToNext());
		}

		db.close();
		return hoursList;
	}

	// Delete Hours
	/**
	 * Delete hours.
	 *
	 * @param id
	 *            the id
	 */
	public void deleteHours(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_HOURS_ACTIVE, "no");
		db.update(TABLE_HOURS, values, KEY_ID_HOURS + " = ?", new String[] { String.valueOf(id) });
		db.close();
	}

	// Getting single hours
	/**
	 * Gets the single hours record.
	 *
	 * @param id
	 *            the id
	 * @return the single hours record
	 */
	public Hours getSingleHoursRecord(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_HOURS, new String[] { KEY_HOURS, "SUBSTR(hours_date,9,2)||'-'||SUBSTR(hours_date,6,2)||'-'||SUBSTR(hours_date,1,4) 'date'", KEY_HOURS_ACTIVE }, KEY_ID_HOURS
				+ "=?", new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Hours hours = new Hours(cursor.getString(0), cursor.getString(1), cursor.getString(2));
		// return single income
		db.close();
		return hours;
	}

	// Add Hours
	/**
	 * Adds the hours.
	 *
	 * @param hours
	 *            the hours
	 */
	public void addHours(Hours hours) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_HOURS, hours.getHours());
		values.put(KEY_HOURS_ACTIVE, hours.getActive());
		values.put(KEY_HOURS_DATE, hours.getDate());

		// Inserting Row
		db.insert(TABLE_HOURS, null, values);
		db.close(); // Closing database connection
	}

	// Update Hours
	/**
	 * Update hours.
	 *
	 * @param recordID
	 *            the record id
	 * @param hoursToBeSaved
	 *            the hours to be saved
	 * @param dateToBeSaved
	 *            the date to be saved
	 */
	public void updateHours(int recordID, String hoursToBeSaved, String dateToBeSaved) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_HOURS, hoursToBeSaved);
		values.put(KEY_HOURS_DATE, dateToBeSaved);

		db.update(TABLE_HOURS, values, KEY_ID_HOURS + " = ?", new String[] { String.valueOf(recordID) });
		db.close();
	}

	/*
	 * 
	 * 
	 * J O B S
	 */
	// All Year Jobs
	/**
	 * All jobs for year.
	 *
	 * @return the int
	 */
	public int allJobsForYear(String currentYear) {
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			int i = 0;
			Cursor c = null;
			c = db.rawQuery("select * from " + TABLE_INCOME + " where " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " LIKE '" + currentYear + "%'", null);
			c.moveToFirst();
			i = c.getCount();
			c.close();
			return i;
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}
		db.close();
		return 0;
	}

	// Get all jobs for single date
	/**
	 * All jobs for date.
	 *
	 * @param date
	 *            the date
	 * @return the int
	 */
	public int allJobsForDate(String date) {
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			int i = 0;
			Cursor c = null;
			c = db.rawQuery("select * from " + TABLE_INCOME + " where " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " LIKE '" + date + "'", null);
			c.moveToFirst();
			i = c.getCount();
			c.close();
			return i;
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}
		db.close();
		return 0;
	}

	// All jobs for date to date
	/**
	 * All jobs for period.
	 *
	 * @param startWeekDay
	 *            the start week day
	 * @param endWeekDay
	 *            the end week day
	 * @return the int
	 */
	public int allJobsForPeriod(String startWeekDay, String endWeekDay) {
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			int i = 0;
			Cursor c = null;
			c = db.rawQuery("select * from " + TABLE_INCOME + " where " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '" + startWeekDay + "' AND " + KEY_INC_DATE + " <= '" + endWeekDay
					+ "'", null);
			c.moveToFirst();
			i = c.getCount();
			c.close();
			return i;
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}
		db.close();
		return 0;
	}

	// All jobs from provider
	/**
	 * All jobs for period provider.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @param provider
	 *            the provider
	 * @return the int
	 */
	public int allJobsForPeriodProvider(String startDate, String endDate, String provider) {
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			int i = 0;
			Cursor c = null;
			c = db.rawQuery("select * from " + TABLE_INCOME + " where " + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '" + startDate + "' AND " + KEY_INC_DATE + " <= '" + endDate
					+ "' AND " + KEY_INC_PROVIDER + " LIKE '" + provider + "'", null);
			c.moveToFirst();
			i = c.getCount();
			c.close();
			return i;
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}
		db.close();
		return 0;
	}

	/*
	 * 
	 * 
	 * P R O V I D E R
	 */
	// Add provider
	/**
	 * Adds the provider.
	 *
	 * @param prov
	 *            the prov
	 */
	public void addProvider(Provider prov) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_PR_NAME, prov.getName());
		values.put(KEY_PR_ACTIVE, prov.getActive());

		// Inserting Row
		db.insert(TABLE_JOB_PROVIDER, null, values);
		db.close(); // Closing database connection
	}

	// Update Provider
	/**
	 * Update provider.
	 *
	 * @param recordID
	 *            the record id
	 * @param provName
	 *            the prov name
	 * @param active
	 *            the active
	 */
	public void updateProvider(int recordID, String provName, String active) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_PR_NAME, provName);
		values.put(KEY_PR_ACTIVE, active);

		db.update(TABLE_JOB_PROVIDER, values, KEY_PR_ID + " = ?", new String[] { String.valueOf(recordID) });
		db.close();

	}

	// update provider jobs
	/**
	 * Update provider jobs.
	 *
	 * @param newName
	 *            the new name
	 * @param oldName
	 *            the old name
	 */
	public void updateProviderJobs(String newName, String oldName) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_INC_PROVIDER, newName);

		db.update(TABLE_INCOME, values, KEY_INC_PROVIDER + " = ?", new String[] { String.valueOf(oldName) });
		db.close();
	}

	// Get All Providers
	/**
	 * Gets the all providers.
	 *
	 * @return the all providers
	 */
	public List<Provider> getAllProviders() {
		List<Provider> provList = new ArrayList<Provider>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_JOB_PROVIDER + " WHERE " + KEY_PR_ACTIVE + " LIKE 'yes'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Provider inc = new Provider();
				inc.setID(Integer.parseInt(cursor.getString(0)));
				inc.setName(cursor.getString(1));
				inc.setActive(cursor.getString(2));

				// Adding contact to list
				provList.add(inc);
			} while (cursor.moveToNext());
		}

		db.close();
		return provList;
	}

    public List<Provider> getProvidersState() {
        List<Provider> provList = new ArrayList<Provider>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_JOB_PROVIDER ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Provider inc = new Provider();
                inc.setID(Integer.parseInt(cursor.getString(0)));
                inc.setName(cursor.getString(1));
                inc.setActive(cursor.getString(2));

                // Adding contact to list
                provList.add(inc);
            } while (cursor.moveToNext());
        }

        db.close();
        return provList;
    }

	// Provider Count
	/**
	 * Gets the provider count.
	 *
	 * @return the provider count
	 */
	public int getProviderCount() {
		SQLiteDatabase db = this.getReadableDatabase();

		try {
			int i = 0;
			Cursor c = null;
			c = db.rawQuery("select " + KEY_PR_NAME + " from " + TABLE_JOB_PROVIDER + " WHERE " + KEY_PR_ACTIVE + " LIKE 'yes'", null);
			c.moveToFirst();
			i = c.getCount();
			c.close();
			return i;
		} catch (Exception e) {
			Log.e("APP", "exception", e);
		}
		db.close();
		return 0;
	}

	/*
	 * 
	 * New Trial
	 */
	/**
	 * Adds the new trial.
	 *
	 * @param trial
	 *            the trial
	 */
	public void addNewTrial(Trial trial) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = null;

		values = new ContentValues();
		values.put(KEY_TRIAL_TIME, trial.getDate());
		values.put(KEY_TRIAL_LAST_LOGIN_TIME, trial.getLoginDate());
		values.put(KEY_TRIAL_IMEI, trial.getImei());
		values.put(KEY_TRIAL_SUCCESS, trial.getSuccess());
		values.put(KEY_TRIAL_EXPIRED, trial.getExpired());
		values.put(KEY_TRIAL_EMAIL, trial.getEmail());

		// Inserting Row
		db.insert(TABLE_TRIAL, null, values);
		db.close(); // Closing database connection

	}

	/**
	 * Gets the trial info.
	 *
	 * @return the trial info
	 */
	public List<Trial> getTrialInfo() {
		List<Trial> list = new ArrayList<Trial>();
		// Select All Query
		String selectQuery = "SELECT " + KEY_TRIAL_ID + "," + KEY_TRIAL_TIME + "," + KEY_TRIAL_LAST_LOGIN_TIME + "," + KEY_TRIAL_IMEI + "," + KEY_TRIAL_SUCCESS + "," + KEY_TRIAL_EXPIRED + ","
				+ KEY_TRIAL_EMAIL + " FROM " + TABLE_TRIAL;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Trial inc = new Trial();
				inc.setID(Integer.parseInt(cursor.getString(0)));
				inc.setDate(cursor.getString(1));
				inc.setLoginDate(cursor.getString(2));
				inc.setImei(cursor.getString(3));
				inc.setSuccess(cursor.getString(4));
				inc.setExpired(cursor.getString(5));
				inc.setEmail(cursor.getString(6));

				// Adding contact to list
				list.add(inc);
			} while (cursor.moveToNext());
		}

		db.close();
		return list;
	}

	/**
	 * Update trial expired.
	 *
	 * @param trial_expired
	 *            the trial_expired
	 * @param imei_number_device
	 *            the imei_number_device
	 */
	public void updateTrialExpired(String trial_expired, String imei_number_device) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_TRIAL_EXPIRED, trial_expired);

		db.update(TABLE_TRIAL, values, KEY_TRIAL_IMEI + " = ?", new String[] { String.valueOf(imei_number_device) });
		db.close();

	}

	/**
	 * Update trial remote saved.
	 *
	 * @param trial_saved
	 *            the trial_saved
	 * @param imei_number_device
	 *            the imei_number_device
	 */
	public void updateTrialRemoteSaved(String trial_saved, String imei_number_device) {

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_TRIAL_SUCCESS, trial_saved);

		db.update(TABLE_TRIAL, values, KEY_TRIAL_IMEI + " = ?", new String[] { String.valueOf(imei_number_device) });
		db.close();

	}

	/**
	 * Update trial login date.
	 *
	 * @param date
	 *            the date
	 * @param imei_number_device
	 *            the imei_number_device
	 */
	public void updateTrialLoginDate(String date, String imei_number_device) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_TRIAL_LAST_LOGIN_TIME, date);

		db.update(TABLE_TRIAL, values, KEY_TRIAL_IMEI + " = ?", new String[] { String.valueOf(imei_number_device) });
		db.close();

	}

}
