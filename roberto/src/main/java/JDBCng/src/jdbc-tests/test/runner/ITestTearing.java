package test.runner;

import java.io.PrintWriter;

public interface ITestTearing
{
	// Tearing tests only (they need a third parameter, see the javadoc 
	// call to runTest()
	void run(PrintWriter out, String dbmsHost, short partOfTheCode) throws Exception;

	static class Runner
	{
		/**
		 * Executes a tearing test. Tearing tests need a third parameter 
		 * to know which part of its code should be executed: 
		 * <ol>
		 * 	<li> Part 1: When a tearing test is launched for the first time, we load delta, 
		 * select star (to ensure the loading has been ok), and do the 
		 * operations tested until the DBMS falls asleep). </li>
		 * 	<li> Part 2: When a tearing test is launched for the second time, we only 
		 * select star and exit. 
		 *	</li>
		 * </ol> 
		 *
		 * @param testname the name of the test class that shall implement ITest
		 * @param out the output where all traces and errors will be printed
		 * @param dbmsHost the dbms host name or address
		 * @param partOfTheCode part of the code that will be executed by the given test class
		 */
		public static void runTest(String testname, PrintWriter out, String dbmsHost, short partOfTheCode)
		{
			if (testname == null) { out.println("ERROR: missing test name"); return; }
			ITestTearing test;
			try
			{
				test = (ITestTearing)(( Class.forName(testname) ).newInstance());
			}
			catch (Exception e)
			{
				out.print("ERROR: failed to instanciate test named ");
				out.println(testname);
				out.print("ExCEPTION: ");
				out.println(e);
				return;
			}
			try
			{
				test.run(out, dbmsHost, partOfTheCode);
			}
			catch (Exception e)
			{
				out.print("ExCEPTION: ");
				e.printStackTrace();
				out.println(e);
			}
		}
	}
}
