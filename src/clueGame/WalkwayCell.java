package clueGame;

public class WalkwayCell extends BoardCell{
	
	public WalkwayCell() {}
	
	public WalkwayCell(int index) {
		super(index);
	}
	
 	@Override
	public Boolean isWalkway(){
		return true;
	}
	
	
	//void draw(){
	//	
	//}
}
