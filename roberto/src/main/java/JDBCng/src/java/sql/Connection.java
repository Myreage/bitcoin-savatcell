/*
 * @(#)Connection.java	1.52 06/09/28
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.sql;

import java.util.Map;
import java.util.Properties;

// import java.util.Properties;

/**
 * <P>A connection (session) with a specific
 * database. SQL statements are executed and results are returned
 * within the context of a connection.
 * <P>
 * A <code>Connection</code> object's database is able to provide information
 * describing its tables, its supported SQL grammar, its stored
 * procedures, the capabilities of this connection, and so on. This
 * information is obtained with the <code>getMetaData</code> method.
 *
 * <P><B>Note:</B> When configuring a <code>Connection</code>, JDBC applications
 *  should use the appropritate <code>Connection</code> method such as
 *  <code>setAutoCommit</code> or <code>setTransactionIsolation</code>.
 *  Applications should not invoke SQL commands directly to change the connection's
 *   configuration when there is a JDBC method available.  By default a <code>Connection</code> object is in
 * auto-commit mode, which means that it automatically commits changes
 * after executing each statement. If auto-commit mode has been
 * disabled, the method <code>commit</code> must be called explicitly in
 * order to commit changes; otherwise, database changes will not be saved.
 * <P>
 * A new <code>Connection</code> object created using the JDBC 2.1 core API
 * has an initially empty type map associated with it. A user may enter a
 * custom mapping for a UDT in this type map.
 * When a UDT is retrieved from a data source with the
 * method <code>ResultSet.getObject</code>, the <code>getObject</code> method
 * will check the connection's type map to see if there is an entry for that
 * UDT.  If so, the <code>getObject</code> method will map the UDT to the
 * class indicated.  If there is no entry, the UDT will be mapped using the
 * standard mapping.
 * <p>
 * A user may create a new type map, which is a <code>java.util.Map</code>
 * object, make an entry in it, and pass it to the <code>java.sql</code>
 * methods that can perform custom mapping.  In this case, the method
 * will use the given type map instead of the one associated with
 * the connection.
 * <p>
 * For example, the following code fragment specifies that the SQL
 * type <code>ATHLETES</code> will be mapped to the class
 * <code>Athletes</code> in the Java programming language.
 * The code fragment retrieves the type map for the <code>Connection
 * </code> object <code>con</code>, inserts the entry into it, and then sets
 * the type map with the new entry as the connection's type map.
 * <pre>
 *      java.util.Map map = con.getTypeMap();
 *      map.put("mySchemaName.ATHLETES", Class.forName("Athletes"));
 *      con.setTypeMap(map);
 * </pre>
 *
 * @see DriverManager#getConnection
 * @see Statement
 * @see ResultSet
 */
public interface Connection {

  /**
   * Creates a <code>Statement</code> object for sending
   * SQL statements to the database.
   * SQL statements without parameters are normally
   * executed using <code>Statement</code> objects. If the same SQL statement
   * is executed many times, it may be more efficient to use a
   * <code>PreparedStatement</code> object.
   * <P>
   * Result sets created using the returned <code>Statement</code>
   * object will by default be type <code>TYPE_FORWARD_ONLY</code>
   * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
   * The holdability of the created result sets can be determined by
   * calling {@link #getHoldability}.
   *
   * @return a new default <code>Statement</code> object
   * @exception SQLException if a database access error occurs
   * or this method is called on a closed connection
   */
  Statement createStatement() throws SQLException;

  /**
   * Creates a <code>PreparedStatement</code> object for sending
   * parameterized SQL statements to the database.
   * <P>
   * A SQL statement with or without IN parameters can be
   * pre-compiled and stored in a <code>PreparedStatement</code> object. This
   * object can then be used to efficiently execute this statement
   * multiple times.
   *
   * <P><B>Note:</B> This method is optimized for handling
   * parametric SQL statements that benefit from precompilation. If
   * the driver supports precompilation,
   * the method <code>prepareStatement</code> will send
   * the statement to the database for precompilation. Some drivers
   * may not support precompilation. In this case, the statement may
   * not be sent to the database until the <code>PreparedStatement</code>
   * object is executed.  This has no direct effect on users; however, it does
   * affect which methods throw certain <code>SQLException</code> objects.
   * <P>
   * Result sets created using the returned <code>PreparedStatement</code>
   * object will by default be type <code>TYPE_FORWARD_ONLY</code>
   * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
   * The holdability of the created result sets can be determined by
   * calling {@link #getHoldability}.
   *
   * @param sql an SQL statement that may contain one or more '?' IN
   * parameter placeholders
   * @return a new default <code>PreparedStatement</code> object containing the
   * pre-compiled SQL statement
   * @exception SQLException if a database access error occurs
   * or this method is called on a closed connection
   */
  PreparedStatement prepareStatement(String sql)
  throws SQLException;

  /**
   * Makes all changes made since the previous
   * commit/rollback permanent and releases any database locks
   * currently held by this <code>Connection</code> object.
   * This method should be
   * used only when auto-commit mode has been disabled.
   *
   * @exception SQLException if a database access error occurs,
   * this method is called while participating in a distributed transaction,
   * if this method is called on a closed conection or this
   *            <code>Connection</code> object is in auto-commit mode
   * @see #setAutoCommit
   */
  void commit() throws SQLException;

  /**
   * Undoes all changes made in the current transaction
   * and releases any database locks currently held
   * by this <code>Connection</code> object. This method should be
   * used only when auto-commit mode has been disabled.
   *
   * @exception SQLException if a database access error occurs,
   * this method is called while participating in a distributed transaction,
   * this method is called on a closed connection or this
   *            <code>Connection</code> object is in auto-commit mode
   * @see #setAutoCommit
   */
  void rollback() throws SQLException;

  /**
   * Releases this <code>Connection</code> object's database and JDBC resources
   * immediately instead of waiting for them to be automatically released.
   * <P>
   * Calling the method <code>close</code> on a <code>Connection</code>
   * object that is already closed is a no-op.
   * <P>
   * It is <b>strongly recommended</b> that an application explicitly
   * commits or rolls back an active transaction prior to calling the
   * <code>close</code> method.  If the <code>close</code> method is called
   * and there is an active transaction, the results are implementation-defined.
   * <P>
   *
   * @exception SQLException SQLException if a database access error occurs
   */
  void close() throws SQLException;

  /**
   * Retrieves whether this <code>Connection</code> object has been
   * closed.  A connection is closed if the method <code>close</code>
   * has been called on it or if certain fatal errors have occurred.
   * This method is guaranteed to return <code>true</code> only when
   * it is called after the method <code>Connection.close</code> has
   * been called.
   * <P>
   * This method generally cannot be called to determine whether a
   * connection to a database is valid or invalid.  A typical client
   * can determine that a connection is invalid by catching any
   * exceptions that might be thrown when an operation is attempted.
   *
   * @return <code>true</code> if this <code>Connection</code> object
   *         is closed; <code>false</code> if it is still open
   * @exception SQLException if a database access error occurs
   */
  boolean isClosed() throws SQLException;

  /**
   * Creates a default <code>PreparedStatement</code> object that has
   * the capability to retrieve auto-generated keys. The given constant
   * tells the driver whether it should make auto-generated keys
   * available for retrieval.  This parameter is ignored if the SQL statement
   * is not an <code>INSERT</code> statement, or an SQL statement able to return
   * auto-generated keys (the list of such statements is vendor-specific).
   * <P>
   * <B>Note:</B> This method is optimized for handling
   * parametric SQL statements that benefit from precompilation. If
   * the driver supports precompilation,
   * the method <code>prepareStatement</code> will send
   * the statement to the database for precompilation. Some drivers
   * may not support precompilation. In this case, the statement may
   * not be sent to the database until the <code>PreparedStatement</code>
   * object is executed.  This has no direct effect on users; however, it does
   * affect which methods throw certain SQLExceptions.
   * <P>
   * Result sets created using the returned <code>PreparedStatement</code>
   * object will by default be type <code>TYPE_FORWARD_ONLY</code>
   * and have a concurrency level of <code>CONCUR_READ_ONLY</code>.
   * The holdability of the created result sets can be determined by
   * calling {@link #getHoldability}.
   *
   * @param sql an SQL statement that may contain one or more '?' IN
   *        parameter placeholders
   * @param autoGeneratedKeys a flag indicating whether auto-generated keys
   *        should be returned; one of
   *        <code>Statement.RETURN_GENERATED_KEYS</code> or
   *	      <code>Statement.NO_GENERATED_KEYS</code>
   * @return a new <code>PreparedStatement</code> object, containing the
   *         pre-compiled SQL statement, that will have the capability of
   *         returning auto-generated keys
   * @exception SQLException if a database access error occurs, this
   *  method is called on a closed connection
   *         or the given parameter is not a <code>Statement</code>
   *         constant indicating whether auto-generated keys should be
   *         returned
   * @exception SQLFeatureNotSupportedException if the JDBC driver does not support
   * this method with a constant of Statement.RETURN_GENERATED_KEYS
   * @since 1.4
   */
  PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
  throws SQLException; /**/

boolean isReadOnly() throws SQLException;

boolean isValid(int timeout) throws SQLException;

Blob createBlob() throws SQLException;

<T> T unwrap(Class<T> iface) throws SQLException;

boolean isWrapperFor(Class<?> iface) throws SQLException;

void clearWarnings() throws SQLException;

Struct createStruct(String typeName, Object[] attributes) throws SQLException;

Array createArrayOf(String typeName, Object[] elements) throws SQLException;

Clob createClob() throws SQLException;

NClob createNClob() throws SQLException;

SQLXML createSQLXML() throws SQLException;

DatabaseMetaData getMetaData() throws SQLException;

boolean getAutoCommit() throws SQLException;

String getCatalog() throws SQLException;

String getClientInfo(String name) throws SQLException;

Properties getClientInfo() throws SQLException;

int getHoldability() throws SQLException;

int getTransactionIsolation() throws SQLException;

Map<String, Class<?>> getTypeMap() throws SQLException;

SQLWarning getWarnings() throws SQLException;

String nativeSQL(String sql) throws SQLException;

CallableStatement prepareCall(String sql) throws SQLException;

CallableStatement prepareCall(String sql, int resultSetType,
		int resultSetConcurrency) throws SQLException;

CallableStatement prepareCall(String sql, int resultSetType,
		int resultSetConcurrency, int resultSetHoldability) throws SQLException;

Statement createStatement(int resultSetType, int resultSetConcurrency)
		throws SQLException;

Statement createStatement(int resultSetType, int resultSetConcurrency,
		int resultSetHoldability) throws SQLException;

PreparedStatement prepareStatement(String sql, int[] columnIndexes)
		throws SQLException;

PreparedStatement prepareStatement(String sql, int resultSetType,
		int resultSetConcurrency) throws SQLException;

PreparedStatement prepareStatement(String sql, int resultSetType,
		int resultSetConcurrency, int resultSetHoldability) throws SQLException;

PreparedStatement prepareStatement(String sql, String[] columnNames)
		throws SQLException;

void releaseSavepoint(Savepoint savepoint) throws SQLException;

void rollback(Savepoint savepoint) throws SQLException;

void setAutoCommit(boolean autoCommit) throws SQLException;

void setCatalog(String catalog) throws SQLException;

void setClientInfo(String name, String value) throws SQLClientInfoException;

void setClientInfo(Properties properties) throws SQLClientInfoException;

void setHoldability(int holdability) throws SQLException;

void setReadOnly(boolean readOnly) throws SQLException;

Savepoint setSavepoint() throws SQLException;

Savepoint setSavepoint(String name) throws SQLException;

void setTransactionIsolation(int level) throws SQLException;

void setTypeMap(Map<String, Class<?>> map) throws SQLException;

}
