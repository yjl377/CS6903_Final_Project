package edu.nyupoly.cs6903.ag3671.test;

import static org.junit.Assert.*;

import java.security.Key;
import java.security.KeyPair;
import java.security.Security;
import java.util.Optional;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Before;
import org.junit.Test;

import edu.nyupoly.cs6903.ag3671.Cryptor;
import edu.nyupoly.cs6903.ag3671.crypto.CryptoImpl;
import edu.nyupoly.cs6903.ag3671.crypto.KeyChainImpl;

public class CryptoTest {
	
	public static final String DATA =
	        "Usage: ftp [options] <hostname> <username> <password> [<remote file> [<local file>]]\n" +
	        "\nDefault behavior is to download a file and use ASCII transfer mode.\n" +
	        "\t-a - use local active mode (default is local passive)\n" +
	        "\t-A - anonymous login (omit username and password parameters)\n" +
	        "\t-b - use binary transfer mode\n" +
	        "\t-c cmd - issue arbitrary command (remote is used as a parameter if provided) \n" +
	        "\t-d - list directory details using MLSD (remote is used as the pathname if provided)\n" +
	        "\t-e - use EPSV with IPv4 (default false)\n" +
	        "\t-f - issue FEAT command (remote and local files are ignored)\n" +
	        "\t-h - list hidden files (applies to -l and -n only)\n" +
	        "\t-k secs - use keep-alive timer (setControlKeepAliveTimeout)\n" +
	        "\t-l - list files using LIST (remote is used as the pathname if provided)\n" +
	        "\t     Files are listed twice: first in raw mode, then as the formatted parsed data.\n" +
	        "\t-L - use lenient future dates (server dates may be up to 1 day into future)\n" +
	        "\t-n - list file names using NLST (remote is used as the pathname if provided)\n" +
	        "\t-p true|false|protocol[,true|false] - use FTPSClient with the specified protocol and/or isImplicit setting\n" +
	        "\t-s - store file on server (upload)\n" +
	        "\t-t - list file details using MLST (remote is used as the pathname if provided)\n" +
	        "\t-w msec - wait time for keep-alive reply (setControlKeepAliveReplyTimeout)\n" +
	        "\t-T  all|valid|none - use one of the built-in TrustManager implementations (none = JVM default)\n" +
	        "\t-PrH server[:port] - HTTP Proxy host and optional port[80] \n" +
	        "\t-PrU user - HTTP Proxy server username\n" +
	        "\t-PrP password - HTTP Proxy server password\n" +
	        "\t-# - add hash display during transfers\n";

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

	@Test
	public void testFileCryptor() throws Exception {
		CryptoImpl cryptoImpl = new CryptoImpl();
		KeyPair kp = cryptoImpl.genKeyPair();
		KeyPair kpSing = cryptoImpl.genKeyPair();
		KeyChainImpl chain = new KeyChainImpl(kp, kpSing);
		Cryptor crypt = new Cryptor(chain);
		
		byte[] ciperText = crypt.encrypt(DATA.getBytes());
		Optional<byte[]> clearText = crypt.decrypt(ciperText);
		
		assertTrue(clearText.isPresent());
		assertArrayEquals(clearText.get(), DATA.getBytes());
	}
	
	@Test
	public void testFileCryptor2() throws Exception {
		CryptoImpl cryptoImpl = new CryptoImpl();
		KeyPair kp = cryptoImpl.genKeyPair();
		KeyPair kpSing = cryptoImpl.genKeyPair();
		KeyChainImpl chain = new KeyChainImpl(kp, kpSing);
		Cryptor crypt = new Cryptor(chain);
		
		byte[] ciperText = crypt.encrypt(DATA.getBytes());
		ciperText[100] = (byte) (ciperText[100] + 1);
		Optional<byte[]> clearText = crypt.decrypt(ciperText);
		
		assertFalse(clearText.isPresent());
	}
}
