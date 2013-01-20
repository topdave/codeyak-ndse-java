package net.codeyak.ndse.v3;

import net.codeyak.ndse.v1.LetterValuation;
import net.codeyak.ndse.v3.gaddag.IGaddag;

public class FastGameGenerator {

	public long p1t = 0;
	public long p2t = 0;
	public long p3t = 0;
	public long tt = 0;
	
	public int topScore = 0;
	
	public int turns = 0;
	public int games = 0;
	
	public void generatePlays(IGaddag gaddag, final FastGrid fGrid, final FastRack2 fRack, final FastBag fBag, final LetterValuation lv) {
		games++;
		//setup
		FastRack2 blankRack = new FastRack2(new char[]{'_'});
		blankRack.playRecorder = fGrid;
		
		FastPlayGenerator fpg = new FastPlayGenerator(gaddag);
		fpg.setFastGrid(fGrid);
		fpg.setLetterValuation(lv);
		
		PlayRecorderPrinter prp = new PlayRecorderPrinter();
		fRack.playRecorder = prp;
		
		long p1 = 0;
		long p2 = 0;
		long p3 = 0;
		
		long st = System.nanoTime();
		//main loop
		while (true) {
			turns++;
			//update letters in rack
			fRack.select(fBag);
			if (fRack.isEmpty()) break;
			
			prp.reset();
			fGrid.genHookIds();
			
			long n1 = System.nanoTime();
			//use special rack just containing a blank to determine valid letters for a hook point on a given dimension
			fpg.setFastRack(blankRack);
			//System.out.println(render());
			fpg.setHookMaskMerged(null);
			fpg.setHookScoreMerged(null);
			fpg.setHookMultiplierMerged(null);
			for (int dim = 0; dim<fGrid.getDimCount(); dim++) {
				fpg.setDim(dim);
				for (int hookId : fGrid.hookIds) {
					if (hookId == -1) break;
					char before = fGrid.tileLetters[hookId + fpg.getDimStep()];
					char after = fGrid.tileLetters[hookId - fpg.getDimStep()];
					if ((before == 0 || before == '*') && (after == 0 || after == '*')) {
						fGrid.hookMasks[dim][hookId] = (1 << 27) -1;
					} else {
						fGrid.hookMasks[dim][hookId] = 0;
						fpg.setHookId(hookId);
						fpg.gen();
					}
					//System.out.println(render(fGrid.hookMasks[dim][hookId])+ " score=" + fGrid.hookScores[dim][hookId]+", multiplier="+fGrid.hookMultipliers[dim][hookId]+ ", dim="+dim+", hookId="+hookId);
				}
			}
			fGrid.genHookMerged();
			long n2 = System.nanoTime();
			
			fpg.setFastRack(fRack);
			for (int dim = 0; dim<fGrid.getDimCount(); dim++) {
				fpg.setDim(dim);
				fpg.setHookMaskMerged(fGrid.hookMasksMerged[dim]);
				fpg.setHookScoreMerged(fGrid.hookScoresMerged[dim]);
				fpg.setHookMultiplierMerged(fGrid.hookMultipliersMerged[dim]);
				//form merged letterMask
				//fGrid.getLetterMask(dim);
				for (int hookId : fGrid.hookIds) {
					if (hookId == -1) break;
					fpg.setHookId(hookId);
					long k1 = System.nanoTime();
					fpg.gen();
					long k2 = System.nanoTime();
					p3 += (k2-k1);
				}
			}
			long n3 = System.nanoTime();
			
			p1 += (n2-n1);
			p2 += (n3-n2);
			
			//prp now contains best play!
			boolean played = prp.makeBestPlay(fGrid, fRack);
			if (!played) break;
			
			//System.out.println("played for "+prp.bestScore);
		}
		
		long et = System.nanoTime();
		
		System.out.println("game took "+(et-st)/1000000+"ms, plays "+fGrid.totalPlays+", score "+fGrid.totalScore);
		System.out.println("pre took "+p1/1000000+"ms, post took "+p2/1000000+"ms, gen took "+p3/1000000+"ms");
		
		if (fGrid.totalScore > topScore) {
			topScore = fGrid.totalScore;
			System.out.println("new top score!");
			System.out.println(fGrid);
		}
		
		p1t += p1;
		p2t += p2;
		p3t += p3;
		tt += (et-st);
	}

}
