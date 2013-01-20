package net.codeyak.ndse.v1;

/**
 * Represents tiles on a rack
 * 
 * @author dave_blake
 *
 */
public class Rack {

	private final char[] tiles;
	
	public Rack(int size) {
		this.tiles = new char[size];
	}
	
	public Rack(String tiles) {
		this.tiles = tiles.toCharArray();
	}
	
	public void pick(Bag bag) {
		for (int i=0; i<tiles.length; i++) {
			if (bag.isEmpty()) return;
			if (tiles[i] == 0) {
				tiles[i] = bag.pick();
			}
		}
	}
	
	public char[] getTiles() {
		return tiles;
	}

	public RackIterator getRackIterator() {
		int count = 0;
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i] > 0) count++; 
		}
		char[] cs = new char[count];
		int pos = 0;
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i] > 0) cs[pos++] = tiles[i]; 
		}
		return new RackIterator(cs);
	}

	public int getSize() {
		return tiles.length;
	}
	
	public int getCount() {
		int count = 0;
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i] > 0) count++; 
		}
		return count;
	}

}
