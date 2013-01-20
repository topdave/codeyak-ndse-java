package net.codeyak.ndse.v3.gaddag;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public final class MagicGaddag implements IGaddag {
	
	static final int XP_CODE = 1073741824; // 2^30 = 31st = [30]
	static final int XP_WORD = 134217728; // 2^27 = 28th = [27]
	
	//private static final int XP_REV  =  67108864; // 2^26 = 27th = [26]
	
	public static final int POS_REV = 26;

	private final int[] data;
	
	public MagicGaddag(File file) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		int size = ois.readInt();
		data = new int[size];
		for (int i=0; i<data.length; i++) {
			data[i] = ois.readInt();
		}
		ois.close();
	}

	public void save(File file) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeInt(data.length);
		for (int i : data) {
			oos.writeInt(i);
		}
		oos.close();
	}
	
	public MagicGaddag(int[] data) {
		this.data = data;
	}
	
	public boolean isWord(String word) {
		return isWord(word.toCharArray());
	}
	
	public boolean isWord(final char[] word) {
		return isWord(word, 0, word.length-1, 1);
	}
	
	public boolean isWord(final char[] grid, final int startId, final int endId, final int step) {
		//root address
		int address = 0;
		//step through word in reverse due to entries in data stored in reverse
		for (int i=endId; i>=startId; i-=step) {
			//find next letter from grid
			char c = grid[i];
			//determine lookup position
			int pos = c - 'A';
			int code = data[address];
			if ((code & (1 << pos)) == 0) return false;
			//determine offset
			code = code << (31 - pos);
			code = code >>> (31 - pos);
			int bits = numbits(code);
			address = data[address + bits];
		}
		return (data[address] & XP_WORD) > 0;
	}
		
	public void print() {
		System.out.println("addr ABCDEFGHIJKLMNOPQRSTUVWXYZ#!");
		for (int i=0; i<data.length; i++) {
			int code = data[i];
			String addr = ""+i;
			while (addr.length() < 4) {
				addr = " " + addr;
			}
			System.out.print(addr);
			System.out.print(" ");
			if ((code & XP_CODE) > 0) {
				for (int j=0; j<30; j++) {
					if ((code & 1) > 0) System.out.print("*"); else System.out.print(" ");
					code = code >> 1;
				}
				System.out.println();
			} else {
				System.out.println("ref " + code);
			}
		}
	}

	public void test(String word) {
		System.out.println(word + ": "+isWord(word));
	}

	public String render() {
		StringBuilder sb = new StringBuilder();
		for (char c = 'A'; c <= 'Z'; c++) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	public String render(int letterMask) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<26; i++) {
			if ((letterMask & (1<<i)) > 0) sb.append((char)('A' + i)); else sb.append(' ');
		}
		return sb.toString();
	}
	
	@Override
	public boolean isValidNextSymbol(int address, int symbol) {
		int code = data[address];
		int m = 1 << symbol;
		return (code & m) > 0;
	}
	
	@Override
	public int getNextAddressFromSymbol(int address, int symbol) {
		int code = data[address];
		
		int lc = code << (31 - symbol);
		int lc2 = lc >>> (31 - symbol);
		int bits = numbits(lc2);
		int nextAddress = data[address + bits];
		return nextAddress;
	}

	private final static int[] bittable = {0, 1, 1, 2,  1, 2, 2, 3,  1, 2, 2, 3,  2, 3, 3, 4};
	
	public static int numbits (int v)
	{
	    int s = 0;
	    while (v != 0)
	    {
	         s += bittable [v & 15];
	         v = v >>> 4;
	    }
	    return s;
	}

	@Override
	public boolean isValidWord(int address) {
		int code = data[address];
		return (code & MagicGaddag.XP_WORD) > 0;
	}
}
