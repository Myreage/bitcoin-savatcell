package test.jdbc.INRIA_0;

/**
 * Tests wiping the NOR memory of the token
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
    init();
    openConnection(dbmsHost, null);
    Desinstall_DBMS_MetaData();
    Shutdown_DBMS();
  }
}

