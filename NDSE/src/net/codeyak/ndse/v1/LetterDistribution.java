package net.codeyak.ndse.v1;

import java.io.File;
import java.io.IOException;


/**
 * Letter frequencies. Supports printing letters in a grid for verification purposes.
 * 
 * @author dave_blake
 *
 */
public class LetterDistribution extends LetterLookup {

	public LetterDistribution(File file) {
		try {
			load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String toGrid(int width) {
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (Character c : getSortedLetters()) {
			for (int i=0; i<lookup.get(c); i++) {
				sb.append(c);
				count++;
				if (count % width == 0) sb.append(Constants.NL);
			}
		}
		return sb.toString();
	}

	public char[] getTiles() {
		char[] rv = new char[getTotal()];
		int pos = 0;
		for (Character c : getSortedLetters()) {
			for (int i=0; i<lookup.get(c); i++) {
				rv[pos++] = c;
			}
		}
		return rv;
	}

}
