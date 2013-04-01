package clueGame;

import java.awt.Graphics;

public abstract class BoardCell {
	protected int row;
	protected int column;
	protected int index;
	static final int CELLSIZE = 20;
	
	public BoardCell(){}
	
	public BoardCell(int index) {
		this.index = index;
	}
	
	abstract void draw (Graphics g);
	
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
