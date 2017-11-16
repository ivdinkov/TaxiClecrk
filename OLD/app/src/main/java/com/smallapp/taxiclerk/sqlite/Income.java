/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk.sqlite;

// TODO: Auto-generated Javadoc
/**
 * The Class Income.
 */
public class Income {

	// private variables
	/** The _id. */
	int _id;

	/** The _amount. */
	String _amount;
	
	/** The _date. */
	String _date;
	
	/** The _note. */
	String _note;

	/** The _active. */
	String _active;
	
	/** The _type. */
	String _type;
	
	/** The _provider. */
	String _provider;

	// Empty constructor
	/**
	 * Instantiates a new income.
	 */
	public Income() {

	}

	// constructor
	/**
	 * Instantiates a new income.
	 *
	 * @param id the id
	 * @param date the date
	 * @param type the type
	 * @param amount the amount
	 * @param note the note
	 * @param active the active
	 * @param provider the provider
	 */
	public Income(int id, String date, String type, String amount, String note, String active, String provider) {
		this._id = id;
		this._date = date;
		this._type = type;
		this._amount = amount;
		this._note = note;
		this._active = active;
		this._provider = provider;

	}

	// constructor
	/**
	 * Instantiates a new income.
	 *
	 * @param date the date
	 * @param type the type
	 * @param amount the amount
	 * @param note the note
	 * @param active the active
	 * @param provider the provider
	 */
	public Income(String date, String type, String amount, String note, String active, String provider) {

		this._date = date;
		this._type = type;
		this._amount = amount;
		this._note = note;
		this._active = active;
		this._provider = provider;
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

	// getting type
	/**
	 * Gets the inc type.
	 *
	 * @return the inc type
	 */
	public String getIncType() {
		return this._type;
	}

	// setting type
	/**
	 * Sets the inc type.
	 *
	 * @param type the new inc type
	 */
	public void setIncType(String type) {
		this._type = type;
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

	// getting note
	/**
	 * Gets the note.
	 *
	 * @return the note
	 */
	public String getNote() {
		return this._note;
	}

	// setting date
	/**
	 * Sets the note.
	 *
	 * @param note the new note
	 */
	public void setNote(String note) {
		this._note = note;
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

	// setting provider
	/**
	 * Sets the provider.
	 *
	 * @param provider the new provider
	 */
	public void setProvider(String provider) {
		this._provider = provider;
	}

	// getting provider
	/**
	 * Gets the provider.
	 *
	 * @return the provider
	 */
	public String getProvider() {
		return this._provider;
	}
}
