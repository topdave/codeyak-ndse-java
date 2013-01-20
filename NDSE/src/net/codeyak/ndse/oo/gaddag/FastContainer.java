package net.codeyak.ndse.oo.gaddag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import net.codeyak.ndse.v1.Board;
import net.codeyak.ndse.v1.LetterDistribution;
import net.codeyak.ndse.v1.LetterValuation;
import net.codeyak.ndse.v1.WordList;


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
		Magic magic = g.build(words.getWordSet());

		File magicFile = new File(rootFolder, "words/gaddag/gaddag."+dictionary+".bin");
		magic.save(magicFile);
		
		magic = new Magic(magicFile);
		
		//Magic m = new Magic(magicFileName);
		
		//Bag bag = new Bag(ld);
		//Rack rack = new Rack(7);
		//rack.pick(bag);
		//Rack rack = new Rack("AI");
		
		Random seed = new Random(5);
		System.gc();
		
		//System.out.println(fGrid.toIdString());
		int games = 100;
		for (int plays = 0; plays < games; plays++) {
			FastBag fBag = new FastBag(ld);
			fBag.shuffle(seed);
			FastRack2 fRack = new FastRack2(7);
			FastGrid fGrid = new FastGrid(board);
			
			magic.generatePlays(fGrid, fRack, fBag, lv);
		}
		
		System.out.println("main loop avg "+ magic.tt / 1000 / games + "ms");
		System.out.println("pre avg "+ magic.p1t / 1000 / games + "ms");
		System.out.println("post avg "+ magic.p2t / 1000 / games + "ms");
	}
}
