package net.codeyak.ndse.v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.codeyak.ndse.v1.RackIterator;
import net.codeyak.ndse.v1.WordList;

/**
 * A play placement pattern.
 * Consists of a list of OOSquare that need to be filled with tiles to make a valid play following this pattern.
 * Precomputes all the separate words that will be created as a result of this play.
 * Also precomputes the scoring pattern - the multiple that will be applied to each individual tile, and the base score for the
 * pattern. So if you play EXPIRATOR on 0,0 using the first R as a hook, the pattern will be 999[18]_999 + 59. Each tile will
 * get 9x it's face value (because it's played across two triple word scores, and the fourth letter will be 18x because of the
 * additional double letter score). The base score will be +9 from the preexisting R (worth 1 face value * 3 * 3 for triple word scores
 * +50 for using all 7 tiles from the rack.
 * <p>
 * By breaking down all possible plays on a board into OOPattern's, the process of finding valid plays is simplified to just
 * finding valid combinations of tile places, as can be seen from the succinct generatePlays method.
 * @author dave_blake
 *
 */
public class OOPattern implements Comparable<OOPattern> {
	
	private OODirection direction;
	
	/**
	 * ordered list of squares currently without tiles that need filling
	 */
	private OOSquare[] squares;
	
	/**
	 * list of all the words that are formed
	 */
	private OOPatternWord[] words;
	
	private int baseScore;
	private int[] tileMultipliers;
	
	public OOPattern(OODirection direction, OOSquare... squares) {
		this.direction = direction;
		this.squares = squares;
	}
	
	public OOSquare getHead() {
		return squares[0];
	}
	
	public OOSquare getTail() {
		return squares[squares.length-1];
	}
	
	private OOSquare getLeftMost(OOSquare square) {
		while (true) {
			if (square.getLeft() == null) return square;
			if (square.getLeft().getTile() == null && square.getLeft().tempIndex == -1) return square;
			square = square.getLeft();
		}
	}
	
	private OOSquare getRightMost(OOSquare square) {
		while (true) {
			if (square.getRight() == null) return square;
			if (square.getRight().getTile() == null && square.getRight().tempIndex == -1) return square;
			square = square.getRight();
		}
	}
	
	private OOSquare getTopMost(OOSquare square) {
		while (true) {
			if (square.getAbove() == null) return square;
			if (square.getAbove().getTile() == null && square.getAbove().tempIndex == -1) return square;
			square = square.getAbove();
		}
	}
	
	private OOSquare getBottomMost(OOSquare square) {
		while (true) {
			if (square.getBelow() == null) return square;
			if (square.getBelow().getTile() == null && square.getBelow().tempIndex == -1) return square;
			square = square.getBelow();
		}
	}
	
	public void process() {
		List<OOPatternWord> wordList = new ArrayList<OOPatternWord>();
		boolean[] horizontal = new boolean[squares.length];
		boolean[] vertical = new boolean[squares.length];
		//set temp field on squares
		for (int i=0; i<squares.length; i++) {
			squares[i].tempIndex = i;
		}
		for (OOSquare square : squares) {
			int index = square.tempIndex;
			if (!horizontal[index]) {
				OOSquare left = getLeftMost(square);
				OOSquare right = getRightMost(square);
				if (left != right) {
					OOSquare[] sqs = new OOSquare[right.getX() - left.getX() + 1];
					OOSquare cur = left;
					for (int i=0; i<sqs.length; i++) {
						sqs[i] = cur;
						if (cur.tempIndex != -1) {
							horizontal[cur.tempIndex] = true;
						}
						cur = cur.getRight();
					}
					OOPatternWord word = new OOPatternWord(OODirection.H, sqs);
					wordList.add(word);
				}
			}
			if (!vertical[index]) {
				OOSquare top = getTopMost(square);
				OOSquare bottom = getBottomMost(square);
				if (top != bottom) {
					OOSquare[] sqs = new OOSquare[bottom.getY() - top.getY() + 1];
					OOSquare cur = top;
					for (int i=0; i<sqs.length; i++) {
						sqs[i] = cur;
						if (cur.tempIndex != -1) {
							vertical[cur.tempIndex] = true;
						}
						cur = cur.getBelow();
					}
					OOPatternWord word = new OOPatternWord(OODirection.V, sqs);
					wordList.add(word);
				}
			}
		}
		//go through words and work
		this.tileMultipliers = new int[squares.length];
		for (OOPatternWord word : wordList) {
			word.process();
			baseScore += word.getBaseScore();
			int[] wtm = word.getTileMultipliers();
			OOSquare[] sqs = word.getSquares();
			for (int i=0; i<sqs.length; i++) {
				if (wtm[i] > 0) {
					tileMultipliers[sqs[i].tempIndex] += wtm[i];
				}
			}
		}
		//set temp field on squares
		for (int i=0; i<squares.length; i++) {
			squares[i].tempIndex = -1;
		}
		this.words = wordList.toArray(new OOPatternWord[wordList.size()]);
	}
	
	
	
	public int getScore() {
		int score = baseScore;
		for (int i=0; i<squares.length; i++) {
			score += squares[i].getTile().value * tileMultipliers[i];
		}
		return score;
	}
	
	public void placeTiles(OOTile[] tiles) {
		for (int i=0; i<squares.length; i++) {
			squares[i].setTile(tiles[i]);
		}
	}

	public int getLength() {
		return squares.length;
	}

	public OOPattern withHead(OODirection direction, OOSquare head) {
		OOSquare[] copy = new OOSquare[squares.length + 1];
		copy[0] = head;
		System.arraycopy(squares, 0, copy, 1, squares.length);
		return new OOPattern(direction, copy);
	}
	
	public OOPattern withTail(OODirection direction, OOSquare tail) {
		OOSquare[] copy = new OOSquare[squares.length + 1];
		System.arraycopy(squares, 0, copy, 0, squares.length);
		copy[squares.length] = tail;
		return new OOPattern(direction, copy);
	}

	public OOSquare getSquare(int index) {
		if (index < 0 || index >= squares.length) return null;
		return squares[index];
	}

	@Override
	public int compareTo(OOPattern that) {
		for (int i=0; i<this.squares.length && i<that.squares.length; i++) {
			int diff = this.squares[i].getCoord().getId() - that.squares[i].getCoord().getId();
			if (diff != 0) return diff;
		}
		return this.squares.length - that.squares.length;
	}

	@Override
	public String toString() {
		return Arrays.toString(squares) + " " + direction.toString() + squares.length + ": " + OOPatternScore.render(squares, tileMultipliers) + (baseScore > 0 ? "+" + baseScore : "");
	}

	public OOSquare[] getSquares() {
		return squares;
	}

	public OOPatternWord[] getWords() {
		return words;
	}

	public int getScore(OOTile[] tiles) {
		int rv = baseScore;
		for (int i=0; i<tileMultipliers.length; i++) {
			rv += tileMultipliers[i] * tiles[i].value;
		}
		return rv;
	}

	public List<OOPlay> generatePlays(OORack rack, OOWordList wordList) {
		OOTile[] tiles = rack.getActualTiles();
		
		OORackIterator it = new OORackIterator(tiles, squares.length);
		
		List<OOPlay> playList = new ArrayList<OOPlay>();
		
		boolean valid = true;
		
		for (OOPatternWord word : words) {
			if (!word.isValid(wordList)) {
				return playList;
			}
		}
		
		OOTile[] selected = it.getSelected();
		while (true) {
			OOTile tile = (!valid ? it.nextSkip() : it.next());
			if (tile == null) break;
			for (int i=0; i<squares.length; i++) {
				squares[i].setTile(selected[i]);
			}
			valid = true;
			for (OOPatternWord word : words) {
				if (!word.isValid(wordList)) {
					valid = false;
					break;
				}
			}
			//if fully filled, record as potential play
			if (valid && selected[selected.length - 1] != null) {
				playList.add(new OOPlay(this, Arrays.copyOf(selected, selected.length)));
			}
		}
		
		for (int i=0; i<squares.length; i++) {
			squares[i].setTile(null);
		}
		
		return playList;
		//return playList.toArray(new OOPlay[playList.size()]);
	}
}