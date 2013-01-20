package net.codeyak.ndse.v2;

/**
 * represents a tile, with a value and a letter.
 * Potentially having an object for a tile would allow more exotic tiles like "any vowel" or "qu"
 * @author dave_blake
 *
 */
public class OOTile {
	
	final int id;
	
	private final char letter;
	
	final int value;
	
	private boolean blank;
	private char blankLetter = 'A';
	
	public OOTile(int id, char letter, int value) {
		super();
		this.id = id;
		this.letter = letter;
		this.value = value;
		if (letter == '_') blank = true;
	}
	
	public String toString() {
		return letter +""+ (blank ? blankLetter : "");
	}

	public static OOTile[] generateTestTiles(String s) {
		char[] letters = s.toCharArray();
		OOTile[] rv = new OOTile[letters.length];
		for (int i=0; i<letters.length; i++) {
			rv[i] = new OOTile(i, letters[i], 1);
		}
		return rv;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OOTile other = (OOTile) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public boolean isBlank() {
		return blank;
	}
	
	public char getLetter() {
		if (blank) return blankLetter;
		return letter;
	}

	public void cycleBlank() {
		if (!blank) throw new RuntimeException("attempt to cycle a non-blank tile");
		blankLetter = (char)(blankLetter + 1);
	}

	public void resetBlank() {
		if (!blank) throw new RuntimeException("attempt to reset a non-blank tile");
		blankLetter = 'A';
	}
	
	public void setBlank(char c) {
		if (!blank) throw new RuntimeException("attempt to set a non-blank tile");
		blankLetter = c;
	}
	
	
}