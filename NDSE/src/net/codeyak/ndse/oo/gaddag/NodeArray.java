package net.codeyak.ndse.oo.gaddag;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class NodeArray {
	
	int[] MASTER = new int[15000000];
	int head = 0;
	Map<Node, Integer> addresses = new HashMap<Node, Integer>();
	
	public void build(Node root) {
		tasks = new PriorityQueue<ChildTask>(100000, new Comparator<ChildTask>() {

			@Override
			public int compare(ChildTask ct1, ChildTask ct2) {
				//return ct1.depth - ct2.depth;
				//return ct1.node.trav - ct2.node.trav;
				//return ct1.node.freq - ct2.node.freq;
				return ct1.node.uid - ct2.node.uid;
			}
			
		});
		
		build(root, 1);
		while (!tasks.isEmpty()) {
			ChildTask ct = tasks.remove();
			ct.build();
		}
	}
	
	private Integer build(Node node, int depth) {
		if (addresses.containsKey(node)) return addresses.get(node);
		int address = head;
		addresses.put(node, address);
		int code = getCode(node);
		MASTER[head++] = code;
		Node[] children = getChildren(node);
		if (children != null) {
			//reserve space for links
			head += children.length;
			for (int i = 0; i<children.length; i++) {
				tasks.add(new ChildTask(this, address, children[i], depth+1, i));
				//MASTER[address + 1 + i] = build(children[i], depth + 1);
			}
		}
		return address;
	}
	
	PriorityQueue<ChildTask> tasks;
	
	private static class ChildTask {
		
		NodeArray na;
		
		int address;
		Node node;
		int depth;
		int i;
		
		public ChildTask(NodeArray na, int address, Node node, int depth, int i) {
			super();
			this.na = na;
			this.address = address;
			this.node = node;
			this.depth = depth;
			this.i = i;
		}

		public void build() {
			na.MASTER[address + 1 + i] = na.build(node, depth);
		}
	}
	
	public int getCode(Node n) {
		int rv = Magic.XP_CODE;
		if (n.word) rv += Magic.XP_WORD;
		if (n.next == null) return rv;
		int m = 1;
		for (int i=0 ; i<27; i++) {
			if(n.next[i] != null) {
				rv += m;
			}
			m *= 2;
		}
		return rv;
	}
	
	public Node[] getChildren(Node n) {
		if (n.next == null) return null;
		int f = 0;
		for (Node c : n.next) {
			if (c != null) f++;
		}
		Node[] rv = new Node[f];
		int p = 0;
		for (Node c : n.next) {
			if (c != null) rv[p++] = c;
		}
		return rv;
	}
	
	//private int[] array = new 
	
	
	
	
	
	
}



