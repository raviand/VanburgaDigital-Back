package com.cdn.vanburga.exception;

public class SystemPropertiesException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3553659002775656190L;

	/**
     * Constructor for SystemPropertiesException.
     */
    public SystemPropertiesException() {
        super();
    }

    /**
     * Constructor for SystemPropertiesException.
     * @param _string
     */
    public SystemPropertiesException(String _string) {
        super(_string);
    }

    /**
     * Constructor for SystemPropertiesException.
     * @param _throwable
     */
    public SystemPropertiesException(Throwable _throwable) {
        super(_throwable);
    }
}
