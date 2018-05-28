package org.inria.demo2008.test.TEST_1;

import java.io.DataInputStream;
import java.io.PrintWriter;

import test.jdbc.Tools;
import test.jdbc.schemaIndexInfo.Tools_schemaIndexInfo;
import test.runner.ITest;

public class Test extends Tools implements ITest {

  @Override
  public void run(PrintWriter out, String dbmsHost) throws Exception {
    this.out = out;
    Tools_schemaIndexInfo t = new Tools_schemaIndexInfo(out);
    init();
    openConnection(dbmsHost, null);

    String schema = "/org/inria/demo2008/schemaV4.rsc";
    DataInputStream is = new DataInputStream(this.getClass().getResourceAsStream(schema));
    byte[] schemaDesc = t.loadSchema(is);
    is.close();
    int usedId = 404;
    Install_DBMS_MetaData(schemaDesc, usedId);
    schemaDesc = null;
    Shutdown_DBMS();
    
    openConnection(dbmsHost, null);
    Desinstall_DBMS_MetaData();
    Shutdown_DBMS();
  }
}

