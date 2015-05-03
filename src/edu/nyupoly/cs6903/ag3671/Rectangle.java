package edu.nyupoly.cs6903.ag3671;

import org.codehaus.preon.annotation.Bound;

public class Rectangle {
	@Bound()
	private int x1;
	@Bound
	private int y1;
	//@Bound
	public int x2;
	//@Bound
	public int y2;
	
	@Override
	public String toString(){
		return "" + x1 + "-" + x2 + "-" + y1 + "-" + y2;
	}
}
