package net.codeyak.ndse.v1;

import java.util.Random;

/**
 * Low level representation of a bag of tiles
 * 
 * @author dave_blake
 *
 */
public class Bag {

	private final int tileCount;
	
	/**
	 * set of letters
	 */
	private final char[] letters;
	
	/**
	 * reverse lookup of char to position
	 */
	private final int[] lookup = new int[255];
	
	private int remaining;
	
	private final int[] frequency;
	private final int[] position;
	
	private final boolean[] used;
	
	/**
	 * flat list of all tiles AAAABBBCC etc
	 */
	private final char[] tiles;
	
	private Random random = new Random();
	
	public int getRemaining() {
		return remaining;
	}
	
	public int getTileCount() {
		return tileCount;
	}
	
	public Bag(LetterDistribution distribution) {
		this.tileCount = distribution.getTotal();
		this.frequency = new int[tileCount];
		this.position = new int[tileCount];
		this.used = new boolean[tileCount];
		this.remaining = this.tileCount;
		this.letters = distribution.getLetters().toCharArray();
		int pos = 0;
		for (int i=0; i<letters.length; i++) {
			char c = letters[i];
			this.lookup[c] = i;
			int n = distribution.getNumber(c);
			this.position[i] = pos;
			this.frequency[i] = n;
			pos += n;
		}
		this.tiles = distribution.getTiles();
	}
	
	public boolean isEmpty() {
		return remaining == 0;
	}
	
	public char[] pick(int n) {
		if (remaining < n) n = remaining;
		char[] rv = new char[n];
		int rvi = 0;
		while (rvi < n) {
			rv[rvi++] = pick();
		}
		return rv;
	}
	
	public char pick() {
		int next = random.nextInt(remaining);
		int pos = 0;
		for (int i=0; i<used.length; i++) {
			if (used[i]) continue;
			if (pos == next) {
				used[i] = true;
				remaining--;
				return tiles[i];
			}
			pos++;
		}
		return 0;
	}
	
	public void putBack(char[] letters) {
		for (char c : letters) {
			int index = lookup[c];
			int freq = frequency[index];
			int pos = position[index];
			for (int i=pos; i<pos+freq; i++) {
				if (used[i]) {
					used[i] = false;
					break;
				}
			}
			remaining++;
		}
	}
	
}
