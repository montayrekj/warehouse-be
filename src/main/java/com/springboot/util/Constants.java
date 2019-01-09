package com.springboot.util;

public class Constants {
	public static String LOGIN_ACCESS_TOKEN = "372d30dd2849813ef674855253900679";
	
	/* Purchase Order Status Pending */
	public static int PO_STATUS_PENDING = 0;
	
	/* Purchase Order Status Regional Manager Approved */
	public static int PO_STATUS_RM_APPROVED = 1;
	
	/* Purchase Order Status Accounting Approved */
	public static int PO_STATUS_ACCT_APPROVED = 2;
	
	/* Purchase Order Status Checker */
	public static int PO_STATUS_CHECKER = 2;
	
	/* Purchase Order Status Done */
	public static int PO_STATUS_DONE = 3;
	
	/* Purchase Order Status Regional Manager Declined */
	public static int PO_STATUS_RM_DECLINED = -1;
	
	/* Purchase Order Status Accounting Declined */
	public static int PO_STATUS_ACCT_DECLINED = -2;
	
	/* Cash Sale Type */
	public static String SALE_TYPE_CASH = "Cash";
	
	/* Term Sale Type */
	public static String SALE_TYPE_TERM = "Term";
	
	/* User Type - Regional Manager */
	public static int REGIONAL_MANAGER = 0;
	
	/* User Type - Accounting */
	public static int ACCOUNTING = 1;
	
	/* User Type - Checker */
	public static int CHECKER = 2;
	
	/* User Type - Admin */
	public static int ADMIN = 3;
}
