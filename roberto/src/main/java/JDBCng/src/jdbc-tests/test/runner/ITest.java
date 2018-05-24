package test.runner;

import java.io.PrintWriter;

public interface ITest {

  void run(PrintWriter out, String dbmsHost) throws Exception;

  static class Runner {

    /**
     * Executes a test
     *
     * @param testname the name of the test class that shall implement ITest
     * @param out the output where all traces and errors will be printed
     * @param dbmsHost the dbms host name or address
     */
    public static void runTest(String testname, PrintWriter out, String dbmsHost) {
      if (testname == null) {
        out.println("ERROR: missing test name");
        return;
      }
      ITest test;
      try {
    	  test = (ITest)( Class.forName(testname) ).newInstance();
      } catch (Exception e) {
        out.print("ERROR: failed to instanciate test named ");
        out.println(testname);
        out.print("ExCEPTION: ");
        out.println(e);
        return;
      }
      try {
        test.run(out, dbmsHost);
      } catch (Exception e) {
        out.print("ExCEPTION: ");
        e.printStackTrace();
        out.println(e);
      }
    }
  }
}
