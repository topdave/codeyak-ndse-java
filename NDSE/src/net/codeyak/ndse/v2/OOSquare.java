package net.codeyak.ndse.v2;

/**
 * represents a square on the board.
 * Partial support support for an n-dimension board with use of OOCoord object, but still provides
 * helper methods for getLeft, getRight etc
 * 
 * @author dave_blake
 *
 */
public class OOSquare {
	
	public String toString() {
		return (tile != null ? tile.getLetter() + " " : "")
				+ coord
				+ (letterMultiplier > 1 ? " " + letterMultiplier + "xL" : "")
				+ (wordMultiplier > 1 ? " " + wordMultiplier + "xW" : "");
	}
	
	public OOSquare(OOCoord coord) {
		this.coord = coord;
	}
		
	private int letterMultiplier;
	private int wordMultiplier;
	
	private OOCoord coord;
	
	private OOTile tile;
	
	private OOSquare[] neighbours;
	
	public OOSquare getLeft() {
		return neighbours[0];
	}
	
	public OOSquare getRight() {
		return neighbours[1];
	}
	
	public OOSquare getAbove() {
		return neighbours[2];
	}
	
	public OOSquare getBelow() {
		return neighbours[3];
	}
	
	public int getX() {
		return coord.getPos(0);
	}
	
	public int getY() {
		return coord.getPos(1);
	}
	
	/** use to make various local operations more efficient - use with care! */
	int tempIndex = -1;
	
	
	
	public void setNeighbours(OOSquare[] neighbours) {
		this.neighbours = neighbours;
	}

	public OOSquare[] getNeighbours() {
		return neighbours;
	}
	
	@Override
	public int hashCode() {
		return coord.hashCode();
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OOSquare other = (OOSquare) obj;
		if (coord == null) {
			if (other.coord != null)
				return false;
		} else if (!coord.equals(other.coord))
			return false;
		return true;
	}

	public String toCoordString() {
		return coord.toString();
	}

	public int getLetterMultiplier() {
		return letterMultiplier;
	}

	public void setLetterMultiplier(int letterMultiplier) {
		this.letterMultiplier = letterMultiplier;
	}

	public int getWordMultiplier() {
		return wordMultiplier;
	}

	public void setWordMultiplier(int wordMultiplier) {
		this.wordMultiplier = wordMultiplier;
	}

	public OOTile getTile() {
		return tile;
	}

	public void setTile(OOTile tile) {
		this.tile = tile;
	}

	public OOCoord getCoord() {
		return coord;
	}
	
	
}