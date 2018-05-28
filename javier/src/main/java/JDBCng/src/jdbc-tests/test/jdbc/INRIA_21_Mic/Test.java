package test.jdbc.INRIA_21_Mic;

/**
 * Test microphone module
 *
 * @author Quentin Lefebvre
 * @date 2014/12/03
 */

import java.io.PrintWriter;

import test.jdbc.Tools;

import test.runner.ITest;

public class Test extends Tools implements ITest {
	 
	@Override
	public void run(PrintWriter out, String dbmsHost) throws Exception {

		this.out = out;
		init();
		openConnection(dbmsHost, null);

		micSimulateRecordedMessages();
		int n = micGetNumberOfRecordedMessages();
		out.println( "Number of recorded messages : " + n + "." );

		for(int i = 0 ; i < n ; i++ )
		{
			byte[] message = micGetFirstRecordedMessage();
			if( message == null )
				out.println( "Microphone is not ready!" );
			else
			{
				out.println( "Message n° " + i + " has size : " + message.length + " bytes." );
				micDeleteFirstRecordedMessage();
			}
			int n2 = micGetNumberOfRecordedMessages();
			out.println( "Number of recorded messages : " + n2 + "." );
		}
		
		Shutdown_DBMS();
	}
}
