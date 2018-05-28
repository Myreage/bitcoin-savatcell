package test.jdbc;

/*=============================================================================

Name: Macro.java

Abs:  Define some global macro

Auth: 18-09-2007, Kevin JACQUEMIN (KJ):
Rev:  02-11-2007, Kevin JACQUEMIN (KJ):

=============================================================================*/

public class Macro
{
//  static protected final byte CMD_SIZE	= 3; // never used?

	// Request types
	static protected final byte STATE_EXIT	= 0; // never used, but can be
	static protected final byte STATE_DBMS	= 1;
	static protected final byte STATE_TUPLE	= 2;

	// Command codes
	static public final byte CMD_QUERY	= 1;
	static public final byte CMD_QUERYMD	= 8;
	static public final byte CMD_UPDATE	= 2;
	static public final byte CMD_NEXT 	= 3;
	static public final byte CMD_CLOSE	= 4;
	static public final byte CMD_COMMIT	= 5;
	static public final byte CMD_INIT	= 6;
	static public final byte CMD_INIT_WITHOUT_CA  = 61;
	static public final byte CMD_INSTALL_META = 7;
	static public final byte CMD_UPDATE_AND_GET_KEY = 11;
	static public final byte CMD_SETTSGLOBAL	= 18;
	static public final byte CMD_IDPATIENT 	= 9;
	static public final byte CMD_TSGLOBAL	= 10;
	static public final byte CMD_TSGLOBAL_NO_INC	= 12;
	static public final byte CMD_RESET_DATABASE	= 13;
	static public final byte CMD_EMPTY_DATABASE	= 14;
	static public final byte CMD_ROLLBACK = 17;
	static public final byte CMD_DEBUG = 19;
	static public final byte CMD_TUPLE_PRODUCED = 20;
	static public final byte CMD_NEXT_COLUMN_SIZE = 21;
	static public final byte CMD_NEXT_COLUMN_COPY = 22;
	// Macros to insert and remove a failure point
	static public final byte CMD_INSERT_FAILURE_POINT = 15;
	static public final byte CMD_REMOVE_FAILURE_POINT = 16;
	// Macros to insert and remove a getoff point
	static public final byte CMD_INSERT_TEARING_POINT = 70;
	static public final byte CMD_REMOVE_TEARING_POINT = 71;
	// Macros to test the insert, lookup and delete of keys in PBFilter
	static public final byte CMD_PBFILTER_INIT = 72;
	static public final byte CMD_PBFILTER_INSERT_KEY = 73;
	static public final byte CMD_PBFILTER_LOOKUP_KEY	= 74;
	static public final byte CMD_PBFILTER_DELETE_KEY = 75;
	static public final byte CMD_PBFILTER_CLOSE = 76;
	// Macros for BLOB management
	static public final byte CMD_BLOB_NEW = 23;
	static public final byte CMD_BLOB_SET = 24;
	static public final byte CMD_BLOB_GET = 25;

	// Other constants
	static protected final int  TUPLE_COLUMN_MAX_SIZE = 2048;
	static protected final int  METADATE_FILE_MAX_SIZE = 3000;//2600;
	static protected final int  PLAN_PARAMS_MAX_SIZE = 600; // can be tuned when we have the final query set

  // Protocg78 Type
  static public final byte	T_CHAR 		= 0;
  static public final byte 	T_NUMBER 	= 1;
  static public final byte 	T_DATE 		= 2;
  static public final byte 	T_PK 		= 3;
  static public final byte 	T_BINARY	= 8;
  static public final byte  NULL_CHAR   = 4;
  static public final byte  NULL_NUMBER = 5;
  static public final byte  NULL_DATE   = 6;
  static public final byte  NULL_BINARY = 7;
  static public final byte  T_BLOB		= 9;
  static public final byte  NULL_BLOB	=10;
  static public final byte  T_VARCHAR	=11;
  static public final byte  NULL_VARCHAR=12;

  static protected final byte T_SIZEINT	= 4;
  static public final byte 	ERROR_CODE_SIZE = 4;

  // Access Control Macro
  static public final byte CATEGORIE_USERDMSP 	= 0;
  static public final byte CATEGORIE_FORMULAIRE	= 1;
  static public final byte CATEGORIE_EPISODE	= 2;

  static public final byte ACTEUR_USERDMSP		= 0;
  static public final byte ACTEUR_ROLE			= 1;

  static public final byte ACCESS_TYPE_READ		= 0;
  static public final byte ACCESS_TYPE_UPDATE	= 1;
  static public final byte ACCESS_TYPE_DELETE	= 2;
  static public final byte ACCESS_TYPE_INSERT	= 3;

  // Failure Point Macros: function id
  static public final byte FUNC_NAND_WRITE_FAIL 		= 0;
  static public final byte FUNC_NAND_ERASE_FAIL 		= 1;
  static public final byte FUNC_NOR_WRITE_ARRAY_FAIL	= 2;
  static public final byte FUNC_NOR_WRITE_BYTE_FAIL		= 3;
  static public final byte FUNC_NOR_WRITE_HALF_FAIL		= 4;
  static public final byte FUNC_NOR_WRITE_WORD_FAIL		= 5;
  static public final byte FUNC_NOR_ERASE_FAIL			= 6;
  static public final byte FUNC_TABLESKT_RECOVERY_FAIL	= 7;
  static public final byte FUNC_DBITMAP_RECOVERY_FAIL	= 8;
  static public final byte FUNC_UBITMAP_RECOVERY_FAIL	= 9;
  static public final byte FUNC_NANDBLS_RECOVERY_FAIL	= 10;

  // Failure Point Macros: structure types
  static public final byte STRUCT_TYPE_TABLE 	= 0;	//table
  static public final byte STRUCT_TYPE_SKT		= 1;	//skt
  static public final byte STRUCT_TYPE_CI_KA	= 2;	//KA(<key, pointer>s) of climbing index
  static public final byte STRUCT_TYPE_CI_SKA	= 3;	//SKA(bloom filters) of climbing index


/* ****************** */
/*  Tearing Point IDs */
//
// Note: do not set an enum to store the ids because we could not set
// their values.

// -------------------------------
//	Tearing tests for tablespaces

//	Tearing points for testing inserts
  static public final byte TPOINT_INSERT_NOREMPTY 					= 1;
  static public final byte TPOINT_INSERT_NORALLUNCOMM 				= 2;
  static public final byte TPOINT_INSERT_NORCOMMUNCOMM 				= 3;
  static public final byte TPOINT_INSERT_NORLASTBEFOREFLUSH 		= 4;
  static public final byte TPOINT_INSERT_NORLASTAFTERFLUSH 			= 5;

  static public final byte TPOINT_INSERT_NANDSRALLUNCOMM 			= 11;
  static public final byte TPOINT_INSERT_NANDSRCOMMUNCOMM 			= 12;
  static public final byte TPOINT_INSERT_NANDPGEMPTY 				= 13;

  static public final byte TPOINT_INSERT_NANDBKEMPTYBEFORELKUPD		= 21;
  static public final byte TPOINT_INSERT_NANDBKEMPTYAFTERLKUPD		= 22;
  static public final byte TPOINT_INSERT_NANDBKNONEMPTY				= 23;

//	Tearing points for testing updates
  static public final byte TPOINT_UPDATE_NOREMPTY 					= 31;
  static public final byte TPOINT_UPDATE_NORALLUNCOMM 				= 32;
  static public final byte TPOINT_UPDATE_NORCOMMUNCOMM 				= 33;
  static public final byte TPOINT_UPDATE_NORLASTBEFOREFLUSH 		= 34;
  static public final byte TPOINT_UPDATE_NORLASTAFTERFLUSH 			= 35;

  static public final byte TPOINT_UPDATE_NANDSRALLUNCOMM 			= 41;
  static public final byte TPOINT_UPDATE_NANDSRCOMMUNCOMM 			= 42;
  static public final byte TPOINT_UPDATE_NANDPGEMPTY 				= 43;

  static public final byte TPOINT_UPDATE_NANDBKEMPTYBEFORELKUPD		= 51;
  static public final byte TPOINT_UPDATE_NANDBKEMPTYAFTERLKUPD		= 52;
  static public final byte TPOINT_UPDATE_NANDBKNONEMPTY				= 53;

//	Tearing points for testing deletes
  static public final byte TPOINT_DELETE_NOREMPTY 					= 61;
  static public final byte TPOINT_DELETE_NORALLUNCOMM 				= 62;
  static public final byte TPOINT_DELETE_NORCOMMUNCOMM 				= 63;
  static public final byte TPOINT_DELETE_NORLASTBEFOREFLUSH 		= 64;
  static public final byte TPOINT_DELETE_NORLASTAFTERFLUSH 			= 65;

  static public final byte TPOINT_DELETE_NANDSRALLUNCOMM 			= 71;
  static public final byte TPOINT_DELETE_NANDSRCOMMUNCOMM 			= 72;
  static public final byte TPOINT_DELETE_NANDPGEMPTY 				= 73;

  static public final byte TPOINT_DELETE_NANDBKEMPTYBEFORELKUPD		= 81;
  static public final byte TPOINT_DELETE_NANDBKEMPTYAFTERLKUPD		= 82;
  static public final byte TPOINT_DELETE_NANDBKNONEMPTY				= 83;

}
