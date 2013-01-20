package net.codeyak.ndse.v3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.codeyak.ndse.v1.LetterValuation;


public final class Magic {
	
	public static final int XP_CODE = 1073741824; // 2^30 = 31st = [30]
	
	public static final int XP_WORD = 134217728; // 2^27 = 28th = [27]
	public static final int XP_REV  =  67108864; // 2^26 = 27th = [26]
	
	public static final int POS_REV = 26;

	private final int[] data;
	
	public Magic(File file) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		int size = ois.readInt();
		data = new int[size];
		for (int i=0; i<data.length; i++) {
			data[i] = ois.readInt();
		}
		ois.close();
	}

	public void save(File file) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeInt(data.length);
		for (int i : data) {
			oos.writeInt(i);
		}
		oos.close();
	}
	
	public Magic(int[] data) {
		this.data = data;
	}
	
	public boolean isWord(String word) {
		return isWord(word.toCharArray());
	}
	
	public boolean isWord(final char[] word) {
		return isWord(word, 0, word.length-1, 1);
	}
	
	public boolean isWord(final char[] grid, final int startId, final int endId, final int step) {
		//root address
		int address = 0;
		//step through word in reverse due to entries in data stored in reverse
		for (int i=endId; i>=startId; i-=step) {
			//find next letter from grid
			char c = grid[i];
			//determine lookup position
			int pos = c - 'A';
			int code = data[address];
			if ((code & (1 << pos)) == 0) return false;
			//determine offset
			code = code << (31 - pos);
			code = code >>> (31 - pos);
			int bits = FastPlayGenerator.numbits(code);
			address = data[address + bits];
		}
		return (data[address] & XP_WORD) > 0;
	}
		
	public void print() {
		System.out.println("addr ABCDEFGHIJKLMNOPQRSTUVWXYZ#!");
		for (int i=0; i<data.length; i++) {
			int code = data[i];
			String addr = ""+i;
			while (addr.length() < 4) {
				addr = " " + addr;
			}
			System.out.print(addr);
			System.out.print(" ");
			if ((code & XP_CODE) > 0) {
				for (int j=0; j<30; j++) {
					if ((code & 1) > 0) System.out.print("*"); else System.out.print(" ");
					code = code >> 1;
				}
				System.out.println();
			} else {
				System.out.println("ref " + code);
			}
		}
	}

	public void test(String word) {
		System.out.println(word + ": "+isWord(word));
	}

	public void generatePlays(final FastGrid fGrid, final FastRack2 fRack, final FastBag fBag, final LetterValuation lv) {
		//setup
		FastRack2 blankRack = new FastRack2(new char[]{'_'});
		blankRack.playRecorder = fGrid;
		
		FastPlayGenerator fpg = new FastPlayGenerator(data);
		fpg.setFastGrid(fGrid);
		fpg.setLetterValuation(lv);
		
		PlayRecorderPrinter prp = new PlayRecorderPrinter();
		fRack.playRecorder = prp;
		
		long p1 = 0;
		long p2 = 0;
		
		long st = System.nanoTime();
		//main loop
		while (true) {
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
					fpg.gen();
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
		System.out.println("pre took "+p1/1000000+", post took "+p2/1000000);
		
		if (fGrid.totalScore > topScore) {
			topScore = fGrid.totalScore;
			System.out.println("new top score!");
			System.out.println(fGrid);
		}
		
		p1t += p1;
		p2t += p2;
		tt += (et-st);
	}
	
	public long p1t = 0;
	public long p2t = 0;
	public long tt = 0;
	
	public int topScore = 0;
	
	public String render() {
		StringBuilder sb = new StringBuilder();
		for (char c = 'A'; c <= 'Z'; c++) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	public String render(int letterMask) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<26; i++) {
			if ((letterMask & (1<<i)) > 0) sb.append((char)('A' + i)); else sb.append(' ');
		}
		return sb.toString();
	}

}
