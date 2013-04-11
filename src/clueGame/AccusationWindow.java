package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AccusationWindow extends JFrame{

	public JButton submit = new JButton("Submit");
	JButton cancel = new JButton("Cancel");;
	JLabel yourRoom = new JLabel("Your Room:");
	JLabel person = new JLabel("Person:");
	JLabel weapon = new JLabel("Weapon:");
	JLabel room = new JLabel(" ");
	public JComboBox roomCombo;
	public JComboBox peopleCombo;
	public JComboBox weaponsCombo;
	Boolean accuse = false;
	
	char roomInitial;
	public AccusationWindow(char r)
	{	
		cancel.addActionListener(new ButtonListener());
		
		if ( r == 'Z' ){			
			needARoom();
		}else{
			roomInitial = r;
			setRoom();
		}
		createAccusation();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Make an Accusation!");
		setSize(300,200);
	}
	
	private void needARoom(){
		String[] rooms = {"Unknown","Conservatory","Kitchen","Ballroom","Billiard Room","Library","Study", "Dining Room", "Lounge", "Hall", "Closet"};
		roomCombo = new JComboBox(rooms);
		accuse = true;
	}
	
	private void setRoom(){
		switch(roomInitial)
		{
		case 'C':
			room.setText("Conservatory");
			break;
		case 'K':
			room.setText("Kitchen");
			break;
		case 'B':
			room.setText("Ballroom");
			break;
		case 'R':
			room.setText("Billiard Room");
			break;
		case 'L':
			room.setText("Library");
			break;
		case 'S':
			room.setText("Study");
			break;
		case 'D':
			room.setText("Dining Room");
			break;
		case 'O':
			room.setText("Lounge");
			break;
		case 'H':
			room.setText("Hall");
			break;
		case 'X':
			room.setText("Closet");
			break;
		}
	}
	
	private void createAccusation()
	{
		


		




		JPanel container = new JPanel();
		container.setLayout(new GridLayout(4,2));

		String[] people = {"Unknown","Miss Scarlet","Colonel Mustard","Mr. Green","Mrs. White","Mrs. Peacock","Professor Plum"};
		peopleCombo = new JComboBox(people);

		String[] weapons = {"Unknown","Candlestick","Knife","Lead Pipe","Revolver","Rope","Wrench"};
		weaponsCombo = new JComboBox(weapons);

		container.add(yourRoom);
		
		if (accuse){
			container.add(roomCombo);
		}else {
			container.add(room);
		}
		
		
		container.add(person);
		container.add(peopleCombo);
		container.add(weapon);
		container.add(weaponsCombo);
		container.add(submit);
		container.add(cancel);
		add(container);
		
		//String n =(String) roomCombo.getSelectedItem();
		
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//if(e.getSource() == cancel){
				dispose();
			//}
			
		}
		
	}
	
}
