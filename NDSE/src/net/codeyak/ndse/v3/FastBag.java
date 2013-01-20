package net.codeyak.ndse.v3;

import java.util.Random;

import net.codeyak.ndse.v1.LetterDistribution;


/**
 * Contains a fixed list of letters
 * These can be randomly shuffled.
 * Returns letters in a fixed order.
 * 
 * @author dave_blake
 *
 */
public class FastBag {

	private char[] tiles;
	
	private int p = 0;
	
	public FastBag(LetterDistribution ld) {
		this.tiles = ld.getTiles();
	}
	
	public void shuffle(Random seed) {
		for (int i=0; i<tiles.length; i++) {
			char c = tiles[i];
			int swapIndex = seed.nextInt(tiles.length);
			tiles[i] = tiles[swapIndex];
			tiles[swapIndex] = c;
		}
	}
	
	public char next() {
		return tiles[p++];
	}
	
	public boolean isEmpty() {
		return p == tiles.length;
	}
	
}
