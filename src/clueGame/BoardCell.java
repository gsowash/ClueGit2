package clueGame;

public abstract class BoardCell {
	protected int row;
	protected int column;
	protected int index;
	
	public BoardCell(){}
	
	public BoardCell(int index) {
		this.index = index;
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
