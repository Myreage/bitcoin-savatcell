package org.inria.database;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;

import org.inria.jdbc.Blob;
import org.inria.jdbc.Statement;
import org.inria.jdbc.ResultSetMetaData;

/**
 * @author Aydogan Ersoz
 */
public class QEPng
{
	// System QEPs
	private static final int EP_QEP_INSERT = 0;						// This id is reserved for QEP insertion
	public static final int SYSTEM_QEP_CNT = EP_QEP_INSERT + 1;		// Total number of system QEPs
	
	// Internal variables
	private static ArrayList<EP> epList = new ArrayList<EP>();
	private static boolean plansLoaded = false;
	private static final int EP_QEP_INSERT_PARAM_CNT = 4; 			// IdGlobal QEPStr SQLStr ExplainStr
	private static String noMetaData, noStaticPlan = "";
	private static int noParamCnt = 0;
	
	protected static class EP
	{
		protected String name;
		protected int id;
		protected int param_cnt;
		protected String meta;
		protected String static_plan;
	}

	/**
	 * Loads classes that contain execution plans
	 * @param idClass			Class that contain ids of execution plans
	 * @param planClasses		Classes that contain execution plans
	 * @throws 					SQLException 
	 * @throws 					IllegalAccessException 
	 * @throws 					IllegalArgumentException 
	 */
	public static void loadExecutionPlans( Class<?> idClass, Class<?>[] planClasses ) throws IllegalArgumentException, IllegalAccessException, SQLException
	{			
		if ( !plansLoaded )
		{
			for ( Class<?> c : planClasses ) // iterate through provided classes
			{
				for ( Field field : c.getDeclaredFields() ) // iterate through fields of current class
				{				
				    if ( field.getType().toString().contains("String") ) // if field type is String
				    {
				        String qep = (String)field.get( null );
				        String string_full_name = field.toString();
				        String[] substring = string_full_name.split( java.util.regex.Pattern.quote(".") );
				        if ( substring.length > 0 )
				        {
				        	String string_name = substring[ substring.length - 1 ];
				        	String class_name = substring[ substring.length - 2 ];
				        	EP ep = new EP();
				        	ep.name = string_name;
				        	ep.id = setId( string_name, class_name, idClass );
				        	ep.param_cnt = setParamCnt( qep );
				        	ep.meta = setMetaData( qep );
				        	ep.static_plan = splitStaticPlan( qep );			        	
				        	epList.add( ep ); // add execution plan to the list
				        }
				    }
				}
			}

			plansLoaded = true;
		}
	}
	
	/**
	 * Inserts all QEPs into system QEP table
	 * @param db		Database connection
	 * @throws 			Exception 
	 */
	public static void installExecutionPlans( java.sql.Connection db ) throws Exception
	{
		if ( !plansLoaded )
			throw new SQLException( "QEPs must be loaded before the installation." );
		
		java.sql.PreparedStatement ps = ((org.inria.jdbc.Connection)db).prepareStatement( EP_QEP_INSERT );
		for ( EP q : epList ) // iterate through list
			insertExecPlan( q.id, q.static_plan, null, null, ps ); // insert QEP tuple
	}
	
	/**
	 * Assigns id to each QEP
	 * @param string_name	Name of the string variable
	 * @param class_name	Class name of the string
	 * @param idClass		Class that contain ids of execution plans
	 * @return				QEP id
	 */
	private static int setId( String string_name, String class_name, Class<?> idClass )
	{
		for ( Class<?> c : idClass.getClasses() ) // iterate through QEPng classes
		{
			for ( Field field : c.getDeclaredFields() ) // iterate through fields of current class
			{
				if ( field.getType().toString().contains("int") ) // if field type is Integer
				{
					int int_value;
					try
					{
						int_value = (Integer)field.get( null );
					}
					catch (Exception e)
					{
						continue;
					}
			        String int_full_name = field.toString();
			        int last_index = int_full_name.lastIndexOf('.');
			        if ( last_index > 0 )
			        {
			        	if ( string_name.equals( int_full_name.substring( last_index + 1 ) ) &&
			        			c.getName().contains( class_name ) )
			        		return int_value;
			        }
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * Splits parameter count from given QEP string
	 * @param qep		QEP string
	 * @return			Parameter count
	 * @throws 			SQLException
	 */
	private static int setParamCnt( String qep ) throws SQLException
	{
		if (qep != null )
			return qep.charAt( Statement.EP_START.length() );
		
		return noParamCnt;
	}
	
	/**
	 * Splits meta data from given QEP string
	 * @param qep		QEP string
	 * @return			Meta data
	 * @throws 			SQLException 
	 */
	private static String setMetaData( String qep ) throws SQLException
	{
		if ( qep != null )
		{
			int index = qep.indexOf( ResultSetMetaData.META_START, 7 );
			if ( index != -1 )
			{
				int meta_start = index + ResultSetMetaData.META_START_LEN;
				return qep.substring( meta_start, qep.indexOf( Statement.EP_STOP, meta_start ) );
			}
		}
		
		return noMetaData;
	}
	
	/**
	 * Splits static plan from given QEP string
	 * @param qep		QEP string
	 * @return			Static plan
	 * @throws 			SQLException 
	 */
	private static String splitStaticPlan( String qep ) throws SQLException
	{
		if ( qep != null )
		{
			int beginIndex = Statement.EP_START.length() + 1 + 1;
			int endIndex = qep.indexOf( ResultSetMetaData.META_START, 7 );
			if ( endIndex <= beginIndex )
				endIndex = qep.indexOf( Statement.EP_STOP, beginIndex );
			return qep.substring( beginIndex, endIndex ) + "\u0000";
		}
		
		return noStaticPlan;
	}
	
	/**
	 * Gets parameter count of the requested QEP id
	 * @param qepId		QEP number
	 * @return			Parameter count
	 * @throws 			SQLException 
	 */
	public static int getParamCnt( int qepId ) throws SQLException
	{
	    if ( qepId == EP_QEP_INSERT ) // this QEP is hardcoded in SGBD code
	    	return EP_QEP_INSERT_PARAM_CNT;
	    
		for ( EP q : epList ) // iterate through list
		{
		    if ( q.id == qepId ) // find related QEP associated with q.id
		    	return q.param_cnt;
		}
		
		throw new SQLException( "QEP id " + qepId + " is not available as it hasn't been inserted into database yet." );
	}
	
	/**
	 * Gets meta data of the requested QEP id
	 * @param qepId		QEP number
	 * @return			Meta data
	 * @throws 			SQLException 
	 */	
	public static String getMetaData( int qepId ) throws SQLException
	{
		for ( EP q : epList ) // iterate through list
		{
		    if ( q.id == qepId ) // find related QEP associated with q.id
		    	return q.meta;
		}
		
		throw new SQLException( "QEP id " + qepId + " is not available as it hasn't been inserted into database yet." );
	}
	
	/**
	 * Inserts a tuple to system QEP table
	 * @param IdGlobal		IdGlobal
	 * @param QEPStr		QEP string
	 * @param SQLStr		SQL string
	 * @param ExplainStr	Explain string
	 * @param Pstmt			Prepared statement
	 * @throws 				SQLException 
	 */
	private static void insertExecPlan( int IdGlobal, String QEPStr,
			Blob SQLStr, Blob ExplainStr, 
 			java.sql.PreparedStatement Pstmt ) throws SQLException
	{
 		Pstmt.setInt( 1, IdGlobal );
 		Pstmt.setString( 2, QEPStr );
 		Pstmt.setBlob( 3, SQLStr );
 		Pstmt.setBlob( 4, ExplainStr );
 		Pstmt.executeUpdate();
 	}
}
