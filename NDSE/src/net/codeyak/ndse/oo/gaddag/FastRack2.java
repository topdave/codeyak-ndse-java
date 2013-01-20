package net.codeyak.ndse.oo.gaddag;

import java.util.Arrays;

import net.codeyak.ndse.v1.Rack;


public final class FastRack2 {

	private final char[] rack;
	
	/**
	 * position ids of where tiles are currently placed
	 */
	private final int[] posIds; 
	
	/**
	 * letters used (records what blank became)
	 */
	private final char[] letters;
	
	private final char[] tiles;
	
	/**
	 * index of tile in tiles
	 */
	private final int[] tileIds;
	
	private int used = 0;
	
	private int tileCount = 0;
	
	IPlayRecorder playRecorder = new PlayRecorderPrinter();
	
	public FastRack2(Rack rack) {
		this(rack.getTiles());
	}
	
	public FastRack2(char[] tiles) {
		this.rack = tiles;
		
		this.posIds = new int[rack.length];
		this.letters = new char[rack.length];
		this.tiles = new char[rack.length];
		this.tileIds = new int[rack.length];

		for (char c : rack) {
			if (c > 0) tileCount++;
		}
	}
	
	public FastRack2(String tiles) {
		this(tiles.toCharArray());
	}
	
	public FastRack2(int len) {
		this(new char[len]);
	}
	
	public char[] getTiles() {
		return rack;
	}
	
	public void select(int tileId, char tile, char letter, int posId) {
		if (rack[tileId] == 0) throw new RuntimeException("no tile available at id "+tileId);
		rack[tileId] = 0;
		tileIds[used] = tileId;
		letters[used] = letter;
		tiles[used] = tile;
		posIds[used] = posId;
		used++;
	}
		
	public void deselect() {
		used--;
		rack[tileIds[used]] = tiles[used];
	}
	
	public void record(int hookId, int dim, int wordScore, int wordMultiplier, int hookScore, int bonusScore) {
		playRecorder.record(hookId, dim, wordScore, wordMultiplier, hookScore, bonusScore, used, tiles, tileIds, letters, posIds);
	}
	
	public void useTiles(char[] usedTiles) {
		skip:for (char t : usedTiles) {
			for (int i=0; i<rack.length; i++) {
				if (rack[i] == t) {
					rack[i] = 0;
					tileCount--;
					continue skip;
				}
			}
			throw new RuntimeException("failed to remove "+t+" from rack");
		}
	}
	
	public void select(FastBag fBag) {
		for (int i=0; i<rack.length; i++) {
			if (fBag.isEmpty()) return;
			if (rack[i] == 0) {
				rack[i] = fBag.next();
				tileCount++;
			}
		}
	}
	
	public String toString() {
		return Arrays.toString(rack);
	}

	public boolean isEmpty() {
		return tileCount == 0;
	}

	public boolean anyUsed() {
		return used > 0;
	}
	
	public boolean allUsed() {
		return used == 7;
	}
}

