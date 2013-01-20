package net.codeyak.ndse.v3.gaddag;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;



public class MagicEncoder {
	
	private int[] encoded = new int[15000000];
	
	private int nextAddress = 0;
	
	private Map<Node, Integer> addresses = new HashMap<Node, Integer>();
	
	public int[] getEncoded() {
		return encoded;
	}
	
	public int getSize() {
		return nextAddress;
	}

	public void build(Node root) {
		tasks = new PriorityQueue<ChildTask>(100000, new Comparator<ChildTask>() {
			@Override
			public int compare(ChildTask ct1, ChildTask ct2) {
				int diff = ct1.node.usageCount - ct2.node.usageCount;
				if (diff != 0) return diff;
				return ct2.node.getUid() - ct1.node.getUid();
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
		int address = nextAddress;
		addresses.put(node, address);
		int code = getCode(node);
		encoded[nextAddress++] = code;
		Node[] children = getChildren(node);
		if (children != null) {
			//reserve space for links
			nextAddress += children.length;
			for (int i = 0; i<children.length; i++) {
				tasks.add(new ChildTask(this, address, children[i], depth+1, i));
			}
		}
		return address;
	}
	
	PriorityQueue<ChildTask> tasks;
	
	private static class ChildTask {
		
		private MagicEncoder na;
		
		private int address;
		private Node node;
		private int depth;
		private int childNum;
		
		public ChildTask(MagicEncoder na, int address, Node node, int depth, int childNum) {
			super();
			this.na = na;
			this.address = address;
			this.node = node;
			this.depth = depth;
			this.childNum = childNum;
		}

		public void build() {
			na.encoded[address + 1 + childNum] = na.build(node, depth);
		}
	}
	
	public int getCode(Node n) {
		int rv = MagicGaddag.XP_CODE;
		if (n.isValidWord()) rv += MagicGaddag.XP_WORD;
		if (!n.hasNext()) return rv;
		int m = 1;
		for (int i=0 ; i<27; i++) {
			if(n.getNext(i) != null) {
				rv += m;
			}
			m *= 2;
		}
		return rv;
	}
	
	public Node[] getChildren(Node n) {
		if (!n.hasNext()) return null;
		int f = 0;
		for (Node c : n.getNext()) {
			if (c != null) f++;
		}
		Node[] rv = new Node[f];
		int p = 0;
		for (Node c : n.getNext()) {
			if (c != null) rv[p++] = c;
		}
		return rv;
	}
	
}



