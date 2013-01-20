package net.codeyak.ndse.v2;

import java.util.Arrays;
import java.util.Random;

import net.codeyak.ndse.v1.LetterDistribution;
import net.codeyak.ndse.v1.LetterValuation;


/**
 * Generates a Bag of Tiles based upon a set of letter frequencies and letter values.
 * Supports picking n tiles are random from the bag.
 * 
 * @author dave_blake
 *
 */
public class OOBag {
	
	private final Random random = new Random();
	
	private final OOTile[] tiles;
	
	private final boolean[] used;
	
	private int remaining;
	
	public OOBag(LetterDistribution ld, LetterValuation lv) {
		char[] cs = ld.getTiles();
		tiles = new OOTile[cs.length];
		used = new boolean[tiles.length];
		for (int i=0; i<tiles.length; i++) {
			tiles[i] = new OOTile(i, cs[i], lv.getNumber(cs[i]));
		}
		remaining = tiles.length;
	}

	public boolean isEmpty() {
		return remaining == 0;
	}
	
	public OOTile[] pick(int n) {
		if (remaining < n) n = remaining;
		OOTile[] rv = new OOTile[n];
		int rvi = 0;
		while (rvi < n) {
			rv[rvi++] = pick();
		}
		return rv;
	}
	
	public OOTile pick() {
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
		return null;
	}
	
	public void putBack(OOTile[] tiles) {
		for (OOTile tile : tiles) {
			used[tile.id] = false; 
			remaining++;
		}
	}
	
	public String toString() {
		return "remaining "+remaining+" "+Arrays.toString(tiles);
	}

}
