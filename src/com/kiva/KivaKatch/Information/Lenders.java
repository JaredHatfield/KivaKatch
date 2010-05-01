package com.kiva.KivaKatch.Information;

public class Lenders {
	
	private String lender_id;
	
	private String name;
	
	private String whereabouts;
	
	public Lenders(){
		this.lender_id = "";
		this.name = "";
		this.whereabouts = "";
	}
	
	public void setLenderId(String lender_id){
		this.lender_id = lender_id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setWhereabouts(String whereabouts){
		this.whereabouts = whereabouts;
	}
	
	public String getLenderId(){
		return this.lender_id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getWhereabouts(){
		return this.whereabouts;
	}
}
