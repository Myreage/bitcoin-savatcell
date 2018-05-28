package org.roberto.javier.PlugDB;

import org.roberto.javier.PlugDB.types.*;
import java.io.PrintWriter;
import org.inria.database.QEPng;
import java.sql.Blob;
import test.jdbc.Tools;
import java.util.ArrayList;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.util.Date;


public class PlugDB extends Tools {
	public PlugDB(String dbmsHost) throws Exception {
		this.out = new PrintWriter(java.lang.System.out);

		// initialize the driver:
		super.init();

		// connect without authentication
		super.openConnection(dbmsHost, null);
	}

	/* Do not forget to call this once the program terminated */
	@Override protected void finalize() throws Throwable {
		// reset the token and shutdown PlugDB:
		super.Shutdown_DBMS();
		out.close();

		super.finalize();
	}

	public void initDB() throws Exception {
		super.Desinstall_DBMS_MetaData();

		// load the DB schema
		String schema = Schema.META;
		// load the DB schema
		super.Install_DBMS_MetaData(schema.getBytes(), 0);
					
		// load and install QEPs
		Class<?>[] executionPlans = new Class[] { QEP.class };
		QEPng.loadExecutionPlans(QEP_IDs.class, executionPlans);
		QEPng.installExecutionPlans(this.db);

		// Ajout d'un wallet
		java.sql.PreparedStatement ps;
		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_WALLET_INSERT);
		ps.setInt(1, 0);
		ps.setBlob(1, new ByteArrayInputStream("cc sava".getBytes()));
		ps.executeUpdate();

		// Ajout d'un utilisateur
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update("ma tÊte quand÷".getBytes());
		byte[] bytes = md.digest("test".getBytes());

		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		String generatedPassword = sb.toString();

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_USER_INSERT);
		ps.setString(1, "test");
		ps.setString(2, generatedPassword);
		ps.executeUpdate();
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

	public List<Address> getAddresses(int idWallet) throws Exception {
		List<Address> ret = new ArrayList<Address>();
		java.sql.PreparedStatement ps;
		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_ADDRESS_SELECT);
		ps.setInt(1, idWallet);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet)ps.executeQuery();
		while (rs.next()) {
			int idGlobal= rs.getInt(1);
			String address = rs.getString(2);
			int balance = rs.getInt(3);
			
			Address a = new Address(idGlobal, address, balance, idWallet);
			ret.add(a);
		}

		return ret;
	}

	public void deleteAddress(String address) throws Exception {
		java.sql.PreparedStatement ps;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_ADDRESS_DELETE);
		ps.setString(1, address);
		ps.executeUpdate();
	}

	public void userSessionInsert(int userId, Date expires, String token) throws Exception {
		java.sql.PreparedStatement ps;

		/* TODO : meilleure méthode pour l'auto increment de l'id */
		int max = 0;
		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_SESSION_SELECT_ALL);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet) ps.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			
			max = (id>max)?id:max;
		}
		max++;

		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_SESSION_INSERT);
		ps.setInt(1, max);
		ps.setString(2, token);
		ps.setInt(3, userId);
		ps.setDate(4, (java.sql.Date)expires);
		ps.executeQuery();
	}

	public int userLogin(String login, String pass) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update("ma tÊte quand÷".getBytes());
		byte[] bytes = md.digest(pass.getBytes());

		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i++) {
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		String generatedPassword = sb.toString();

		java.sql.PreparedStatement ps;
		ps = ((org.inria.jdbc.Connection)super.db).prepareStatement(QEP_IDs.EP_Javier.EP_USER_SELECT);
		ps.setString(1, login.toLowerCase());
		ps.setString(2, generatedPassword);
		org.inria.jdbc.ResultSet rs = (org.inria.jdbc.ResultSet)ps.executeQuery();

		if(!rs.next()) {
			return -1;
		}

		return rs.getInt(1);
	}

	public boolean userLoginSession(String token) throws Exception {
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
