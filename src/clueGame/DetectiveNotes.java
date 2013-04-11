package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.Border;
///////////////////
import javax.swing.JMenuItem;	//test delete later
import javax.swing.JPopupMenu;  //test delete later
import javax.swing.JTextArea;   //test delete later
//////////////////

public class DetectiveNotes extends JFrame{
	
	
	///////////////////////////////////////////////
	private JMenuItem detective;
	////////////////////////////////////////////////////
	
	
	public DetectiveNotes()
	{	
		createDetective();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Detective Notes");
		setSize(550,400);
	}

	private void createDetective()
	{
        
        
		Border borderAppearance = BorderFactory.createBevelBorder(1);
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(3,2));	
		
		JPanel topLeft = new JPanel();
		topLeft.setLayout(new GridLayout(3,2));
		topLeft.setBorder(BorderFactory.createTitledBorder(borderAppearance,"People"));
		JCheckBox missScarlet = new JCheckBox("Miss Scarlet");
		JCheckBox colonelMustard = new JCheckBox("Colonel Mustard");
		JCheckBox mrGreen = new JCheckBox("Mr. Green");
		JCheckBox mrsWhite = new JCheckBox("Mrs. White");
		JCheckBox mrsPeacock = new JCheckBox("Mrs. Peacock");
		JCheckBox professorPlum = new JCheckBox("Professor Plum");
		topLeft.add(missScarlet);
		topLeft.add(colonelMustard);
		topLeft.add(mrGreen);
		topLeft.add(mrsWhite);
		topLeft.add(mrsPeacock);
		topLeft.add(professorPlum);
		
		
		JPanel topRight = new JPanel();
		topRight.setLayout(new GridLayout(3,2));
		topRight.setBorder(BorderFactory.createTitledBorder(borderAppearance,"Person Guess"));
		String[] people = {"Unknown","Miss Scarlet","Colonel Mustard","Mr. Green","Mrs. White","Mrs. Peacock","Professor Plum"};
		JComboBox peopleCombo = new JComboBox(people);
		topRight.add(peopleCombo,BorderLayout.CENTER);
		
		JPanel midLeft = new JPanel();
		midLeft.setLayout(new GridLayout(5,2));
		midLeft.setBorder(BorderFactory.createTitledBorder(borderAppearance,"Rooms"));
		JCheckBox kitchen = new JCheckBox("Kitchen");
		JCheckBox diningRoom = new JCheckBox("Dining Room");
		JCheckBox lounge = new JCheckBox("Lounge");
		JCheckBox ballroom = new JCheckBox("Ballroom");
		JCheckBox conservatory = new JCheckBox("Conservatory");
		JCheckBox hall = new JCheckBox("Hall");
		JCheckBox study = new JCheckBox("Study");
		JCheckBox library = new JCheckBox("Library");
		JCheckBox billiardRoom = new JCheckBox("Billiard Room");
		midLeft.add(kitchen);
		midLeft.add(diningRoom);
		midLeft.add(lounge);
		midLeft.add(ballroom);
		midLeft.add(conservatory);
		midLeft.add(hall);
		midLeft.add(study);
		midLeft.add(library);
		midLeft.add(billiardRoom);
		
		JPanel midRight = new JPanel();
		midRight.setLayout(new GridLayout(3,2));
		midRight.setBorder(BorderFactory.createTitledBorder(borderAppearance,"Room Guess"));
		String[] rooms = {"Unknown","Kitchen","Dining Room","Lounge","Ballroom","Conservatory","Hall","Study","Library","Billiard Room"};
		JComboBox roomsCombo = new JComboBox(rooms);
		midRight.add(roomsCombo,BorderLayout.CENTER);
		
		JPanel botLeft = new JPanel();
		botLeft.setLayout(new GridLayout(3,2));
		botLeft.setBorder(BorderFactory.createTitledBorder(borderAppearance,"Weapons"));
		JCheckBox candlestick = new JCheckBox("Candlestick");
		JCheckBox knife = new JCheckBox("Knife");
		JCheckBox leadPipe = new JCheckBox("Lead Pipe");
		JCheckBox revolver = new JCheckBox("Revolver");
		JCheckBox rope = new JCheckBox("Rope");
		JCheckBox wrench = new JCheckBox("Wrench");
		botLeft.add(candlestick);		
		botLeft.add(knife);
		botLeft.add(leadPipe);
		botLeft.add(revolver);
		botLeft.add(rope);
		botLeft.add(wrench);	
		
		JPanel botRight = new JPanel();
		botRight.setLayout(new GridLayout(3,2));
		botRight.setBorder(BorderFactory.createTitledBorder(borderAppearance,"Weapon Guess"));
		String[] weapons = {"Unknown","Candlestick","Knife","Lead Pipe","Revolver","Rope","Wrench"};
		JComboBox weaponsCombo = new JComboBox(weapons);
		botRight.add(weaponsCombo,BorderLayout.CENTER);
		
		
		container.add(topLeft);
		container.add(topRight);
		container.add(midLeft);
		container.add(midRight);
		container.add(botLeft);
		container.add(botRight);
		
		add(container);
		
	}
	
//	public static void main(String[] args) {
//		DetectiveNotes gui = new DetectiveNotes();
//		gui.setVisible(true);
//	}

}
