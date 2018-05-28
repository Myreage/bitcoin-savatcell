/*
 * @(#)ResultSetMetaData.java	1.33 05/12/01
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.sql;

/**
 * An object that can be used to get information about the types
 * and properties of the columns in a <code>ResultSet</code> object.
 * The following code fragment creates the <code>ResultSet</code> object rs,
 * creates the <code>ResultSetMetaData</code> object rsmd, and uses rsmd
 * to find out how many columns rs has and whether the first column in rs
 * can be used in a <code>WHERE</code> clause.
 * <PRE>
 *
 *     ResultSet rs = stmt.executeQuery("SELECT a, b, c FROM TABLE2");
 *     ResultSetMetaData rsmd = rs.getMetaData();
 *     int numberOfColumns = rsmd.getColumnCount();
 *     boolean b = rsmd.isSearchable(1);
 *
 * </PRE>
 */

public interface ResultSetMetaData {

  /**
   * Returns the number of columns in this <code>ResultSet</code> object.
   *
   * @return the number of columns
   * @exception SQLException if a database access error occurs
   */
  int getColumnCount() throws SQLException;


  /**
   * Gets the designated column's suggested title for use in printouts and
   * displays. The suggested title is usually specified by the SQL <code>AS</code>
   * clause.  If a SQL <code>AS</code> is not specified, the value returned from
   * <code>getColumnLabel</code> will be the same as the value returned by the
   * <code>getColumnName</code> method.
   *
   * @param column the first column is 1, the second is 2, ...
   * @return the suggested column title
   * @exception SQLException if a database access error occurs
   */
  String getColumnLabel(int column) throws SQLException;

  /**
   * Get the designated column's name.
   *
   * @param column the first column is 1, the second is 2, ...
   * @return column name
   * @exception SQLException if a database access error occurs
   */
  String getColumnName(int column) throws SQLException;

  /**
   * Retrieves the designated column's SQL type.
   *
   * @param column the first column is 1, the second is 2, ...
   * @return SQL type from java.sql.Types
   * @exception SQLException if a database access error occurs
   * @see Types
   */
  int getColumnType(int column) throws SQLException;

  /**
   * Retrieves the designated column's database-specific type name.
   *
   * @param column the first column is 1, the second is 2, ...
   * @return type name used by the database. If the column type is
   * a user-defined type, then a fully-qualified type name is returned.
   * @exception SQLException if a database access error occurs
   */
  String getColumnTypeName(int column) throws SQLException;


<T> T unwrap(Class<T> iface) throws SQLException;


boolean isWrapperFor(Class<?> iface) throws SQLException;


String getCatalogName(int column) throws SQLException;


String getColumnClassName(int column) throws SQLException;


int getColumnDisplaySize(int column) throws SQLException;


int getPrecision(int column) throws SQLException;


int getScale(int column) throws SQLException;


String getSchemaName(int column) throws SQLException;


String getTableName(int column) throws SQLException;


boolean isAutoIncrement(int column) throws SQLException;


boolean isCaseSensitive(int column) throws SQLException;


boolean isCurrency(int column) throws SQLException;


boolean isDefinitelyWritable(int column) throws SQLException;


int isNullable(int column) throws SQLException;


boolean isReadOnly(int column) throws SQLException;


boolean isSearchable(int column) throws SQLException;


boolean isSigned(int column) throws SQLException;


boolean isWritable(int column) throws SQLException;

}
