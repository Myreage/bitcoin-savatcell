package org.roberto.javier.PlugDB.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Transaction {
	
	public int idGlobal;
	public String address;
	public String to;
	public int amount;
	public Date date;

	public Transaction() {
		super();
	}

	public Transaction(int idGlobal, String address, String to, int amount, Date date) {
		this.idGlobal = idGlobal;
		this.address = address;
		this.to = to;
		this.amount = amount;
		this.date = date;
	}
}
