package net.codeyak.ndse.v2;

public class OOPatternWord {
	private OODirection direction;
	/**
	 * to check these are still valid, check first and last squares, to see if they are still last!
	 * also check other words made through tiles in different directions
	 */
	private OOSquare[] squares;
	
	public OOPatternWord(OODirection direction, OOSquare[] squares) {
		super();
		this.direction = direction;
		this.squares = squares;
	}
	
	private int baseScore;
	private int[] tileMultipliers;

	public void process() {
		int playCount = 0;
		this.tileMultipliers = new int[squares.length];
		int wordMultiplier = 1;
		for (int i=0; i<squares.length; i++) {
			OOSquare square = squares[i];
			if (square.getTile() != null) {
				baseScore += square.getTile().value;
			} else {
				playCount++;
				tileMultipliers[i] = square.getLetterMultiplier();
				wordMultiplier *= square.getWordMultiplier();
			}
		}
		if (wordMultiplier > 1) {
			baseScore *= wordMultiplier;
			for (int i=0; i<tileMultipliers.length; i++) {
				tileMultipliers[i] *= wordMultiplier;
			}
		}
		if (playCount == 7) baseScore += 50;
	}

	public OODirection getDirection() {
		return direction;
	}

	public OOSquare[] getSquares() {
		return squares;
	}

	public int getBaseScore() {
		return baseScore;
	}

	public int[] getTileMultipliers() {
		return tileMultipliers;
	}
	
	public String toString() {
		return squares[0].toCoordString() + " " + direction.toString() + squares.length + ": " + OOPatternScore.render(squares, tileMultipliers) + (baseScore > 0 ? "+" + baseScore : "");
	}

	public boolean isValid(OOWordList wordList) {
		//form String
		boolean unknown = false;
		char[] cs = new char[squares.length];
		for (int i=0; i<squares.length; i++) {
			OOTile tile = squares[i].getTile();
			if (tile != null) {
				cs[i] = tile.getLetter();
			} else {
				cs[i] = '?';
				unknown = true;
			}
		}
		if (unknown) {
			//find start fragment
			int start = 0;
			int end = cs.length;
			if (cs[0] != '?') {
				for (int i=0; i<cs.length; i++) {
					if (cs[i] == '?') {
						String sf = new String(cs, 0, i);
						start = i;
						if (!wordList.isValidStartFragment(cs.length, sf)) return false;
						break;
					}
				}
			}
			//find end fragment
			if (cs[cs.length - 1] != '?') {
				for (int i=0; i<cs.length; i++) {
					if (cs[cs.length - 1 - i] == '?') {
						String ef = new String(cs, cs.length - i, i);
						end = cs.length - i;
						if (!wordList.isValidEndFragment(cs.length, ef)) return false;
						break;
					}
				}
			}
			//find middle fragments
			int sp = -1;
			int ep = -1;
			for (int i=start+1; i<end; i++) {
				if (cs[i] != '?') {
					if (sp == -1) {
						sp = i;
					}
					ep = i;
				} else {
					if (sp != -1) {
						String f = new String(cs, sp, ep-sp+1);
						if (!wordList.isValidMiddleFragment(cs.length, f)) return false;
						sp = -1;
					}
				}
			}
			
			return true;
		}
		return wordList.isWord(new String(cs));
	}
}
