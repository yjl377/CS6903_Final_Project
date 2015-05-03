package edu.nyupoly.cs6903.ag3671.file;

import org.codehaus.preon.annotation.Bound;
import org.codehaus.preon.annotation.BoundList;

public class EncryptedFile extends BaseFileStructure {
	
	@Bound
	private int signatureLen;
	
	@BoundList(size = "signatureLen")
	private byte[] signature;
	
	@Bound
	private int keyLen;

	@BoundList(size = "keyLen")
	private byte[] key;
	
	@Bound
	private int payloadLen;
	
	@BoundList(size = "payloadLen")
	private byte[] payload;

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.keyLen = key.length;
		this.key = key;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signatureLen = signature.length;
		this.signature = signature;
	}

	public byte[] getPayload() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payloadLen = payload.length;
		this.payload = payload;
	}
}
