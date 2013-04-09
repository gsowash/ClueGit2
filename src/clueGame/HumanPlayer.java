package clueGame;

import java.util.HashSet;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, String strColor, int location) {
		super(playerName, strColor, location);
}

	public HumanPlayer() {
		super();
	}
	
	public void displayTargets(HashSet<BoardCell> targets){
//		for(BoardCell b: targets){
//			b.draw();
//		}
		
	}

}
