package edu.nyupoly.cs6903.ag3671.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class CryptoImpl {
	
	public String getAlgorithm() {
		return "RSA";
	}
	
	public KeyPair genKeyPair() throws Exception {
		SecureRandom random = new SecureRandom();
		KeyPairGenerator fact;
		fact = KeyPairGenerator.getInstance(getAlgorithm(), "BC");
		fact.initialize(2048, random);
		KeyPair keyPair = fact.generateKeyPair();
		return keyPair;
	}
	
}
