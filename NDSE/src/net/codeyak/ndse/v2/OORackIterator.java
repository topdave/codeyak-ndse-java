package net.codeyak.ndse.v2;

/**
 * complex class that iterates over a set of tiles, producing all length n combinations.
 * Also supports playing the blank tile.
 * 
 * @author dave_blake
 *
 */
public class OORackIterator {

	private final OOTile[] tiles;
	
	private final int[] current;
	
	private final boolean[] used;
	
	private final OOTile[] selected;
	
	public OORackIterator(OOTile[] tiles, int size) {
		this.tiles = tiles;
		this.current = new int[size];
		for (int i=0; i<current.length; i++) {
			this.current[i] = -1;
		}
		this.used = new boolean[tiles.length];
		this.selected = new OOTile[size];
	}
	
	/**
	 * maintains used and current vectors
	 * 
	 * @param pos
	 * @return next tile to consider
	 */
	public OOTile next(int pos) {
		//check if pos beyond length of array
		if (pos >= current.length) return null;
		//free up those beyond
		free(pos+1);
		
		//handle blanks
		if (selected[pos] != null && selected[pos].isBlank()) {
			if (selected[pos].getLetter() != 'Z') {
				selected[pos].cycleBlank();
				return selected[pos];
			} else {
				selected[pos].resetBlank();
			}
		}
		
		//choose next tile in sequence
		int cur = current[pos];
		while (true) {
			cur++;
			if (cur >= tiles.length) return null;
			if (used[cur]) continue;
			int old = current[pos];
			if (old >= 0)
				used[old] = false;
			current[pos] = cur;
			used[cur] = true;
			return tiles[cur];
		}
	}
	
	public OOTile reset(int pos) {
		//free up those beyond
		free(pos+1);
		return tiles[current[pos]];
	}
	
	private void free(int pos) {
		for (int p=pos; p<current.length; p++) {
			int index = current[p];
			if (index == -1) break;
			used[index] = false;
			current[p] = -1;
		}
	}
	
	private int itp = 0;
	
	public OOTile[] getSelected() {
		return selected;
	}
	
	public OOTile nextSkip() {
		if (itp == -1) return null;
		if (selected[itp] == null || !selected[itp].isBlank()) {
			free(itp);
			selected[itp] = null;
			itp--;
		}
		return next();
	}
	
	public OOTile next() {
		while (true) {
			if (itp == -1) return null;
			OOTile tile = next(itp);
			selected[itp] = tile;
			if (tile == null) {
				itp--;
				continue;
			} else {
				itp++;
				if (itp >= selected.length) itp--;
			}
			return tile;
		}
	}

	public int getPivot() {
		return itp;
	}
}
