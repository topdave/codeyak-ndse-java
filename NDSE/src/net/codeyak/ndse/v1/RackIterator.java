package net.codeyak.ndse.v1;

/**
 * Produces all combinations of a list of letters
 * @author dave_blake
 *
 */
public class RackIterator {

	private final char[] tiles;
	
	private final int[] current;
	
	private final boolean[] used;
	
	private final char[] selected;
	
	public RackIterator(char[] tiles) {
		this.tiles = tiles;
		this.current = new int[tiles.length];
		for (int i=0; i<current.length; i++) {
			this.current[i] = -1;
		}
		this.used = new boolean[tiles.length];
		this.selected = new char[tiles.length];
	}
	
	public char next(int pos) {
		//check if pos beyond length of array
		if (pos >= tiles.length) return 0;
		//free up those beyond
		free(pos+1);
		//choose next tile in sequence
		int cur = current[pos];
		while (true) {
			cur++;
			if (cur >= tiles.length) return 0;
			if (used[cur]) continue;
			int old = current[pos];
			if (old >= 0)
				used[old] = false;
			current[pos] = cur;
			used[cur] = true;
			return tiles[cur];
		}
	}
	
	public char reset(int pos) {
		//free up those beyond
		free(pos+1);
		return tiles[current[pos]];
	}
	
	private void free(int pos) {
		for (int p=pos; p<tiles.length; p++) {
			int index = current[p];
			if (index == -1) break;
			used[index] = false;
			current[p] = -1;
		}
	}
	
	private int itp = 0;
	
	public char[] getSelected() {
		return selected;
	}
	
	public char next() {
		while (true) {
			if (itp == -1) return 0;
			char c = next(itp);
			selected[itp] = c;
			if (c == 0) {
				itp--;
				continue;
			} else {
				itp++;
				if (itp >= selected.length) itp--;
			}
			return c;
		}
	}

	public int getPivot() {
		return itp;
	}
}
