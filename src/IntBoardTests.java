import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.Set;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;


public class IntBoardTests {
	private IntBoard board;
	private int test;
	@Before
	public void setup(){
		board = new IntBoard();
		board.calcAdjacencies();
	}

	@Test
	public void calcIndexTest1() {
		test = board.calcIndex(1,1);
		assertEquals(5, test, .01);
		test = board.calcIndex(0,0);
		assertEquals(0, test, .01);
		test = board.calcIndex(3,03);
		assertEquals(15, test, .01);
	}

	@Test
	public void Adjacency0(){//top left corner test
		LinkedList<Integer> testList = board.getAdjList(0);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void Adjacency15(){//bottom right corner test
		LinkedList<Integer> testList = board.getAdjList(15);
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(14));
		Assert.assertEquals(2, testList.size());
	}
	@Test
	public void Adjacency8(){//left edge test
		LinkedList<Integer> testList = board.getAdjList(8);
		Assert.assertTrue(testList.contains(12));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(4));
		Assert.assertEquals(3, testList.size());
	}
	@Test
	public void Adjacency7(){//right edge test
		LinkedList<Integer> testList = board.getAdjList(7);
		Assert.assertTrue(testList.contains(11));
		Assert.assertTrue(testList.contains(3));
		Assert.assertTrue(testList.contains(6));
		Assert.assertEquals(3, testList.size());
	}
	@Test
	public void Adjacency5(){//middle left test
		LinkedList<Integer> testList = board.getAdjList(5);
		Assert.assertTrue(testList.contains(4));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(1));
		Assert.assertEquals(4, testList.size());
	}
	@Test
	public void Adjacency10(){//middle right test
		LinkedList<Integer> testList = board.getAdjList(10);
		Assert.assertTrue(testList.contains(14));
		Assert.assertTrue(testList.contains(9));
		Assert.assertTrue(testList.contains(6));
		Assert.assertTrue(testList.contains(11));
		Assert.assertEquals(4, testList.size());
	}
	
	@Test
	public void testTargets4_1(){
		board.startTargets(4, 1);
		Set targets= board.getTargets();
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(0));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(8));
	}
	@Test
	public void testTargets10_2(){
		board.startTargets(10, 2);
		Set targets= board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(7));
		Assert.assertTrue(targets.contains(8));
		Assert.assertTrue(targets.contains(13));
		Assert.assertTrue(targets.contains(15));
	}
	@Test
	public void testTargets7_2(){
		board.startTargets(7, 2);
		Set targets= board.getTargets();
		Assert.assertEquals(4, targets.size());
		Assert.assertTrue(targets.contains(2));
		Assert.assertTrue(targets.contains(5));
		Assert.assertTrue(targets.contains(10));
		Assert.assertTrue(targets.contains(15));
	}
	
	@Test
	public void testTargets2_3(){
		board.startTargets(2,3);
		Set targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(14));
	}
	@Test
	public void testTargets9_4(){
		board.startTargets(9, 4);
		Set targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(12));
		Assert.assertTrue(targets.contains(14));
	}
	@Test
	public void testTargets12_6(){
		board.startTargets(12, 6);
		Set targets= board.getTargets();
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(1));
		Assert.assertTrue(targets.contains(3));
		Assert.assertTrue(targets.contains(4));
		Assert.assertTrue(targets.contains(6));
		Assert.assertTrue(targets.contains(9));
		Assert.assertTrue(targets.contains(11));
		Assert.assertTrue(targets.contains(14));
	}
	
}
