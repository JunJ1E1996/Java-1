package worm;

import java.awt.*;
import java.util.Arrays;

/**
 * 怜勛彴濬
 * @author Leslie Leung
 * @see Cell
 */
public class Worm {
	
	public static final int UP = 1;		//方向：上
	public static final int DOWN = -1;		//方向：下
	public static final int LEFT = 2;	//方向：左
	public static final int RIGHT = -2;	//方向：右
	public static final int DEFAULT_LENGTH = 6;	//设置贪吃蛇的默认长度为6
	public static final int DEFAULT_DIRECTION = RIGHT;	//默认运行方向
	public static final int INIT_SPEED = 100;	//贪吃蛇初始速度
	
	private int currentLength;		//贪吃蛇当前长度
	private int currentDirection;	//贪吃蛇当前方向
	private boolean eat;	//判断贪吃蛇是否吃到食物
	private Cell[] cells;
	
	/**
	 * 构造方法，初始化贪吃蛇
	 */
	public Worm() {
		cells = new Cell[DEFAULT_LENGTH];
		currentDirection = DEFAULT_DIRECTION;	//设置游戏一开始时的默认方向为DOWN
		currentLength = DEFAULT_LENGTH;		//初始化当前贪吃蛇长度为默认长度
		
		for(int i = 0;i < DEFAULT_LENGTH; i ++) {
			cells[i] = new Cell(DEFAULT_LENGTH - i - 1, 0);
		}

	}
	
	/**
	 * 鳳怜勛彴絞酗僅
	 * @return 怜勛彴腔絞酗僅
	 */
	public int getCurrentLength() {
		return currentLength;
	}
	
	/**
	 * 鳳怜勛彴腔絞源砃
	 * @return 怜勛彴腔絞源砃
	 */
	public int getCurrentDirection() {
		return currentDirection;
	}
	
	/**
	 * 潰脤怜勛彴杅郪岆瘁迵珨跺賦萸腔弇离笭詁
	 * @param x 換腔賦萸腔筵釴梓
	 * @param y 換腔賦萸腔軝釴梓
	 * @return 笭詁ㄛtrue˙祥笭詁ㄛfalse
	 */
	public boolean contains(int x, int y) {
		
		for(int i = 0; i < currentLength; i ++) {
			if(x == cells[i].getX() && y == cells[i].getY()) {
				return true;
			}
		}
			
		return false;
	}
	
	/**
	 * 蜊曹源砃
	 * @param 怜勛彴陔腔鰾俴源砃
	 */
	public void changeDirection(int direction) {
		/* 彆換腔陔源砃迵絞怜勛彴堍俴源砃眈肮麼眈毀ㄛ殿隙ㄛ祥粒睡紱釬   */
		if(currentDirection == direction || currentDirection + direction == 0) {
			return;
		}
		
		currentDirection = direction;
	}
	
	/**
	 * 鰾俴呾楊ㄩ痄壺藺帣賦萸ㄛ豻垀衄賦萸厘綴痄ㄛ婬參藺帣賦萸氝樓善芛賦萸腔弇离笢
	 * @param direction 鰾俴源砃
	 * @return 怜勛彴岆瘁勛善妘昜ㄛtrue桶尨勛善ㄛfalse桶尨勛祥善
	 */
	public boolean creep(int direction, Cell food) {
		eat = false;
		currentDirection = direction;	//蔚鰾俴源砃扢离峈怀腔源砃
		Cell head = newHead(currentDirection);	//笭扢芛賦萸
		
		/* 彆怜勛彴鰾俴腔狟珨弇离奻湔婓妘昜ㄛ輛俴杅郪孺ㄛ汜傖陔怜勛彴ㄛ甜笭陔汜傖妘昜 */
		if( head.getX() == food.getX() && head.getY() == food.getY() ) {
			cells = Arrays.copyOf(cells, cells.length + 1);
			eat = true;
			currentLength ++;	//勛善妘昜ㄛ酗僅赻崝
		}
		
		for(int i = cells.length - 1; i > 0; i --) {
			cells[i] = cells[i - 1];
		}
		
		cells[0] = head;
		
		return eat;
	}
	
	/**
	 * 笭陔汜傖芛賦萸呾楊ㄩ跦擂鰾俴源砃笭陔汜傖芛賦萸
	 * @param currentDirection 絞鰾俴源砃
	 * @return 陔膘腔芛賦萸
	 */
	public Cell newHead(int currentDirection) {
		Cell newHead = null;
		
		switch(currentDirection) {
			case UP: 
				newHead = new Cell(cells[0].getX(), cells[0].getY() - 1);
				break;
			case DOWN:
				newHead = new Cell(cells[0].getX(), cells[0].getY() + 1);
				break;
			case LEFT: 
				newHead = new Cell(cells[0].getX() - 1, cells[0].getY());
				break;
			case RIGHT:
				newHead = new Cell(cells[0].getX() + 1, cells[0].getY());
				break;
		}
		
		return newHead;
	}
	
	/**
	 * 潰脤怜勛彴岆瘁袉僻呾楊
	 * @param direction 絞堍雄源砃
	 * @return 岆瘁莉汜袉僻
	 */
	public boolean hit(int direction) {
		Cell nextHead = newHead(direction);
		
		/* 潰脤岆瘁癲袉善赻旯 */
		if( this.contains(nextHead.getX(), nextHead.getY()) ) {
			return true;
		}
		
		/* 潰脤怜勛彴岆瘁癲族 */
		if(nextHead.getX() < 0 || nextHead.getX() >= WormStage.COLUMNS
				||nextHead.getY() < 0 || nextHead.getY() >= WormStage.ROWS) {
			return true;
		}
		
		return false;
	}
	
	/* 餅秶怜勛彴 */
	public void paint(Graphics g) {
		for(int i = 0; i < cells.length; i ++) {
			cells[i].paintCell(g);
		}
	}
}
