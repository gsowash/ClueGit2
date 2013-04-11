package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.Border;

import clueGame.Board.BoardListener;
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
	public ArrayList<Card> unSeenCards = new ArrayList<Card>();
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private Solution solution = new Solution();
	private Boolean humanMove = true;
	public AccusationWindow win;
	public AccusationWindow suggest;

	
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
		controlPanel.accusation.addActionListener(new ButtonListener());
		win = new AccusationWindow();
		win.submit.addActionListener(new ButtonListener());
		suggest = new AccusationWindow();
		suggest.submit.addActionListener(new ButtonListener());
		board.addMouseListener(new BoardListener());
		

		
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
	        final JFrame win = new DetectiveNotes();
	        class detectiveAction implements ActionListener{
				@Override
				public void actionPerformed(ActionEvent arg0){
			        win.setVisible(true);
			        win.addWindowListener(new WindowAdapter()
					{
						@Override
						public void windowClosing(WindowEvent e)
						{
							win.setVisible(false);
						}
					});
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
		unSeenCards = cards;
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
		
		Random rn = new Random();
		int w = Math.abs(rn.nextInt() % 6)  + 6;
		int r = Math.abs(rn.nextInt() % 9) + 12;
		int p = Math.abs(rn.nextInt() % 6);
		int cw= 0, cr = 0, cp = 0;
		
		
		String person = null, weapon = null, room = null;
		person = cards.get(p).getCardName();
		dealtCards.add(p);
		weapon = cards.get(w).getCardName();
		dealtCards.add(w);
		room = cards.get(r).getCardName();
		dealtCards.add(r);
	
		
		solution.setSolution(person,  room, weapon);

	
		
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
			Suggestion accuse = ((ComputerPlayer)players.get(currPlayer)).getAccuse();
			if(accuse != null){
				String accusationin =  accuse.getPerson() + " in the " + accuse.getRoom() + " with the " + accuse.getWeapon();
				//JOptionPane.showMessageDialog(null, players.get(currPlayer).getPlayerName() + "is accusing " + accuse.getPerson(), accuse.getRoom(), accuse.getWeapon() + "\nNice try. Not Quite.");

				controlPanel.showSuggestion("Accusing " + accuse.getPerson(), accuse.getRoom(), accuse.getWeapon());
				if(accuse.equals(solution)){
					JOptionPane.showMessageDialog(null, players.get(currPlayer).getPlayerName() + " wins the game! \nThey guessed\n" + accusationin);
				}else{
					JOptionPane.showMessageDialog(null, players.get(currPlayer).getPlayerName() + " accused \n" + accusationin + "\n but not right");
					((ComputerPlayer)players.get(currPlayer)).setAccuse(null);
					//controlPanel.showResult("False");
					//JOptionPane.showMessageDialog(null, players.get(currPlayer).getPlayerName() + " doesn't win");
				}


			}else{

				players.get(currPlayer).setLocation(((ComputerPlayer)players.get(currPlayer)).pickLocation(board.getTargets()));
				int spot = players.get(currPlayer).getLocation();
				if (board.isRoom(spot)){
					Suggestion s = ((ComputerPlayer)players.get(currPlayer)).createSuggestion();

					for(Player value: players.values()){
						if(s.getPerson().equalsIgnoreCase(value.getPlayerName())){
							value.setLocation(players.get(currPlayer).getLocation());
						}
					}					

					controlPanel.showSuggestion(s.getPerson(), s.getRoom(), s.getWeapon());
					Card c = handleSuggestion(s.getPerson(),s.getRoom(), s.getWeapon(), currPlayer );
						if (c == null){
							controlPanel.showResult("Cannot disprove");
//							String r = null;
//							for(Card p: unSeenCards){
//								if(p.getCardType() == CardType.ROOM){
//									r= p.getCardName();
//								}
//							}
//							if(!r.equals(null)){
//								Suggestion accusing = new Suggestion(s.getPerson(), r,  s.getWeapon());
////							
//								((ComputerPlayer)players.get(currPlayer)).setAccuse(accusing);
//							}

							((ComputerPlayer)players.get(currPlayer)).setAccuse(s);
						}else{
							controlPanel.showResult(c.getCardName());
							unSeenCards.remove(c);
						}

						//currPlayer++;
					}
				}
			}else if (currPlayer==0)
			{
				humanMove = false;
				HashSet<BoardCell> places = board.getTargets();
				for (BoardCell b: places){
					b.setColor(Color.CYAN);
					
				}
				//((HumanPlayer)players.get(currPlayer)).displayTargets(board.getTargets());

			}

			repaint();
			currPlayer++;
			if (currPlayer>=NUMPLAYERS)
				currPlayer=0;
//			if (1==0)
//			{
//				endGame=false;
//			}

		//}
	}
	
	public static void main(String[] args) {
		ClueGame mainInstance = new ClueGame("ClueLayout.csv","ClueLegend.txt","CluePlayers.txt","ClueCards1.txt");
		JOptionPane.showMessageDialog(mainInstance, "You are " + mainInstance.players.get(0).getPlayerName() + ", press Next Player to begin play", "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		mainInstance.setVisible(true);
	}




	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == controlPanel.nextPlayer){
				if(humanMove){
					controlPanel.showSuggestion(null, null, null);
					controlPanel.showResult(null);
					runGame();
				} else {
					//JDialog j = new JDialog();
					JOptionPane.showMessageDialog(null, "You must move first.");
				}

			}else if(e.getSource() == controlPanel.accusation){
				if(currPlayer == 1 && !humanMove){
					win = new AccusationWindow();
					win.submit.addActionListener(new ButtonListener());
					//JFrame accuse = new AccusationWindow('Z');
					win.type('Z');
					win.setVisible(true);
					
					humanMove = true;
					HashSet<BoardCell> places = board.getTargets();
					if(humanMove){
						for (BoardCell c: places){
							if(c.isWalkway()){
								c.setColor(Color.YELLOW);
							}else c.setColor(Color.WHITE);
						}	
						repaint();
					}
					
//					int spot = players.get(currPlayer-1).getLocation();
//					if (board.isRoom(spot)){
//						JOptionPane.showMessageDialog(null, "ACCUSING!!");
//					}else{
//						JOptionPane.showMessageDialog(null, "YOU GOTTA BE IN A ROOM FOOL!!");
//					}
				}else {
					JOptionPane.showMessageDialog(null, "It is not your turn, sir/madam!");
				}
				
			}else if(e.getSource() == win.submit){
				
				
				String person = (String)win.peopleCombo.getSelectedItem();
				String weapon = (String)win.weaponsCombo.getSelectedItem();
				String room = (String)win.roomCombo.getSelectedItem();
				if(person.equals("Unknown" ) || weapon.equals("Unknown" ) || room.equalsIgnoreCase("Unknown")){
					JOptionPane.showMessageDialog(null, "That's not a valid choice. Try again.");
				}else{

					win.dispose();
					Solution test = new Solution(person, room, weapon);

					if(solution.equals(test) ){
						JOptionPane.showMessageDialog(null, "THAT'S IT! YOU WON!\n" + person + " in the " + room + " with the " + weapon);
						dispose();
					}else{
						
						JOptionPane.showMessageDialog(null, "Nice try. Not Quite.");
					
					}
				}

			}else if(e.getSource() == suggest.submit){
				String person = (String)suggest.peopleCombo.getSelectedItem();
				String weapon = (String)suggest.weaponsCombo.getSelectedItem();
				if(person.equals("Unknown" ) || weapon.equals("Unknown" )){
					JOptionPane.showMessageDialog(null, "That's not a valid choice. Try again.");
				}else{
					suggest.dispose();
				String room = suggest.getRoom();
				//System.out.println(room);
				Card c = handleSuggestion(person, room, weapon, currPlayer-1);
				controlPanel.showSuggestion(person, room, weapon);
				if (c == null){
					controlPanel.showResult("Cannot disprove");
					
				}else{
					controlPanel.showResult(c.getCardName());
					unSeenCards.remove(c);
				}
				}
				
				
				
				
				
			}

		}
	}
	
	
class BoardListener implements MouseListener{

		
		@Override
		public void mouseClicked(MouseEvent e) {
			HashSet<BoardCell> places = board.getTargets();
			if (currPlayer == 1){
				Boolean clicked = false;
				for(BoardCell b : places)
				{
					if(b.containsClick(e.getX(), e.getY())){
						int n= b.getIndex();
						players.get(currPlayer-1).setLocation(n);
						clicked = true;
						humanMove = true;							
					}
						
					
				}
				if(!clicked){
					JOptionPane.showMessageDialog(null, "That is not an available. Try a blue one.");
				}
				
			}
			if(humanMove){
				for (BoardCell c: places){
					if(c.isWalkway()){
						c.setColor(Color.YELLOW);
					}else c.setColor(Color.WHITE);
				}	
				repaint();
			}
			int spot = players.get(currPlayer-1).getLocation();
			if(board.isRoom(spot)){
				//JOptionPane.showMessageDialog(null, "SUGGEST!");
				
				//AccusationWindow suggest = new AccusationWindow(((RoomCell)board.getCellAt(players.get(currPlayer-1).getLocation())).getInitial());
				//suggest.submit.addActionListener(new ButtonListener());
				char t = ((RoomCell)board.getCellAt(players.get(currPlayer-1).getLocation())).getInitial();
				
				suggest = new AccusationWindow();
				suggest.submit.addActionListener(new ButtonListener());
				suggest.type(t);
				
				suggest.setVisible(true);
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
