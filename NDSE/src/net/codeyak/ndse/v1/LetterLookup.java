package net.codeyak.ndse.v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract base class for a Character to Integer mapping.
 * Used for both letter values and letter frequency.
 * Supports load from a txt file.
 * 
 * @author dave_blake
 *
 */
public abstract class LetterLookup {
	protected Map<Character, Integer> lookup = new HashMap<Character, Integer>();
	
	public void multiply(int m) {
		for (Character c : lookup.keySet()) {
			int i = lookup.get(c);
			lookup.put(c, i * m);
		}
	}
	
	public List<Character> getSortedLetters() {
		List<Character> rv = new ArrayList<Character>(lookup.keySet());
		Collections.sort(rv);
		return rv;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Character c : getSortedLetters()) {
			if (sb.length() > 0) sb.append(", ");
			sb.append(c+"="+lookup.get(c));
		}
		return sb.toString();
	}
	
	protected void add(char c, String v) {
		lookup.put(c, Integer.parseInt(v));
	}
	
	protected void load(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		while (true) {
			String line = br.readLine();
			if (line == null) break;
			String[] split = line.split(" ");
			char c = split[0].charAt(0);
			int v = Integer.parseInt(split[1]);
			lookup.put(c, v);
		}
		br.close();
	}
	
	public int getTotal() {
		int rv = 0;
		for (int i : lookup.values()) {
			rv += i;
		}
		return rv;
	}
		
	public String getLetters() {
		StringBuilder sb = new StringBuilder();
		for (char c : getSortedLetters()) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	public int getNumber(char c) {
		return lookup.get(c);
	}
}
