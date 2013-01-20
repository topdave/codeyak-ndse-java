package net.codeyak.ndse.v3.gaddag;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.codeyak.ndse.v1.WordList;
import net.codeyak.ndse.v3.Magic;


/**
 * Generates a GADDAG - this is a two way directed acyclic word graph.
 * 
 * The magic is in the reverse character
 * 
 * @author dave_blake
 *
 */
public class Generator {

	public static void main(String[] args) {
		Generator g = new Generator();
		//g.populate("CARE");
		//g.populate("CAR");
		//g.populate("BAR");
		//g.populate("BARE");
		
		
		File rootFolder = new File("data");
		String dictionary = "SOWPODS";
		WordList wordList = new WordList(new File(rootFolder, "words/opusthepenguin.wordpress.com/" + dictionary + ".txt"));
		Collection<String> words = wordList.getWordList();
		
		//Collection<String> words = new ArrayList<String>();
		//words.add("FT");
		
		g.populate(words);
		
		g.process(words);
		g.printInfo();
		
		NodeArray na = new NodeArray();
		na.build(g.root);
		System.out.println("size = "+na.head);
		
		Magic magic = new Magic(Arrays.copyOf(na.MASTER, na.head));
		
		magic.test("FT");
		magic.test("AXE");
		magic.test("HHH");
	}
	
	public Magic build(Collection<String> words) {
		populate(words);
		process(words);
		printInfo();
		
		NodeArray na = new NodeArray();
		na.build(root);
		System.out.println("size = "+na.head);
		
		Magic magic = new Magic(Arrays.copyOf(na.MASTER, na.head));
		return magic;
	}
	
	Node root = new Node();
	
	Map<Node,Node> nodeMap = new HashMap<Node, Node>();
	
	public void process(Collection<String> words) {
		root.genHash();
		
		//root.print("\n");
		//System.out.println();
		//System.out.println();
		
		root.capture(nodeMap);
		root.minimize(nodeMap);
		
		for (String word : words) {
			//root.setTrav(rev(word.toCharArray()), 0);
			trav(word);
		}
		//printTrav(500);
		
		
		root.setFreq();
		//printFreq(500);
	}
	
	public char[] rev(char[] cs) {
		char[] rv = new char[cs.length];
		for (int i=0; i<cs.length; i++) {
			rv[cs.length - 1 - i] = cs[i];
		}
		return rv;
	}
	
	public void trav(String word) {
		//System.out.println(word);
		wordCount++;
		final char[] cs = word.toCharArray();
		
		for (int i=0; i<cs.length-1; i++) {
			char[] gen = new char[cs.length + 1];
			for (int j=0; j<=i; j++) {
				gen[i-j] = cs[j];
			}
			gen[i+1] = '#';
			for (int j=i+1; j<cs.length; j++) {
				gen[j+1] = cs[j];
			}
			root.setTrav(gen, 0);
		}
		//last entry with no reversal
		char[] gen = new char[cs.length];
		for (int i=0; i<cs.length; i++) {
			gen[i] = cs[cs.length - 1 - i];
		}
		root.setTrav(gen, 0);
	}
	
	public void printTrav(int num) {
		List<Node> nodes = new ArrayList<Node>(nodeMap.keySet());
		Collections.sort(nodes, new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n1.trav - n2.trav;
			}
		});
		
		int c = 1;
		for (Node n : nodes) {
			System.out.println("Node "+(c) + ": "+n.trav);
			n.print("\n");
			System.out.println();
			if (c++ >= num) break;
		}
	}
	
	public void printFreq(int num) {
		List<Node> nodes = new ArrayList<Node>(nodeMap.keySet());
		Collections.sort(nodes, new Comparator<Node>() {
			@Override
			public int compare(Node n1, Node n2) {
				return n2.freq - n1.freq;
			}
		});
		
		int c = 1;
		for (Node n : nodes) {
			System.out.println("Node "+(c) + ": "+n.freq);
			n.print("\n");
			System.out.println();
			if (c++ >= num) break;
		}
	}
	
	public void populate(Collection<String> words) {
		for (String word : words) {
			populate(word);
		}
	}
	
	public void populateUni(Collection<String> words) {
		for (String word : words) {
			wordCount++;
			record(word.toCharArray());
		}
	}
	
	private int wordCount;
	
	public void populate(String word) {
		//System.out.println(word);
		wordCount++;
		final char[] cs = word.toCharArray();
		
		for (int i=0; i<cs.length-1; i++) {
			char[] gen = new char[cs.length + 1];
			for (int j=0; j<=i; j++) {
				gen[i-j] = cs[j];
			}
			gen[i+1] = '#';
			for (int j=i+1; j<cs.length; j++) {
				gen[j+1] = cs[j];
			}
			record(gen);
		}
		//last entry with no reversal
		char[] gen = new char[cs.length];
		for (int i=0; i<cs.length; i++) {
			gen[i] = cs[cs.length - 1 - i];
		}
		record(gen);
	}
	
	public void record(char[] gen) {
		//System.out.println(new String(gen));
		
		Node cur = root;
		for (char c : gen) {
			cur = cur.add(c);
		}
		cur.word = true;
	}
	
	public void printInfo() {
		System.out.println(wordCount+" words");
		System.out.println(Node.getNodeCount()+" nodes");
		System.out.println(nodeMap.size()+" unique");
	}
	

}
