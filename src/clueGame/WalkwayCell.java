package clueGame;

import java.awt.Color;
import java.awt.Graphics;

public class WalkwayCell extends BoardCell{
	private Color color = Color.YELLOW;
	
	public WalkwayCell() {}
	
	public WalkwayCell(int index) {
		super(index);
	}
	
 	@Override
	public Boolean isWalkway(){
		return true;
	}
 	
 	public void setColor(Color c){
 		this.color = c;
 	}

	@Override
	void draw(Graphics g) {
		g.setColor(color);
	    g.fillRect (CELLSIZE*column,CELLSIZE*row,CELLSIZE,CELLSIZE);
	    
	    g.setColor(Color.BLACK);
	    g.drawRect (CELLSIZE*column,CELLSIZE*row,CELLSIZE,CELLSIZE);
	}

}
