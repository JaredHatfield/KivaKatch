package com.kiva.KivaKatch.Exceptions;

public class LenderLoansCreationException extends Exception
{
    /**
     * Constructs a new exception with null as its detail message.
     */
    public LenderLoansCreationException()
    {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * 
     * @param message
     *            message the detail message (which is saved for later retrieval
     *            by the Throwable.getMessage() method).
     */
    public LenderLoansCreationException(String message)
    {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * 
     * @param message
     *            message the detail message (which is saved for later retrieval
     *            by the Throwable.getMessage() method).
     * @param cause
     *            cause the cause (which is saved for later retrieval by the
     *            Throwable.getCause() method). (A null value is permitted, and
     *            indicates that the cause is nonexistent or unknown.)
     */
    public LenderLoansCreationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Determines if a de-serialized file is compatible with this class.
     */
    private static final long serialVersionUID = 1L;
}
