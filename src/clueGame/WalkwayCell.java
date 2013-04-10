package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell{
	
	
	public WalkwayCell() {}
	
	public WalkwayCell(int index) {
		
		super(index);
		color = Color.YELLOW;
		
	}
	
 	@Override
	public Boolean isWalkway(){
		return true;
	}
 	
 	

	@Override
	void draw(Graphics g) {
		g.setColor(color);
	    g.fillRect (CELLSIZE*column,CELLSIZE*row,CELLSIZE,CELLSIZE);
	    
	    g.setColor(Color.BLACK);
	    g.drawRect (CELLSIZE*column,CELLSIZE*row,CELLSIZE,CELLSIZE);
	}

}
