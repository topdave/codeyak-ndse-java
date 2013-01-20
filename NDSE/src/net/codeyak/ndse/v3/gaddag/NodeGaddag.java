package net.codeyak.ndse.v3.gaddag;

public class NodeGaddag implements IGaddag {

	private Node[] nodes;
	
	public NodeGaddag(Node[] nodes) {
		this.nodes = nodes;
	}
	
	@Override
	public boolean isValidNextSymbol(int address, int symbol) {
		Node n = nodes[address];
		return n.hasNext(symbol);
	}


	@Override
	public int getNextAddressFromSymbol(int address, int symbol) {
		Node n = nodes[address];
		Node next = n.getNext(symbol);
		next.usageCount++;
		return next.getUid();
	}

	@Override
	public boolean isValidWord(int address) {
		Node n = nodes[address];
		return n.isValidWord();
	}
	
	
	
}
