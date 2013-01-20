package net.codeyak.ndse.v2;

import java.util.Arrays;

/**
 * represents rack of tiles.
 * Can pick tiles from bag, and supports taking a list of tiles from the rack.
 * @author dave_blake
 *
 */
public class OORack {

	private final OOTile[] tiles;
	
	public OORack(int size) {
		this.tiles = new OOTile[size];
	}
	
	public OORack(OOTile[] tiles) {
		this.tiles = tiles;
	}

	public void pick(OOBag bag) {
		for (int i=0; i<tiles.length; i++) {
			if (bag.isEmpty()) return;
			if (tiles[i] == null) {
				tiles[i] = bag.pick();
			}
		}
	}
	
	public OOTile[] getTiles() {
		return tiles;
	}
	
	public OOTile[] getActualTiles() {
		int count = getCount();
		if (count == tiles.length) return tiles;
		OOTile[] rv = new OOTile[count];
		int p = 0;
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i] != null) rv[p++] = tiles[i]; 
		}
		return rv;
	}

	public int getSize() {
		return tiles.length;
	}
	
	public int getCount() {
		int count = 0;
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i] != null) count++; 
		}
		return count;
	}

	@Override
	public String toString() {
		return "OORack [tiles=" + Arrays.toString(tiles) + "]";
	}

	public void useTiles(OOTile[] used) {
		for (OOTile tile : used) {
			for (int i=0; i<tiles.length; i++) {
				if (tiles[i] == tile) {
					tiles[i] = null;
					break;
				}
			}
		}
	}

	
}
