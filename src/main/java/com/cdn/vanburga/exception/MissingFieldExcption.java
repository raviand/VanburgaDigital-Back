package com.cdn.vanburga.exception;

public class MissingFieldException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1262399689321613471L;

	/**
     * Constructor for MissingObjectExcption.
     */
    public MissingFieldException() {
        super();
    }

    /**
     * Constructor for MissingObjectExcption.
     * @param _string
     */
    public MissingFieldException(String _string) {
        super(_string);
    }

    /**
     * Constructor for MissingObjectExcption.
     * @param _throwable
     */
    public MissingFieldException(Throwable _throwable) {
        super(_throwable);
    }
}
