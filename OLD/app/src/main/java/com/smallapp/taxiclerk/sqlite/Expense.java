/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk.sqlite;

// TODO: Auto-generated Javadoc
/**
 * The Class Expense.
 */
public class Expense {

	// private variables
	/** The _id. */
	int _id;
	
	/** The _expense_name. */
	String _expense_name;
	
	/** The _date. */
	String _date;
	
	/** The _amount. */
	String _amount;
	
	/** The _milage. */
	String _milage;
	
	/** The _payment_type. */
	String _payment_type;
	
	/** The _notes. */
	String _notes;
	
	/** The _active. */
	String _active;

	// Empty constructor
	/**
	 * Instantiates a new expense.
	 */
	public Expense() {

	}

	// constructor
	/**
	 * Instantiates a new expense.
	 *
	 * @param id the id
	 * @param expense_name the expense_name
	 * @param amount the amount
	 * @param date the date
	 * @param milage the milage
	 * @param payment_type the payment_type
	 * @param notes the notes
	 * @param active the active
	 */
	public Expense(int id, String expense_name, String amount, String date, String milage, String payment_type, String notes, String active) {
		this._id = id;
		this._expense_name = expense_name;
		this._date = date;
		this._amount = amount;
		this._milage = milage;
		this._payment_type = payment_type;
		this._notes = notes;
		this._active = active;
	}

	// // constructor
	/**
	 * Instantiates a new expense.
	 *
	 * @param expense_name the expense_name
	 * @param amount the amount
	 * @param date the date
	 * @param milage the milage
	 * @param payment_type the payment_type
	 * @param notes the notes
	 * @param active the active
	 */
	public Expense(String expense_name, String amount, String date, String milage, String payment_type, String notes, String active) {

		this._expense_name = expense_name;
		this._date = date;
		this._amount = amount;
		this._milage = milage;
		this._payment_type = payment_type;
		this._notes = notes;
		this._active = active;
	}

	// getting ID
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return this._id;
	}

	// setting id
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setID(int id) {
		this._id = id;
	}

	// getting name
	/**
	 * Gets the expense name.
	 *
	 * @return the expense name
	 */
	public String getExpenseName() {
		return this._expense_name;
	}

	// setting name
	/**
	 * Sets the expense name.
	 *
	 * @param expense_name the new expense name
	 */
	public void setExpenseName(String expense_name) {
		this._expense_name = expense_name;
	}

	// getting amount
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public String getAmount() {
		return this._amount;
	}

	// setting amount
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(String amount) {
		this._amount = amount;
	}

	// getting date
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return this._date;
	}

	// setting date
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this._date = date;
	}

	// getting milage
	/**
	 * Gets the milage.
	 *
	 * @return the milage
	 */
	public String getMilage() {
		return this._milage;
	}

	// setting milage
	/**
	 * Sets the milage.
	 *
	 * @param milage the new milage
	 */
	public void setMilage(String milage) {
		this._milage = milage;
	}

	// setting payment type
	/**
	 * Sets the payment type.
	 *
	 * @param payment_type the new payment type
	 */
	public void setPaymentType(String payment_type) {
		this._payment_type = payment_type;
	}

	// get payment type
	/**
	 * Gets the payment type.
	 *
	 * @return the payment type
	 */
	public String getPaymentType() {
		return this._payment_type;
	}

	// setting notes
	/**
	 * Sets the notes.
	 *
	 * @param notes the new notes
	 */
	public void setNotes(String notes) {
		this._notes = notes;
	}

	// get notes
	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return this._notes;
	}

	// setting active
	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active) {
		this._active = active;
	}

	// getting active
	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive() {
		return this._active;
	}
}
