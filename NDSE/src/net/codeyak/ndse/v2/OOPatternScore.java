package net.codeyak.ndse.v2;

public class OOPatternScore {

	public static String render(OOSquare[] squares, int[] tileMultipliers) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<squares.length; i++) {
			if (tileMultipliers[i] == 0) {
				sb.append(squares[i].getTile().getLetter());
			} else {
				int m = tileMultipliers[i];
				if (m < 10) 
					sb.append(m); else sb.append("["+m+"]");
			}
		}
		return sb.toString();
	}
}
