package test.runner;

import java.io.PrintWriter;

import test.runner.ITest;

/**
 * A simple servlet to run the JDBC tests
 */
public class MainRunner {

  public static void main(String argv[]) {
    PrintWriter pw = null;
    try {
      pw = new PrintWriter(java.lang.System.out);
//      Tearing tests need another parameter
      if (argv.length == 3){
    	  short partOfTheCode = Short.parseShort(argv[2]);
    	  ITestTearing.Runner.runTest(argv[0], pw, argv[1], partOfTheCode);
      }else{
    	  ITest.Runner.runTest(argv[0], pw, argv[1]);
      }
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }

}
