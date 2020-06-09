package com.cdn.vanburga.exception;

public class FieldTypeException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3499900926135218044L;

	/**
     * Constructor for FieldTypeException.
     */
    public FieldTypeException() {
        super();
    }

    /**
     * Constructor for FieldTypeException.
     * @param _string
     */
    public FieldTypeException(String _string) {
        super(_string);
    }

    /**
     * Constructor for FieldTypeException.
     * @param _throwable
     */
    public FieldTypeException(Throwable _throwable) {
        super(_throwable);
    }
}
