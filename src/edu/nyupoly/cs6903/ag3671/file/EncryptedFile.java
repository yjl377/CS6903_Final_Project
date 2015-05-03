package edu.nyupoly.cs6903.ag3671.file;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;

public class EncryptedFile extends BaseFileStructure {
	
	@Bound
	private int keyLen;

	@BoundList(size = "keyLen")
	private byte[] key;
	
	public int getKeyLen() {
		return keyLen;
	}

	public void setKeyLen(int keyLen) {
		this.keyLen = keyLen;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}
}
