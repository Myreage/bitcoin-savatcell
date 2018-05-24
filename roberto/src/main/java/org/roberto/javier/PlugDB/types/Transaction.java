package org.roberto.javier.PlugDB.types;

import java.util.Date;

public class Transaction {
	
	public int idGlobal;
	public int idAddress;
	public String to;
	public int amount;
	public Date date;

	public Transaction(int idGlobal, int idAddress, String to, int amount, Date date) {
		this.idGlobal = idGlobal;
		this.idAddress = idAddress;
		this.to = to;
		this.amount = amount;
		this.date = date;
	}
}
