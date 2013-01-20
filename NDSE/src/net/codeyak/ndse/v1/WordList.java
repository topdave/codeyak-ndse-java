package net.codeyak.ndse.v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Contains an alphabetically sorted list of valid words.
 * Also contains set of words for rapid lookup.
 * 
 * @author dave_blake
 *
 */
public class WordList {
	
	private final Set<String> wordSet;

	private final List<String> wordList;
	
	public WordList(File file) {
		this.wordList = load(file);
		this.wordSet = new HashSet<String>(wordList);
	}

	protected List<String> load(File file) {
		List<String> rv = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while (true) {
				String line = br.readLine();
				if (line == null) break;
				rv.add(line.toUpperCase());
			}
			Collections.sort(rv);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rv;
	}
	
	public List<String> getWordList() {
		return wordList;
	}

	public int getSize() {
		return wordList.size();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (String word : wordList) {
			sb.append(word + Constants.NL);
		}
		return sb.toString();
	}

	public boolean isWord(String word) {
		if (word.length() == 1) return true;
		return wordSet.contains(word);
	}

	public Set<String> getWordSet() {
		return wordSet;
	}
	

}
