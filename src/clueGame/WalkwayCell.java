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
		g.setColor(Color.YELLOW);
	    g.fillRect (CELLSIZE*row,CELLSIZE*column,CELLSIZE,CELLSIZE);
	    
	    g.setColor(Color.BLACK);
	    g.drawRect (CELLSIZE*row,CELLSIZE*column,CELLSIZE,CELLSIZE);
	}

}
