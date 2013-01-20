package net.codeyak.ndse.v3;

import net.codeyak.ndse.v1.Rack;

public class FastRack {

	public final byte BLANK = 26;
	
	public FastRack(Rack rack) {
		for (char c : rack.getTiles()) {
			if (c == '_') freqs[BLANK] ++; else freqs[c - 'A']++;
			tiles++;
		}
		
	}

	/**
	 * 0 = A
	 * 25 = Z
	 * 26 = blank
	 */
	byte[] freqs = new byte[27];

	int tiles;
	int p = 0;
	
	int[] positionIds = new int[25]; //ids of where tiles have been played
	byte[] letterCodes = new byte[25]; //letters used
	boolean[] blanks = new boolean[25]; //was letter a blank
		
	public boolean select(byte letterCode, int posId) {
		if (freqs[letterCode] > 0) {
			freqs[letterCode]--;
			letterCodes[p] = letterCode;
			blanks[p] = false;
		} else if (freqs[BLANK] > 0) {
			freqs[BLANK]--;
			letterCodes[p] = BLANK;
			blanks[p] = true;
		} else {
			return false;
		}
		positionIds[p] = posId;
		tiles--;
		p++;
		return true;
	}
	
	public void back() {
		p--;
		boolean blank = blanks[p];
		if (blank) {
			tiles++;
			freqs[BLANK]++;
		} else {
			tiles++;
			freqs[letterCodes[p]]++;
		}
	}
	
	public void record() {
		System.out.println(asString());
	}
	
	public boolean hasTiles() {
		return tiles > 0;
	}
	
	public String toString() {
		return asString();
	}
	
	private String asString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i=0; i<p; i++) {
			if (i > 0) sb.append(",");
			sb.append(positionIds[i]+":"+(char)('A'+letterCodes[i]));
			if (blanks[i]) sb.append("_");
		}
		sb.append("]");
		return sb.toString();
	}
}

