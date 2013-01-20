package net.codeyak.ndse.v3.gaddag;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.codeyak.ndse.v1.WordList;


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
		
		MagicEncoder na = new MagicEncoder();
		na.build(g.root);
		System.out.println("size = "+na.getSize());
		
		MagicGaddag magic = new MagicGaddag(Arrays.copyOf(na.getEncoded(), na.getSize()));
		
		magic.test("FT");
		magic.test("AXE");
		magic.test("HHH");
	}
	
	private Node root = new Node();
	
	private Map<Node,Node> nodeMap = new HashMap<Node, Node>();
	
	private int wordCount;
	
	public void build(Collection<String> words) {
		populate(words);
		process(words);
		printInfo();
	}
	
	public MagicGaddag getMagicGaddag() {
		MagicEncoder me = new MagicEncoder();
		me.build(root);
		System.out.println("size = "+me.getSize());
		
		MagicGaddag gaddag = new MagicGaddag(Arrays.copyOf(me.getEncoded(), me.getSize()));
		return gaddag;
	}
	
	public NodeGaddag getNodeGaddag() {
		NodeGaddag gaddag = new NodeGaddag(Node.allNodes);
		return gaddag;
	}
	
	public void process(Collection<String> words) {
		root.genHash();
		
		root.capture(nodeMap);
		root.minimize(nodeMap);
	}
	
	public void populate(Collection<String> words) {
		for (String word : words) {
			populate(word);
		}
	}
	
	/**
	 * Given a word ABC generate A#BC, BA#C, CBA. This represent playing ABC with letters before # being played right to left,
	 * then after the #, being played left to right (from the initial letter).
	 * So given positions 12345, starting at position 3
	 * <br>A#BC means A at 3, B at 4, C at 5
	 * <br>BA#C means B at 3, A at 2, C at 4
	 * <br>CBA means C at 3, B at 2, A at 1
	 * <br>So you can see that the data structure allows all possible play positions for a word to be evaluated for
	 * a single hook point without any backtracking.
	 * @param word
	 */
	public void populate(String word) {
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
	
	/**
	 * Record the letters in a Node structure
	 * @param gen
	 */
	public void record(char[] gen) {
		//System.out.println(gen);
		Node cur = root;
		for (char c : gen) {
			cur = cur.add(c);
		}
		cur.setValidWord(true);
	}
	
	public void printInfo() {
		System.out.println(wordCount+" words");
		System.out.println(Node.getNodeCount()+" nodes");
		System.out.println(nodeMap.size()+" unique");
	}
	

}
