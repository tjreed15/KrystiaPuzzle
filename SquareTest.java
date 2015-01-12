import acm.graphics.*;
import acm.program.*;
import java.awt.event.*;

public class SquareTest extends GraphicsProgram{
	
	private Square[][] squares;
	private GCompound world;
	private int score, max;
	private GLabel scoreLabel;
	private GOval marker;
	
	public void run(){
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
		marker = new GOval(0,0,0,0);
		add(marker);
		score = max = NROWS*NCOLUMNS;
		scoreLabel = new GLabel("Score: " + score, 700, 200);
		add(scoreLabel);
		addMouseListeners();
	}
	
	public void mouseClicked(MouseEvent e){
		if(e.getButton() == 1){
			GRect clicked = (GRect) world.getElementAt(e.getX(), e.getY());
			Square change = (clicked == null)? null:getSquare(clicked);
			if (change != null && change.getPoints() < 4)  change.setPoints(change.getPoints() + 1, true);
			else if (change != null) change.setPoints(1, true);
		}
		else if (e.getButton() == 3){	
			remove(marker);
			if(checkAll() == null){
				marker = new GOval(0,0);
			}
			else{
				marker = new GOval(checkAll().getX() + 45, checkAll().getY() + 45, 10, 10);
			}
			add(marker);
		}
		editScore();
	}
	
	private Square getSquare(GObject clicked){
		for (int i = 0; i<NCOLUMNS; i++){
			for(int j = 0; j<NROWS; j++){
				if (squares[i][j].square == clicked) return squares[i][j];
			}
		}
		return null;
	}
	
	private Square checkAll(){
		for (int i = 0; i<NCOLUMNS; i++){
			for(int j = 0; j<NROWS; j++){
				if(!squares[i][j].isFulfilled()) return squares[i][j];
			}
		}
		return null;
	}
	
	private void editScore(){
		score = 0;
		for (int i = 0; i<NCOLUMNS; i++){
			for(int j = 0; j<NROWS; j++){
				score += squares[i][j].getPoints();
			}
		}
		if (score>max && checkAll() == null) max = score;
		remove(scoreLabel);
		scoreLabel = new GLabel("Score: " + score + " and Max: " + max, 700, 200);
		add(scoreLabel);
	}
	
	
	private static final int NROWS = 3;
	private static final int NCOLUMNS = 3;
	
}
