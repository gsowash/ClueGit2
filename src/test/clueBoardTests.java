package test;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;

public class clueBoardTests {
	private static Board board;
	public static final int NUM_ROOMS = 11; //walkway is a room :D
	public static final int NUM_ROWS = 22;
	public static final int NUM_COLUMNS = 22;
	public static Map<Character, String> rooms;
	@Before 
	public void setup() throws BadConfigFormatException {
		board = new Board("config.csv", "Legend.txt");
		board.loadConfigFiles(); //if you comment out this line 2 tests pass.  So i figure there is a mistake somewhere in the initialization of the board files
		rooms=board.getRooms();
	}
	/*
	@Test
	public void testException(){
		try{
			board.loadConfigFiles();
		}catch (BadConfigFormatException e){
			e.toString();
		}
	}
	
	@Test
	public void RoomsNumber(){
		int test = rooms.size();
		assertEquals(NUM_ROOMS, test);
	}
	@Test
	public void RoomsMap(){
		assertEquals("Conservatory", rooms.get('C'));
		assertEquals("Ballroom", rooms.get('B'));
		assertEquals("Billiard room", rooms.get('R'));
		assertEquals("Dining room", rooms.get('D'));
		assertEquals("Walkway", rooms.get('W'));
		assertEquals("Kitchen", rooms.get('K'));
		assertEquals("Study", rooms.get('S'));
		assertEquals("Hall", rooms.get('H'));
		assertEquals("Closet", rooms.get('X'));
		assertEquals("Library", rooms.get('L'));
		assertEquals("Lounge", rooms.get('O'));
	}
	
	@Test
	public void correctBoardDimensions(){
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());
}	
	@Test
	public void DoorDirections(){
		RoomCell room = board.getRoomCellAt(1,2);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		room = board.getRoomCellAt(0,16);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		room = board.getRoomCellAt(17,1);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getRoomCellAt(17,16);	
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
	}
	@Test
	public void testRoomInitials() {
		assertEquals(new Character('L'), board.getRoomCellAt(1, 1).getInitial());
		assertEquals(new Character('S'), board.getRoomCellAt(1, 6).getInitial());
		assertEquals(new Character('H'), board.getRoomCellAt(16, 1).getInitial());
		assertEquals(new Character('B'), board.getRoomCellAt(20, 10).getInitial());
		assertEquals(new Character('K'), board.getRoomCellAt(19, 19).getInitial());
	}
	
	@Test
	public void testCalcIndex(){
		int test = board.calcIndex(3, 8);
		assertEquals(74, test);
		test = board.calcIndex(1, 20);
		assertEquals(42, test);
		test = board.calcIndex(21, 22);
		assertEquals(484, test);	
	}
	*/
	@Test
	public void ajacencyWalkway(){//Light Blue (13,5)
		int index = board.calcIndex(12,5);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(4,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(11,5)));
		Assert.assertTrue(test.contains(board.calcIndex(13,5)));
		Assert.assertTrue(test.contains(board.calcIndex(12,4)));
		Assert.assertTrue(test.contains(board.calcIndex(12,6)));
	}
	@Test
	public void ajacencyEdge1(){//Brown (15,1)
		int index = board.calcIndex(14,0);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(2,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(13,0)));	
		Assert.assertTrue(test.contains(board.calcIndex(14,1)));
	}
	@Test
	public void ajacencyEdge2(){//Brown (1,6)
		int index = board.calcIndex(0,7);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(2,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(0,6)));
		Assert.assertTrue(test.contains(board.calcIndex(1,7)));	
	}
	@Test
	public void ajacencyEdge3(){//Brown (1,15)
		int index = board.calcIndex(21,7);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(2,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(20,7)));	
		Assert.assertTrue(test.contains(board.calcIndex(21,6)));
	}
	@Test
	public void ajacencyEdge4(){//Brown (1,22)
		int index = board.calcIndex(7,21);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(2,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(8,21)));	
		Assert.assertTrue(test.contains(board.calcIndex(7,20)));
	}
	@Test
	public void ajacencyBesidesRoom1(){//White (13,1)
		int index = board.calcIndex(12,0);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(2,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(13,0)));	
		Assert.assertTrue(test.contains(board.calcIndex(12,1)));
	}
	@Test
	public void ajacencyBesidesRoom2(){//Green (16,20)
		int index = board.calcIndex(16,20);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(3,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(16,19)));	
		Assert.assertTrue(test.contains(board.calcIndex(16,21)));
		Assert.assertTrue(test.contains(board.calcIndex(15,20)));
	}
	@Test
	public void isDoorway1(){//Pink (16,12)
		Boolean test = board.getRoomCellAt(17,1).isDoorway();
		Assert.assertTrue(test);	
	}
	@Test
	public void isDoorway2(){//Pink (16,7)
		Boolean test = board.getRoomCellAt(15,6).isDoorway();
		Assert.assertTrue(test);	
	}
	@Test
	public void ajacencyDoorway1(){//Purple (7,12)
		int index = board.calcIndex(6,11);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(4,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(6,10)));	
		Assert.assertTrue(test.contains(board.calcIndex(6,12)));
		Assert.assertTrue(test.contains(board.calcIndex(5,11)));
		Assert.assertTrue(test.contains(board.calcIndex(7,11)));
	}
	@Test
	public void ajacencyDoorway2(){//Purple (15,12)
		int index = board.calcIndex(7,16);
		LinkedList<Integer> test=board.getAdjList(index);
		assertEquals(3,test.size());
		Assert.assertTrue(test.contains(board.calcIndex(7,15)));	
		Assert.assertTrue(test.contains(board.calcIndex(8,16)));
		Assert.assertTrue(test.contains(board.calcIndex(7,17)));
	}
	@Test
	public void alongWalkwayTarget1(){
		int index = board.calcIndex(12,0);
		board.calcTargets(index,1);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 1))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 0))));
	}
	@Test
	public void alongWalkwayTarget2(){
		int index = board.calcIndex(5,3);
		board.calcTargets(index,3);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 3))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 3))));
	}
	@Test
	public void alongWalkwayTarget3(){
		int index = board.calcIndex(10,14);
		board.calcTargets(index,4);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(12, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 13))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(14, 14))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(7, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 15))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(8, 16))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(12, 16))));
	}
	@Test
	public void alongWalkwayTarget4(){
		int index = board.calcIndex(4,7);
		board.calcTargets(index,2);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 7))));
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(6, 7))));
	}
	@Test
	public void leavingRoom1(){
		int index = board.calcIndex(1,2);
		board.calcTargets(index,4);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(2, 3))));
	}
	@Test
	public void leavingRoom2(){
		int index = board.calcIndex(20,6);
		board.calcTargets(index,6);
		Set<BoardCell> targets = board.getTargets();
		Assert.assertEquals(1, targets.size());
		Assert.assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 7))));
	}
	
}