package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;	//test delete later
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;  //test delete later
import javax.swing.JTextArea;   //test delete later

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;

import clueGame.Card.CardType;

public class ClueGame extends JFrame{
	
	private Board board;
	public ControlPanel controlPanel;
	private DisplayCards displayCards;
	public static final int NUMPLAYERS=6;
	private int currPlayer;
	int roll;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Card> seenCards = new ArrayList<Card>();
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private Solution solution = new Solution();
	//Graphics g;
	
	public ClueGame() {		// Default constructor for test purposes only. Use 4 argument ctor below
		board = new Board("ClueLayout.csv", "ClueLegend.txt");
		add(board, BorderLayout.CENTER);
		
		for (Integer i = 0; i<NUMPLAYERS; i++) {						//players map initialization stub!!
			players.put(i, new Player());
		}
	}
	
	public ClueGame(String clueMap, String clueLegend, String cluePlayers, String clueCards) {

		currPlayer = 0;
		detectiveDropdownSetup ();
		board = new Board(clueMap, clueLegend);
		controlPanel = new ControlPanel();
		controlPanel.nextPlayer.addActionListener(new ButtonListener());

		
		Border boardBorder = BorderFactory.createBevelBorder(1);
		board.setBorder(boardBorder);

		try {
			loadConfigFilesPlayers(cluePlayers);
		}
		catch (BadConfigFormatException e) {
			e.printStackTrace();
		}	
		try {
			loadConfigFilesCards(clueCards);
		}
		catch (BadConfigFormatException e) {
			e.printStackTrace();
		}	
		dealCardsToPlayers();
		
		displayCards = new DisplayCards(players.get(0).getMyCards());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Clue Command Interface");
		setSize(920,900);
		
		add(board,BorderLayout.CENTER);
		add(controlPanel,BorderLayout.SOUTH);
		add(displayCards,BorderLayout.EAST);
	}
	
	private void detectiveDropdownSetup ()
	{
		 JMenuBar topMenu = new JMenuBar();
	        JMenu fileDropDown = new JMenu("File");
	        topMenu.add(fileDropDown);
	        JMenuItem detectiveDropButton = new JMenuItem ("Detective Notes");
	        fileDropDown.add(detectiveDropButton);
	        setJMenuBar(topMenu);
	        
	        class detectiveAction implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent arg0){
					JFrame win = new DetectiveNotes();
			        win.setVisible(true);
				}
	        }
	        detectiveDropButton.addActionListener(new detectiveAction());
	}
	
	private int rollDice()
	{
		Random generator = new Random();
		int diceNum = Math.abs(generator.nextInt(6)+1);
		return diceNum;
	}
	
	private void loadConfigFilesCards(String fileName) throws BadConfigFormatException {

		String newLine;
		ArrayList<String[]> inputLines = new ArrayList<String[]>();

		try{
			FileReader reader = new FileReader(fileName);
			BufferedReader bReader = new BufferedReader(reader);

			while ((newLine = bReader.readLine()) != null){		// Array list populated with String[]'s pulled from tab delimited data file
				String[] newLineArray = newLine.split("\t");
				inputLines.add(newLineArray);
			}
			for (String[] newLineArray : inputLines) {	// Cards containing data from file are added to the deck 'cards'
				cards.add(new Card(newLineArray[0], newLineArray[1]));
			}
			bReader.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Can not open the file named: " + e.getMessage());
		}
		catch (IOException e) {
		    System.err.println("Caught IOException: " + e.getMessage());
		}
	}

	private void loadConfigFilesPlayers(String fileName) throws BadConfigFormatException {
		
		String newLine;
		int playerIndex = 0;
		ArrayList<String[]> inputLines = new ArrayList<String[]>();

		try{
			FileReader reader = new FileReader(fileName);
			BufferedReader bReader = new BufferedReader(reader);
			
			while ((newLine = bReader.readLine()) != null){		// Array list populated with String[]'s pulled from tab delimited data file
				String[] newLineArray = newLine.split("\t");
				inputLines.add(newLineArray);
			}
			for (String[] newLineArray : inputLines) {	//Index 0 is populated with a human, 1-5 with computers
				if (playerIndex == 0) {
					players.put(playerIndex, new HumanPlayer(newLineArray[0], newLineArray[1], Integer.parseInt(newLineArray[2]))); 
				} else {
					players.put(playerIndex, new ComputerPlayer(newLineArray[0], newLineArray[1], Integer.parseInt(newLineArray[2])));
				}
				playerIndex++;
			}
			bReader.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Can not open the file named: " + e.getMessage());
		}
		catch (IOException e) {
		    System.err.println("Caught IOException: " + e.getMessage());
		}
		board.setPlayers(players);
		for(Player value: players.values()){
			value.setTotCol(board.getNumColumns());
			
		}
	}

	public int numCards(){
		return cards.size();
	}
	
	public int numWeaponCards(){
		int numWeaponCards = 0;
		for (Card card : cards) {
			if (card.getCardType() == CardType.WEAPON){
				numWeaponCards++;
			}
		}
		return numWeaponCards;
	}
	
	public int numRoomCards(){
		int numRoomCards = 0;
		for (Card card : cards) {
			if (card.getCardType() == CardType.ROOM){
				numRoomCards++;
			}
		}
		return numRoomCards;
	}

	public int numPersonCards(){
		int numPersonCards = 0;
		for (Card card : cards) {
			if (card.getCardType() == CardType.PERSON){
				numPersonCards++;
			}
		}
		return numPersonCards;
	}

	public ArrayList<Card> getSeenCards() {
		return seenCards;
	}

	public void setSeenCards(ArrayList<Card> seenCards) {
		this.seenCards = seenCards;
	}
	
	public void updateSeen(Card seen){
		seenCards.add(seen);
	}

	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	public void dealCardsToPlayers(){
		ArrayList<Integer> dealtCards = new ArrayList<Integer>();
		ArrayList<Card> cardsSolution = new ArrayList<Card>();
		int personIndex = 0, roomIndex = 0, weaponIndex = 0;

		Random generator = new Random();
		Boolean solutionHasRoom = false;
		Boolean solutionHasPerson = false;
		Boolean solutionHasWeapon = false;
		
		for (int i = 0; i < players.size(); i++) {
			players.get(i).setDeck(cards);
		}

		
		while (dealtCards.size() < 3) {
			int randomCard = Math.abs(generator.nextInt()%21);		// Random card index generated
			if (!dealtCards.contains(randomCard)) {			// Check if the current index has not been deal
				if (randomCard < 6 && !solutionHasPerson) {
					solutionHasPerson = true;
					cardsSolution.add(cards.get(randomCard));
					dealtCards.add(randomCard);			// Adds the dealt card to the list
					personIndex = cardsSolution.size() - 1;
				} else if (randomCard < 12 && !solutionHasWeapon) {
					solutionHasWeapon = true;
					cardsSolution.add(cards.get(randomCard));
					dealtCards.add(randomCard);			// Adds the dealt card to the list
					weaponIndex = cardsSolution.size() - 1;
				} else if (!solutionHasRoom) {
					solutionHasRoom = true;
					cardsSolution.add(cards.get(randomCard));
					dealtCards.add(randomCard);			// Adds the dealt card to the list
					roomIndex = cardsSolution.size() - 1;
				}	
			}
		}
		
		solution = new Solution(cardsSolution.get(personIndex), cardsSolution.get(weaponIndex), cardsSolution.get(roomIndex));
		
		
		while (dealtCards.size() < cards.size()) {		// While cards dealt to hand < cards in deck
			int randomCard = Math.abs(generator.nextInt()%21);		// Random card index generated
			if (!dealtCards.contains(randomCard)) {			// Check if the current index has not been deal
				getPlayerNum(dealtCards.size()%6).giveCardToPlayer(cards.get(randomCard));		//Makes sure that no player is skipped
				dealtCards.add(randomCard);			// Adds the dealt card to the list
			}
		}
	};
	
	public void selectAnswer(){};
	
	public Card handleSuggestion(String person, String room, String weapon, int accusingPlayer){
		Random generator = new Random();
		ArrayList<Integer> alreadyHandledIndices = new ArrayList<Integer>();
		while (alreadyHandledIndices.size() < players.size() - 1) {
			int randomIndex = Math.abs(generator.nextInt())%players.size();
			if (!alreadyHandledIndices.contains(randomIndex) && randomIndex != accusingPlayer) {
				Card disprovedCard = players.get(randomIndex).disproveSuggestion(person, room, weapon);
				if (disprovedCard != null) {
					for (int i = 0; i < players.size(); i++) {
						players.get(i).addSeenCard(disprovedCard);
					}
					seenCards.add(disprovedCard);
					return disprovedCard;
				}
				alreadyHandledIndices.add(randomIndex);
			}
		}
		return null;
	}
	
	public void resetSeenList(){
		seenCards = new ArrayList<Card>();
	}
	
	public Boolean checkAccusation(Solution solutionIn) {
		if (this.solution.equals(solutionIn))
		{
			return true;
		}
		else return false;
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return players;
	}
	
	public Player getPlayerNum(int playerIndex) {
		return players.get(playerIndex);
	}
	
	public void setPlayers(HashMap<Integer, Player> players) {
		this.players = players;
	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(String person, String room, String weapon) {     //testing only
		solution = new Solution(person, room, weapon);
	}	
	
	public void runGame()
	{
		boolean endGame;
//		currPlayer=1;
//		while (true)
//		{
		//controlPanel.action(arg0, controlPanel.nextPlayer);	
		
			controlPanel.whoseTurn(players.get(currPlayer).getPlayerName());
			roll = rollDice();
			controlPanel.Dice(roll);
			board.calcTargets(players.get(currPlayer).getLocation(), roll);
			if (currPlayer>0)
			{
				players.get(currPlayer).setLocation(((ComputerPlayer)players.get(currPlayer)).pickLocation(board.getTargets()));
				int spot = players.get(currPlayer).getLocation();
				if (board.isRoom(spot)){
					Suggestion s = ((ComputerPlayer)players.get(currPlayer)).createSuggestion();
					controlPanel.showSuggestion(s.getPerson(), s.getRoom(), s.getWeapon());
					Card c = handleSuggestion(s.getPerson(),s.getRoom(), s.getWeapon(), currPlayer );
					controlPanel.showResult(c.getCardName());
				}
			}else if (currPlayer==0)
			{
				
				((HumanPlayer)players.get(currPlayer)).displayTargets(board.getTargets());

			}

			repaint();
			currPlayer++;
			if (currPlayer>=NUMPLAYERS)
				currPlayer=0;
			if (1==0)
			{
				endGame=false;
			}

		//}
	}
	
	public static void main(String[] args) {
		ClueGame mainInstance = new ClueGame("ClueLayout.csv","ClueLegend.txt","CluePlayers.txt","ClueCards.txt");
		JOptionPane.showMessageDialog(mainInstance, "You are " + mainInstance.players.get(0).getPlayerName() + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		mainInstance.setVisible(true);
		//mainInstance.runGame();
	}




	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == controlPanel.nextPlayer){
				
				controlPanel.showSuggestion(null, null, null);
				controlPanel.showResult(null);
				runGame();
			}

		}
	}
}
