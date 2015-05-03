package edu.nyupoly.cs6903.ag3671.crypto;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;


public class CryptoImpl {
	
	public String getAlgorithm() {
		return "RSA";
	}
	
	public String getSignAlgorithm() {
		return "SHA256withRSA";
	}
	
	public String getPadding() {
		return "RSA/NONE/OAEPWithSHA1AndMGF1Padding";
	}
	
	public KeyPair genKeyPair() throws Exception {
		SecureRandom random = new SecureRandom();
		KeyPairGenerator fact;
		fact = KeyPairGenerator.getInstance(getAlgorithm(), "BC");
		fact.initialize(2048, random);
		KeyPair keyPair = fact.generateKeyPair();
		return keyPair;
	}
	
	public byte[] sign(byte[] payload, KeyPair keypair) throws Exception {
		Signature signature = Signature.getInstance(getSignAlgorithm(), "BC");
		signature.initSign(keypair.getPrivate(), new SecureRandom());
        signature.update(payload);
        return signature.sign();	
	}
	
	public boolean signVerify(byte[] payload, byte[] sigBytes, KeyPair keyPair) throws Exception {
		Signature signature = Signature.getInstance(getSignAlgorithm(), "BC");
		signature.initVerify(keyPair.getPublic());
        signature.update(payload);
        return signature.verify(sigBytes);
	}
	
	public byte[] getEncryptedBlockCipherKey(KeyPair keyPair, Key wrappedKey) throws Exception {
        // Cipher cipher = Cipher.getInstance(getPadding(), "BC");
        Cipher cipher = Cipher.getInstance(getPadding(), "BC");
        cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

        return cipher.doFinal(wrappedKey.getEncoded());
	}
	
	public byte[] getEncryptedBlockCipherKey(KeyPair keyPair) throws Exception {
		return getEncryptedBlockCipherKey(keyPair, createRandomKeyForAES());
	}
	
	public byte[] decryptBlockCipherKey(KeyPair keyPair, byte[] payload) throws Exception {
		Cipher cipher = Cipher.getInstance(getPadding(), "BC");
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate() );
        return cipher.doFinal(payload);
	}
	
	public Key createRandomKeyForAES() throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance("AES", "BC");
        generator.init(256, new SecureRandom());
        return generator.generateKey();
	}
	
}
