package net.codeyak.ndse.v3;

import java.util.Arrays;

import net.codeyak.ndse.v1.Board;


public class FastGrid implements IPlayRecorder {

	public final char[] tileLetters;
	public final char[] tileTiles;
	
	/**
	 * by default 0 means 1x, 1 means 2x etc
	 */
	public final byte[] squareWordMultipliers;
	public final byte[] squareLetterMultipliers;
	
	public final FastBitSet hookBitSet;
	
	public final int[] hookIds;
	
	/**
	 * per dimensions, defines what are acceptable 1-letter hooks
	 * these are merged into letter mask per dimension
	 * eg x,y,z plays along the x axis must only play acceptable hooks in y and z axes
	 */
	public final int[][] hookMasks;
	public final int[][] hookScores;
	public final int[][] hookMultipliers;
	
	public final int[][] hookMasksMerged;
	public final int[][] hookScoresMerged;
	public final int[][] hookMultipliersMerged;

	public void genHookIds() {
		if (totalPlays == 0) {
			//return middle
			int[] coords = new int[dimSizesWB.length];
			for (int i=0; i<dimSizesWB.length; i++) {
				coords[i] = dimSizesWB[i] / 2;
			}
			int middleId = getId(coords);
			hookIds[0] = middleId;
			hookIds[1] = -1;
		} else {
			int p = 0;
			hookBitSet.reset();
			for (int i=0; i<totalPlays; i++) {
				for (int posId : playPosIds[i]) {
					for (int dimStep : dimStepsWB) {
						while (true) {
							int id = posId + dimStep;
							if (tileLetters[id] == 0) {
								if (!hookBitSet.isSet(id)) { 
									hookIds[p++] = id;
									hookBitSet.set(id);
								}
							}
							if (dimStep > 0) dimStep = -dimStep; else break;
						}
					}
				}
			}
			hookIds[p] = -1;
		}
	}
	
	int totalPlays = 0;
	int totalScore = 0;
	
	int[][] playPosIds = new int[1000][];
	char[][] playLetters = new char[1000][];
	char[][] playTiles = new char[1000][];
	
	
	public void makePlay(int[] posIds, char[] letters, char[] tiles, int score) {
		//place letters into grid!
		for (int i=0; i<posIds.length; i++) {
			tileLetters[posIds[i]] = letters[i];
			tileTiles[posIds[i]] = tiles[i];
		}
		//record play
		playPosIds[totalPlays] = posIds;
		playLetters[totalPlays] = letters;
		playTiles[totalPlays] = tiles;
		totalPlays++;
		//scoring
		totalScore += score; 
	}
	
	int[] dimSizesAct;
	
	int[] dimSizesWB;
	int[] dimStepsWB;
	
	public FastGrid(Board board) {
		this(board.getWidth(), board.getHeight());
		//set multipliers
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				int id = getId(x+1, y+1);
				squareLetterMultipliers[id] = (byte)(board.getLetterMultiplier(x, y) - 1);
				squareWordMultipliers[id] = (byte)(board.getWordMultiplier(x, y) - 1);
			}
		}
	}
	
	public FastGrid(int... dimSizesAct) {
		this.dimSizesAct = dimSizesAct;
		this.dimSizesWB = new int[dimSizesAct.length];
		this.dimStepsWB = new int[dimSizesAct.length];
		int volumeWB = 1;
		int m = 1;
		for (int i=0; i<dimSizesAct.length; i++) {
			dimSizesWB[i] = dimSizesAct[i]+2;
			dimStepsWB[i] = m;
			m *= dimSizesWB[i];
			volumeWB *= dimSizesWB[i];
		}
		tileLetters = new char[volumeWB];
		tileTiles = new char[volumeWB];
		hookBitSet = new FastBitSet(volumeWB);
		//create hook helpers
		hookMasks = new int[dimSizesAct.length][volumeWB];
		hookScores = new int[dimSizesAct.length][volumeWB];
		hookMultipliers = new int[dimSizesAct.length][volumeWB];
		hookMasksMerged = new int[dimSizesAct.length][volumeWB];
		hookScoresMerged = new int[dimSizesAct.length][volumeWB];
		hookMultipliersMerged = new int[dimSizesAct.length][volumeWB];
		//
		hookIds = new int[volumeWB];
		squareLetterMultipliers = new byte[volumeWB];
		squareWordMultipliers = new byte[volumeWB];
		//draw border
		for (int i=0; i<volumeWB; i++) {
			//on edge of any dim is 0 or size+1
			boolean edge = false;
			for (int j=0; j<dimSizesAct.length; j++) {
				int div = i / dimStepsWB[j];
				int mod = div % (dimSizesWB[j]);
				if (mod == 0 || mod == dimSizesAct[j] + 1) {
					edge = true;
					break;
				}
			}
			if (edge) tileLetters[i] = '*';
		}
	}
	
	private final static String NL = "\r\n";
	
	public String basicToString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<tileLetters.length; i++) {
			char c = tileLetters[i];
			if (c == 0) sb.append(' '); else sb.append(c);
			for (int j=1; j<dimStepsWB.length; j++) {
				if (i % dimStepsWB[j] == dimStepsWB[j]-1) sb.append(NL);
			}
		}
		return sb.toString();
	}
	
	public String toString() {
		//creates a grid of grids, all even dims 0,2,4... horizontal, all odd dims 1,3,5... vertical
		int xSum = 1;
		int ySum = 1;
		int[] xyDimSizes = new int[dimSizesWB.length];
		for (int i=0; i<dimSizesWB.length; i++) {
			if ((i % 2) == 0) {
				xyDimSizes[i] = xSum;
				xSum *= dimSizesWB[i];
				xSum++;
			} else {
				xyDimSizes[i] = ySum;
				ySum *= dimSizesWB[i];
				ySum++;
			}
		}
		char[][] outputGrid = new char[ySum][xSum];
		for (int i=0; i<tileLetters.length; i++) {
			char c = tileLetters[i];
			if (c == 0) c = ' ';
			int x = 0;
			int y = 0;
			for (int j=0; j<dimSizesAct.length; j++) {
				int div = i / dimStepsWB[j];
				int mod = div % (dimSizesWB[j]);
				//mod is coord in dim
				if ((j % 2) == 0) {
					x += mod * xyDimSizes[j];
				} else {
					y += mod * xyDimSizes[j];
				}
			}
			outputGrid[y][x] = c;
		}
		StringBuilder sb = new StringBuilder();
		for (char[] line : outputGrid) {
			for (int i=0; i<line.length; i++) {
				if (line[i] == 0) line[i] = ' ';
			}
			sb.append(line);
			sb.append(NL);
		}
		return sb.toString();
	}
	
	public String toIdString() {
		int maxId = tileLetters.length;
		int cellWidth = (""+maxId).length() + 1;
		//creates a grid of grids, all even dims 0,2,4... horizontal, all odd dims 1,3,5... vertical
		int xSum = 1;
		int ySum = 1;
		int[] xyDimSizes = new int[dimSizesWB.length];
		for (int i=0; i<dimSizesWB.length; i++) {
			if ((i % 2) == 0) {
				xyDimSizes[i] = xSum;
				xSum *= dimSizesWB[i];
				xSum++;
			} else {
				xyDimSizes[i] = ySum;
				ySum *= dimSizesWB[i];
				ySum++;
			}
		}
		char[][] outputGrid = new char[ySum][xSum * cellWidth];
		for (int i=0; i<tileLetters.length; i++) {
			int x = 0;
			int y = 0;
			for (int j=0; j<dimSizesAct.length; j++) {
				int div = i / dimStepsWB[j];
				int mod = div % (dimSizesWB[j]);
				//mod is coord in dim
				if ((j % 2) == 0) {
					x += mod * xyDimSizes[j];
				} else {
					y += mod * xyDimSizes[j];
				}
			}
			String s = i + " ";
			while (s.length() < cellWidth) s = " " + s;
			char[] cs = s.toCharArray();
			for (int j=0; j<cs.length; j++) {
				outputGrid[y][(x*cellWidth) + j] = cs[j];
			}
		}
		StringBuilder sb = new StringBuilder();
		for (char[] line : outputGrid) {
			for (int i=0; i<line.length; i++) {
				if (line[i] == 0) line[i] = ' ';
			}
			sb.append(line);
			sb.append(NL);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		//test
		test(2,2,2);
		test(2,2,2,2);
		
		test(8,8,8,8);
		
		test(4);
		test(4,4);
		test(4,2);
		test(2,4);
		test(2,2,2);
		test(1,2,3);
		test(2,2,2,2);
	}
	
	public static void test(int... dimSizes) {
		System.out.println("Test of "+Arrays.toString(dimSizes));
		FastGrid fGrid = new FastGrid(dimSizes);
		System.out.println(fGrid);
	}

	public void setWithCoord(char letter, int... coords) {
		int id = getId(coords);
		this.tileLetters[id] = letter;
	}
	
	public int getId(int... coords) {
		if (coords.length != dimStepsWB.length) throw new RuntimeException("coords length not equal to space dimensions");
		int rv = 0;
		for (int i=0; i<coords.length; i++) {
			rv += coords[i] * dimStepsWB[i];
		}
		return rv;
	}

	public int getDimCount() {
		return dimSizesAct.length;
	}

	@Override
	public void record(int hookId, int dim, int wordScore, int wordMultiplier, int hookScore, int bonusScore,
			int used, char[] tiles, int[] tileIds, char[] letters, int[] posIds) {
		hookMasks[dim][hookId] |= (1 << (letters[0] - 'A'));
		hookScores[dim][hookId] = wordScore * wordMultiplier;
		hookMultipliers[dim][hookId] = wordMultiplier * squareLetterMultipliers[hookId];
	}

	public void genHookMerged() {
		for (int hookId : hookIds) {
			if (hookId == -1) break;
			for (int dimA = 0; dimA < dimSizesAct.length; dimA++) {
				hookMasksMerged[dimA][hookId] = (1 << 27)-1;
				hookScoresMerged[dimA][hookId] = 0;
				hookMultipliersMerged[dimA][hookId] = 1;
				for (int dimB = 0; dimB < dimSizesAct.length; dimB++) {
					if (dimA == dimB) continue;
					hookMasksMerged[dimA][hookId] &= hookMasks[dimB][hookId];
					hookScoresMerged[dimA][hookId] += hookScores[dimB][hookId];
					hookMultipliersMerged[dimA][hookId] += hookMultipliers[dimB][hookId];
				}
			}
		}
	}
}
