/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk.sqlite;

// TODO: Auto-generated Javadoc
/**
 * The Class Hours.
 */
public class Hours {


	
	//private variables
	/** The _id_hours. */
	int _id_hours;
	
	/** The _date. */
	String _date;
	
	/** The _hours. */
	String _hours;
	
	/** The _active. */
	String _active;
	
	
	// Empty constructor
	/**
	 * Instantiates a new hours.
	 */
	public Hours(){
		
	}
	// constructor
	/**
	 * Instantiates a new hours.
	 *
	 * @param id the id
	 * @param hours the hours
	 * @param date the date
	 * @param active the active
	 */
	public Hours(int id, String hours, String date, String active){
		this._id_hours = id;
		this._date = date;
		this._hours = hours;
		this._active = active;
		
	}
	
	// constructor
	/**
	 * Instantiates a new hours.
	 *
	 * @param hours the hours
	 * @param date the date
	 * @param active the active
	 */
	public Hours(String hours, String date, String active){
		this._date = date;
		this._hours = hours;
		this._active = active;
		
	}
	// getting ID
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID(){
		return this._id_hours;
	}
	
	// setting id
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setID(int id){
		this._id_hours = id;
	}
	
	// getting date
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate(){
		return this._date;
	}	
	// setting date
	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date){
		this._date = date;
	}
	
	// getting hours
	/**
	 * Gets the hours.
	 *
	 * @return the hours
	 */
	public String getHours(){
		return this._hours;
	}
	
	// setting hours
	/**
	 * Sets the hours.
	 *
	 * @param hours the new hours
	 */
	public void setHours(String hours){
		this._hours = hours;
	}
	
	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active) {
		this._active = active;
		
	}
	
	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive(){
		return this._active;
	}

}
