package test;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ClueGame;

public class GameSetupTests {
	static ClueGame testGame;
	
	@BeforeClass
	public static void setUp() {
		testGame = new ClueGame("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueCards.txt");
	}

	
	@Test
	public void testHumanPlayer() {
		int playerNumber=0;
		assertEquals(testGame.getPlayerNum(playerNumber).getPlayerName(),"Colonel Mustard");
		assertEquals(testGame.getPlayerNum(playerNumber).getColor(),Color.orange);
		assertEquals(testGame.getPlayerNum(playerNumber).getLocation(),498);	
	}
	
	@Test
	public void testCompPlayer1() {
		int playerNumber=3;
		assertEquals(testGame.getPlayerNum(playerNumber).getPlayerName(),"Professor Plum");
		assertEquals(testGame.getPlayerNum(playerNumber).getColor(),Color.magenta);	
		assertEquals(testGame.getPlayerNum(playerNumber).getLocation(),19);	
	}
	
	@Test
	public void testCompPlayer2() {
		int playerNumber=5;
		assertEquals(testGame.getPlayerNum(playerNumber).getPlayerName(),"Miss Scarlet");
		assertEquals(testGame.getPlayerNum(playerNumber).getColor(),Color.red);	
		assertEquals(testGame.getPlayerNum(playerNumber).getLocation(),321);	
	}

	@Test
	public void testNumCards() {
		assertEquals(testGame.numCards(),21);
	}
	
	@Test
	public void testNumWpnCards() {
		assertEquals(testGame.numWeaponCards(),6);
	}
	
	@Test
	public void testNumRoomCards() {
		assertEquals(testGame.numRoomCards(),9);
	}
	
	@Test
	public void testNumPersonCards() {
		assertEquals(testGame.numPersonCards(),6);
	}
	
	@Test
	public void CheckWpnCard() {
		boolean cardFound=false;
		Card testCard = new Card ("Revolver",CardType.WEAPON);
		for (Card i: testGame.getCards())
		{
			if (i.equals(testCard))
			{
				cardFound=true;
			}
		}
		assertTrue(cardFound);
	}
	
	@Test
	public void CheckRoomCard() {
		boolean cardFound=false;
		Card testCard = new Card ("Conservatory",CardType.ROOM);
		for (Card i: testGame.getCards())
		{
			if (i.equals(testCard))
			{
				cardFound=true;
			}
		}
		assertTrue(cardFound);
	}
	
	@Test
	public void CheckPersonCard() {
		boolean cardFound=false;
		Card testCard = new Card ("Mrs. White",CardType.PERSON);
		for (Card i: testGame.getCards())
		{
			if (i.equals(testCard))
			{
				cardFound=true;
			}
		}
		assertTrue(cardFound);
	}
	
	@Test
	public void CheckNumCardsInPlayers() {
		int testNumCards=0;
		for (int i=0;i<ClueGame.NUMPLAYERS;i++)								//goes through all players
		{
			testNumCards += testGame.getPlayerNum(i).getMyCards().size(); 	//adds number of cards held by player to current number of cards
		}
		assertEquals(testNumCards,18);										//checks that number of cards equals 21lo
	}
	
	@Test
	public void CheckSameNumCardsInPlayers() {  							//Test will pass with unimplemented methods because each player is initialized 
		boolean sameNumCards = true;										//with one generic card
		int firstCardNum = testGame.getPlayerNum(0).getMyCards().size();
		
		for (int i = 0; i < testGame.NUMPLAYERS; i++) {
			int currCardNum = testGame.getPlayerNum(i).getMyCards().size();
			if (!(currCardNum == firstCardNum) && !(currCardNum == firstCardNum - 1)) {
				sameNumCards = false;
			}
		}
		assertTrue(sameNumCards);
	}
	
	@Test
	public void CheckNoCardDupsPlayers() {	
		Boolean duplicateExists = false;
		ArrayList<Card> checkedCards = new ArrayList<Card>();

		for (int i = 0; i < testGame.NUMPLAYERS; i++) {
			for (int j = 0; j < testGame.getPlayerNum(i).getMyCards().size(); j++) {
				Boolean checkedFail = false;
				for (int k = 0; k < checkedCards.size(); k++) {
					if(checkedCards.get(k).equals(testGame.getPlayerNum(i).getMyCards().get(j))) {
						checkedFail = true;
					}
				}
				if (checkedFail == false) {
					checkedCards.add(testGame.getPlayerNum(i).getMyCards().get(j));
				} else {
					duplicateExists = true;
				}
			}
		}
		
		Assert.assertFalse(duplicateExists);

//		int testNumCards=0;																//with one generic card
//		boolean duplicateCard=false;
//		LinkedList<Card> testCardList = new LinkedList();
//		for (int i=0;i<ClueGame.NUMPLAYERS;i++)  										//iterate through all players
//		{
//			for (int j=0;j<testGame.getPlayerNum(i).getMyCards().size();j++)  			//and all cards held by each player
//			{
//				if (testCardList.contains(testGame.getPlayerNum(i).getMyCards().get(j))) //if card is already in test list
//				{
//					duplicateCard=true;													//then set boolean flag to true
//				}
//				else
//				{
//					testCardList.add(testGame.getPlayerNum(i).getMyCards().get(j));		//else add it to test list
//				}
//			}
//			
//		}
//		assertFalse(duplicateCard);														//make sure boolean flag == false
	}
}

