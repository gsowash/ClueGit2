package clueGame;

import java.awt.Graphics;

public class WalkwayCell extends BoardCell{
	
	public WalkwayCell() {}
	
	public WalkwayCell(int index) {
		super(index);
	}
	
 	@Override
	public Boolean isWalkway(){
		return true;
	}

	@Override
	void draw(Graphics g) {
	    g.drawRect (CELLSIZE*row,CELLSIZE*column,CELLSIZE,CELLSIZE);
	}

}
