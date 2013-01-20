package net.codeyak.ndse.v2;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.codeyak.ndse.v1.WordList;


/**
 * constructed using a v1 WordList object. Provides a useful set of functionality for finding valid plays.
 * @author dave_blake
 *
 */
public class OOWordList {
	
	private final int MAX_LENGTH = 15;

	/**
	 * the set of valid words
	 */
	private final Set<String> wordSet;
	
	private String[] wordsAlpha;
	private String[] wordsLengthAlpha;
	
	/**
	 * map of letters that can be added to the end of words to form new valid words. End hooks in common terminology.
	 * eg WIMP -> [Y,S] because WIMPY and WIMPS are valid words.
	 */
	private Map<String, char[]> suffixes;
	
	/**
	 * map of letters that can be added to the start of words to form new valid words. Start hooks in common terminology.
	 * RAM -> [C,D,T] because CRAM, DRAM and TRAM are valid words.
	 */
	private Map<String, char[]> prefixes;
	
	/**
	 * set of all valid word fragments. All substring sequences of letters that appear in any valid word.
	 */
	private Set<String> fragmentSet;
	
	/**
	 * all n length fragments that form the start of words
	 */
	private Map<Integer, Set<String>> validStartFragments = new HashMap<Integer, Set<String>>();
	
	/**
	 * all n length fragments that form the middle of words
	 */private Map<Integer, Set<String>> validEndFragments = new HashMap<Integer, Set<String>>();
	
	 /**
	  * all n length fragments that form the start of words
      */
	 private Map<Integer, Set<String>> validMiddleFragments = new HashMap<Integer, Set<String>>();
	
	public OOWordList(WordList wordList) {
		this.wordSet = wordList.getWordSet();
		System.out.println("Loaded "+wordSet.size()+" words");
		
		List<String> words = wordList.getWordList();
		this.wordsAlpha = words.toArray(new String[words.size()]);
		
		Collections.sort(words, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				int rv = s1.length() - s2.length();
				if (rv == 0) {
					return s1.compareTo(s2);
				}
				return rv;
			}
		});
		this.wordsLengthAlpha = words.toArray(new String[words.size()]);
		
		generateSuffixes();
		generatePrefixes();
		generateFragments();
		generateStartFragments();
		generateEndFragments();
		generateMiddleFragments();
	}
	
	/**
	 * prints words that do not form fragments of other words
	 */
	public void printNonFragments() {
		for (String word : wordsLengthAlpha) {
			if (fragmentSet.contains(word)) continue;
			System.out.println(word);
		}
	}
	
	/**
	 * is the string a valid word
	 * 
	 * @param word
	 * @return
	 */
	public boolean isWord(String word) {
		return wordSet.contains(word);
	}
	
	private void generateFragments() {
		fragmentSet = new HashSet<String>();
		for (String word : wordSet) {
			for (int i=0; i<word.length() - 1; i++) {
				for (int j=i+1; j<=word.length(); j++) {
					if (j-i == word.length()) continue;
					String fragment = word.substring(i, j);
					fragmentSet.add(fragment);
				}
			}
		}
		System.out.println("Generated "+fragmentSet.size()+" fragments");
	}
	
	private void generateStartFragments() {
		for (String word : wordSet) {
			Set<String> set = validStartFragments.get(word.length());
			if (set == null) {
				set = new HashSet<String>();
				validStartFragments.put(word.length(), set);
			}
			for (int i=1; i<word.length(); i++) {
				String fragment = word.substring(0, i);
				if (!set.contains(fragment)) set.add(fragment);
			}
		}
		System.out.println("Generated "+getTotalSize(validStartFragments)+" start fragments");
	}
	
	private int getTotalSize(Map<Integer, Set<String>> map) {
		int rv = 0;
		for (Set<String> set : map.values()) {
			rv += set.size();
		}
		return rv;
	}
		
	private void generateEndFragments() {
		for (String word : wordSet) {
			Set<String> set = validEndFragments.get(word.length());
			if (set == null) {
				set = new HashSet<String>();
				validEndFragments.put(word.length(), set);
			}
			for (int i=1; i<word.length(); i++) {
				String fragment = word.substring(i);
				if (!set.contains(fragment)) set.add(fragment);
			}
		}
		System.out.println("Generated "+getTotalSize(validEndFragments)+" end fragments");
	}
		
	private void generateMiddleFragments() {
		for (String word : wordSet) {
			Set<String> set = validMiddleFragments.get(word.length());
			if (set == null) {
				set = new HashSet<String>();
				validMiddleFragments.put(word.length(), set);
			}
			for (int i=1; i<word.length() - 1; i++) {
				for (int j=i+1; j<word.length(); j++) {
					String fragment = word.substring(i, j);
					set.add(fragment);
				}
			}
		}
		System.out.println("Generated "+getTotalSize(validMiddleFragments)+" middle fragments");
	}
		
	private void generateSuffixes() {
		suffixes = new HashMap<String, char[]>(wordSet.size());
		boolean[] bs = new boolean[26];
		for (char c='A'; c<='Z'; c++) {
			innerSuffixes(c+"", bs);
		}
		for (String word : wordsAlpha) {
			innerSuffixes(word, bs);
		}
		System.out.println("Generated "+suffixes.size()+" suffixes");
	}
	
	private void innerSuffixes(String word, boolean[] bs) {
		int n = 0;
		for (char c='A'; c<='Z'; c++) {
			boolean valid = wordSet.contains(word + c);
			bs[c-'A'] = valid;
			if (valid) n++;
		}
		if (n > 0) {
			char[] lookup = new char[n];
			int p=0;
			for (char c='A'; c<='Z'; c++) {
				if (bs[c-'A']) lookup[p++] = c;
			}
			suffixes.put(word, lookup);
		}
	}

	private void generatePrefixes() {
		prefixes = new HashMap<String, char[]>();
		boolean[] bs = new boolean[26];
		for (char c='A'; c<='Z'; c++) {
			innerPrefixes(c+"", bs);
		}
		for (String word : wordsAlpha) {
			innerPrefixes(word, bs);
		}
		System.out.println("Generated "+prefixes.size()+" prefixes");
	}
	
	private void innerPrefixes(String word, boolean[] bs) {
		int n = 0;
		for (char c='A'; c<='Z'; c++) {
			boolean valid = wordSet.contains(c + word);
			bs[c-'A'] = valid;
			if (valid) n++;
		}
		if (n > 0) {
			char[] lookup = new char[n];
			int p=0;
			for (char c='A'; c<='Z'; c++) {
				if (bs[c-'A']) lookup[p++] = c;
			}
			prefixes.put(word, lookup);
		}
	}
	
	public void print() {
		for (char c='A'; c<='Z'; c++) {
			printPrefix(c+"");
			System.out.print(c);
			printSuffix(c+"");
			System.out.print("Fragment");
			System.out.println();
		}
		for (String word : wordsLengthAlpha) {
			printPrefix(word);
			System.out.print(word);
			printSuffix(word);
			if (fragmentSet.contains(word)) System.out.print("Fragment");
			System.out.println();
		}
	}
	
	private static int INDENT = 30;
	
	private void printPrefix(String word) {
		char[] pxs = prefixes.get(word);
		if (pxs == null) {
			printSpaces(INDENT);
		} else {
			printSpaces(INDENT - pxs.length - 3);
			System.out.print("[");
			for (char c : pxs) {
				System.out.print(c);
			}
			System.out.print("] ");
		}
	}
	
	private void printSuffix(String word) {
		char[] sxs = suffixes.get(word);
		if (sxs == null) {
			printSpaces(INDENT);
		} else {
			System.out.print(" [");
			for (char c : sxs) {
				System.out.print(c);
			}
			System.out.print("]");
			printSpaces(INDENT - sxs.length - 3);
		}
	}
	
	private void printSpaces(int n) {
		for (int i=0; i<n; i++) {
			System.out.print(" ");
		}
	}

	public boolean isValidStartFragment(int length, String fragment) {
		if (length > MAX_LENGTH) return false;
		return validStartFragments.get(length).contains(fragment);
	}

	public boolean isValidEndFragment(int length, String fragment) {
		if (length > MAX_LENGTH) return false;
		return validEndFragments.get(length).contains(fragment);
	}

	public boolean isValidMiddleFragment(int length, String fragment) {
		if (length > MAX_LENGTH) return false;
		return validMiddleFragments.get(length).contains(fragment);
	}

}
