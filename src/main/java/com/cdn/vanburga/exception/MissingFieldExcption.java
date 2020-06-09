package com.cdn.vanburga.exception;

public class MissingFieldExcption extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1262399689321613471L;

	/**
     * Constructor for MissingObjectExcption.
     */
    public MissingFieldExcption() {
        super();
    }

    /**
     * Constructor for MissingObjectExcption.
     * @param _string
     */
    public MissingFieldExcption(String _string) {
        super(_string);
    }

    /**
     * Constructor for MissingObjectExcption.
     * @param _throwable
     */
    public MissingFieldExcption(Throwable _throwable) {
        super(_throwable);
    }
}
