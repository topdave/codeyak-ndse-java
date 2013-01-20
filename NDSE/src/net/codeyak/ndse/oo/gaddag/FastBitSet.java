package net.codeyak.ndse.oo.gaddag;

public class FastBitSet {

	int length;
	private long[] array;
	
	public FastBitSet(int length) {
		this.length = length;
		this.array = new long[(length / 64) + 1];
	}
	
	public void set(int pos) {
		array[pos / 64] |= (1L << (pos % 64));
	}
	
	public void clear(int pos) {
		array[pos / 64] &= ~(1L << (pos % 64));
	}
	
	public boolean isSet(int pos) {
		return (array[pos / 64] & (1L << (pos % 64))) > 0;
	}
	
	public void reset() {
		for (int i=0; i<array.length; i++) {
			array[i] = 0L;
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(length);
		for (int i=0; i<length; i++) {
			if (isSet(i)) sb.append(1); else sb.append(0);
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		FastBitSet fbs = new FastBitSet(100);
		fbs.set(0);
		fbs.set(99);
		fbs.set(50);
		System.out.println(fbs);
		fbs.clear(50);
		fbs.clear(99);
		System.out.println(fbs);
	}

}
