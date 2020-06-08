package com.cdn.vanburga.exception;

/**
 * Clase de excepcion padre para todas las excepciones manejadas en el servicio
 * 
 * @author ramiro.videla
 *
 */
public class ServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Throwable throwable = null;
	String errorCodeString = null;
	String descriptionString = null;

	/**
	 * 
	 */
	public ServiceException() {
		super();
	}

	/**
	 * 
	 * @param _string
	 */
	public ServiceException(String _string) {
		super(_string);
	}

	/**
	 * 
	 * @param _errorCodeString
	 * @param _exception
	 */
	public ServiceException(String _errorCodeString, Exception _exception) {

		super("ErrorCode: " + _errorCodeString + "\n" + _exception);
		if (getErrorCodeString() == null) {
			setErrorCodeString(_errorCodeString);
		}

	}

	/**
	 * 
	 * @param _message
	 * @param _cause
	 */
	public ServiceException(String _message, Throwable _cause) {

		super(_message, _cause);
	}

	/**
	 * 
	 * @param _errorCodeString
	 * @param _descriptionString
	 */
	public ServiceException(String _errorCodeString, String _descriptionString) {

		super("ErrorCode: " + _errorCodeString
				+ (_descriptionString == null ? "" : ". Particular Description: " + _descriptionString));

		if (getErrorCodeString() == null) {
			setErrorCodeString(_errorCodeString);
		}
		if (getDescriptionString() == null) {
			setDescriptionString(_descriptionString);
		}
	}

	/**
	 * 
	 * @param _throwable
	 */
	public ServiceException(Throwable _throwable) {
		super(_throwable.getMessage());
		throwable = _throwable;
	}

	/**
	 * obtiene la descripcion seteada en la excepcion lanzada
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getDescriptionString() {
		return descriptionString;
	}

	/**
	 * Obtiene el codigo de error de la excepcion
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getErrorCodeString() {
		return errorCodeString;
	}

	/**
	 * imprime el stackTrace del error
	 */
	public void printStackTrace() {
		if (throwable == null) {
			super.printStackTrace();
		} else {
			throwable.printStackTrace();
		}
	}

	/**
	 * Setea una descripcion en una excepcion
	 * 
	 * @param newDescriptionString java.lang.String
	 */
	public ServiceException setDescriptionString(java.lang.String newDescriptionString) {
		descriptionString = newDescriptionString;
		return this;
	}

	/**
	 * Setea un codigo de error en una excepcion
	 * 
	 * @param newErrorCodeString java.lang.String
	 */
	public void setErrorCodeString(java.lang.String newErrorCodeString) {
		errorCodeString = newErrorCodeString;
	}

	/**
	 * Constructor con codigo, descripcion y excepcion lanzada
	 * 
	 * @param _errorCodeString
	 * @param _descriptionString
	 * @param _exception
	 */
	public ServiceException(String _errorCodeString, String _descriptionString, Exception _exception) {

		super("ErrorCode: " + _errorCodeString
				+ (_descriptionString == null ? "" : ". Particular Description: " + _descriptionString));
		if (getErrorCodeString() == null) {
			setErrorCodeString(_errorCodeString);
		}
		if (getDescriptionString() == null && _descriptionString != null) {
			setDescriptionString(_descriptionString);
		}
	}
}
