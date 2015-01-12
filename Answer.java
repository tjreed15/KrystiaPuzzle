import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;

public class Answer extends GraphicsProgram{

	private Square[][] squares;
	private GCompound world;
	private int max;
	private boolean visual;
	
	public void run(){
		visual = true;
		addMouseListeners();
		world = new GCompound();
		add(world);
		squares = new Square[NCOLUMNS][NROWS];
		for (int i = 0; i<NCOLUMNS; i++){
			for(int j = 0; j<NROWS; j++){
				squares[i][j] = new Square(world, 1, i*Square.SQUARE_SIDE, j*Square.SQUARE_SIDE);
				if(i>0){
					squares[i][j].setLeft(squares[i-1][j]);
					squares[i-1][j].setRight(squares[i][j]);
				} 
				if(j>0){
					squares[i][j].setAbove(squares[i][j-1]);
					squares[i][j-1].setBelow(squares[i][j]);
				}
			}
		}
		max = NROWS*NCOLUMNS;
		re(squares[0][0], 1);
		re(squares[0][0], 2);
		re(squares[0][0], 3);
		System.out.println("Final: " + max);
	}
	
	public void mouseClicked(MouseEvent e){
		//viewAll();
		//if(e.getButton() == 1) visual = !visual;
	}
	
	
	
	private void re(Square current, int point){
		if (max >= 20){
			viewAll();
			System.out.println(max);
			max = 0;
			waitForClick();
		}
		current.setPoints(point, visual);
		if(current.getAbove() != null && !current.getAbove().isFulfilled()){}
		else{
			Square next = (current.getRight() == null)? null:current.getRight();
			if (next == null) {
				next = (current.getBelow() == null)? null:current.getBelow();
				while(next != null && next.getLeft() != null) next = next.getLeft();
			}
			if (next != null){
				for(int i = 1; i<=MAX_POINTS_PER_SQUARE; i++){
					re(next, i);
					int temp = getTotal();
					if (temp>max && checkAll() == null){
						System.out.println(temp);
					}
					max = (temp>max && checkAll() == null)? temp:max;
				}
			}
		}
	}
	
	private void viewAll(){
		for (int i = 0; i<NCOLUMNS; i++){
			for(int j = 0; j<NROWS; j++){
				squares[i][j].setPoints(squares[i][j].getPoints(), visual);
			}
		}
	}
	
	private int getTotal(){
		int sum = 0;
		for (int i = 0; i<NCOLUMNS; i++){
			for(int j = 0; j<NROWS; j++){
				sum += squares[i][j].getPoints();
			}
		}
		return sum;
	}
	
	private Square checkAll(){
		for (int i = 0; i<NCOLUMNS; i++){
			for(int j = 0; j<NROWS; j++){
				if(!squares[i][j].isFulfilled()) return squares[i][j];
			}
		}
		return null;
	}
	
	
	private static final int NROWS = 3;
	private static final int NCOLUMNS = 3;
	private static final int MAX_POINTS_PER_SQUARE = 4;
}
