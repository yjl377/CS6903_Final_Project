package edu.nyupoly.cs6903.ag3671.file;

import org.codehaus.preon.annotation.Bound;

public class BaseFileStructure {
	
	@Bound
	private int version;

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
