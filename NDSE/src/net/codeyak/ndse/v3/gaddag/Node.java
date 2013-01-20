package net.codeyak.ndse.v3.gaddag;

import java.util.Arrays;
import java.util.Map;

public class Node {

	private static int uidNext = 0;
	
	public static int getNodeCount() {
		return uidNext;
	}
	
	public static Node[] allNodes = new Node[15000000];
	
	/**
	 * 0 = A
	 * ...
	 * 25 = Z
	 * 26 = REV
	 */
	private Node[] next;
	
	private boolean validWord;
	
	private int hash;
	
	private int uid = uidNext++;
	
	int usageCount;
	
	public Node() {
		allNodes[uid] = this;
	}
	
	public boolean isValidWord() {
		return validWord;
	}
	
	public void setValidWord(boolean validWord) {
		this.validWord = validWord;
	}
	
	public boolean hasNext() {
		return next != null;
	}
	
	public int getUid() {
		return uid;
	}
	
	public Node getNext(int index) {
		return next[index];
	}
	
	public Node[] getNext() {
		return next;
	}
	
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
		hash = prime * hash + (validWord ? 1231 : 1237);
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
		if (validWord != other.validWord)
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
		if (validWord) System.out.print('!');
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

	public boolean hasNext(int index) {
		return next != null && next[index] != null;
	}


}
