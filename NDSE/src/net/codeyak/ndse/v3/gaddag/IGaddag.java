package net.codeyak.ndse.v3.gaddag;

public interface IGaddag {

	//public boolean isValidNextLetter(int address, char c);
	
	public boolean isValidNextSymbol(int address, int symbol);

	//public int getNextAddressFromLetter(int address, char c);
	
	public int getNextAddressFromSymbol(int address, int symbol);

	public boolean isValidWord(int address);
	
	
}
