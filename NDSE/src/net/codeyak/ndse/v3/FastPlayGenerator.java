package net.codeyak.ndse.v3;

import net.codeyak.ndse.v1.LetterValuation;

public final class FastPlayGenerator {

	private final int[] data;
	
	static final int[] freqs = new int[2000000];
	
	public FastPlayGenerator(int[] data) {
		this.data = data;
	}
	
	private LetterValuation letterValuation;
	
	private FastGrid fGrid;
	
	private FastRack2 fRack;
	
	private int hookId;
	
	private int dim;
	private int dimStep;
	
	private boolean mustRev;
	
	private int[] hookMaskMerged;
	private int[] hookScoreMerged;
	private int[] hookMultiplierMerged;
	
	public void setLetterValuation(LetterValuation letterValuation) {
		this.letterValuation = letterValuation;
	}
	
	public void setFastGrid(FastGrid fastGrid) {
		this.fGrid = fastGrid;
	}
	
	public void setFastRack(FastRack2 fastRack) {
		this.fRack = fastRack;
	}
	
	public void setHookId(int hookId) {
		this.hookId = hookId;
	}
	
	public void setDim(int dim) {
		//must be negative initially!
		this.dim = dim;
		this.dimStep = -fGrid.dimStepsWB[dim];
	}
	
	public void setHookMaskMerged(int[] hookMaskMerged) {
		this.hookMaskMerged = hookMaskMerged;
	}
	
	public void setHookScoreMerged(int[] hookScoreMerged) {
		this.hookScoreMerged = hookScoreMerged;
	}
	
	public void setHookMultiplierMerged(int[] hookMultiplierMerged) {
		this.hookMultiplierMerged = hookMultiplierMerged;
	}
	
	private void precalc() {
		char adj = fGrid.tileLetters[hookId - dimStep];
		this.mustRev =  (adj != 0 && adj != '*');
	}
	
	public void gen() {
		precalc();
		gen(0, hookId, 0, 1, 0);
	}
	
	private void gen(final int address, final int posId, final int wordScore, final int wordMultiplier, final int hookScore) {
		if (freqs != null) freqs[address]++;
		//consider letter mask (for hook points)
		int lmask = (1 << 27) -1;
		if (hookMaskMerged != null && fGrid.hookBitSet.isSet(posId)) {
			lmask = hookMaskMerged[posId];
		}
		if (lmask == 0) {
			//if no letters are playable at this square (given restrictions along other dimensions), early abort
			return;
		}
		//consider tiles already placed
		final char c = fGrid.tileLetters[posId];
		if (c > 0) {
			int i = c-'A';
			int m = 1 << (c - 'A');
			//stop if hit edge (block)
			if (fGrid.tileLetters[posId] != '*') {
				//no need to consider hooks as it won't be a hook point!
				final int code = data[address];
				if ((code & m) > 0) {
					//iterate to next square
					gen(nextAddress(address, code, i), posId + dimStep, wordScore + letterValuation.getNumber(fGrid.tileTiles[posId]), wordMultiplier, hookScore);
				}
				return;
			}
		}
		final int code = data[address];
		if (fRack.anyUsed() && ((code & Magic.XP_WORD) > 0)) {
			//ensure there aren't unconsidered tiles to right (+ve) side of hook
			if (dimStep > 0 || !mustRev) {
				//record word!
				fRack.record(hookId, dim, wordScore, wordMultiplier, hookScore, (fRack.allUsed() ? 50 : 0));
			}
		}
		if (dimStep > 0 && fGrid.hookBitSet.isSet(posId)) {
			//don't reverse into another hook point (as this will create dups)
			return;
		}
		if ((code & Magic.XP_REV) > 0 && fGrid.tileLetters[hookId - dimStep] != '*') {
			//consider reversal (in any chain you will only hit reversal once)
			dimStep = -dimStep;
			gen(nextAddress(address, code, Magic.POS_REV), hookId + dimStep, wordScore, wordMultiplier, hookScore);
			dimStep = -dimStep;
		}
		if (fGrid.tileLetters[posId] == '*') {
			//we are edge of playing area (or have hit a boundary)
			return;
		}
		if (fRack.isEmpty()) {
			//we have run out of tiles
			return;
		}
		final char[] tiles = fRack.getTiles();
		//avoid evaluating same tile twice!
		int dedup = 0;
		for (int tileId = 0; tileId < tiles.length; tileId++) {
			final char tile = tiles[tileId];
			if (tile == 0) continue;
			int letterCode = (tile == '_' ? 26 : tile - 'A');
			int ip = 1 << letterCode;
			if ((dedup & ip) > 0) continue;
			dedup += ip;
			if (tile == '_') {
				letterCode = 0;
			}
			while (true) {
				int m = 1 << letterCode;
				if ((code & m) > 0 && (lmask & m) > 0) {
					fRack.select(tileId, tile, (char)(letterCode + 'A'), posId);
					//iterate to next square
					int tileScore = letterValuation.getNumber(tile);
					gen(nextAddress(address, code, letterCode), 
							posId + dimStep, 
							wordScore + (tileScore * (1+fGrid.squareLetterMultipliers[posId])), 
							wordMultiplier * (1+fGrid.squareWordMultipliers[posId]), 
							hookScore + (fGrid.hookBitSet.isSet(posId) && hookScoreMerged != null && hookMultiplierMerged != null ? hookScoreMerged[posId] + (tileScore * hookMultiplierMerged[posId]) : 0));
					fRack.deselect();
				}
				if (tile == '_' && letterCode < 25) {
					letterCode++;
				} else {
					break;
				}
			}
		}
	}

	private int nextAddress(final int address, final int code, final int symbol) {
		//determine offset
		int lc = code << (31 - symbol);
		int lc2 = lc >>> (31 - symbol);
		int bits = numbits(lc2);
		int nextAddress = data[address + bits];
		return nextAddress;
	}

	private final static int[] bittable = {0, 1, 1, 2,  1, 2, 2, 3,  1, 2, 2, 3,  2, 3, 3, 4};
	
	public static int numbits (int v)
	{
	    int s = 0;
	    while (v != 0)
	    {
	         s += bittable [v & 15];
	         v = v >>> 4;
	    }
	    return s;
	}

	public int getDimStep() {
		return dimStep;
	}


}
