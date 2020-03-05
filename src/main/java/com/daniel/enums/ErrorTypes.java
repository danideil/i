package com.daniel.enums;

public enum ErrorTypes {
	
	GENERAL_ERROR(699, "General error", "please contact administrator or wait for the next version", false),
	
	// Login + Register:
	LOGIN_FAIL(605, "Login failure", "please check email and password", true),
	INVALID_EMAIL(606, "Register error:", "Email is already taken.", false),
	PASSWORD_VALIDATION_FAILED(607, "Password validation has failed:", "password not valid", false),
	
	// Users errors:
	//FAIL_TO_GENERATE_ID(606, "Problem while adding user ", "generating an id in database", true),
	USER_ADD_FAILED(610, "User failure:", "Fail to add user", true),
	USER_UPDATE_FAILED(611, "User failure:", "Fail to update user", true),
	USER_DELETE_FAILED(612, "User failure:", "Fail to delete user", false),
	FAILED_TO_GET_USER(613, "Fail to get user", "", true),	
	UNAUTHORIZED_USER(614, "Unauthorized user","User not authorized", true),
	UNAUTHORIZED_ATTAMPT(615, "Unauthorized attampt:","User attampted to enter data to diffrent company", true),
	USER_EMAIL_FAILED(616, "Email check failed:","could not check email", true),
	INVALID_USER_ID(617, "Invalid user","User was not found by id", false),
	
	// Customers errors:
	CUSTOMER_ADD_FAILED(620, "Customer failure:","Failed to add customer", true),
	CUSTOMER_UPDATE_FAILED(621, "Customer failure:", "Fail to update customer", false),
	CUSTOMER_DELETE_FAILED(622, "Customer failure:", "Fail to delete customer", true),
	FAILED_TO_GET_CUSTOMER(623, "Fail to get customer", "check if theres a customer in this id ;-)", false),	
	INVALID_CUSTOMER_ID(624, "Invalid customer","Customer was not found by id", false),
	
	// Companies errors:
	COMPANY_GENERAL_ERROR(630, "Company failure:","General error was found", true),
	COMPANY_ADD_FAILED(631, "Company failure:","Failed to add company", true),
	COMPANY_UPDATE_FAILED(632, "Company failure:", "Fail to update company", false),
	COMPANY_DELETE_FAILED(633, "Company failure:", "Fail to delete company", false),
	FAILED_TO_GET_COMPANY(634, "Company failure:", "Fail to get company", false),
	INVALID_COMPANY(635, "Company error was found:", "", false),
	INVALID_COMPANY_NAME(636, "Company error:", "Company already exists", false),
	DELETE_COMPANY_USERS_FAILED(637, "Fail to delete company users", "", true),
	USER_ERROR_UNKNOWN(638, "Fail to get user", "", true),
	USERS_LIST_EMPTY(639, "Users list is empty", "", true),
	
	// Coupons errors:
	COUPON_GENERAL_ERROR(640, "Coupon failure:","General error was found", false),
	COUPON_ADD_FAILED(641, "Coupon failure:","Failed to add coupon", false),
	COUPON_UPDATE_FAILED(642, "Coupon failure:", "Fail to update coupon", false),
	COUPON_DELETE_FAILED(643, "Coupon failure:", "Fail to delete coupon", false),
	COUPON_DELETE_LIST_FAILED(644, "Coupon failure:", "Fail to delete company coupons", false),
	FAILED_TO_GET_COUPON(645, "Coupon failure:", "Fail to get coupon", false),
	COUPONS_LIST_EMPTY(646, "Users list is empty", "", true),
	INVALID_COUPON(647, "Coupon error was found:", "Coupon title already in use", false),
	INVALID_COUPON_ID(648, "Coupon error was found:", "Coupon was not found", false),
	INVALID_PRICE(649, "Coupon price error", "price value is wrong", false),
	INVALID_START_DATE(650, "Coupon start date error", "start date already passed", false),
	INVALID_END_DATE(651, "Coupon end date error", "End date cannot be previously to the start date.", false),
	INVALID_CUSTOMER(652, "Invalid customer","Customer was not found by id", false),
	NO_COUPONS(653, "Coupons error:","Not enough coupons", false),
	
	// Purchases errors:
	FAILED_TO_PURCHASE(660, "Purchase error: ", "fail to create purchase", true),
	FAILED_TO_DELETE_PURCHASE(661, "Purchase error: ", "fail to delete purchase", true),
	FAILED_TO_UPDATE_PURCHASE(662, "Purchase error: ", "fail to update purchase", true),
	FAILED_TO_GET_PURCHASE(663, "Purchase error: ", "fail to get purchase", false),
	PURCHASE_ERROR_UNKNOWN(664, "Purchase error: ", "fail to get purchase: NULL or unknown", true),
	PURCHASES_LIST_EMPTY(665, "Purchase error: ", "purchase list is empty", false),
	FAILED_TO_SET_PURCHASE(666, "Purchase error: ", "purchase list is empty", true),
	FAILED_TO_GET_PURCHASES(667, "Purchase error: ", "purchase list is empty", false),
	FAILED_TO_SET_PURCHASE_PRICE(668, "Purchase error: ", "fail to set purchase price", true),
	FAILED_TO_SET_PURCHASE_DATE(669, "Purchase error: ", "fail to set purchase date", true),
	
//	INVALID_PURCHASE,
//	MISSING_COMPANY,
	INVALID_PASSWORD(609, "Problem while login: ", "Wrong password was given", false),
	PURCHASE_DELETE_FAILED(609, "Fail to delete customer purchases", "", true),
	DELETE_CUSTOMER_FAILED(609, "Fail to delete customer", "", true);
	
	private final int errorNumber;
	private final String errorName;
	private final String errorMessage;
	private boolean isShowStackTrace;
	
	private ErrorTypes(int errorNumber, String errorName, String errorMessage, boolean isShowStackTrace) {
		this.errorNumber = errorNumber;
		this.errorName = errorName;
		this.errorMessage = errorMessage;
		this.isShowStackTrace = isShowStackTrace;
	}

	public int getErrorNumber() {
		return errorNumber;
	}

	public String getErrorName() {
		return errorName;
	}

	public boolean isShowStackTrace() {
		return isShowStackTrace;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
