package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 俄罗斯方块游戏场景类
 * @author Leslie Leung
 */
public class TetrisPane extends JPanel {
	public static final int ROWS = 20;	// tetris row
	public static final int COLUMNS = 10;	// tetris columns
	
	/* Difference kinds of shape*/
	public static final int I_SHAPED = 0;
	public static final int S_SHAPED = 1;
	public static final int T_SHAPED = 2;
	public static final int Z_SHAPED = 3;
	public static final int L_SHAPED = 4;
	public static final int O_SHAPED = 5;
	public static final int J_SHAPED = 6;
	
	public static final int KIND = 7;	
	public static final int INIT_SPEED = 1000;	// initial speed of dropping
	
	private static int randomNum = 0;	// number of excisted block
	
	private Random random;
	private Tetromino currentTetromino;	// current four block 
	private Cell[][] wall;		// when value is null , there is no item in block
	private Timer autoDrop;		//timer of autodrop
	private KeyControl keyListener;	
	
	/**
	 build the game pane
	 */
	public TetrisPane() {
		setPreferredSize(new Dimension(COLUMNS * Cell.CELL_SIZE, ROWS * Cell.CELL_SIZE));
		
		random = new Random();
		wall = new Cell[ROWS][COLUMNS];
		autoDrop = new Timer();
		keyListener = new KeyControl();
		
		randomOne();
		
		autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
	}
	
	/**
	 * ramdom build atetromino 
	 */
	public void randomOne() {
		Tetromino tetromino = null;
		
		/*7 kinds of tetromino */
		switch(random.nextInt(KIND)) {
			case I_SHAPED: 
				tetromino = new IShaped();
				break;
			case S_SHAPED: 
				tetromino = new SShaped();
			   	break;
			case T_SHAPED: 
				tetromino = new TShaped();
			    break;
			case Z_SHAPED: 
				tetromino = new ZShaped();
			    break;
			case L_SHAPED: 
				tetromino = new LShaped();
			    break;
			case O_SHAPED: 
				tetromino = new OShaped();
			    break;
			case J_SHAPED: 
				tetromino = new JShaped();
			    break;
		}
		currentTetromino = tetromino;	
		randomNum ++;
	}
	
	/**
	 * 
	 * @return true , player is lose 
	 */
	public boolean isGameOver() {
		int x, y;	// current position
		for(int i = 0; i < getCurrentCells().length; i ++) {
			x = getCurrentCells()[i].getX();
			y = getCurrentCells()[i].getY();
			
			if(isContain(x, y)) {// when current position has another cell , the player is lost
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @return the time interval to generate a block
	 */
	public double interval() {
		return INIT_SPEED * Math.pow((double)39 / 38, 0 - randomNum);
	}
	
	/**
	 * 
	 * @return KeyControl of instance
	 */
	public KeyControl getInnerInstanceOfKeyControl() {
		return keyListener;
	}
	
	/**
	 *implement auto-drop 
	 * @author Leslie Leung
	 */
	private class DropExecution extends TimerTask {	
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			if(isGameOver()) {//如isgameover 
				JOptionPane.showMessageDialog(null, "GameOver");
				autoDrop.cancel();
				removeKeyListener(keyListener);
				return;
			}
			
			if(!isReachBottomEdge()) {
				currentTetromino.softDrop();
			} 
			else {
				landIntoWall();		// add to wall
				removeRows();	//如if row is full , remove it
				randomOne();	// generate new one
				
				autoDrop.cancel();
				autoDrop = new Timer();
				autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
			}
			
			repaint();
		}	
	}
	
	
	public void landIntoWall() {
		int x, y;	// fix x,y position
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			x = getCurrentCells()[i].getX();
			y = getCurrentCells()[i].getY();
			
			wall[y][x] = getCurrentCells()[i];	// add current cell to the wall 
		}
	}
	
	
	private class KeyControl extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT: 
					
					if(!isReachLeftEdge()) {
						currentTetromino.moveLeft();
						repaint();
					}					
					break;
					
				case KeyEvent.VK_RIGHT:	
					
					if(!isReachRightEdge()) {
						currentTetromino.moveRight();
						repaint();
					}
					break;
				
				case KeyEvent.VK_DOWN:	
					
					if(!isReachBottomEdge()) {
						currentTetromino.softDrop();
						repaint();
					}
					
					break;
					
				case KeyEvent.VK_SPACE:	
					
					hardDrop();	
					landIntoWall();		
					removeRows();	
					
					randomOne();
					autoDrop.cancel();
					autoDrop = new Timer();
					autoDrop.schedule(new DropExecution(), (long)interval(), (long)interval());
					
					repaint();
					break;
					
				case KeyEvent.VK_D:	
					
					if(!clockwiseRotateIsOutOfBounds() && !(currentTetromino instanceof OShaped)) {
						currentTetromino.clockwiseRotate(getAxis(), getRotateCells());
						repaint();
					}
					break;
					
				case KeyEvent.VK_A:	
					
					if(!anticlockwiseRotateIsOutOfBounds() && !(currentTetromino instanceof OShaped)) {
						currentTetromino.anticlockwiseRotate(getAxis(), getRotateCells());
						repaint();
					}
					break;
			}
		}
	}
	
	
	private class IShaped extends Tetromino {
		
		public IShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_CYAN);
			cells[0] = new Cell(4, 0, Cell.COLOR_CYAN);
			cells[2] = new Cell(5, 0, Cell.COLOR_CYAN);
			cells[3] = new Cell(6, 0, Cell.COLOR_CYAN);
			
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	
	private class SShaped extends Tetromino {
		
		public SShaped() {
			cells = new Cell[4];
			
			cells[0] = new Cell(4, 0, Cell.COLOR_BLUE);
			cells[1] = new Cell(5, 0, Cell.COLOR_BLUE);
			cells[2] = new Cell(3, 1, Cell.COLOR_BLUE);
			cells[3] = new Cell(4, 1, Cell.COLOR_BLUE);
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	
	private class TShaped extends Tetromino {
		
		public TShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_GREEN);
			cells[0] = new Cell(4, 0, Cell.COLOR_GREEN);
			cells[2] = new Cell(5, 0, Cell.COLOR_GREEN);
			cells[3] = new Cell(4, 1, Cell.COLOR_GREEN);
			
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	

	private class ZShaped extends Tetromino {
	
		public ZShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_ORANGE);
			cells[2] = new Cell(4, 0, Cell.COLOR_ORANGE);
			cells[0] = new Cell(4, 1, Cell.COLOR_ORANGE);
			cells[3] = new Cell(5, 1, Cell.COLOR_ORANGE);
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	
	
	private class LShaped extends Tetromino {
		
		public LShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_PINK);
			cells[0] = new Cell(4, 0, Cell.COLOR_PINK);
			cells[2] = new Cell(5, 0, Cell.COLOR_PINK);
			cells[3] = new Cell(3, 1, Cell.COLOR_PINK);
			
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	

	private class OShaped extends Tetromino {
		
		public OShaped() {
			cells = new Cell[4];
			
			cells[0] = new Cell(4, 0, Cell.COLOR_RED);
			cells[1] = new Cell(5, 0, Cell.COLOR_RED);
			cells[2] = new Cell(4, 1, Cell.COLOR_RED);
			cells[3] = new Cell(5, 1, Cell.COLOR_RED);
			
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}
	

	private class JShaped extends Tetromino {
		
		public JShaped() {
			cells = new Cell[4];
			
			cells[1] = new Cell(3, 0, Cell.COLOR_YELLOW);
			cells[0] = new Cell(4, 0, Cell.COLOR_YELLOW);
			cells[2] = new Cell(5, 0, Cell.COLOR_YELLOW);
			cells[3] = new Cell(5, 1, Cell.COLOR_YELLOW);
			
			
			setAxis();
			setRotateCells();
			
			repaint();
		}
	}

	public void removeRows() {
		for(int i = 0; i < getCurrentCells().length; i ++) {
			removeRow(getCurrentCells()[i].getY());
		}
	}
	

	public Cell getAxis() {
		return currentTetromino.getAxis();
	}
	

	public Cell[] getRotateCells() {
		return currentTetromino.getRotateCells();
	}
	
	public Cell[] getCurrentCells() {
		return currentTetromino.getCells();
	}
	

	public boolean isReachBottomEdge() {
		int oldY, newY, oldX;		
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldY = getCurrentCells()[i].getY();
			newY = oldY + 1;
			oldX = getCurrentCells()[i].getX();
			
			if(oldY == ROWS - 1) {
				return true;
			}
			
			if(isContain(oldX, newY)) {
				return true;
			}
		}
		return false;
	}
	
	
	public boolean isReachLeftEdge() {
		int oldX, newX, oldY;		
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldX = getCurrentCells()[i].getX();
			newX = oldX - 1;
			oldY = getCurrentCells()[i].getY();
			
			if(oldX == 0 || isContain(newX, oldY)) {
				return true;
			}
			
			if(isContain(newX, oldY)) {
				return true;
			}
		}
		return false;
	}
	

	public boolean isReachRightEdge() {
		int oldX, newX, oldY;		
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			oldX = getCurrentCells()[i].getX();
			newX = oldX + 1;
			oldY = getCurrentCells()[i].getY();
			
			if(oldX == COLUMNS - 1 || isContain(newX, oldY)) {
				return true;
			}
			
			if(isContain(newX, oldY)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean clockwiseRotateIsOutOfBounds() {
		int oldX;	
		int oldY;	
		int newX;	
		int newY;	
		
		for(int i = 0; i < 3; i ++) {
			oldX = getRotateCells()[i].getX();
			oldY = getRotateCells()[i].getY();
			
			newX = getAxis().getX() - oldY + getAxis().getY();	
			newY = getAxis().getY() + oldX - getAxis().getX();	
			
			if(newX < 0 || newY < 0 || newX > COLUMNS - 1 || newY > ROWS - 1) {
				return true;
			}
			
			if(isContain(newX, newY)) {
				return true;
			}
		}
		
		return false;
	}
	

	public boolean anticlockwiseRotateIsOutOfBounds() {
		int oldX;	
		int oldY;	
		int newX;	
		int newY;	
		
		for(int i = 0; i < 3; i ++) {
			oldX = getRotateCells()[i].getX();
			oldY = getRotateCells()[i].getY();
			
			newX = getAxis().getX() - getAxis().getY() + oldY;	
			newY = getAxis().getY() + getAxis().getX() - oldX;	
			
			if(newX < 0 || newY < 0 || newX > COLUMNS - 1 || newY > ROWS - 1) {
				return true;
			}
			
			if(isContain(newX, newY)) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isContain(int x, int y) {
		if(wall[y][x] == null) {
			return false;
		} else {
			return true;
		}
	}
	

	public void hardDrop() {
		while(!isReachBottomEdge()) {
			currentTetromino.softDrop();
		}
	}
	

	public void removeRow(int i) {
		int oldY, newY;	
		
		for(int j = 0; j < COLUMNS; j ++) {
			if(wall[i][j] == null) {
				return;
			}
		}
		
		
		for(int k = i; k >= 1; k --){
			System.arraycopy(wall[k - 1], 0, wall[k], 0, COLUMNS);
			
			for(int m = 0; m < COLUMNS; m ++) {
				if(wall[k][m] != null) {
					oldY = wall[k][m].getY();
					newY = oldY + 1;
					wall[k][m].setY(newY);				
				}
			}
			
		}
		Arrays.fill(wall[0], null);
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getBounds().width, getBounds().height);
		
		for(int i = 0; i < ROWS; i ++) {
			for(int j = 0; j < COLUMNS; j ++) {
				if(wall[i][j] == null) {
					g.setColor(Color.WHITE);
					g.fillRect(j * Cell.CELL_SIZE + 1, i * Cell.CELL_SIZE + 1, Cell.CELL_SIZE - 2, Cell.CELL_SIZE - 2);
				} else {
					wall[i][j].paintCell(g);
				}
			}
		}
		
		
		for(int i = 0; i < getCurrentCells().length; i ++) {
			getCurrentCells()[i].paintCell(g);
		}
		
	}
}
