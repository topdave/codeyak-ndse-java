package net.codeyak.ndse.v3.gaddag;

import java.util.Arrays;
import java.util.Map;

public class Node {

	private static int uidNext = 0;
	
	public static int getNodeCount() {
		return uidNext;
	}
	
	/**
	 * 0 = A
	 * ...
	 * 25 = Z
	 * 26 = REV
	 */
	Node[] next;
	
	boolean word;
	
	private int hash;
	
	int uid = uidNext++;
	
	int freq;
	
	int trav;
	
	int minDepth;
	
	@Override
	public int hashCode() {
		return hash;
	}
	
	public void genHash() {
		if (next != null) {
			for (Node n : next) {
				if (n != null) n.genHash();
			}
		}
		final int prime = 31;
		hash = 1;
		hash = prime * hash + Arrays.hashCode(next);
		hash = prime * hash + (word ? 1231 : 1237);
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (!Arrays.equals(next, other.next))
			return false;
		if (word != other.word)
			return false;
		return true;
	}

	public Node add(char c) {
		int index = -1;
		if (c == '#') index = 26;
		if (c >= 'A' && c <= 'Z') {
			index = (int)(c - 'A');
		}
		if (index == -1) throw new RuntimeException("invalid char "+c);
		if (next == null) {
			next = new Node[27];
		}
		Node n = next[index];
		if (n == null) {
			n = new Node();
			next[index] = n;
		}
		return n;
	}
	
	public void print(String prefix) {
		if (word) System.out.print('!');
		if (next != null) {
			for (int i=0; i<next.length; i++) {
				Node n = next[i];
				if (n != null) {
					System.out.print(prefix);
					char c = '#';
					if (i<26) c = (char)('A' + i);
					System.out.print(c);
					n.print(prefix + c);
				}
			}
		}
	}

	public void capture(Map<Node, Node> nodeMap) {
		if (nodeMap.containsKey(this)) return;
		nodeMap.put(this, this);
		if (next != null) {
			for (Node n : next) {
				if (n != null) {
					n.capture(nodeMap);
				}
			}
		}
	}
	
	public void minimize(Map<Node, Node> nodeMap) {
		if (next != null) {
			for (int i=0; i<next.length; i++) {
				Node n = next[i];
				if (n != null) {
					Node nc = nodeMap.get(n);
					if (n == nc) {
						n.minimize(nodeMap);
					} else {
						next[i] = nc;
					}
				}
			}
		}
	}

	public void setFreq() {
		freq++;
		if (next != null) {
			for (Node n : next) {
				if (n != null) {
					n.setFreq();
				}
			}
		}
	}
	
	public void setTrav(char[] cs, int p) {
		trav++;
		if (p < cs.length) {
			char c = cs[p];
			int i = (c == '#' ? 26 : (int)(c - 'A'));
			next[i].setTrav(cs, p+1);
		}
	}
	
	public void setDepth(char[] cs, int p) {
		trav++;
		if (p < cs.length) {
			char c = cs[p];
			int i = (c == '#' ? 26 : (int)(c - 'A'));
			Node n = next[i];
			if (n.minDepth < p) n.minDepth = p;
			n.setDepth(cs, p+1);
		}
	}

}
