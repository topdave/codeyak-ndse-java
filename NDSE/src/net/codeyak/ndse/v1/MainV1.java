package net.codeyak.ndse.v1;

import java.io.File;
import java.util.Arrays;

/**
 * Contains main entry point. Doesn't do much apart from print useful about various classes. Used to verify correctness.
 * 
 * @author dave_blake
 *
 */
public class MainV1 {

	public static void main(String[] args) {
		String language = "english";
		String game = "Scrabble";
		
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
		
		WordList words = new WordList(new File(rootFolder, "words/opusthepenguin.wordpress.com/TWL06.txt"));
		System.out.println(words.getSize());
		//System.out.println(words);
		
		Bag bag = new Bag(ld);
		StringBuilder tileOrder = new StringBuilder();
		while (bag.getRemaining() > 0) {
			char[] tiles = bag.pick(7);
			bag.putBack(tiles);
			tiles = bag.pick(5);
			tileOrder.append(tiles);
		}
		char[] ordering = tileOrder.toString().toCharArray();
		Arrays.sort(ordering);
		System.out.println("bag match to original : " + Arrays.equals(ordering, ld.getTiles()));
		
		//Rack rack = new Rack(7);
		//rack.pick(bag);
		Rack rack = new Rack("ABCDE");
		RackIterator it = rack.getRackIterator();
		int n = 0;
		char[] selected = it.getSelected();
		while (true) {
			char c = it.next();
			if (c == 0) break;
			n++;
			System.out.println(n+": "+Arrays.toString(selected)+" with "+it.getPivot());
		}
		System.out.println("combations = "+n);
		
		BoardState state = new BoardState(board);
		System.out.println(state);
	}
	
}
