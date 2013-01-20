package net.codeyak.ndse.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.codeyak.ndse.v1.Board;
import net.codeyak.ndse.v1.Constants;


/**
 * Represents the game board. Has methods to print out the board (with tiles).
 * <br>The getPatterns method is the key method that generates all potential play "patterns".
 * It generates all possible valid positional plays, following standard Scrabble rules, where tiles must all be played on the
 * same horizontal or vertical axis, they must connect to an existing tile (or start at the middle square if this is the first play),
 * and they must form a continuous sequence of tiles.
 * <br>The OOPattern class represent a given pattern of OOSquares, see Javadoc in OOPattern for more details.
 * <p>
 * The implementation is partially abstracted to not be exclusively 2 dimensional. This is part of an (abortive) attempt to support
 * n-dimensional Scrabble (actually support in v3!!)
 * <br>The board just contains a list of squares on the board.
 * <br>The adjacency of OOSquares are defined by the squares themselves.
 * 
 * @author dave_blake
 *
 */
public class OOBoard {
	
	OOSquare[] squares;
	
	OOSquare middleSquare;
	
	OOCoordSpace coordSpace;
	
	/** list of the current plays */
	List<OOPlay> plays = new ArrayList<OOPlay>();
	
	void makePlay(OOPlay play) {
		play.makePlay();
		plays.add(play);
	}
	
	public int getWidth() {
		return coordSpace.getSize(0);
	}
	
	public int getHeight() {
		return coordSpace.getSize(1);
	}
	
	public OOBoard(Board board) {
		
		coordSpace = new OOCoordSpace(new OOCoordDimension(0, "x"), new OOCoordDimension(1, "Y"));
		int width = board.getWidth();
		int height = board.getHeight();
		coordSpace.setSizes(width, height);
		this.squares = new OOSquare[coordSpace.getTotalVolume()];
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				OOCoord coord = coordSpace.getCoord(x, y);
				OOSquare square = new OOSquare(coord);
				square.setLetterMultiplier(board.getLetterMultiplier(x, y));
				square.setWordMultiplier(board.getWordMultiplier(x, y));
				squares[coord.getId()] = square;
			}
		}
		this.middleSquare = squares[coordSpace.getId(width/2, height/2)];
		//set linkages
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				OOSquare square = squares[coordSpace.getId(x, y)];
				OOCoord[] coords = coordSpace.getNeighbours(square.getCoord());
				square.setNeighbours(getSquares(coords));
			}
		}
	}
	
	public OOSquare[] getSquares(OOCoord... coords) {
		OOSquare[] rv = new OOSquare[coords.length];
		for (int i=0; i<coords.length; i++) {
			if (coords[i] == null) continue;
			rv[i] = squares[coords[i].getId()];
		}
		return rv;
	}
	
	public String renderWithPattern(OOPattern pattern) {
		StringBuilder sb = new StringBuilder();
		String border = renderBorder();
		sb.append(border);
		sb.append(Constants.NL);
		int p = 0;
		for (int y=0; y<getHeight(); y++) {
			sb.append("|");
			for (int x=0; x<getWidth(); x++) {
				OOSquare square = squares[coordSpace.getId(x, y)];
				if (square.getTile() != null) {
					sb.append(square.getTile().getLetter());
				} else {
					if (square.equals(pattern.getSquare(p))) {
						sb.append(p++);
					} else {
						sb.append(" ");
					}
				}
			}
			sb.append("|"+Constants.NL);
		}
		sb.append(border);
		return sb.toString();
	}
	
	public String render() {
		StringBuilder sb = new StringBuilder();
		String border = renderBorder();
		sb.append(border);
		sb.append(Constants.NL);
		for (int y=0; y<getHeight(); y++) {
			sb.append("|");
			for (int x=0; x<getWidth(); x++) {
				OOSquare square = squares[coordSpace.getId(x, y)];
				if (square.getTile() != null) {
					sb.append(square.getTile().getLetter());
				} else {
					sb.append(" ");
				}
			}
			sb.append("|"+Constants.NL);
		}
		sb.append(border);
		return sb.toString();
	}
	
	public String renderWithMirror() {
		StringBuilder sb = new StringBuilder();
		String border = renderBorder();
		sb.append(border);
		final String GAP = "   ";
		sb.append(GAP);
		sb.append(border);
		sb.append(Constants.NL);
		for (int y=0; y<getHeight(); y++) {
			sb.append("|");
			for (int x=0; x<getWidth(); x++) {
				OOSquare square = squares[coordSpace.getId(x, y)];
				if (square.getTile() != null) {
					sb.append(square.getTile().getLetter());
				} else {
					sb.append(" ");
				}
			}
			sb.append("|");
			sb.append(GAP);
			sb.append("|");
			for (int x=0; x<getWidth(); x++) {
				OOSquare square = squares[coordSpace.getId(y, x)];
				if (square.getTile() != null) {
					sb.append(square.getTile().getLetter());
				} else {
					sb.append(" ");
				}
			}
			sb.append("|"+Constants.NL);
		}
		sb.append(border);
		sb.append(GAP);
		sb.append(border);
		return sb.toString();
	}
	
	public String renderBorder() {
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		for (int x = 0; x < getWidth(); x++) {
			sb.append("-");
		}
		sb.append("+");
		return sb.toString();
	}
	
	public List<OOPattern> getPatterns(int max) {
		Set<OOPattern> set = new HashSet<OOPattern>();
		for (OOSquare hook : getHooks()) {
			OOPattern root = new OOPattern(OODirection.H, hook);
			extendPatternUp(max, set, root);
			extendPatternLeft(max, set, root);
		}
		List<OOPattern> list = new ArrayList<OOPattern>(set.size());
		for (OOPattern pattern : set) {
			pattern.process();
			if (pattern.getWords().length > 0) {
				list.add(pattern);
			}
		}
		Collections.sort(list);
		return list;
	}
	
	public void extendPatternUp(int max, Set<OOPattern> patterns, OOPattern root) {
		extendPatternDown(max, patterns, root);
		if (root.getLength() >= max) return;
		OOSquare start = root.getHead();
		while (true) {
			start = start.getAbove();
			if (start == null) break;
			if (start.getTile() != null) continue;
			extendPatternUp(max, patterns, root.withHead(OODirection.V, start));
			return;
		}
	}
	
	public void extendPatternDown(int max, Set<OOPattern> patterns, OOPattern root) {
		patterns.add(root);
		if (root.getLength() >= max) return;
		OOSquare end = root.getTail();
		while (true) {
			end = end.getBelow();
			if (end == null) break;
			if (end.getTile() != null) continue;
			extendPatternDown(max, patterns, root.withTail(OODirection.V, end));
			return;
		}
	}
	
	public void extendPatternLeft(int max, Set<OOPattern> patterns, OOPattern root) {
		extendPatternRight(max, patterns, root);
		if (root.getLength() >= max) return;
		OOSquare start = root.getHead();
		while (true) {
			start = start.getLeft();
			if (start == null) break;
			if (start.getTile() != null) continue;
			extendPatternLeft(max, patterns, root.withHead(OODirection.H, start));
			return;
		}
	}
	
	public void extendPatternRight(int max, Set<OOPattern> patterns, OOPattern root) {
		patterns.add(root);
		if (root.getLength() >= max) return;
		OOSquare end = root.getTail();
		while (true) {
			end = end.getRight();
			if (end == null) break;
			if (end.getTile() != null) continue;
			extendPatternRight(max, patterns, root.withTail(OODirection.H, end));
			return;
		}
	}
	
	public Set<OOSquare> getHooks() {
		Set<OOSquare> hooks = new HashSet<OOSquare>();
		if (plays.size() == 0) {
			hooks.add(middleSquare);
		} else {
			for (OOPlay play : plays) {
				for (OOSquare square : play.getPattern().getSquares()) {
					for (OOSquare possibleHook : square.getNeighbours()) {
						if (possibleHook == null) continue;
						if (possibleHook.getTile() == null) 
							hooks.add(possibleHook);
					}
				}
			}
		}
		return hooks;
	}
}