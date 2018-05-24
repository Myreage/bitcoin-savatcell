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
		public static final int EP_WALLET_UPDATE = EP_QEP.FIRST_FREE_QEP_ID + 2;
		public static final int EP_USER_SELECT = EP_QEP.FIRST_FREE_QEP_ID + 3;
		public static final int EP_WALTER_SELECT = EP_QEP.FIRST_FREE_QEP_ID + 4;
		public static final int EP_WALTER_DELETE = EP_QEP.FIRST_FREE_QEP_ID + 5;
		public static final int EP_SESSION_SELECT = EP_QEP.FIRST_FREE_QEP_ID + 6;
		public static final int EP_SESSION_DELETE = EP_QEP.FIRST_FREE_QEP_ID + 7;
		public static final int EP_SESSION_INSERT = EP_QEP.FIRST_FREE_QEP_ID + 8;
		public static final int EP_TRANSACTION_SELECT = EP_QEP.FIRST_FREE_QEP_ID + 9;
		public static final int EP_TRANSACTION_SELECT_ALL = EP_QEP.FIRST_FREE_QEP_ID + 10;
		public static final int EP_TRANSACTION_INSERT = EP_QEP.FIRST_FREE_QEP_ID + 11;
		public static final int EP_TRANSACTION_DELETE = EP_QEP.FIRST_FREE_QEP_ID + 12;
		public static final int EP_ADDRESS_SELECT_ALL = EP_QEP.FIRST_FREE_QEP_ID + 13;
		public static final int EP_ADDRESS_INSERT = EP_QEP.FIRST_FREE_QEP_ID + 14;
		public static final int EP_ADDRESS_UPDATE = EP_QEP.FIRST_FREE_QEP_ID + 15;
		public static final int EP_ADDRESS_DELETE = EP_QEP.FIRST_FREE_QEP_ID + 16;
	}
}
