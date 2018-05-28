package org.roberto.javier.PlugDB;

public class QEP_IDs {
	/* EP_QEP class must exist in every application. It allows to interact hardcoded QEPs inside SGBD.
	 * Application QEP start id should be greater than the value of last element of this class. */
	public static class EP_QEP { // 1
		public static final int EP_QEP_INSERT	= 0;
		public static final int FIRST_FREE_QEP_ID = 0;
	}
	
	/* Application QEP ids */
	public static class EP_Javier {
		public static final int EP_WALLET_SELECT = EP_QEP.FIRST_FREE_QEP_ID + 1; // Application QEP start id 
		public static final int EP_WALLET_UPDATE = EP_WALLET_SELECT + 1;
		public static final int EP_USER_SELECT = EP_WALLET_UPDATE + 1;
		public static final int EP_WALTER_SELECT = EP_USER_SELECT + 1;
		public static final int EP_WALTER_DELETE = EP_WALTER_SELECT + 1;
		public static final int EP_SESSION_SELECT = EP_WALTER_DELETE + 1;
		public static final int EP_SESSION_SELECT_ALL = EP_SESSION_SELECT + 1;
		public static final int EP_SESSION_DELETE = EP_SESSION_SELECT_ALL + 1;
		public static final int EP_SESSION_INSERT = EP_SESSION_DELETE + 1;
		public static final int EP_TRANSACTION_SELECT = EP_SESSION_INSERT + 1;
		public static final int EP_TRANSACTION_SELECT_ALL = EP_TRANSACTION_SELECT + 1;
		public static final int EP_TRANSACTION_INSERT = EP_TRANSACTION_SELECT_ALL + 1;
		public static final int EP_TRANSACTION_DELETE = EP_TRANSACTION_INSERT + 1;
		public static final int EP_ADDRESS_SELECT = EP_TRANSACTION_DELETE + 1;
		public static final int EP_ADDRESS_SELECT_ALL = EP_ADDRESS_SELECT + 1;
		public static final int EP_ADDRESS_INSERT = EP_ADDRESS_SELECT_ALL + 1;
		public static final int EP_ADDRESS_UPDATE = EP_ADDRESS_INSERT + 1;
		public static final int EP_ADDRESS_DELETE = EP_ADDRESS_UPDATE + 1;
	}
}
