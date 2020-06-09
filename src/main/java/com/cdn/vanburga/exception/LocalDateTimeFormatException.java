package com.cdn.vanburga.exception;

public class LocalDateTimeFormatException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8826535960002980097L;

	/**
     * Constructor for LocalDateTimeFormatException.
     */
    public LocalDateTimeFormatException() {
        super();
    }

    /**
     * Constructor for LocalDateTimeFormatException.
     * @param _string
     */
    public LocalDateTimeFormatException(String _string) {
        super(_string);
    }

    /**
     * Constructor for LocalDateTimeFormatException.
     * @param _throwable
     */
    public LocalDateTimeFormatException(Throwable _throwable) {
        super(_throwable);
    }
}
