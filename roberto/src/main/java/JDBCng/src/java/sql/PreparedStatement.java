/*
 * @(#)PreparedStatement.java	1.52 06/07/10
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Calendar;
/**
 * An object that represents a precompiled SQL statement.
 * <P>A SQL statement is precompiled and stored in a
 * <code>PreparedStatement</code> object. This object can then be used to
 * efficiently execute this statement multiple times.
 *
 * <P><B>Note:</B> The setter methods (<code>setShort</code>, <code>setString</code>,
 * and so on) for setting IN parameter values
 * must specify types that are compatible with the defined SQL type of
 * the input parameter. For instance, if the IN parameter has SQL type
 * <code>INTEGER</code>, then the method <code>setInt</code> should be used.
 *
 * <p>If arbitrary parameter type conversions are required, the method
 * <code>setObject</code> should be used with a target SQL type.
 * <P>
 * In the following example of setting a parameter, <code>con</code> represents
 * an active connection:
 * <PRE>
 *   PreparedStatement pstmt = con.prepareStatement("UPDATE EMPLOYEES
 *                                     SET SALARY = ? WHERE ID = ?");
 *   pstmt.setBigDecimal(1, 153833.00)
 *   pstmt.setInt(2, 110592)
 * </PRE>
 *
 * @see Connection#prepareStatement
 * @see ResultSet
 */

public interface PreparedStatement extends Statement {

  /**
   * Executes the SQL query in this <code>PreparedStatement</code> object
   * and returns the <code>ResultSet</code> object generated by the query.
   *
   * @return a <code>ResultSet</code> object that contains the data produced by the
   *         query; never <code>null</code>
   * @exception SQLException if a database access error occurs;
   * this method is called on a closed  <code>PreparedStatement</code> or the SQL
   *            statement does not return a <code>ResultSet</code> object
   */
  ResultSet executeQuery() throws SQLException;

  /**
   * Executes the SQL statement in this <code>PreparedStatement</code> object,
   * which must be an SQL Data Manipulation Language (DML) statement, such as <code>INSERT</code>, <code>UPDATE</code> or
   * <code>DELETE</code>; or an SQL statement that returns nothing,
   * such as a DDL statement.
   *
   * @return either (1) the row count for SQL Data Manipulation Language (DML) statements
   *         or (2) 0 for SQL statements that return nothing
   * @exception SQLException if a database access error occurs;
   * this method is called on a closed  <code>PreparedStatement</code>
   * or the SQL
   *            statement returns a <code>ResultSet</code> object
   */
  int executeUpdate() throws SQLException;

  /**
   * Sets the designated parameter to SQL <code>NULL</code>.
   *
   * <P><B>Note:</B> You must specify the parameter's SQL type.
   *
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param sqlType the SQL type code defined in <code>java.sql.Types</code>
   * @exception SQLException if parameterIndex does not correspond to a parameter
   * marker in the SQL statement; if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   * @exception SQLFeatureNotSupportedException if <code>sqlType</code> is
   * a <code>ARRAY</code>, <code>BLOB</code>, <code>CLOB</code>,
   * <code>DATALINK</code>, <code>JAVA_OBJECT</code>, <code>NCHAR</code>,
   * <code>NCLOB</code>, <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
   *  <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
   * or  <code>STRUCT</code> data type and the JDBC driver does not support
   * this data type
   */
  void setNull(int parameterIndex, int sqlType) throws SQLException;

  /**
   * Sets the designated parameter to the given Java <code>int</code> value.
   * The driver converts this
   * to an SQL <code>INTEGER</code> value when it sends it to the database.
   *
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if parameterIndex does not correspond to a parameter
   * marker in the SQL statement; if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   */
  void setInt(int parameterIndex, int x) throws SQLException; /**/

  /**
   * Sets the designated parameter to the given Java <code>String</code> value.
   * The driver converts this
   * to an SQL <code>VARCHAR</code> or <code>LONGVARCHAR</code> value
   * (depending on the argument's
   * size relative to the driver's limits on <code>VARCHAR</code> values)
   * when it sends it to the database.
   *
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if parameterIndex does not correspond to a parameter
   * marker in the SQL statement; if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   */
  void setString(int parameterIndex, String x) throws SQLException; /**/

  /**
   * Sets the designated parameter to the given Java array of bytes.  The driver converts
   * this to an SQL <code>VARBINARY</code> or <code>LONGVARBINARY</code>
   * (depending on the argument's size relative to the driver's limits on
   * <code>VARBINARY</code> values) when it sends it to the database.
   *
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if parameterIndex does not correspond to a parameter
   * marker in the SQL statement; if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   */
  void setBytes(int parameterIndex, byte x[]) throws SQLException; /**/

  /**
   * Sets the designated parameter to the given <code>java.sql.Date</code> value
   * using the default time zone of the virtual machine that is running
   * the application.
   * The driver converts this
   * to an SQL <code>DATE</code> value when it sends it to the database.
   *
   * @param parameterIndex the first parameter is 1, the second is 2, ...
   * @param x the parameter value
   * @exception SQLException if parameterIndex does not correspond to a parameter
   * marker in the SQL statement; if a database access error occurs or
   * this method is called on a closed <code>PreparedStatement</code>
   */
  void setDate(int parameterIndex, java.sql.Date x)
  throws SQLException; /**/

  void setBlob( int parameterIndex, Blob x ) throws SQLException;

void addBatch() throws SQLException;

void clearParameters() throws SQLException;

boolean execute() throws SQLException;

ResultSetMetaData getMetaData() throws SQLException;

ParameterMetaData getParameterMetaData() throws SQLException;

void setArray(int parameterIndex, Array theArray) throws SQLException;

void setAsciiStream(int parameterIndex, InputStream theInputStream, int length)
		throws SQLException;

void setBigDecimal(int parameterIndex, BigDecimal theBigDecimal)
		throws SQLException;

void setBinaryStream(int parameterIndex, InputStream theInputStream, int length)
		throws SQLException;

void setBoolean(int parameterIndex, boolean theBoolean) throws SQLException;

void setByte(int parameterIndex, byte theByte) throws SQLException;

void setCharacterStream(int parameterIndex, Reader reader, int length)
		throws SQLException;

void setClob(int parameterIndex, Clob theClob) throws SQLException;

void setDate(int parameterIndex, Date theDate, Calendar cal)
		throws SQLException;

void setDouble(int parameterIndex, double theDouble) throws SQLException;

void setFloat(int parameterIndex, float theFloat) throws SQLException;

void setLong(int parameterIndex, long theLong) throws SQLException;

void setNull(int paramIndex, int sqlType, String typeName) throws SQLException;

void setObject(int parameterIndex, Object theObject) throws SQLException;

void setObject(int parameterIndex, Object theObject, int targetSqlType)
		throws SQLException;

void setObject(int parameterIndex, Object theObject, int targetSqlType,
		int scale) throws SQLException;

void setRef(int parameterIndex, Ref theRef) throws SQLException;

void setShort(int parameterIndex, short theShort) throws SQLException;

void setTime(int parameterIndex, Time theTime) throws SQLException;

void setTime(int parameterIndex, Time theTime, Calendar cal)
		throws SQLException;

void setTimestamp(int parameterIndex, Timestamp theTimestamp)
		throws SQLException;

void setTimestamp(int parameterIndex, Timestamp theTimestamp, Calendar cal)
		throws SQLException;

void setUnicodeStream(int parameterIndex, InputStream theInputStream, int length)
		throws SQLException;

void setURL(int parameterIndex, URL theURL) throws SQLException;

void setRowId(int parameterIndex, RowId theRowId) throws SQLException;

void setNString(int parameterIndex, String theString) throws SQLException;

void setNCharacterStream(int parameterIndex, Reader reader, long length)
		throws SQLException;

void setNClob(int parameterIndex, NClob value) throws SQLException;

void setClob(int parameterIndex, Reader reader, long length)
		throws SQLException;

void setBlob(int parameterIndex, InputStream inputStream, long length)
		throws SQLException;

void setNClob(int parameterIndex, Reader reader, long length)
		throws SQLException;

void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException;

void setAsciiStream(int parameterIndex, InputStream inputStream, long length)
		throws SQLException;

void setBinaryStream(int parameterIndex, InputStream inputStream, long length)
		throws SQLException;

void setCharacterStream(int parameterIndex, Reader reader, long length)
		throws SQLException;

void setAsciiStream(int parameterIndex, InputStream inputStream)
		throws SQLException;

void setBinaryStream(int parameterIndex, InputStream inputStream)
		throws SQLException;

void setCharacterStream(int parameterIndex, Reader reader) throws SQLException;

void setNCharacterStream(int parameterIndex, Reader reader) throws SQLException;

void setClob(int parameterIndex, Reader reader) throws SQLException;

void setBlob(int parameterIndex, InputStream inputStream) throws SQLException;

void setNClob(int parameterIndex, Reader reader) throws SQLException;
}