package com.cdn.vanburga.constant;

public enum ResponseCode {

	OK(0, "Success"),
	
	CREATED (0, "New register created"),

	FOUND (0, "Register found"),
	
	REFUND_SUCCESS (0, "Refund successfully"),
	
	NOT_FOUND (8, "Register not found"),
	
	ERROR_PAGE_INDEX(9 , "Page number must not be less than 1"),
	
	INVALID_OFFSET(10, "Offset field can not be less than cero"),

	REQUIRE_PAGE(11, "For pagination the page number is require"),
	
	DATE_ERROR(12, "Date from can not be later than date to"),
	
	REQUIRE_COMPANY(13, "The entry of Company code is mandatory"),
	
	REQUIRE_DATE(14, "The entry of dates from and to is mandatory"),

	EXPIRATION_DATE_ERROR(15, "Expiration Date must be before then the actual local date time"),
	
	DATE_FORMAT_ERROR(16, "Wrong Date format"),
	
	WRONG_COMPANY_OR_STORE(17, "Merchant Code or merchant store code does not exist"),
	
	MISSING_FIELD(18, "Missing mandatory field: "),
	
	MERCHANT_ORDER_ALREADY_EXIST(19, "MerchantOrderId value already exist"),
	
	INVALID_FORMAT(20, "Invalid field format"),
	
	INVALID_PAYMENT_METHOD(21, "Invalid Payment Method"),
	
	INVALID_STATUS(22, "Can not execute the operation with the actual status"),
	
	REPEAT_ID(23, "The entered Id it is already registered"),
	
	INVALID_AMOUNT(24, "Invalid amount"),
	
	VTOL_CONNECTION_FAIL(25, "Connection with VTOL fail"),
	
	ORDER_NOT_FOUND(26, "Original Payment Order not found"),
	
	ITEM_LIMIT(27, "Excedes the MAX limit of items or item value size"),
	
	INVALID_CONTRY(28, "Invalid Country Code"),

	CURRENCY_CODE_ERROR(29, "Invalid Currency Code, must be '$' or 'U$S'" ),

	REFUNDED_AMOUNT_ERROR(30, "Refund transaction was made with diferent amount" ),

	EMAIL_FORMAT_ERROR(31, "Email field does not have the right format" ),
	
	MERCHANT_ORIGIN_ERROR(32, "The merchant code does not correspond to the transaction" ),
	
	MAIL_ERROR(33, "Error sending email to customer" ),

	CONFIGURATION_ERROR(34, "Error in configuration" ),

	DATABASE_CONNECTION_ERROR(35, "No Connection with database" ),
	
	ERROR_PROCESS(999, "System error. Impossible to process request"),
    
    UNAUTHORIZED_ERROR(401,"Unauthorized");
	
	
	
    private final int fieldNumber;

    private final String fieldName;

    private ResponseCode(int fieldNumber, String fieldName) {
        this.fieldNumber = fieldNumber;
        this.fieldName = fieldName;
    };

    public String fieldName() {
        return fieldName;
    }

    public int fieldNumber() {
        return this.fieldNumber;
    }
	
}
