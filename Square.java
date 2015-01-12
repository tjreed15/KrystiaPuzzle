import java.awt.*;
import acm.graphics.*;

public class Square {

	private Square above, left, right, below;
	private Color color;
	private int points, x, y;
	public GRect square;
	
	private GCompound world;
	
	public Square(GCompound world, int points, int x, int y){
		this.world = world;
		this.points = points;
		this.x = x;
		this.y = y;
		above = left = right = below = null;
		switch (points){
			case 1: color = Color.blue; break;
			case 2: color = Color.red; break;
			case 3: color = Color.green; break;
			case 4: color = Color.yellow; break;
			default: color = Color.blue; break;
		}
		square = new GRect(x, y, SQUARE_SIDE, SQUARE_SIDE);
		square.setFilled(true);
		square.setFillColor(color);
		world.add(square);
	}
	
	public int getPoints(){
		return points;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public Color getColor(){
		return color;
	}
	
	public Square getAbove(){
		return above;
	}
	
	public Square getBelow(){
		return below;
	}
	
	public Square getRight(){
		return right;
	}
	
	public Square getLeft(){
		return left;
	}
	
	public boolean isFulfilled(){
		return fulfilledRec(points-1);
	}
	
	private boolean fulfilledRec(int value){
		if (value == 0) return true;
			boolean check = false;
			if (above != null) check = (above.points == value);
			if (below != null && !check) check = (below.points == value);
			if (left != null && !check) check = (left.points == value);
			if (right != null && !check) check = (right.points == value);
			check = check && fulfilledRec(value - 1);
			return check;
	}
	
	public void setX(int x){
		this.x = x;
		refactor();
	}
	
	public void setY(int y){
		this.y = y;
		refactor();
	}
	
	public void setPoints(int points, boolean visual){
		this.points = points;
		if(visual){
			switch (points){
				case 1: color = Color.blue; break;
				case 2: color = Color.red; break;
				case 3: color = Color.green; break;
				case 4: color = Color.yellow; break;
				default: color = Color.blue; break;
			}
			refactor();
		}
	}
	
	public void setAbove(Square above){
		this.above = above;
	}
	
	public void setBelow(Square below){
		this.below = below;
	}
	
	public void setRight(Square right){
		this.right = right;
	}
	
	public void setLeft(Square left){
		this.left = left;
	}
	
	private void refactor(){
		world.remove(square);
		square = new GRect(x, y, SQUARE_SIDE, SQUARE_SIDE);
		square.setFilled(true);
		square.setFillColor(color);
		world.add(square);
	}
	
	
	
	public static final int SQUARE_SIDE = 100;
}
