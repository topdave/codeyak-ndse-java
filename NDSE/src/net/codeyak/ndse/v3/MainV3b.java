package net.codeyak.ndse.v3;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import net.codeyak.ndse.v1.LetterDistribution;
import net.codeyak.ndse.v1.LetterValuation;
import net.codeyak.ndse.v3.gaddag.IGaddag;
import net.codeyak.ndse.v3.gaddag.MagicGaddag;


public class MainV3b {

	public static void main(String[] args) throws IOException {
		String language = "english";
		String game = "Scrabble";
		String dictionary = "SOWPODS";
		
		File rootFolder = new File("data");
		
		LetterDistribution ld = new LetterDistribution(new File(rootFolder, "letters/"+language+"/"+game+"Distribution.txt"));
		ld.multiply(15);
		System.out.println(ld.getTotal());
		System.out.println(ld.toGrid(50));
		System.out.println(ld);
		
		LetterValuation lv = new LetterValuation(new File(rootFolder, "letters/"+language+"/"+game+"Valuation.txt"));
		System.out.println(lv.getLetters());
		System.out.println(lv);
		
		File magicFile = new File(rootFolder, "words/gaddag/gaddag."+dictionary+".bin");
		IGaddag gaddag = new MagicGaddag(magicFile);
		
		Random seed = new Random(5);
		
		int games = 1;
		
		FastGameGenerator fGameGen = new FastGameGenerator();
		for (int plays = 0; plays < games; plays++) {
			FastBag fBag = new FastBag(ld);
			fBag.shuffle(seed);
			FastRack2 fRack = new FastRack2(7);
			FastGrid fGrid = new FastGrid(15,15,15);
			
			fGameGen.generatePlays(gaddag, fGrid, fRack, fBag, lv);
		}
		
		System.out.println("executed "+games+" games");
		System.out.println("main loop avg "+ fGameGen.tt / 1000 / games + " microSecs");
		System.out.println("pre avg "+ fGameGen.p1t / 1000 / games + " microSecs");
		System.out.println("post avg "+ fGameGen.p2t / 1000 / games + " microSecs");
		
		System.out.println("games per second "+1000000000 / (fGameGen.tt / fGameGen.games));
		System.out.println("turns per second "+1000000000 / (fGameGen.tt / fGameGen.turns));
		
		System.out.println("called FastPlayGenerate.gen "+FastPlayGenerator.genCalls+" times");
	}
}
