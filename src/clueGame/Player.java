package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import clueGame.Card.CardType;

public class Player {
	private String playerName;
	private Color color;
	private int location;
	private ArrayList<Card> myCards;
	protected ArrayList<Card> seenCards = new ArrayList<Card>();
	protected ArrayList<Card> deckOfCards = new ArrayList<Card>();
	protected int totCol =1;
	protected int column;
	protected int row;
	
		
	public Player() {
		myCards = new ArrayList<Card>();
		myCards.add(new Card());
	}
	
	public Player(String playerName, String strColor, int location) {
		super();
		this.playerName = playerName;
		this.location = location;
		myCards  = new ArrayList<Card>();

		try {     				//strColor String argument is converted into a Color object and saved as Player's Color member var
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}

	}

	public Player(String playerName, String strColor, int location, ArrayList<Card> myCards) {
		super();
		this.playerName = playerName;
		this.location = location;
		this.myCards = myCards;

		try {     				//strColor String argument is converted into a Color object and saved as Player's Color member var
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());     
			color = (Color)field.get(null); } 
		catch (Exception e) {  
			color = null; // Not defined } 
		}

	}
	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval(BoardCell.CELLSIZE*column,BoardCell.CELLSIZE*row, BoardCell.CELLSIZE, BoardCell.CELLSIZE);
		g.setColor(Color.black);
		g.drawOval(BoardCell.CELLSIZE*column,BoardCell.CELLSIZE*row, BoardCell.CELLSIZE, BoardCell.CELLSIZE);
	}
	
	public void setTotCol(int t){
		this.totCol = t;
		this.row = location/t;
		this.column = location%t;
		//System.out.println(totCol);
	}

	public Card disproveSuggestion(String person, String weapon, String room) {
		ArrayList<Integer> checkedCards = new ArrayList<Integer>();
		Random generator = new Random();
		//for (Card currentCard : myCards) {
		while (checkedCards.size() < myCards.size()) {
			int randomIndex = Math.abs(generator.nextInt())%myCards.size();
			if (!checkedCards.contains(randomIndex)) {
				if (myCards.get(randomIndex).getCardName().equalsIgnoreCase(person) || myCards.get(randomIndex).getCardName().equalsIgnoreCase(weapon) || myCards.get(randomIndex).getCardName().equalsIgnoreCase(room)) {
					return myCards.get(randomIndex);
				} 
				checkedCards.add(randomIndex);
			}
		}
		return null;
		//return new Card("None", CardType.BAD);
	}
	
	public Suggestion createSuggestion() {
		return new Suggestion();
	}
	
	public void resetSeenCards(){
		seenCards = new ArrayList<Card>();
	}
	
	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}

	public void addSeenCard(Card cardIn) {
		seenCards.add(cardIn);
	}
	
	public void setDeck(ArrayList<Card> cardsIn) {
		deckOfCards = cardsIn;
	}
	
	public ArrayList<Card> getDeck() {
		return deckOfCards;
	}
	
	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}
	
	public void giveCardToPlayer(Card cardIn) {
		myCards.add(cardIn);
	}

	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
		this.row = location/totCol;
		this.column = location%totCol;
		
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}
	
	
}
