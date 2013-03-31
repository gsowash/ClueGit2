import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class IntBoard {
	HashSet targetList = new HashSet();
	TreeSet<Integer> targets;
	private static int numSpaces;
	Boolean[] visited;
	private static int numColumns;
	private static int numRows;
	private Map<Integer, LinkedList<Integer>> adjMtx;

	public IntBoard() {
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new TreeSet<Integer>();
		numRows = 4;
		numColumns = 4;
		numSpaces = 16;
		visited = new Boolean[numSpaces];
		calcAdjacencies();
	}

	public void calcAdjacencies() {
		for (int x = 0; x < numRows; x++) {
			for (int y = 0; y < numColumns; y++) {
				int index = calcIndex(x, y);
				LinkedList<Integer> list = new LinkedList<Integer>();
				if (x != numRows - 1) {// right border
					int newAdj = calcIndex(x + 1, y);
					list.add(newAdj);
				}
				if (x != 0) {// left border
					int newAdj = calcIndex(x - 1, y);
					list.add(newAdj);
				}
				if (y != 0) {// top border
					int newAdj = calcIndex(x, y - 1);
					list.add(newAdj);
				}
				if (y != numColumns - 1) {// bottom border
					int newAdj = calcIndex(x, y + 1);
					list.add(newAdj);
				}
				adjMtx.put(index, list);
			}
		}
	}

	public void startTargets(int start, int numSteps) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		LinkedList<Integer> list = new LinkedList<Integer>();
		for (int i = 0; i < numSpaces; i++) { //verifys all paths have not been visited. 
			visited[i] = false;
		}
		visited[start] = true; //obviously you visit the starting location
		list = getAdjList(start);
		StartTargetRec(path,list, numSteps);
	}

	public void StartTargetRec(LinkedList<Integer> path,LinkedList<Integer> adjacentCells, int numSteps) {
		for (int adjCell : adjacentCells) { //tests for each value in the list
			if (visited[adjCell] == false) {
				visited[adjCell] = true;
				path.addLast(adjCell); //adding a value to path( 1 value is 1 square traveled
				if (path.size() == numSteps) {
					targets.add(adjCell); //if you have traveled the correct length add to targets
				} else {
					LinkedList<Integer> newAdjCells = new LinkedList<Integer>();
					newAdjCells = getAdjList(adjCell);
					StartTargetRec(path,newAdjCells, numSteps); // call the function with the new 'path' taken
				}
				path.removeLast(); //if you have already traveled here the path needs to be removed(found the path)
				visited[adjCell] = false; //reset path that did not get takken
			}
		}	
	}

	public TreeSet getTargets() {
		return targets;
	}

	public LinkedList<Integer> getAdjList(int cell) {
		LinkedList list = new LinkedList();
		list = adjMtx.get(cell);
		return list;
	}

	public int calcIndex(int rowNumber, int columnNumber) {
		int index;
		index = numColumns * rowNumber + columnNumber;
		return index;
	}
}
