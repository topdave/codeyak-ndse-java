package net.codeyak.ndse.v3;

import java.util.Arrays;

public class PlayRecorderPrinter implements IPlayRecorder {

	
	
	@Override
	public void record(int hookId, int dim, int wordScore, int wordMultiplier, int hookScore, int bonusScore, 
			int used, char[] tiles, int[] tileIds, char[] letters, int[] posIds) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i=0; i<used; i++) {
			if (i > 0) sb.append(",");
			sb.append(posIds[i]+":"+letters[i]);
			if (tiles[i] != letters[i]) 
				sb.append("_");
		}
		sb.append("]");
		int totalScore = ((wordScore * wordMultiplier) + hookScore + bonusScore);
		sb.append(" ws="+wordScore+", wm="+wordMultiplier+", hs="+hookScore+", bs="+bonusScore+", total="+totalScore);
		//System.out.println(sb);
		
		//record most valuable play
		if (totalScore > bestScore) {
			this.bestScore = totalScore;
			this.letters = Arrays.copyOf(letters, used);
			this.tiles = Arrays.copyOf(tiles, used);
			this.posIds = Arrays.copyOf(posIds, used);
		}
	}

	int[] posIds;
	char[] letters;
	char[] tiles;
	int bestScore = -1;
	
	boolean makeBestPlay(FastGrid fGrid, FastRack2 fRack) {
		if (bestScore == -1) return false;
		fGrid.makePlay(posIds, letters, tiles, bestScore);
		fRack.useTiles(tiles);
		return true;
	}

	void reset() {
		bestScore = -1;
	}
}
