package org.roberto.javier.PlugDB.types;

public class Address {
	
	public int idGlobal;
	public String address;
	public int balance;
	public int idWallet;

	public Address(int idGlobal, String address, int balance, int idWallet) {
		this.idGlobal = idGlobal;
		this.address = address;
		this.balance = balance;
		this.idWallet = idWallet;
	}
}
