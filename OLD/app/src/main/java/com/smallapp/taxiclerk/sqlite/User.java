/*
 * Copyright (c) 2014. Small-app.com
 */
package com.smallapp.taxiclerk.sqlite;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User {


	
	//private variables
	/** The _id. */
	int _id;
	
	/** The _name. */
	String _name;
	
	/** The _pin. */
	String _pin;
	
	/** The _email. */
	String _email;
	
	// Empty constructor
	/**
	 * Instantiates a new user.
	 */
	public User(){
		
	}
	// constructor
	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param name the name
	 * @param email the email
	 * @param pin the pin
	 */
	public User(int id, String name, String email, String pin){
		this._id = id;
		this._name = name;
		this._email = email;
		this._pin = pin;
	}
	
//	// constructor
	/**
 * Instantiates a new user.
 *
 * @param name the name
 * @param email the email
 * @param pin the pin
 */
public User(String name, String email, String pin){
		this._name = name;
		this._pin = pin;
		this._email = email;
	}
	// getting ID
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getID(){
		return this._id;
	}
	
	// setting id
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setID(int id){
		this._id = id;
	}
	
	// getting name
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){
		return this._name;
	}	
	// setting name
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name){
		this._name = name;
	}
	
	// getting pin
	/**
	 * Gets the pin.
	 *
	 * @return the pin
	 */
	public String getPin(){
		return this._pin;
	}
	
	// setting pin
	/**
	 * Sets the pin.
	 *
	 * @param pin the new pin
	 */
	public void setPin(String pin){
		this._pin = pin;
	}
	
	// getting email
	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail(){
		return this._email;
	}
	// setting email
	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email){
		this._email = email;
	}
}
