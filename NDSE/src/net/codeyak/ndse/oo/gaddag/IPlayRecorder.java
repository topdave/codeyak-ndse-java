package net.codeyak.ndse.oo.gaddag;

public interface IPlayRecorder {
	public void record(int hookId, int dim, int wordScore, int wordMultiplier, int hookScore, int bonusScore, int used, char[] tiles, int[] tileIds, char[] letters, int[] posIds);
}
