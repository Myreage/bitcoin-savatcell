package org.roberto.javier.PlugDB;

import org.roberto.javier.PlugDB.types.*;
import java.io.PrintWriter;
import org.inria.database.QEPng;
import java.sql.Blob;
import test.jdbc.Tools;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.Date;


public class PlugDB extends Tools {
	public PlugDB(String dbmsHost) throws Exception {
		this.out = new PrintWriter(java.lang.System.out);

		// initialize the driver:
		super.init();

		// connect without authentication
		super.openConnection(dbmsHost, null);
		// load the DB schema
		String schema = QEP.META;

		// load the DB schema
		super.Install_DBMS_MetaData(schema.getBytes(), 0);
					
		// load and install QEPs
		Class<?>[] executionPlans = new Class[] { QEP.class };
		QEPng.loadExecutionPlans(QEP_IDs.class, executionPlans);
		QEPng.installExecutionPlans(this.db);
	}

	/* Do not forget to call this once the program terminated */
	public void close() throws Exception {
		// reset the token and shutdown PlugDB:
		super.Desinstall_DBMS_MetaData();
		super.Shutdown_DBMS();
		out.close();
	}

	public List<Wallet> getWallets() throws Exception {
		List<Wallet> ret = new ArrayList<Wallet>();
		java.sql.PreparedStatement ps;
		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_WALLET_SELECT);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet) ps.executeQuery();
		while(rs.next()) {
			int idGlobal= rs.getInt(1);
			Blob b = rs.getBlob(2);
			byte[] wallet = b.getBytes(1, (int)b.length());
			Wallet w = new Wallet(idGlobal, wallet);
			ret.add(w);
		}

		return ret;
	}

	public void updateWallet(Wallet w) throws Exception {
		java.sql.PreparedStatement ps;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_WALLET_UPDATE);
		ps.setBlob(1, new ByteArrayInputStream(w.wallet));
		ps.setInt(2, w.idGlobal);
		ps.executeUpdate();
	}

	public List<Transaction> getTransactions(String address) throws Exception {
		List<Transaction> ret = new ArrayList<Transaction>();
		java.sql.PreparedStatement ps;
		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_TRANSACTION_SELECT);
		ps.setString(1, address);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet) ps.executeQuery();
		while (rs.next()) {
			int idGlobal= rs.getInt(1);
			String taddress = rs.getString(2);
			String to = rs.getString(3);
			int amount = rs.getInt(4);
			Date date = rs.getDate(5);
			
			Transaction t = new Transaction(idGlobal, taddress, to, amount, date);
			ret.add(t);
		}

		return ret;
	}

	public void addTransaction(Transaction t) throws Exception {
		java.sql.PreparedStatement ps;

		/* TODO : meilleure méthode pour l'auto increment de l'id */
		int max = 0;
		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_TRANSACTION_SELECT_ALL);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet) ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			
			max = (id>max)?id:max;
		}
		max++;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_TRANSACTION_INSERT);
		ps.setInt(1, max);
		ps.setString(2, t.address);
		ps.setString(3, t.to);
		ps.setInt(4, t.amount);
		ps.setDate(5, (java.sql.Date)t.date);
		ps.executeUpdate();
	}

	public void addAddress(Address a) throws Exception {
		java.sql.PreparedStatement ps;

		/* TODO : meilleure méthode pour l'auto increment de l'id */
		int max = 0;
		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_ADDRESS_SELECT_ALL);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet) ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			
			max = (id>max)?id:max;
		}
		max++;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_ADDRESS_INSERT);
		ps.setInt(1, max);
		ps.setString(2, a.address);
		ps.setInt(3, a.balance);
		ps.setInt(4, a.idWallet);
		ps.executeUpdate();
	}

	public void deleteAddress(String address) throws Exception {
		java.sql.PreparedStatement ps;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_ADDRESS_DELETE);
		ps.setString(1, address);
		ps.executeUpdate();
	}

	public boolean loginUser(String login, String pass) throws Exception {
		String passHash = Base64.getEncoder().encode(pass.getBytes()).toString();
		java.sql.PreparedStatement ps;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_USER_SELECT);
		ps.setString(1, login.toLowerCase());
		ps.setString(2, passHash);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet)ps.executeQuery();

		/* TODO : generate session token */

		return rs.next();
	}

	public boolean loginUserSession(String token) throws Exception {
		java.sql.PreparedStatement ps;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_SESSION_SELECT);
		ps.setString(1, token);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet)ps.executeQuery();

		if(!rs.next()) {
			return false;
		}

		Date d = rs.getDate(2), dnow = new Date();
		if(d.before(dnow)) {
			return false;
		}

		return true;
	}


	public boolean loginWalter(String token) throws Exception {
		java.sql.PreparedStatement ps;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_WALTER_SELECT);
		ps.setString(1, token);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet)ps.executeQuery();

		if(!rs.next()) {
			return false;
		}

		Date d = rs.getDate(2), dnow = new Date();
		if(d.before(dnow)) {
			return false;
		}

		return true;
	}
}
