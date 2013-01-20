package net.codeyak.ndse.v2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.codeyak.ndse.v1.Board;
import net.codeyak.ndse.v1.LetterDistribution;
import net.codeyak.ndse.v1.LetterValuation;
import net.codeyak.ndse.v1.WordList;


/**
 * Prints a series of diagnostic information, then enters a loop generating games.
 * <br>There is a single player who chooses the highest scoring word to play on each turn.
 * <br>Each game takes between 1 and 2 seconds on average (on my iMac i5)
 * <br>If no play can be found, the game aborts. The total score from each game is displayed.
 * <br>The highest scoring play overall is also printed out.
 * <br>For example EXPIATOR scored 212 points
 * <br>OOPlay [score=212, pattern=[E (0,0) 3xW, X (1,0), P (2,0), I (3,0) 2xL, A (4,0), T (5,0), R (7,0) 3xW] H7: 999[18]999+59, tiles=[E, X, P, I, A, T, R]]
 * 
 * @author dave_blake
 *
 */
public class MainV2 {

	public static void main(String[] args) {
		String language = "english";
		String game = "Scrabble";
		String dictionary = "SOWPODS";
		
		if (true) {
			OORackIterator it = new OORackIterator(OOTile.generateTestTiles("__"), 2);
			int n = 0;
			OOTile[] selected = it.getSelected();
			while (true) {
				OOTile c = (n == 5 ? it.nextSkip() : it.next());
				if (c == null) break;
				n++;
				System.out.println(n+": "+Arrays.toString(selected)+" with "+it.getPivot());
			}
			System.out.println("combinations = "+n);
		}
		
		File rootFolder = new File("data");
		Board board = new Board(new File(rootFolder, "boards/"+game+"Board.txt"));
		System.out.println(board);
		
		WordList wordList = new WordList(new File(rootFolder, "words/opusthepenguin.wordpress.com/" + dictionary + ".txt"));
		
		OOWordList ooWordList = new OOWordList(wordList);
		//ooWordList.printNonFragments();
		//ooWordList.print();
		
		//OOPattern pattern = new OOPattern(OODirection.H, ooBoard.middleSquare.getLeft().getLeft(), ooBoard.middleSquare.getLeft(), ooBoard.middleSquare);
		//OOPlay play = new OOPlay(pattern, new OOTile('M', 3), new OOTile('I', 1), new OOTile('X', 8));
		//ooBoard.makePlay(play);
		
		//MAIN LOOP!
		
		LetterDistribution ld = new LetterDistribution(new File(rootFolder, "letters/"+language+"/"+game+"Distribution.txt"));
		System.out.println(ld.getTotal());
		System.out.println(ld.toGrid(10));
		System.out.println(ld);
		
		LetterValuation lv = new LetterValuation(new File(rootFolder, "letters/"+language+"/"+game+"Valuation.txt"));
		System.out.println(lv.getLetters());
		System.out.println(lv);
		
		int bestScore = 0;
		int gameCount = 0;
		int playCount = 0;
		
		while (true) {
		
		OOBoard ooBoard = new OOBoard(board);
		
		OOBag bag = new OOBag(ld, lv);
		OORack rack = new OORack(7);
		
		//OORack rack = new OORack(OOTile.generateTestTiles("ABC"));
		
		long fst = System.currentTimeMillis();
		
		int totalScore = 0;
		int playNum = 0;
		while (true) {
			playNum++;
			
			//System.out.println("Play "+(playNum));
			playCount++;
			//System.out.println("Score "+totalScore);
			//System.out.println(ooBoard.renderWithMirror());
			rack.pick(bag);
			//System.out.println(rack);
			
			long st = System.nanoTime();
			List<OOPattern> patterns = ooBoard.getPatterns(rack.getCount());
			Map<OOPattern, List<OOPlay>> playMap = new HashMap<OOPattern, List<OOPlay>>();
			for (OOPattern pattern : patterns) {
				List<OOPlay> plays = pattern.generatePlays(rack, ooWordList);
				if (plays.size() > 0) 
					playMap.put(pattern, plays);
			}
			long et = System.nanoTime();
			//System.out.println("generated all play in "+(et-st)/1000000+"ms");
			
			//choose a play
			List<OOPlay> allPlays = new ArrayList<OOPlay>();
			for (List<OOPlay> plays : playMap.values()) {
				allPlays.addAll(plays);
			}
			Collections.sort(allPlays);
			if (allPlays.size() == 0) break;
			OOPlay chosenPlay = allPlays.get(0);
			//System.out.println(chosenPlay);
			ooBoard.makePlay(chosenPlay);
			rack.useTiles(chosenPlay.getTiles());
			totalScore += chosenPlay.getScore();
			
			if (chosenPlay.getScore() > bestScore) {
				System.out.println(ooBoard.renderWithMirror());
				System.out.println(chosenPlay);
				bestScore = chosenPlay.getScore();
			}
			
			//for (OOPattern pattern : patterns) {
			//	System.out.println(pattern.toString());
			//}
			
				/*
				System.out.println("pattern "+n++);
				System.out.println(ooBoard.renderWithPattern(pattern));
				System.out.println(pattern.toString());
				for (OOPatternWord word : pattern.getWords()) {
					System.out.println(word);
				}
				for (OOPlay play : plays) {
					System.out.println(play.getScore()+": " +play);
				}
				*/
		}
		
		
		long fet = System.currentTimeMillis();
		System.out.println("Game "+gameCount+++" took "+(fet-fst)+"ms, plays = " + playNum+", score = "+totalScore);
		
		}
	}
}
