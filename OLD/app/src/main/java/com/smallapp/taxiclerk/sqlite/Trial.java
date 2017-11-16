/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk.sqlite;

// TODO: Auto-generated Javadoc
/**
 * The Class Trial.
 */
public class Trial {

	// private variables
	/** The _id. */
	int _id;
	
	/** The _date. */
	private String _date;
	
	/** The _imei. */
	private String _imei;
	
	/** The _success. */
	private String _success;
	
	/** The _expired. */
	private String _expired;
	
	/** The _email. */
	private String _email;
	
	/** The _login_date. */
	private String _login_date;

	// Empty constructor
	/**
	 * Instantiates a new trial.
	 */
	public Trial() {

	}

	// constructor
	/**
	 * Instantiates a new trial.
	 *
	 * @param date the date
	 * @param login_date the login_date
	 * @param imei the imei
	 * @param success the success
	 * @param expired the expired
	 * @param email the email
	 */
	public Trial(String date, String login_date, String imei, String success, String expired, String email) {
		this._date = date;
		this._login_date = login_date;
		this._imei = imei;
		this._success = success;
		this._expired = expired;
		this._email = email;
	}

	// constructor
	/**
	 * Instantiates a new trial.
	 *
	 * @param id the id
	 * @param date the date
	 * @param login_date the login_date
	 * @param imei the imei
	 * @param success the success
	 * @param expired the expired
	 * @param email the email
	 */
	public Trial(int id, String date, String login_date, String imei, String success, String expired, String email) {
		this._id = id;
		this._date = date;
		this._login_date = login_date;
		this._imei = imei;
		this._success = success;
		this._expired = expired;
		this._email = email;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setID(int id) {
		this._id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID() {
		return this._id;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this._date = date;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return this._date;
	}

	/**
	 * Sets the login date.
	 *
	 * @param login_date the new login date
	 */
	public void setLoginDate(String login_date) {
		this._login_date = login_date;

	}

	/**
	 * Gets the login date.
	 *
	 * @return the login date
	 */
	public String getLoginDate() {
		return this._login_date;
	}

	/**
	 * Sets the imei.
	 *
	 * @param imei the new imei
	 */
	public void setImei(String imei) {
		this._imei = imei;
	}

	/**
	 * Gets the imei.
	 *
	 * @return the imei
	 */
	public String getImei() {
		return this._imei;
	}

	/**
	 * Sets the success.
	 *
	 * @param success the new success
	 */
	public void setSuccess(String success) {
		this._success = success;
	}

	/**
	 * Gets the success.
	 *
	 * @return the success
	 */
	public String getSuccess() {
		return this._success;
	}

	/**
	 * Sets the expired.
	 *
	 * @param expired the new expired
	 */
	public void setExpired(String expired) {
		this._expired = expired;
	}

	/**
	 * Gets the expired.
	 *
	 * @return the expired
	 */
	public String getExpired() {
		return this._expired;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this._email = email;

	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return this._email;

	}

}
