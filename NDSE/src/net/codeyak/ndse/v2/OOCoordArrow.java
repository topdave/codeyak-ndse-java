package net.codeyak.ndse.v2;

public enum OOCoordArrow {
	POSITIVE(1), NEGATIVE(-1);
	
	public final int sign;
	
	private OOCoordArrow(int sign) {
		this.sign = sign;
	}
}
