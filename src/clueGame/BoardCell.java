package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class BoardCell {
	protected int row;
	protected int column;
	protected int index;
	static final int CELLSIZE = 30;
	protected int totCol;
	protected Color color;
	
	public BoardCell(){}
	
	public BoardCell(int index) {
		this.index = index;
		//int col = Board.getNumColumns();
		
		
	}
	
	abstract void draw (Graphics g);
	
	public void setTotCol(int t){
		this.totCol = t;
		this.row = index/t;
		this.column = index%t;
		//System.out.println(totCol);
	}
	
	public void setColor(Color c){
 		this.color = c;
 	}
	
	public boolean containsClick(int mouseX, int mouseY) {
		  Rectangle rect = new Rectangle(CELLSIZE*column,CELLSIZE*row,CELLSIZE,CELLSIZE);
		  if (rect.contains(new Point(mouseX, mouseY))) {
		    return true;
		  }else return false;
		 // return true;
		}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Boolean isWalkway(){
		return false;
	}
	public Boolean isRoom(){
		return false;
	}
	public Boolean isDoorway(){
		return false;
	}
	//void draw(){
	//	
	//}
}
