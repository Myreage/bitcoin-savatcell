package test.jdbc;

import java.sql.SQLException;

/**
 *
 *
 */
public class Util {

 /**
  *
  * @param len int array length
  * @return a non-persistent int array of the request size
  */
  public static int[] makeTransientIntArray(int len) {
    return new int[len];
  } /**/

 /**
  *
  * @param len byte array length
  * @return a non-persistent byte array of the request size
  */
  public static byte[] makeTransientByteArray(int len) {
    return new byte[len];
  } /**/

 /**
  *
  * @param len byte array length
  * @return a non-persistent boolean array of the request size
  */
  public static boolean[] makeTransientBooleanArray(int len) {
    return new boolean[len];
  } /**/

  /**
   * Converts a string from Java to C memory format
   * @param str the original string
   * @return the C string bytes, ending by 0
   */
  public static byte[] getCBytes(String str) throws SQLException {
    int len = str.length();
    byte[] bytes = new byte[len + 1];
    System.arraycopy(str.getBytes(), 0, bytes, 0, len);
    bytes[len] = 0;
    return bytes;
  } /**/
  
  /**
   * Converts an object as an Temporary Entry Point Object
   * @param obj the object to convert
   */
  public static void setTEPO(Object obj) {
  }


  /**
   * Converts an object as an Entry Point Object
   * @param obj the object to convert
   */
  public static void setEPO(Object obj) {
  }

  /**
   * Assigns an object to the client application context
   * @param o the object to assign
   */
  public static void transferToClientContext(Object o) {
  }

}
