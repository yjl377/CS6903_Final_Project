package edu.nyupoly.cs6903.ag3671.test;

import static org.junit.Assert.*;

import java.security.Key;
import java.security.KeyPair;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

import edu.nyupoly.cs6903.ag3671.crypto.CryptoImpl;

public class CryptoTest {

	@Before
	public void init() {
		Security.addProvider(new BouncyCastleProvider());
	}

	@Test
	public void testWrappedKey() throws Exception {
		CryptoImpl cryptoImpl = new CryptoImpl();
		KeyPair kp = cryptoImpl.genKeyPair();
		Key clearKey = cryptoImpl.createRandomKeyForAES();
		byte[] aesKey = cryptoImpl.getEncryptedBlockCipherKey(kp, clearKey);
		assertArrayEquals(clearKey.getEncoded(), cryptoImpl.decryptBlockCipherKey(kp, aesKey));
	}
	
	@Test
	public void testAesCrypto() throws Exception {
		CryptoImpl cryptoImpl = new CryptoImpl();
		Key clearKey = cryptoImpl.createRandomKeyForAES();
		byte[] data = "Yo Yo Hello world".getBytes();
		
		byte[] ctext = cryptoImpl.encryptWithBlockCipher(clearKey.getEncoded(), data);
		assertArrayEquals(data , cryptoImpl.decryptWithBlockCipher(clearKey.getEncoded(), ctext));
	}
	
	@Test
	public void testSign() throws Exception {
		byte[] data = "Yo Yo Hello world".getBytes();
		CryptoImpl cryptoImpl = new CryptoImpl();
		KeyPair kp = cryptoImpl.genKeyPair();
		byte[] sign = cryptoImpl.sign(data, kp);
		assertTrue(cryptoImpl.signVerify(data, sign, kp));
	}

}
