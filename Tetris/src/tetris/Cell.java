package tetris;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 网格类
 * @author Leslie Leung
 */
public class Cell {
	public static final int CELL_SIZE = 25;		// ONE BLOCK SIZE

	/* ALL BLOCK COLOR  */
	public static final int COLOR_CYAN = 0;
	public static final int COLOR_BLUE = 1;
	public static final int COLOR_GREEN = 2;
	public static final int COLOR_YELLOW = 3;
	public static final int COLOR_ORANGE = 4;
	public static final int COLOR_RED = 5;
	public static final int COLOR_PINK = 6;

	private int color;	// BLOCK COLOR
	private int x;	// X POSITION 
	private int y;	// Y POSITION

	/**
	 * 构造方法
	 * @param x POSITION
	 * @param y POSITION
	 * @param style BLOCK STYLE,USE TO DICIDE BLOCK COLOR
	 */
	public Cell(int x, int y, int style) {
		/* CONSTRUCTOR of Cell */
		switch(style) {
			case 0: color = COLOR_CYAN; break;
			case 1: color = COLOR_BLUE; break;
			case 2: color = COLOR_GREEN; break;
			case 3: color = COLOR_YELLOW; break;
			case 4: color = COLOR_ORANGE; break;
			case 5: color = COLOR_RED; break;
			case 6: color = COLOR_PINK; break;
		}

		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 * @param newX NEW X POSITION
	 */
	public void setX(int newX) {
		x = newX;
	}

	/**
	 * 
	 * @param newY NEW Y POSITION
	 */
	public void setY(int newY) {
		y = newY;
	}

	/**
	 * GET THE Cell X POSITION 
	 */
	public int getX() {
		return x;
	}

	/**
	 * GET THE Cell Y POSITION
	 */
	public int getY() {
		return y;
	}

	/**
	 * PAINT METHOD
	 * @param g Graphics 
	 */
	public void paintCell(Graphics g) {
		switch(color) {
			case COLOR_CYAN: g.setColor(Color.CYAN);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_BLUE: g.setColor(Color.BLUE);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_GREEN: g.setColor(Color.GREEN);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_YELLOW: g.setColor(Color.YELLOW);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_ORANGE: g.setColor(Color.ORANGE);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_RED: g.setColor(Color.RED);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
			case COLOR_PINK: g.setColor(Color.PINK);
				g.fillRect(x * CELL_SIZE + 1, y * CELL_SIZE + 1, CELL_SIZE - 2, CELL_SIZE - 2);
				break;
		}
	}
}
