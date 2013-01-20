package net.codeyak.ndse.v1;

import java.util.Arrays;

/**
 * Low level record of a play
 * 
 * @author dave_blake
 *
 */
public class Placement {

	int[] positions;
	char[] letters;
	
	public Placement(int size, int[] positions, char[] letters) {
		this.positions = Arrays.copyOf(positions, size);
		this.letters = Arrays.copyOf(letters, size);
	}
	
}
