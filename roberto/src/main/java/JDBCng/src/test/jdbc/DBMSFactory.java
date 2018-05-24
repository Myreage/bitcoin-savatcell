package test.jdbc;

import java.sql.SQLException;

public class DBMSFactory {

  private static DBMS dbms = null;
  private final static int MASK_00FF = 0x000000FF;

  /**
   *
   * @param fqDBMSClassName
   * @throws SQLException
   */
  public static synchronized void registerDBMS(String fqDBMSClassName) throws SQLException {
    if (dbms != null) {
      return;
    }
    try {
      dbms = (DBMS)Class.forName(fqDBMSClassName).newInstance();
      Util.setEPO(dbms);
    } catch (Exception e) {
      throw new SQLException("Cannot instantiate the DBMS implementation class");
    }
  } /**/

  /**
   *
   * @return
   */
  public static DBMS getDBMS() {
    return dbms;
  } /**/

  /**
   * FONCTION CONVERSION byte[] to int
   */
  static public int bytea2int(byte[] t) {
    return ((t[3] << 24) | ((t[2]&0xff) << 16) | ((t[1]&0xff) << 8) | ((t[0]&0xff)));
  } /**/
  
  /**
   * FONCTION CONVERSION byte[] to int, with base pos
   */
  static public int bytea2int(byte[] t, int pos) {
    return ((t[pos+3] << 24) | ((t[pos+2]&0xff) << 16) | ((t[pos+1]&0xff) << 8) | ((t[pos]&0xff)));
  } /**/

  /**
   * FONCTION CONVERSION integer to byte[]
   */
  static public byte[] int2bytea(int i, byte[] t, int index) {
    t[index]     = (byte)(i);
    t[index + 1] = (byte)(i >> 8);
    t[index + 2] = (byte)(i >> 16);
    t[index + 3] = (byte)(i >> 24);
    return t;
  } /**/

  /**
   * FONCTION CONVERSION short to byte[]
   */
  static public byte[] short2bytea(short s, byte[] t, int index) {
    t[index] 			= (byte)(s);
    t[index + 1]	= (byte)(s >> 8);
    return t;
  } /**/

  /**
   * FONCTION CONVERSION byte[] to short
   */
  static public short bytea2short(byte[] t) {
	return (short)( ( (t[1]&0xff) << 8 ) | ( t[0]&0xff ) );
  } /**/

  /**
   * FONCTION CONVERSION byte to int
   */
  static public int byte2int( byte b ) {
	return ( b < 0 ) ? ( 256 + b ) : b;
  } /**/

  /**
   * Convert an IdGlobal in the string format 0x... to the byte[20] format
   *
  static public byte[] ConvertString2Bytes(String IdGlobal) {
    byte res[] = new byte[4];
    for (int i = 1; i <= 4; i++) {
      int i2 = (i * 2);
      res[i-1] = (byte)Integer.parseInt(IdGlobal.substring(i2, (i2 + 2)), 16);
    }
    return res;
  } /**/

  /**
   * Temp function to convert user and role global Ids to byte[]
   * for the Connection.open method. Note that bytes are stored
   * in the order opposite to int2bytea
   *
   * @param id the global id
   * @param t the destination byte[]
   * @param index position at which the integer will be stored
   */
  static public void int2byteaInv(String id, byte[] t, int index) {
    int i = Integer.parseInt(id);
    t[index + 3] = (byte)(i & MASK_00FF);
    t[index + 2] = (byte)((i >>> 8) & MASK_00FF);
    t[index + 1] = (byte)((i >>> 16) & MASK_00FF);
    t[index] = (byte)((i >>> 24) & MASK_00FF);
  } /**/

}
