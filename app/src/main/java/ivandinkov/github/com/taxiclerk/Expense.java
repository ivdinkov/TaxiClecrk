package ivandinkov.github.com.taxiclerk;

/**
 * Created by iv on 24/09/2017.
 */

class Expense {
	
	//private variables
	/** The _id. */
	private int _id;
	
	/** The _name. */
	private String _name;
	
	/** The _active. */
	private String _active;
	
	
	// Empty constructor
	/**
	 * Instantiates a new provider.
	 */
	public Expense(){
		
	}
	// constructor
	/**
	 * Instantiates a new provider.
	 *
	 * @param id the id
	 * @param name the name
	 * @param active the active
	 */
	public Expense(int id, String name, String active){
		this._id = id;
		this._name = name;
		this._active = active;
		
	}

//	// constructor
	/**
	 * Instantiates a new provider.
	 *
	 * @param name the name
	 * @param active the active
	 */
	public Expense(String name, String active){
		
		this._name = name;
		this._active = active;
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
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getActive(){
		return this._active;
	}
	
	// setting pin
	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(String active){
		this._active = active;
	}
	
}
