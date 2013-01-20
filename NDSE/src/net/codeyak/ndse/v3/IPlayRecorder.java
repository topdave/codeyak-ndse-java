package net.codeyak.ndse.v3;

public interface IPlayRecorder {
	public void record(int hookId, int dim, int wordScore, int wordMultiplier, int hookScore, int bonusScore, int used, char[] tiles, int[] tileIds, char[] letters, int[] posIds);
}
