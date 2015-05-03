package edu.nyupoly.cs6903.ag3671.crypto;

import java.security.KeyPair;

public class KeyChainImpl {
	private final KeyPair keys;
	
	private final KeyPair signKeys;
	
	public KeyChainImpl(KeyPair keys, KeyPair signKeys) {
		this.keys = keys;
		this.signKeys = signKeys;
	}

	public KeyPair getKeys() {
		return keys;
	}

	public KeyPair getSignKeys() {
		return signKeys;
	}
}
