package edu.nyupoly.cs6903.ag3671;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import edu.nyupoly.cs6903.ag3671.crypto.CryptoImpl;
import edu.nyupoly.cs6903.ag3671.crypto.KeyChainImpl;


public class KeyStorage {
	
	private static final String PUBLIC_SIGN_KEY = "publicSign.key";
	private static final String PRIVATE_SIGN_KEY = "privateSign.key";
	public static final String PUBLIC_KEY = "public.key";
	public static final String PRIVATE_KEY = "private.key";
	public static final File DEFAULT_PATH = new File(".");
	
	private final CryptoImpl crypto = new CryptoImpl();
	
	public String genKeys() throws Exception {
		return genKeys(DEFAULT_PATH);
	}
	
	public String genKeys(File path) throws Exception {
		KeyPair keyPair = crypto.genKeyPair();
		FileOutputStream fos;
		
		X509EncodedKeySpec privateKey = new X509EncodedKeySpec(keyPair.getPrivate().getEncoded());		
		fos = new FileOutputStream(new File(path, PRIVATE_KEY));
		fos.write(privateKey.getEncoded());
		fos.close();
		
		X509EncodedKeySpec publicKey = new X509EncodedKeySpec(keyPair.getPublic().getEncoded());		
		fos = new FileOutputStream(new File(path, PUBLIC_KEY));
		fos.write(publicKey.getEncoded());
		fos.close();
		
		KeyPair keyPairSign = crypto.genKeyPair();
		
		X509EncodedKeySpec privateSign = new X509EncodedKeySpec(keyPairSign.getPrivate().getEncoded());		
		fos = new FileOutputStream(new File(path, PRIVATE_SIGN_KEY));
		fos.write(privateSign.getEncoded());
		fos.close();
		
		X509EncodedKeySpec publicSign = new X509EncodedKeySpec(keyPairSign.getPublic().getEncoded());		
		fos = new FileOutputStream(new File(path, PUBLIC_SIGN_KEY));
		fos.write(publicSign.getEncoded());
		fos.close();
		
		return path.getCanonicalPath();
	}
	
	public KeyChainImpl readKeys() throws Exception {
		return readKeys(DEFAULT_PATH);
	}
	
	public KeyChainImpl readKeys(File path) throws Exception {
		
		FileInputStream fis;
		KeyFactory keyFactory = KeyFactory.getInstance(crypto.getAlgorithm());
		
		File filePrivateKey = new File(path, PRIVATE_KEY);
		fis = new FileInputStream(filePrivateKey);
		byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
		fis.read(encodedPrivateKey);
		fis.close();
		
		File filePublicKey = new File(path, PUBLIC_KEY);
		fis = new FileInputStream(filePublicKey);
		byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
		fis.read(encodedPublicKey);
		fis.close();
		
		File filePrivateSignKey = new File(path, PRIVATE_SIGN_KEY);
		fis = new FileInputStream(filePrivateSignKey);
		byte[] encodedPrivateSignKey = new byte[(int) filePrivateSignKey.length()];
		fis.read(encodedPrivateSignKey);
		fis.close();
		
		File filePublicSignKey = new File(path, PUBLIC_SIGN_KEY);
		fis = new FileInputStream(filePublicSignKey);
		byte[] encodedPublicSignKey = new byte[(int) filePublicSignKey.length()];
		fis.read(encodedPublicSignKey);
		fis.close();
		
		// Generate KeyPair.
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
				encodedPublicKey);
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
				encodedPrivateKey);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

		// Generate Sign KeyPair.
		X509EncodedKeySpec publicKeySignSpec = new X509EncodedKeySpec(
				encodedPublicKey);
		PublicKey publicSignKey = keyFactory.generatePublic(publicKeySignSpec);

		PKCS8EncodedKeySpec privateKeySignSpec = new PKCS8EncodedKeySpec(
				encodedPrivateKey);
		PrivateKey privateSignKey = keyFactory
				.generatePrivate(privateKeySignSpec);
		
		
		return new KeyChainImpl(new KeyPair(publicKey, privateKey),
				new KeyPair(publicSignKey, privateSignKey));
	}
}
