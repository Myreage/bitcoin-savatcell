package test.jdbc;

import java.sql.SQLException;
import java.sql.SQLInvalidAuthorizationSpecException;

import org.inria.jdbc.Blob;
import org.inria.jdbc.Statement;

/**
 *
 *
 */
public // remove
abstract class DBMS
{
  static private final boolean[] locked =
    Util.makeTransientBooleanArray(1);

  static private final boolean[] lockedReent =
    Util.makeTransientBooleanArray(1);

  // should be protected, but is set public on PC
  // to enable opening consecutive connections
  public /*protected*/ static boolean initialized;

  public /*final*/ static boolean DEBUG = "true".equals(System.getProperty("jdbc.debug"));


  void installSchema(byte[] schema, int userid) throws SQLException {
    int len = schema.length;
    byte[] tmp = Util.makeTransientByteArray(len + 4);
    DBMSFactory.int2bytea(userid, tmp, 0);
    System.arraycopy(schema, 0, tmp, 4, len);
    call(Macro.CMD_INSTALL_META, null, tmp, len + 4, null);
    initialized = true;
  }

  void uninstallSchema() throws Exception {
    call(Macro.CMD_RESET_DATABASE, null, null, 0, null);
    initialized = false;
  }

  void eraseData() throws Exception {
    call(Macro.CMD_EMPTY_DATABASE, null, null, 0, null);
  }

  void open(String host, String user, String role) throws SQLException {
    if (DEBUG) {
      // print trace header and function name; end of line
      System.out.print("\nTRACE_API;open; \n");
    }
    synchronized (locked) {
      while (locked[0]) {
        try {
          locked.wait();
        } catch (InterruptedException e) {
        }
      }
      locked[0] = true;
    }
    if ((user == null) || (role == null)) {
      call(Macro.CMD_INIT_WITHOUT_CA, null, null, 0, null);
    } else {
      byte[] res = new byte[4];
      byte[] globalId = new byte[8];
      DBMSFactory.int2byteaInv(user, globalId, 0);
      DBMSFactory.int2byteaInv(role, globalId, 4);

	  call(Macro.CMD_INIT, null, globalId, globalId.length, res);
	  if ( DBMSFactory.bytea2int(res) == 0 )	// 1 - access rights are OK, 0 - KO
      {
          locked[0] = false;	// unlock connection (enables next attempts)
          throw new SQLInvalidAuthorizationSpecException("UserDMSP = " + user +
				" with the Role = " + role + " has no access at all to this DMSP");
      }
    }
  }

  void close() throws SQLException {
    call(Macro.CMD_CLOSE, null, null, 0, null);
  }

  void exit() throws SQLException {
    if (DEBUG) {
      // print trace header and function name; end of line
      System.out.print("\nTRACE_API;exit; \n");
    }
    synchronized (locked) {
      locked[0] = false;
      locked.notify();
    }
  }

  void insertFailurePoint(byte function_id, byte struct_id, byte struct_type) throws SQLException {
    byte [] buffer = new byte[3];
    buffer[0]   = function_id;
    buffer[1]   = struct_id;
    buffer[2]   = struct_type;
    call(Macro.CMD_INSERT_FAILURE_POINT, null, buffer, buffer.length, null);
  }

  void removeFailurePoint() throws SQLException {
    call(Macro.CMD_REMOVE_FAILURE_POINT, null, null, 0, null);
  }

  void rollbackTransaction() throws SQLException {
    call(Macro.CMD_ROLLBACK, null, null, 0, null);
  }

  void insertTearingPoint(byte function_id) throws SQLException {
    byte [] buffer = new byte[1];
    buffer[0]   = function_id;
    call(Macro.CMD_INSERT_TEARING_POINT, null, buffer, buffer.length, null);
  }

  void removeTearingPoint() throws SQLException {
    call(Macro.CMD_REMOVE_TEARING_POINT, null, null, 0, null);
  }

  void PBFilterInit( byte with_partition, byte n_hashes ) throws SQLException {
    byte [] buffer = new byte[2];
    buffer[0]   = with_partition;
    buffer[1]   = n_hashes;
    call(Macro.CMD_PBFILTER_INIT, null, buffer, buffer.length, null);
  }

  void PBFilterInsertKey( String key ) throws SQLException {
	call(Macro.CMD_PBFILTER_INSERT_KEY, key, null, 0, null);
  }

  void PBFilterDeleteKey( String key ) throws SQLException {
	call(Macro.CMD_PBFILTER_DELETE_KEY, key, null, 0, null);
  }

  void PBFilterLookupKey( String key ) throws SQLException {
	call(Macro.CMD_PBFILTER_LOOKUP_KEY, key, null, 0, null);
  }

  void PBFilterClose(byte reset_db) throws SQLException {
	byte [] buffer = new byte[1];
	buffer[0]   = reset_db;
	call(Macro.CMD_PBFILTER_CLOSE, null, buffer, buffer.length, null);
  }

  public void call(byte cmd, String static_plan, byte[] plan_params, int plan_params_size, byte[] res) throws SQLException {
    if (!initialized) {
      switch (cmd) {
      case Macro.CMD_INIT:
      case Macro.CMD_INIT_WITHOUT_CA:
      case Macro.CMD_INSTALL_META:
        break;
      default:
        throw new SQLException("DBMS not initialized");
      }
    }

	switch (cmd) {
      case Macro.CMD_QUERY:
	  case Macro.CMD_UPDATE:
        synchronized (lockedReent) {
		  while (lockedReent[0]) {
			try {
			  System.out.print(" WAIT CALL (lockedReent) = TRUE cmd:" + cmd);
			  lockedReent.wait();
			  System.out.print(" AFTER WAIT CALL (lockedReent) = TRUE cmd:" + cmd);
			} catch (InterruptedException e) {
			}
		  }
		  lockedReent[0] = true;
		}
		default:
		break;
      }

    // Print parameters to be able to replay a sequence of calls on PC
    // The result of the print is a line:
    // TRACE_CALL;cmd;nb_params;static_plan_modif;plan_params_size;plan_params[0]-plan_params[1]-...;
    //  cmd:= cast into int -> String
    //  nb_params:= other unicode (ie, nb of params) in static_plan cast into int -> String
    //	static_plan_modif:= static_plan modified by
    //		replacing \u0000 by _u0000
    //		replacing the other unicode (only one) by _uNBPARAM
    //  plan_params_size:= int -> String
    //	plan_params[i]:= cast into int -> String
    if (DEBUG) {
//      System.out.print("\nTRACE_CALL;cmd;nb_params;static_plan_modif;plan_params_size;plan_params[0]-plan_params[1]-...;");
      System.out.print("\nTRACE_CALL;");
      // print and close cmd:
      System.out.print(cmd);
      System.out.print(";");
      // if there is a string with an EP
      if (static_plan != null) {
        // if the query plan if more than 5, read, print, and replace the unicode
        //		at position 5 (nb_params):
        String s = "";
        if (static_plan.length() > 5) {
          char nb_param = static_plan.charAt(5);
          // replace nb_param in static_plan
          s = static_plan.substring(0, 5) + "_uNBPARAM"
              + static_plan.substring(6, static_plan.length());
          // print nb_param:
          System.out.print((int)nb_param);
        } else {
          s = static_plan;
        }
        // replace unicode 0 (if any) in static_plan:
        int begin_pos = 0;
        int end_pos = s.indexOf("\u0000");
        String new_s = "";
        while (end_pos != -1) {
          new_s = new_s + s.substring(begin_pos, end_pos) + "_u0000";
          begin_pos = end_pos + 1;
          end_pos = s.indexOf("\u0000", begin_pos);
        }
        int last_index_pos = s.lastIndexOf('\u0000');
        if (last_index_pos != -1) {
          new_s = new_s + s.substring(last_index_pos + 1, s.length());
        }
        // close nb_param:
        System.out.print(";");
        // print and close static_plan:
        System.out.print(new_s);
        System.out.print(";");
      } else {
        // close nb_param and static_plan:
        System.out.print(";");
        System.out.print(";");
      }
      // print and close plan_params_size:
      System.out.print(plan_params_size);
      System.out.print(";");
      // print plan_params
      if (plan_params != null) {
        for (int i = 0; i < plan_params.length; i ++) {
          System.out.print("" + (int)plan_params[i]);
          System.out.print(" ");
        }
        // close plan_params
        System.out.print(";");
      } else {
        // close plan_params
        System.out.print(";");
      }
      // print result set
      if (res != null) {
        for (int i = 0; i < res.length; i ++) {
          System.out.print("" + (int)res[i]);
          System.out.print(" ");
        }
      }
      // close res and end of line
      System.out.println("; \n");
    }

    // for the moment, get the end position of the EP by parsing the string
    // in the future, the end position will be produced by mulinette and included in the EP
    int ep_end = 0;
    if (static_plan != null) {
      int ep_pos = (static_plan.indexOf(Statement.EP_START, 0) + 6); // start pos of the EP
      ep_end = static_plan.indexOf('\u0000', ep_pos);
    }

    int ret;
    ret = call0(cmd, static_plan, ep_end, plan_params, plan_params_size, res);
    if (ret < 0) {
      unlockedReent();
      throw new SQLException("1.DBMS Error code = " + ret + " cmd = " + cmd);
    } else {
      while (ret > 0) {
        cmd = (byte)ret;
        ret = call0(cmd, null, 0, null, 0, res);
      }
      // TODO: make a constant for error codes somewhere
      if (ret < 0) {
        unlockedReent();
        throw new SQLException("2.DBMS Error code = " + ret + " cmd = " + cmd);
      }
    }
    unlockedReent();
  }

  public void unlockedReent(){

    synchronized (lockedReent) {
      if(lockedReent[0]){
	      lockedReent[0] = false;
	      lockedReent.notify();
	  }
    }
  }

  /**
   *
   * @param cmd
   * @param req
   * @param res
   * @return
   * @throws SQLException
   */
  abstract int call0(byte cmd, String static_plan, int sp_end_pos, byte[] plan_params, int plan_params_size, byte[] res) throws SQLException; /**/

  /**
   *
   * @return
   */
  abstract int tupleProduced() throws SQLException;

  /**
   *
   * @return
   */
  abstract int nextColumnSize() throws SQLException;

  /**
   *
   * @param buffer
   * @param len
   */
  abstract void nextColumnCopy(byte[] buffer, int len) throws SQLException;

  /**
   * @return
   */
  abstract int getGlobalTimestamp() throws SQLException;

  /**
   * Increments the global timestamp and returns the value before it
   */
  abstract int nextGlobalTimestamp() throws SQLException;

  abstract void setGlobalTimestamp(int ts) throws SQLException;

	abstract byte[] getDebugInfo( byte param, byte[] result ) throws SQLException;
  /**
   * @return
   */
  abstract int getSptIdPatient() throws SQLException;

  /**
   * Reset the counter measuring the time spent in the DBMS engine.
   * @return the counter value, before reset;
   */
  abstract long resetDBMSCallTime();

  /**
   * Retrieves the amount of time spent in the DBMS engine
   *
   * @param tag a string which can be used to "tag" this measurement
   * @return a number of milliseconds
   */
  abstract long getDBMSCallTime(String tag);

	/**
	 * Send CMD_BLOB_NEW
	 * Used to create new blob object or to abandon existing one
	 * @return New blob head part of id
	 */
	abstract int newBlob() throws SQLException;
	/**
	 * Send CMD_BLOB_SET
	 * Used to append data to the specified blob's head and size from its id
	 * @param id Blob id
	 * @param bytes Data to append
	 * @return New blob hash
	 */
	abstract byte[] setBlob( Blob.BlobId id, byte[] bytes ) throws SQLException;
	/**
	 * Send CMD_BLOB_GET
	 * Used to retrieve length bytes of data of given blob's head from its id at given offset
	 * @param id Blob id
	 * @param offset Offset from blob's head to retrieve data from
	 * @param length Number of bytes to retrieve
	 * @return Received data
	 */
	abstract byte[] getBlob( Blob.BlobId id, long offset, short length ) throws SQLException;
}
