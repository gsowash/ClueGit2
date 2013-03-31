package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.BoardCell;
import clueGame.Card;
import clueGame.Card.CardType;
import clueGame.ClueGame;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;
import clueGame.Suggestion;

public class GameActionTests {
	static ClueGame testGame;
	private Solution goodSolution, badPerson, badWeapon, badRoom, badAll;
	private ComputerPlayer testPlayer;

	@BeforeClass
	public static void setUp() {
		testGame = new ClueGame("ClueLayout.csv", "ClueLegend.txt", "CluePlayers.txt", "ClueCards.txt");
	}
	@Before
	public void testAccusationSetup() {
		testGame.setSolution("Colonel Mustard", "Kitchen", "Knife");
		goodSolution = new Solution("Colonel Mustard", "Kitchen", "Knife");
		badPerson = new Solution("Reverend Green", "Kitchen", "Knife");
		badWeapon = new Solution("Colonel Mustard", "Kitchen", "Ice Pick");
		badRoom = new Solution("Colonel Mustard", "Conservatory", "Knife");
		badAll = new Solution("Reverend Green", "Conservatory", "Burrito");
	}
	
	@Test
	public void testAccusationGood() {								//Passes because unimplemented check accusation returns true
		Assert.assertTrue(testGame.checkAccusation(goodSolution));		
	}

	@Test
	public void testAccusationBadPerson() {
		Assert.assertFalse(testGame.checkAccusation(badPerson));
	}
	
	@Test
	public void testAccusationBadWeapon() {
		Assert.assertFalse(testGame.checkAccusation(badWeapon));
	}
	
	@Test
	public void testAccusationBadRoom() {
		Assert.assertFalse(testGame.checkAccusation(badRoom));
	}
	
	@Test
	public void testAccusationBadAll() {
		Assert.assertFalse(testGame.checkAccusation(badAll));
	}
	
	@Test
	public void testPickLocationWithRoomNotVisited() {						//Test will ensure that computer player chooses room
		testPlayer = new ComputerPlayer();									//10 of 10 times. Test assumes room is not most recently visited
		int roomCounter = 0;
		testGame.getBoard().calcTargets(testGame.getBoard().calcIndex(10, 8), 2);
		
		for (int i = 0; i < 10; i++) {
			testPlayer.setLastRoom('z');
			int chosenRoom = testPlayer.pickLocation(testGame.getBoard().getTargets());
			//System.out.println("Correct: " + testGame.getBoard().calcIndex(10, 6));
			//System.out.println("Calculated: " + chosenRoom);
			if (chosenRoom == testGame.getBoard().calcIndex(10, 6)) {
				roomCounter++;
			}
		}
		Assert.assertTrue(roomCounter == 10);
	}
	
	@Test
	public void testPickLocationRandomSelection() {							//Test ensures computer will randomly pick options from a list of targets, rather than just picking one in a nonrandom fashion.
		testPlayer = new ComputerPlayer();
		int room20_15_Counter = 0;
		int room20_17_Counter = 0;
		int room19_16_Counter = 0;
		testGame.getBoard().calcTargets(testGame.getBoard().calcIndex(21, 16), 2);
//		System.out.println(testGame.getBoard().calcIndex(20, 15));
//		System.out.println(testGame.getBoard().calcIndex(20, 17));
//		System.out.println(testGame.getBoard().calcIndex(19, 16));

		
		for (int i = 0; i < 100; i++) {
			int chosenRoom = testPlayer.pickLocation(testGame.getBoard().getTargets());
			if (chosenRoom == testGame.getBoard().calcIndex(20, 15)) {
				room20_15_Counter++;
			}
			else if (chosenRoom == testGame.getBoard().calcIndex(20, 17)) {
				room20_17_Counter++;
			}
			else if (chosenRoom == testGame.getBoard().calcIndex(19, 16)) {
				room19_16_Counter++;
			}
			else {
				System.out.println(chosenRoom + "yeah");
				//fail("Invalid room chosen");
			}
		}
		Assert.assertTrue(room20_15_Counter + room20_17_Counter + room19_16_Counter == 100);
		Assert.assertTrue(room20_15_Counter > 10);
		Assert.assertTrue(room20_17_Counter > 10);
		Assert.assertTrue(room19_16_Counter > 10);
	}
	
	@Test
	public void testPickLocationRoomVisited() {			//Test ensures that a computer player cannot return to a room if they have just visited it
		testPlayer = new ComputerPlayer();
		testPlayer.setLastRoom('K');
		Boolean choseRoom = false;
		
		testGame.getBoard().calcTargets(testGame.getBoard().calcIndex(20, 6), 4);
		
		for (int i = 0; i < 100; i++) {
			int chosenRoom = testPlayer.pickLocation(testGame.getBoard().getTargets());
			if (chosenRoom == testGame.getBoard().calcIndex(17, 5)) {
				choseRoom = true;
				System.out.println(chosenRoom + ", " + i + ", ");
			} 
			else {
				//System.out.println(chosenRoom + ", " + i);
				//fail("Invalid room chosen");
			}			
		}
		Assert.assertFalse(choseRoom);
	}
	
	@Test
	public void testDisproveSuggestionSingleMatch() {					//Tests ensure that a suggestion with a single match is disproved
		testPlayer = new ComputerPlayer();
		ArrayList<Card> testCards = new ArrayList<Card>();
		testCards.add(new Card("Colonel Mustard", CardType.PERSON));
		testCards.add(new Card("Reverend Green", CardType.PERSON));
		testCards.add(new Card("Library", CardType.ROOM));
		testCards.add(new Card("Kitchen", CardType.ROOM));
		testCards.add(new Card("Candlestick", CardType.WEAPON));
		testCards.add(new Card("Revolver", CardType.WEAPON));
		
		testPlayer.setMyCards(testCards);   //computer player now has a hand with two of each type of card
		
		Assert.assertTrue(testPlayer.disproveSuggestion("Reverend Green", "Wrench", "Conservatory").getCardName().equalsIgnoreCase("Reverend Green")); //Person is disprovable
		Assert.assertTrue(testPlayer.disproveSuggestion("Miss Scarlet", "Revolver", "Conservatory").getCardName().equalsIgnoreCase("Revolver")); //Weapon is disprovable
		Assert.assertTrue(testPlayer.disproveSuggestion("Miss Scarlet", "Wrench", "Kitchen").getCardName().equalsIgnoreCase("Kitchen")); //Room is disprovable	
		Assert.assertEquals(testPlayer.disproveSuggestion("Miss Scarlet", "Wrench", "Conservatory"), null); //No cards are disprovable
		
	}
	
	@Test
	public void testDisproveSuggestionMultiMatch() {					//Tests ensure that a suggestion with a multiple matches is disproved by a random selection
		testPlayer = new ComputerPlayer();
		ArrayList<Card> testCards = new ArrayList<Card>();
		testCards.add(new Card("Colonel Mustard", CardType.PERSON));
		testCards.add(new Card("Reverend Green", CardType.PERSON));
		testCards.add(new Card("Library", CardType.ROOM));
		testCards.add(new Card("Kitchen", CardType.ROOM));
		testCards.add(new Card("Candlestick", CardType.WEAPON));
		testCards.add(new Card("Revolver", CardType.WEAPON));
		
		testPlayer.setMyCards(testCards);   //computer player now has a hand with two of each type of card
		
		int personCounter = 0;
		int weaponCounter = 0;
		int roomCounter = 0;
		
		for(int i = 0; i < 100; i++) {
			String disproved = testPlayer.disproveSuggestion("Reverend Green", "Library", "Candlestick").getCardName();
			if (disproved.equalsIgnoreCase("Reverend Green")) {
				personCounter++;
			}
			else if (disproved.equalsIgnoreCase("Library")) {
				roomCounter++;
			}
			else if (disproved.equalsIgnoreCase("Candlestick")) {
				weaponCounter++;
			}
		}
		
		Assert.assertTrue(personCounter + roomCounter + weaponCounter == 100);
		Assert.assertTrue(personCounter > 10);
		Assert.assertTrue(weaponCounter > 10);
		Assert.assertTrue(roomCounter > 10);
		
	}

	@Test
	public void testDisproveSuggestionAllPlayers() {			//Tests ensure that all player are queried, and in a random order
		HumanPlayer testPlayer0 = new HumanPlayer();
		ArrayList<Card> testCards0 = new ArrayList<Card>();
		testCards0.add(new Card("Mrs. White", CardType.PERSON));
		testCards0.add(new Card("Candlestick", CardType.WEAPON));
		testCards0.add(new Card("Library", CardType.ROOM));
		testCards0.add(new Card("Kitchen", CardType.ROOM));
		testPlayer0.setMyCards(testCards0);   //player now has a hand
		
		ComputerPlayer testPlayer1 = new ComputerPlayer();
		ArrayList<Card> testCards1 = new ArrayList<Card>();
		testCards1.add(new Card("Mrs. Peacock", CardType.PERSON));
		testCards1.add(new Card("Revolver", CardType.WEAPON));
		testCards1.add(new Card("Study", CardType.ROOM));
		testCards1.add(new Card("Conservatory", CardType.ROOM));
		testPlayer1.setMyCards(testCards1);   //computer player now has a hand
		
		ComputerPlayer testPlayer2 = new ComputerPlayer();
		ArrayList<Card> testCards2 = new ArrayList<Card>();
		testCards2.add(new Card("Miss Scarlet", CardType.PERSON));
		testCards2.add(new Card("Rope", CardType.WEAPON));
		testCards2.add(new Card("Hall", CardType.ROOM));
		testCards2.add(new Card("Ballroom", CardType.ROOM));
		testPlayer2.setMyCards(testCards2);   //computer player now has a hand
		
		ComputerPlayer testPlayer3 = new ComputerPlayer();
		ArrayList<Card> testCards3 = new ArrayList<Card>();
		testCards3.add(new Card("Colonel Mustard", CardType.PERSON));
		testCards3.add(new Card("Wrench", CardType.WEAPON));
		testCards3.add(new Card("Dining Room", CardType.ROOM));
		testPlayer3.setMyCards(testCards3);   //computer player now has a hand 
		
		ComputerPlayer testPlayer4 = new ComputerPlayer();
		ArrayList<Card> testCards4 = new ArrayList<Card>();
		testCards4.add(new Card("Reverend Green", CardType.PERSON));
		testCards4.add(new Card("Lead Pipe", CardType.WEAPON));
		testCards4.add(new Card("Lounge", CardType.ROOM));
		testPlayer4.setMyCards(testCards4);   //computer player now has a hand
		
		ComputerPlayer testPlayer5 = new ComputerPlayer();
		ArrayList<Card> testCards5 = new ArrayList<Card>();
		testCards5.add(new Card("Professor Plum", CardType.PERSON));
		testCards5.add(new Card("Knife", CardType.WEAPON));
		testCards5.add(new Card("Billiard Room", CardType.ROOM));
		testPlayer5.setMyCards(testCards5);   //computer player now has a hand
		
		HashMap<Integer, Player> testPlayers = new HashMap<Integer, Player>();
		testPlayers.put(0, testPlayer0);
		testPlayers.put(1, testPlayer1);
		testPlayers.put(2, testPlayer2);
		testPlayers.put(3, testPlayer3);
		testPlayers.put(4, testPlayer4);
		testPlayers.put(5, testPlayer5);
		
		testGame.setPlayers(testPlayers);		//All players have been given hands and added to the player list for test game
		
		Assert.assertEquals(testGame.handleSuggestion("Colonel Mustard", "Dining Room", "Wrench", 3), null); 		//made a suggestion that no players could disprove because he possesses all the suggested cards
		
		Assert.assertEquals(testGame.handleSuggestion("Reverend Green", "Library", "Lead Pipe", 4).getCardName(), "Library");		//made a suggestion that the human player can disprove
		
		Assert.assertEquals(testGame.handleSuggestion("Mrs. White", "Kitchen", "Candlestick", 0), null);			//made a suggestion containing only cards that the suggesting player has. very similar to the first assert in this test because all cards have been dealt
	
		String tempString=new String();
		int counter1=0,counter2=0;
		for (int i = 0; i < 100; i++){
			tempString=testGame.handleSuggestion("Miss Scarlet", "Billiard Room", "Wrench", 5).getCardName();
			if (tempString.equalsIgnoreCase("Miss Scarlet"))
			{
				counter1++;
			}
			if (tempString.equalsIgnoreCase("Wrench"))
			{
				counter2++;
			}
		}
		assertTrue(counter1>10);					// test that Miss Scarlet was return more than 10 times to show random order of querying b/c cards held by two separate players
		assertTrue(counter2>10);					// test that Wrench was return more than 10 times to show random order of querying b/c cards held by two separate players
		assertTrue(counter1+counter2==100);			// test that only Miss Scarlet and the Wrench were returned during the 100 test runs
	}

	@Test
	public void testComputerSuggestion() {	
		Suggestion testSuggestion= new Suggestion();
		testGame.resetSeenList();
		
		HumanPlayer testPlayer0 = new HumanPlayer();
		ArrayList<Card> testCards0 = new ArrayList<Card>();
		testCards0.add(new Card("Mrs. White", CardType.PERSON));
		testCards0.add(new Card("Candlestick", CardType.WEAPON));
		testCards0.add(new Card("Library", CardType.ROOM));
		testCards0.add(new Card("Kitchen", CardType.ROOM));
		testPlayer0.setMyCards(testCards0);   //player now has a hand
		
		ComputerPlayer testPlayer1 = new ComputerPlayer();
		ArrayList<Card> testCards1 = new ArrayList<Card>();
		testCards1.add(new Card("Mrs. Peacock", CardType.PERSON));
		testCards1.add(new Card("Revolver", CardType.WEAPON));
		testCards1.add(new Card("Study", CardType.ROOM));
		testCards1.add(new Card("Conservatory", CardType.ROOM));
		testPlayer1.setMyCards(testCards1);   //computer player now has a hand
		

		HashMap<Integer, Player> testPlayers = new HashMap<Integer, Player>();
		testPlayers.put(0, testPlayer0);
		testPlayers.put(1, testPlayer1);
		
		testGame.setPlayers(testPlayers);		//All players have been given hands and added to the player list for test game
		
		testGame.updateSeen(new Card("Miss Scarlet",CardType.PERSON));
		testGame.updateSeen(new Card("Reverend Green",CardType.PERSON));
		testGame.updateSeen(new Card("Professor Plum",CardType.PERSON));
		testGame.updateSeen(new Card("Rope",CardType.WEAPON));
		testGame.updateSeen(new Card("Knife",CardType.WEAPON));
		testGame.updateSeen(new Card("Wrench",CardType.WEAPON));
		testGame.updateSeen(new Card("Library",CardType.ROOM));
		testGame.updateSeen(new Card("Billiard Room",CardType.ROOM));
		testGame.updateSeen(new Card("Ballroom",CardType.ROOM));
		
		testGame.getPlayerNum(1).setSeenCards(testGame.getSeenCards());
		testGame.getPlayerNum(1).setDeck(testGame.getCards());
		testGame.getPlayerNum(1).setLocation(testGame.getBoard().calcIndex(4,3));
		

		
		((ComputerPlayer) testGame.getPlayers().get(1)).setLastRoom('C');		//Sets player's room to conservatory
		
		
		for (int i=0;i<100;i++) {
			testSuggestion = testGame.getPlayerNum(1).createSuggestion();
//			System.out.println(testSuggestion.getPerson());
//			System.out.println(testSuggestion.getWeapon());
//			System.out.println(testSuggestion.getRoom());

			Assert.assertFalse(testSuggestion.getPerson().equalsIgnoreCase("Miss Scarlet"));	//Doesn't choose any seen person cards
			Assert.assertFalse(testSuggestion.getPerson().equalsIgnoreCase("Reverend Green"));			
			Assert.assertFalse(testSuggestion.getPerson().equalsIgnoreCase("Professor Plum"));			
			Assert.assertFalse(testSuggestion.getWeapon().equalsIgnoreCase("Rope"));			//Doesn't choose any seen weapon cards
			Assert.assertFalse(testSuggestion.getWeapon().equalsIgnoreCase("Knife"));	
			Assert.assertFalse(testSuggestion.getWeapon().equalsIgnoreCase("Wrench"));	
//			Assert.assertFalse(testSuggestion.getRoom().equalsIgnoreCase("Library"));			//Doesn't choose any seen room cards
//			Assert.assertFalse(testSuggestion.getRoom().equalsIgnoreCase("Billiard Room"));
//			Assert.assertFalse(testSuggestion.getRoom().equalsIgnoreCase("Ballroom"));

			Assert.assertFalse(testSuggestion.getPerson().equalsIgnoreCase("Mrs. Peacock"));	//Doesn't choose any cards that it itself has
			Assert.assertFalse(testSuggestion.getWeapon().equalsIgnoreCase("Revolver"));


			
			Assert.assertTrue(testSuggestion.getRoom().equalsIgnoreCase("Conservatory"));		//Must choose room that it is in
		}
	}
}
