package net.codeyak.ndse.v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**
 * Low level representation of a Scrabble-like board.
 * <br>Code to load representation from a txt file.
 * <br>Code to render the board
 * 
 * @author dave_blake
 *
 */
public class Board {

	private int height;
	private int width;

	private byte[][] letterMultipliers;
	private byte[][] wordMultipliers;

	public Board(File file) {
		try {
			load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getLetterMultiplier(int x, int y) {
		return letterMultipliers[y][x];
	}
	
	public int getWordMultiplier(int x, int y) {
		return wordMultipliers[y][x];
	}

	protected void load(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String header = br.readLine();
		String[] xy = header.split("x");
		width = Integer.parseInt(xy[0]);
		height = Integer.parseInt(xy[1]);
		letterMultipliers = new byte[height][width];
		wordMultipliers = new byte[height][width];
		int y = 0;
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			if (line.startsWith("+"))
				continue;
			char[] row = line.toCharArray();
			for (int x = 0; x < width; x++) {
				char c = row[x + 1];
				byte wm = 1;
				byte lm = 1;
				switch (c) {
				case '2':
					wm = 2;
					break;
				case '3':
					wm = 3;
					break;
				case '4':
					wm = 4;
					break;
				case 'b':
					lm = 2;
					break;
				case 'c':
					lm = 3;
					break;
				case 'd':
					lm = 4;
					break;
				}
				letterMultipliers[y][x] = lm;
				wordMultipliers[y][x] = wm;
			}
			y++;
		}
		br.close();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getTopBorder());
		for (int y = 0; y < height; y++) {
			sb.append("|");
			for (int x = 0; x < width; x++) {
				int lm = letterMultipliers[y][x];
				int wm = wordMultipliers[y][x];
				if (wm > 1)
					sb.append(wm);
				else if (lm > 1)
					sb.append((char)('a' + lm - 1));
				else
					sb.append(" ");
			}
			sb.append("|" + Constants.NL);
		}
		sb.append(getTopBorder());
		return sb.toString();
	}

	private String getTopBorder() {
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		for (int x = 0; x < width; x++) {
			sb.append("-");
		}
		sb.append("+"+Constants.NL);
		return sb.toString();
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}
