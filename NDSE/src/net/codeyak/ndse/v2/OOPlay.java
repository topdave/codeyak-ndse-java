package net.codeyak.ndse.v2;

import java.util.Arrays;

public class OOPlay implements Comparable<OOPlay> {
	private final OOPattern pattern;
	private final OOTile[] tiles;
	
	/**
	 * used to support blanks
	 */
	private final char[] overrides;
	
	private final int score;
			
	void makePlay() {
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i].isBlank()) {
				tiles[i].setBlank(overrides[i]);
			}
		}
		pattern.placeTiles(tiles);
	}
	
	public OOPlay(OOPattern pattern, OOTile... tiles) {
		this.pattern = pattern;
		this.tiles = tiles;
		this.score = pattern.getScore(tiles);
		this.overrides = new char[tiles.length];
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i].isBlank()) {
				overrides[i] = tiles[i].getLetter();
			}
		}
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return "OOPlay [score=" + score + ", pattern=" + pattern + ", tiles="
				+ Arrays.toString(tiles) + "]";
	}
	
	public OOPattern getPattern() {
		return pattern;
	}

	@Override
	public int compareTo(OOPlay that) {
		int rv = that.score - this.score;
		if (rv != 0) return rv;
		return pattern.compareTo(that.pattern);
	}

	public OOTile[] getTiles() {
		return tiles;
	}
	
	
}