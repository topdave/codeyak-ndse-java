package net.codeyak.ndse.v1;

/**
 * Initial attempt at capturing board state
 */
public class BoardState {

	private final int height;
	private final int width;
	
	private final char[] tiles;
	
	boolean firstPlay;
	
	WordList wordList;
	
	public BoardState(Board board) {
		this.height = board.getHeight();
		this.width = board.getWidth();
		this.tiles = new char[height * width];
		
		potentialHooksReusable = new int[height * width];
		fixesReusable = new char[height + width];
	}
	
	
	@SuppressWarnings("unused")
	public void generatePotentialPlacementsSimple(Rack rack) {
		char[] newTiles = tiles.clone();
		for (int i=0; i<tiles.length; i++) {
			if (tiles[i] > 0) continue;
			char[] letters = rack.getTiles();
			
		}
	}
	
	public void getScore(char[] newTiles) {
		
	}
	
	public void generatePotentialPlacements(Rack rack) {
		int[] hookPoints = getPotentialHookPoints();
		for (int hookPoint : hookPoints) {
			if (hookPoint == -1) break;
			String verticalPrefix = getVerticalPrefix(hookPoint);
			String verticalSuffix = getVerticalSuffix(hookPoint);
			String horizontalPrefix = getHorizontalPrefix(hookPoint);
			String horizontalSuffix = getHorizontalSuffix(hookPoint);
			
			//
			RackIterator it = rack.getRackIterator();
			
			char c = it.next();
			String vw = verticalPrefix + c + verticalSuffix;
			String hw = horizontalPrefix + c + horizontalSuffix;
			boolean tryHorizontal = false;
			boolean tryVertical = false;
			if (wordList.isWord(vw)) tryHorizontal = true;
			if (wordList.isWord(hw)) tryVertical = true;
			//special treatment of initial play (can't both be of length 1)
			boolean validPlay = tryHorizontal && tryVertical && (vw.length() > 1 && hw.length() > 1);
			if (validPlay) record();
		}
	}
	
	private void record() {
		
	}
	
	private String EMPTY = "";
	
	private char[] fixesReusable;
	
	private String getVerticalPrefix(int point) {
		int pos = 0;
		while (point / width > 0 && tiles[point-width] > 0) {
			point -= width;
			fixesReusable[pos++] = tiles[point];
		}
		if (pos == 0) return EMPTY;
		return new String(fixesReusable, 0, pos);
	}
	
	private String getVerticalSuffix(int point) {
		int pos = 0;
		while (point / width < height-1 && tiles[point+width] > 0) {
			point += width;
			fixesReusable[pos++] = tiles[point];
		}
		if (pos == 0) return EMPTY;
		return new String(fixesReusable, 0, pos);
	}
	
	private String getHorizontalPrefix(int point) {
		int pos = 0;
		while (point % width > 0 && tiles[point-1] > 0) {
			point -= 1;
			fixesReusable[pos++] = tiles[point];
		}
		if (pos == 0) return EMPTY;
		return new String(fixesReusable, 0, pos);
	}
	
	private String getHorizontalSuffix(int point) {
		int pos = 0;
		while (point % width < width-1 && tiles[point+1] > 0) {
			point += 1;
			fixesReusable[pos++] = tiles[point];
		}
		if (pos == 0) return EMPTY;
		return new String(fixesReusable, 0, pos);
	}
	
	private int[] potentialHooksReusable;
	
	public int[] getPotentialHookPoints() {
		int pos = 0;
		
		if (firstPlay) {
			//add centre point
			potentialHooksReusable[pos++] = (width * height) / 2 + 1;
		} else {
			for (int i=0; i<tiles.length; i++) {
				if (tiles[i] > 0) continue;
				//left
				if (i % width > 0 && tiles[i-1] > 0) {
					potentialHooksReusable[pos++] = i;
					continue;
				}
				//right
				if (i % width < width-1 && tiles[i+1] > 0) {
					potentialHooksReusable[pos++] = i;
					continue;
				}
				//above
				if (i / width > 0 && tiles[i-width] > 0) {
					potentialHooksReusable[pos++] = i;
					continue;
				}
				//below
				if (i / width < height-1 && tiles[i+width] > 0) {
					potentialHooksReusable[pos++] = i;
					continue;
				}
			}
		}
		potentialHooksReusable[pos] = -1;
		return potentialHooksReusable;
	}

	@Override
	public String toString() {
		return "BoardState [height=" + height + ", width=" + width + "]";
	}
	
	
}
