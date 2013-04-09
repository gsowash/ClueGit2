package clueGame;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import clueGame.RoomCell.DoorDirection;

public class Board extends JPanel
{
	private ArrayList<BoardCell> cells = new ArrayList<BoardCell>(); 
	private Map<Character, String> rooms = new HashMap<Character, String>(); 
	private Map<Integer, LinkedList<Integer>> adjMtx = new HashMap<Integer, LinkedList<Integer>>();
	private HashSet<BoardCell> targets = new HashSet<BoardCell>();
	private static int numRows;
	private  int numColumns;
	private static int TOTAL_NUMB;
	private boolean[] visited;
	private String configFile;
	private String legend;
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	
	public Board(String configFile, String legend) {
		super();
		//adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		//targets = new HashSet<BoardCell>();
		this.configFile = configFile;
		this.legend = legend;
		try {
			loadConfigFiles();
		} catch (BadConfigFormatException e) {
			e.printStackTrace();
		}
		TOTAL_NUMB = numRows * numColumns;
		visited = new boolean[TOTAL_NUMB];
		calcAdjacencies();
		setBorder(new EtchedBorder());

	}
	
//	public HashSet<BoardCell> getTargets() {
//		return targets;
//	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		for (BoardCell cell : cells)
		{
			cell.draw(g);
		}
		
		for(Player value: players.values()){
			//value.setTotCol(numColumns);
			value.draw(g);
		}
		
		//System.out.println(numColumns);

		//g.drawLine(numColumns, y1, x2, y2);
		
		
		
	}
	
	public void loadConfigFiles() throws BadConfigFormatException {
			loadConfigFilesLegend(legend);//change based on our files
			loadConfigFilesFormat(configFile); //change name based on our files
	}

	public void loadConfigFilesFormat(String fileName) throws BadConfigFormatException{
		try {
			int columns = 0;
			int rows = 0;
			FileReader reader = new FileReader(fileName);
			Scanner in = new Scanner(reader);
			int testcol =0;
			while (in.hasNextLine()) {
				String[] rooms;
				String line = in.nextLine();
				rooms = line.split(",");
				if(testcol > 0){
					if(columns != rooms.length){
						throw new BadConfigFormatException("columns are not equal");
					}
				}
				columns = rooms.length;
				testcol++;
				rows++;
				for (int i = 0; i < rooms.length; i++) {
					if (rooms[i].equals("W")) {
						WalkwayCell walkway = new WalkwayCell(cells.size());
						cells.add(walkway);
					} else if(rooms[i].charAt(0) != 'C' && rooms[i].charAt(0) != 'K' && rooms[i].charAt(0)!= 'B'
					&& rooms[i].charAt(0) != 'R' && rooms[i].charAt(0) != 'L' && rooms[i].charAt(0) != 'S' 
					&& rooms[i].charAt(0) != 'D' && rooms[i].charAt(0) != 'O' && rooms[i].charAt(0) != 'H' 
					&& rooms[i].charAt(0) != 'X' && rooms[i].charAt(0) != 'W') {
						throw new BadConfigFormatException(rooms[i]);
					}else{
						RoomCell room = new RoomCell(rooms[i], cells.size());
						cells.add(room);
					}
				}
			}
			in.close();
			numColumns = columns;
			
			numRows = rows;
		} catch (FileNotFoundException e) {
			System.out.println("Can't open file: " + e.getMessage());
		}
		//System.out.println(numColumns);
		for (BoardCell c: cells){
			c.setTotCol(numColumns);
		}
		for(Player value: players.values()){
			value.setTotCol(numColumns);
		}
		
	}

	private void loadConfigFilesLegend(String fileName) throws BadConfigFormatException {
		try{
			FileReader reader = new FileReader(fileName);
			Scanner in = new Scanner(reader);
			while (in.hasNextLine()){
				String newLine = in.nextLine();
				int commas = 0;
				for(int i = 0; i < newLine.length(); i++) { //returns number of items as a function of commas in a row
					if(newLine.charAt(i) == ',') commas++;
				}
				if(commas != 1){
					throw new BadConfigFormatException(newLine);
				}
				char letterOfRoom = newLine.charAt(0);
				if(newLine.indexOf(',') == -1){
					throw new BadConfigFormatException(newLine);
					}
				if(letterOfRoom != 'C' && letterOfRoom != 'K' && letterOfRoom != 'B'
					&& letterOfRoom != 'R' && letterOfRoom != 'L' && letterOfRoom != 'S' 
					&& letterOfRoom != 'D' && letterOfRoom != 'O' && letterOfRoom != 'H' 
					&& letterOfRoom != 'X' && letterOfRoom != 'W'){
						throw new BadConfigFormatException(newLine);
					}
				String room = newLine.substring(3);
				rooms.put(letterOfRoom, room);
			}
				in.close();
			}
			catch(FileNotFoundException e){
				System.out.println("Can not open the file named: " + e.getMessage());
			}
	}

	public int calcIndex(int rowNumber, int columnNumber) {
		return (numColumns * rowNumber) + columnNumber;
	}

	public void calcAdjacencies(){
		for(int x = 0; x < numRows; x++){ //Used x to represent horizontal axis 
			for(int y =0; y < numColumns; y++){ //Used y to represent vertical axis
				int index = calcIndex(x,y);
				LinkedList<Integer> list = new LinkedList<Integer>();
				//board cell is a doorway
				if(cells.get(index).isDoorway() == true){
					RoomCell roomCell = (RoomCell)cells.get(index);
					//UP
					if(roomCell.getDoorDirection() == DoorDirection.UP){
						int value = calcIndex(x-1, y);
						list.add(value);
					}
					//DOWN
					else if(roomCell.getDoorDirection() == DoorDirection.DOWN){
						int value = calcIndex(x+1, y);
						list.add(value);
					}
					//LEFT
					else if(roomCell.getDoorDirection() == DoorDirection.LEFT){
						int value = calcIndex(x, y-1);
						list.add(value);
					}
					//RIGHT
					else{
						int value = calcIndex(x, y+1);
						list.add(value);
					}
				}
				//board is a walkway
				if(cells.get(index).isWalkway() == true){
					//UP
					if(x != 0){ //ensures it is not on an edge of the board
						if(cells.get(calcIndex(x-1, y)).isWalkway() == true){ //If a walkway is directly above
							int value = calcIndex(x-1, y);
							list.add(value);
							}
						if(cells.get(calcIndex(x-1, y)).isDoorway() == true){ //If a doorway facing down is above
							RoomCell roomcell = (RoomCell) cells.get(calcIndex(x-1, y));
							if(roomcell.getDoorDirection() == DoorDirection.DOWN){
								int value = calcIndex(x-1, y);
								list.add(value);
							}
						}
					}//LEFT
					if(y != 0){//ensures it is not on an edge of the board
						if(cells.get(calcIndex(x, y-1)).isWalkway() == true){ //If to the left is a walkway 
							int value = calcIndex(x, y-1);
							list.add(value);
							}
						if(cells.get(calcIndex(x, y-1)).isDoorway() == true){ //If a doorway is facing right
							RoomCell roomcell = (RoomCell) cells.get(calcIndex(x, y-1));
							if(roomcell.getDoorDirection() == DoorDirection.RIGHT){
								int value = calcIndex(x, y-1);
								list.add(value);
							}
						}
					}
					//BOTTOM
					if(x != numRows -1){ // not on edge of the board
						if(cells.get(calcIndex(x+1, y)).isWalkway() == true){ //If down is a walkway
							int value = calcIndex(x+1, y);
							list.add(value);
							}
						if(cells.get(calcIndex(x+1, y)).isDoorway() == true){ //If a doorway is facing UP
							RoomCell roomcell = (RoomCell) cells.get(calcIndex(x+1, y));
							if(roomcell.getDoorDirection() == DoorDirection.UP){
								int value = calcIndex(x+1, y);
								list.add(value);
							}
						}
					}
					//RIGHT
					if(y != numColumns -1){ // not on edge of board
						if(cells.get(calcIndex(x, y+1)).isWalkway() == true){ // if right is a walkway
							int value = calcIndex(x, y+1);
							list.add(value);
							}
						if(cells.get(calcIndex(x, y+1)).isDoorway() == true){ //Doorway facing left 
							RoomCell roomcell = (RoomCell) cells.get(calcIndex(x, y+1));
							if(roomcell.getDoorDirection() == DoorDirection.LEFT){
								int value = calcIndex(x, y+1);
								list.add(value);
								}
							}
						}
					}
				adjMtx.put(index, list);
			}
		}
	}
	
	public void calcTargets(int startLocation, int steps) {
		targets = new HashSet<BoardCell>();
		for (int i = 0; i < TOTAL_NUMB; i++) {
			visited[i] = false;
		}
		LinkedList<Integer> list = new LinkedList<Integer>();
		LinkedList<Integer> path = new LinkedList<Integer>();
		visited[startLocation] = true;
		list = getAdjList(startLocation);
		calcTargetsHelper(path, list, steps);
	}

	public void calcTargetsHelper(LinkedList<Integer> path,LinkedList<Integer> list, int steps) {
		for (int item : list) {
			if (visited[item] == false) {
				visited[item] = true;
				path.addLast(item);
				if (path.size() == steps
						|| cells.get(item).isDoorway() == true) {
					targets.add(cells.get(item));
				} else {
					LinkedList<Integer> adjacent = new LinkedList<Integer>();
					adjacent = getAdjList(item);
					calcTargetsHelper(path, adjacent, steps);
				}
				path.removeLast();
				visited[item] = false;
			}
		}
	}

	public RoomCell getRoomCellAt(int rowNumber, int columnNumber) {
		int index = calcIndex(rowNumber, columnNumber);
		RoomCell room = new RoomCell();
		room = (RoomCell)cells.get(index);
		return room;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public void setRooms(Map<Character, String> rooms) {
		this.rooms = rooms;
	}

	public static int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumColumns() {
		
		return numColumns;
		
	}

	public void setNumColumns(int numColumns) {
		this.numColumns = numColumns;
	}

	public LinkedList<Integer> getAdjList(int index) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list = adjMtx.get(index);
		return list;
	}

	public HashSet<BoardCell> getTargets() {
		return targets;	
	}
	public BoardCell getCellAt(int index) {
		BoardCell cell = new WalkwayCell();
		cell =(BoardCell)cells.get(index);
		return cell;
	}

	

	public void setPlayers(HashMap<Integer, Player> players) {
		this.players = players;
	}
	
	

}
