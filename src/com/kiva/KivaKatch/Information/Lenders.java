package com.kiva.KivaKatch.Information;

public class Lenders {
	
	/**
	 * The lender_id.
	 */
	private String lender_id;
	
	/**
	 * The name.
	 */
	private String name;
	
	/**
	 * The whereabouts.
	 */
	private String whereabouts;
	
	/**
	 * Constructor for Lenders.
	 */
	public Lenders(){
		this.lender_id = "";
		this.name = "";
		this.whereabouts = "";
	}
	
	/**
	 * Sets the value of lender_id.
	 * @param lender_id The new lender_id.
	 */
	public void setLenderId(String lender_id){
		this.lender_id = lender_id;
	}
	
	/**
	 * Sets the value of name.
	 * @param name The new name.
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Sets the value of whereabouts.
	 * @param whereabouts The new whereabouts.
	 */
	public void setWhereabouts(String whereabouts){
		this.whereabouts = whereabouts;
	}
	
	/**
	 * Gets the value of lender_id.
	 * @return The lender_id.
	 */
	public String getLenderId(){
		return this.lender_id;
	}
	
	/**
	 * Gets the value of name.
	 * @return The name.
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the value of whereabouts.
	 * @return The whereabouts.
	 */
	public String getWhereabouts(){
		return this.whereabouts;
	}
}
