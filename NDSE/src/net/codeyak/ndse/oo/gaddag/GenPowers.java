package net.codeyak.ndse.oo.gaddag;

public class GenPowers {

	public static void main(String[] args) {
		int m = 1;
		for (int i=0; i<32; i++) {
			System.out.print("private final static int xp");
			String n = i+"";
			if (n.length() == 1) n = "0"+n;
			System.out.println(n+" = "+m+";");
			m *= 2;
		}
	}
	
}
