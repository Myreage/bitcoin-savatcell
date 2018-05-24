package org.roberto.javier.PlugDB.types;

public class Wallet {
	
	public int idGlobal;
	public byte[] wallet;

	public Wallet(int idGlobal, byte[] wallet) {
		this.idGlobal = idGlobal;
		this.wallet = wallet;
	}
}
