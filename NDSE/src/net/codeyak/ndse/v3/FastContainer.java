package net.codeyak.ndse.v3;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import net.codeyak.ndse.v1.Board;
import net.codeyak.ndse.v1.LetterDistribution;
import net.codeyak.ndse.v1.LetterValuation;
import net.codeyak.ndse.v1.WordList;
import net.codeyak.ndse.v3.gaddag.Generator;
import net.codeyak.ndse.v3.gaddag.IGaddag;
import net.codeyak.ndse.v3.gaddag.MagicGaddag;
import net.codeyak.ndse.v3.gaddag.NodeGaddag;


public class FastContainer {

	public static void main(String[] args) throws IOException {
		String language = "english";
		String game = "Scrabble";
		String dictionary = "SOWPODS";
		
		File rootFolder = new File("data");
		
		LetterDistribution ld = new LetterDistribution(new File(rootFolder, "letters/"+language+"/"+game+"Distribution.txt"));
		System.out.println(ld.getTotal());
		System.out.println(ld.toGrid(10));
		System.out.println(ld);
		
		LetterValuation lv = new LetterValuation(new File(rootFolder, "letters/"+language+"/"+game+"Valuation.txt"));
		System.out.println(lv.getLetters());
		System.out.println(lv);
		
		Board board = new Board(new File(rootFolder, "boards/"+game+"Board.txt"));
		System.out.println(board);
		
		WordList words = new WordList(new File(rootFolder, "words/opusthepenguin.wordpress.com/"+dictionary+".txt"));
		System.out.println(words.getSize());
		//System.out.println(words);
		
		Generator g = new Generator();
		g.build(words.getWordSet());
		
		IGaddag gaddag = g.getNodeGaddag();
		
		File magicFile = new File(rootFolder, "words/gaddag/gaddag."+dictionary+".bin");
		gaddag = new MagicGaddag(magicFile);
		
		//Bag bag = new Bag(ld);
		//Rack rack = new Rack(7);
		//rack.pick(bag);
		//Rack rack = new Rack("AI");
		
		Random seed = new Random(5);
		System.gc();
		
		//System.out.println(fGrid.toIdString());
		int games = 100;
		
		FastGameGenerator fGameGen = new FastGameGenerator();
		for (int plays = 0; plays < games; plays++) {
			FastBag fBag = new FastBag(ld);
			fBag.shuffle(seed);
			FastRack2 fRack = new FastRack2(7);
			FastGrid fGrid = new FastGrid(board);
			
			fGameGen.generatePlays(gaddag, fGrid, fRack, fBag, lv);
		}
		
		System.out.println("executed "+games+" games");
		System.out.println("main loop avg "+ fGameGen.tt / 1000 / games + " microSecs");
		System.out.println("pre avg "+ fGameGen.p1t / 1000 / games + " microSecs");
		System.out.println("post avg "+ fGameGen.p2t / 1000 / games + " microSecs");
		
		System.out.println("games per second "+1000000000 / (fGameGen.tt / fGameGen.games));
		System.out.println("turns per second "+1000000000 / (fGameGen.tt / fGameGen.turns));
		
		//MagicGaddag magicGaddag = g.getMagicGaddag();
		//magicGaddag.save(magicFile);
		//magicGaddag = new MagicGaddag(magicFile);		
	}
}
