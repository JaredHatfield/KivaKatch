package com.kiva.KivaKatch.Exceptions;

/**
 * The exception that is thrown when a lender can not be loaded.
 * @author Jared Hatfield
 *
 */
public class LenderCreationException extends Exception {
	
	/**
	 * Constructs a new exception with null as its detail message.
	 */
	public LenderCreationException() {
		super(); 
	}
	
	/**
	 * Constructs a new exception with the specified detail message.
	 * @param message message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 */
	public LenderCreationException(String message) { 
		super(message);
	}
	
	/**
	 * Constructs a new exception with the specified detail message and cause.
	 * @param message message the detail message (which is saved for later retrieval by the Throwable.getMessage() method).
	 * @param cause cause the cause (which is saved for later retrieval by the Throwable.getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public LenderCreationException(String message, Throwable cause){
		super(message, cause);
	}

	/**
	 * Determines if a de-serialized file is compatible with this class.
	 */
	private static final long serialVersionUID = 1L;
}
