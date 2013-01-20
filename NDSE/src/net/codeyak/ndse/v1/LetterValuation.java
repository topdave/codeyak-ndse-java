package net.codeyak.ndse.v1;

import java.io.File;
import java.io.IOException;

/**
 * Letter values
 * 
 * @author dave_blake
 *
 */
public class LetterValuation extends LetterLookup {

	public LetterValuation(File file) {
		try {
			load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
