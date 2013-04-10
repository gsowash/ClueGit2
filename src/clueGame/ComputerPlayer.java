package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import clueGame.Card.CardType;

public class ComputerPlayer extends Player {

	private Suggestion accuse = null;
	private char lastRoomVisited;
	Random generator = new Random();
	
	public ComputerPlayer(String playerName, String strColor, int location) {
		super(playerName, strColor, location);
	}

	public ComputerPlayer() {
		super();
	}

	public int pickLocation(HashSet<BoardCell> targetList) {
		ArrayList<BoardCell> targetArray = new ArrayList<BoardCell>();
		//Iterator<BoardCell> i = targetList.iterator();
		for (BoardCell currentCell : targetList) {
			targetArray.add(currentCell);
			//System.out.println(currentCell.isDoorway() + ", " + currentCell.getIndex());
			if (currentCell.isDoorway()) {
				RoomCell tempRoom = (RoomCell)currentCell;
				if (!tempRoom.getInitial().equals(lastRoomVisited)) {
					//System.out.println(lastRoomVisited);
					lastRoomVisited = tempRoom.getInitial();
					return currentCell.getIndex();
				} else {
					targetArray.remove(targetArray.size()-1);
				}
			}
		}
		int randomSquare = Math.abs(generator.nextInt()%targetArray.size());
		return targetArray.get(randomSquare).getIndex();
	}
	
	public void setLastRoom(char lastRoom) {
		lastRoomVisited = lastRoom;
	}
	
	public char getLastRoom() {
		return lastRoomVisited;
	}
	
	@Override
	public Suggestion createSuggestion(){
		Random generator = new Random();
		ArrayList<Card> tempDeck = getDeck();
		boolean  acceptable;
		boolean unacceptable1 = false;
		boolean unacceptable2 = false;
		//boolean isGood = false;
		
		int randomIndex = 0;
		int randomIndex2 = 0;
		
//		for (Card card : getSeenCards()) {
//			System.out.println(card.getCardName());
//		}
//		System.out.println("seen above, deck below");
//		for (Card card : deckOfCards) {
//			System.out.println(card.getCardName());
//		}
//		System.out.println(getMyCards().size());
//		for (Card card : getMyCards()) {
//			System.out.println(card.getCardName());
//		}
		
//		while (unacceptable1 == false && unacceptable2 == false)
//		{
//			unacceptable1 = false;
//			unacceptable2 = false;
//			randomIndex = Math.abs(generator.nextInt()%5);
//			for (Card card : getSeenCards()) {
//				if(unacceptable1 == false){
//					if (card == deckOfCards.get(randomIndex)) {
//						unacceptable1 = true;
//					} else {
//						unacceptable1 = false;
//					}
//				}
//			}
//			for (Card card : getMyCards()) {
//				if(unacceptable2 == false) {
//					if (card != deckOfCards.get(randomIndex))
//					{
//						unacceptable2 = true;
//					} else {
//						unacceptable2 = false;
//					}
//				}
//			}
//		}
		acceptable = false;
		while (acceptable==false)
		{
			acceptable = true;
			randomIndex = Math.abs(generator.nextInt()%5);
			//System.out.println(randomIndex);
			for (Card card : getSeenCards())
			{
				//System.out.println(card.getCardName());
				if (card.getCardName().equalsIgnoreCase(deckOfCards.get(randomIndex).getCardName()))
				{
					acceptable = false;
				}
			}
			for (Card card : getMyCards())
			{
				if (card.getCardName().equalsIgnoreCase(deckOfCards.get(randomIndex).getCardName()))
				{
					acceptable = false;
				}
			}
		}

		acceptable = false;
		while (acceptable==false)
		{
			acceptable = true;
			randomIndex2 = 6+Math.abs(generator.nextInt()%5);
		//	System.out.println(randomIndex2);
			for (Card card : getSeenCards())
			{
				if (card.getCardName().equalsIgnoreCase(deckOfCards.get(randomIndex2).getCardName()))//card.equals(deckOfCards.get(randomIndex)))
				{
					acceptable = false;
				}
			}
			for (Card card : getMyCards())
			{
				if (card.getCardName().equalsIgnoreCase(deckOfCards.get(randomIndex2).getCardName()))
				{
					acceptable = false;
				}
			}
		}
		
		Character lastRoom = getLastRoom();
		String roomName;
		if (lastRoom.equals('C')) {
			roomName = "Conservatory";
		} else if (lastRoom.equals('K')) {
			roomName = "Kitchen";
		} else if (lastRoom.equals('B')) {
			roomName = "Ballroom";
		} else if (getLastRoom() == 'R') {
			roomName = "Billiard room";
		} else if (getLastRoom() == 'L') {
			roomName = "Library";
		} else if (getLastRoom() == 'S') {
			roomName = "Study";
		} else if (getLastRoom() == 'D') {
			roomName = "Dining room";
		} else if (getLastRoom() == 'O') {
			roomName = "Lounge";
		} else if (getLastRoom() == 'H') {
			roomName = "Hall";
		} else {
			roomName = "Walkway";
		}
		
		return new Suggestion(tempDeck.get(randomIndex).getCardName(), roomName, tempDeck.get(randomIndex2).getCardName());
	}

	public Suggestion getAccuse() {
		return accuse;
	}

	public void setAccuse(Suggestion accuse) {
		this.accuse = accuse;
	}
	
	

}
