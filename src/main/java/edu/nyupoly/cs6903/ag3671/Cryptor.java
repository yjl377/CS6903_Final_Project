package edu.nyupoly.cs6903.ag3671;

import java.security.Key;
import java.util.Optional;

import edu.nyupoly.cs6903.ag3671.crypto.CryptoImpl;
import edu.nyupoly.cs6903.ag3671.crypto.KeyChainImpl;
import edu.nyupoly.cs6903.ag3671.file.EncryptedFile;
import edu.nyupoly.cs6903.ag3671.file.MyCodec;

public class Cryptor {

	private final KeyChainImpl keyChain;
	private final CryptoImpl crypto;
	private final MyCodec<EncryptedFile> codec;

	public Cryptor(KeyChainImpl keyChain) {
		this.keyChain = keyChain;
		this.crypto = new CryptoImpl();
		this.codec = new MyCodec<EncryptedFile>(EncryptedFile.class);
	}

	public byte[] encrypt(byte[] payload) throws Exception {
		EncryptedFile file = new EncryptedFile();

		Key blockCipherKey = getCrypto().createRandomKeyForAES();
		file.setKey(getCrypto().getEncryptedBlockCipherKey(getKeyChain().getKeys(), blockCipherKey));
		file.setPayload(getCrypto().encryptWithBlockCipher(blockCipherKey.getEncoded(), payload));
		
		file.setSignature(getCrypto().sign(file.getPayload(), getKeyChain().getSignKeys()));
		

		return getCodec().encode(file);
	}
	
	public Optional<byte[]> decrypt(byte[] payload) throws Exception {
		Optional<byte[]> returnVal = Optional.empty();
		Optional<EncryptedFile> opt = getCodec().decode(payload);
		if(opt.isPresent()) {
			EncryptedFile file = opt.get();
			
			boolean valid = getCrypto().signVerify(file.getPayload(), file.getSignature(), getKeyChain().getSignKeys());
			if(valid) {
				byte[] key = getCrypto().decryptBlockCipherKey(getKeyChain().getKeys(), file.getKey());
				byte[] clear = getCrypto().decryptWithBlockCipher(key, file.getPayload());
				returnVal = Optional.of(clear);
			}
			else {
				System.out.println("Encryped file signature verification failed.");
			}
			
		}
		else {
			System.out.println("Encryped file format is not supported.");
		}
		
		return returnVal;
	}

	public KeyChainImpl getKeyChain() {
		return keyChain;
	}

	public CryptoImpl getCrypto() {
		return crypto;
	}

	public MyCodec<EncryptedFile> getCodec() {
		return codec;
	}
}
