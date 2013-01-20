package net.codeyak.ndse.v2;

/**
 * enum used to traverse in either a positive or negative direction along a given axis
 * 
 * @author dave_blake
 *
 */
public enum OOCoordArrow {
	POSITIVE(1), NEGATIVE(-1);
	
	public final int sign;
	
	private OOCoordArrow(int sign) {
		this.sign = sign;
	}
}
