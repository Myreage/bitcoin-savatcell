package test.jdbc.INRIA_20_FP;

/**
 * Test fingerprint module
 *
 * @author Quentin Lefebvre
 * @date 2014/06/17
 */

import java.io.PrintWriter;

import test.jdbc.Tools;

import test.runner.ITest;

public class Test extends Tools implements ITest {
	 
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception {

		this.out = out;
		int fId = -1;
		init();
		openConnection(dbmsHost, null);

		// to reset recent operations
		/*Fingerprint_Deauthenticate();*/
		Fingerprint_Deactivate();
		
		// step1: fingerprint module activation
		Fingerprint_Activate();
		
		// step2: waiting fingerprint to authenticate
		out.println( "Please authenticate with your finger" );
		while ( (fId = Fingerprint_GetAuthenticatedId()) == -1 )
		{
			Thread.sleep( 1000 );
		}
		out.println( String.format( "Fingerprint ID:%d authenticated", fId ) );

		Thread.sleep( 2500 );

		// step3: fingerprint de-authentication
//		Fingerprint_Deauthenticate();
		// step4: fingerprint module de-activation
		Fingerprint_Deactivate();

		// step1: fingerprint module activation
/*		Fingerprint_Activate();

		fId = -1;
		// step2: waiting fingerprint to authenticate
		out.println( "Please authenticate with your finger" );
		while ( (fId = Fingerprint_GetAuthenticatedId()) == -1 )
		{
			Thread.sleep( 1000 );
		}
		out.println( String.format( "Fingerprint ID:%d authenticated", fId ) );
*/
		Shutdown_DBMS();
	}
}
