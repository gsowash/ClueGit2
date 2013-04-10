package clueGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class RoomCell extends BoardCell {
	private char roomInitial;
	private DoorDirection doorDirection;
	private final int DOORWIDTH = 5;
	private boolean name = false;

	public enum DoorDirection {
		UP, DOWN, LEFT, RIGHT, NONE
	}

	public RoomCell() {
		super();
		color = Color.WHITE;
	}
	public RoomCell(int index) {
		super(index);
		color = Color.WHITE;
	}

	public RoomCell(String room) {
		super();
		roomInitial = room.charAt(0);
		if (room.length() == 2) {
			char direction = room.charAt(1);
			if (direction == 'U') {
				doorDirection = DoorDirection.UP;
			} else if (direction == 'D') {
				doorDirection = DoorDirection.DOWN;
			} else if (direction == 'L') {
				doorDirection = DoorDirection.LEFT;
			} else if (direction == 'R'){
				doorDirection = DoorDirection.RIGHT;
			} else{
				doorDirection = DoorDirection.NONE;
			}
		} else {
			doorDirection = DoorDirection.NONE;
		}
	}
	
	public RoomCell(String room, int index) {
		super(index);
		roomInitial = room.charAt(0);
		if (room.length() == 2) {
			char direction = room.charAt(1);
			if (direction == 'U') {
				doorDirection = DoorDirection.UP;
			} else if (direction == 'D') {
				doorDirection = DoorDirection.DOWN;
			} else if (direction == 'L') {
				doorDirection = DoorDirection.LEFT;
			} else if (direction == 'R'){
				doorDirection = DoorDirection.RIGHT;

			} else{
				if (direction == 'N'){
					name = true;
				}
				doorDirection = DoorDirection.NONE;
			}
		} else {
			doorDirection = DoorDirection.NONE;
		}
	}
	@Override
	public Boolean isRoom() {
		return true;
	}
	@Override
	public Boolean isDoorway(){
		if(doorDirection == DoorDirection.NONE){
			return false;
		}else{
			return true;
			}
	}
	public DoorDirection getDoorDirection() {
		return doorDirection;

	}
	public void setDoorDirection(DoorDirection doorDirection) {
		this.doorDirection = doorDirection;
	}

	public Character getInitial() {
		return roomInitial;
	}
	public void setInitial(char roomInitial) {
		this.roomInitial = roomInitial;
	}

	@Override
	void draw(Graphics g) {
	
		if(color == Color.CYAN){
			g.setColor(color);
			g.fillRect(CELLSIZE*column,CELLSIZE*row,CELLSIZE,CELLSIZE);
			g.setColor(Color.BLACK);
			g.drawRect(CELLSIZE*column,CELLSIZE*row,CELLSIZE,CELLSIZE);
		}
		
		
		g.setColor(Color.BLUE);

		
		
		
		switch(doorDirection){
		case UP:
			g.fillRect(CELLSIZE*column,CELLSIZE*row,CELLSIZE,DOORWIDTH);
			break;
		case DOWN:
			g.fillRect(CELLSIZE*column,(CELLSIZE*(row+1)- DOORWIDTH),CELLSIZE,DOORWIDTH);
			break;
		case RIGHT:
			g.fillRect((CELLSIZE*(column+1) - DOORWIDTH),CELLSIZE*row,DOORWIDTH,CELLSIZE);
			break;
		case LEFT:
			g.fillRect(CELLSIZE*column,CELLSIZE*row,DOORWIDTH,CELLSIZE);
			break;
		case NONE:
			break;		
		}
		
		if (name){
			//g.setFont(Font.SANS_SERIF);
			//Graphics2D g2 = (Graphics2D)g;
			//System.out.println("yes");
			g.setFont(new Font(null, Font.BOLD, 14));
			switch(roomInitial){
			case 'C':
				g.drawString("Conservatory", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'K':
				g.drawString("Kitchen", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'B':
				g.drawString("Ballroom", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'R':
				g.drawString("Billiard Room", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'L':
				g.drawString("Library", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'S':
				g.drawString("Study", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'D':
				g.drawString("Dining Room", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'O':
				g.drawString("Lounge", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'H':
				g.drawString("Hall", CELLSIZE*column,CELLSIZE*row);
				break;
			case 'X':
				g.drawString("Closet", CELLSIZE*column,CELLSIZE*row);
				break;
			
			}
			
			
			
		}
	}
}