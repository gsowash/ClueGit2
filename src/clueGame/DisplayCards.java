package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class DisplayCards extends JPanel
{
	private JLabel myCards;
	private JTextField peopleField;
	private JTextField roomsField;
	private JTextField weaponsField;
	private String person = "", weapon = "", place = "";

	DisplayCards (ArrayList<Card> cardList)
	{
		Border borderAppearance = BorderFactory.createBevelBorder(1);
		myCards = new JLabel("My Cards:");
		peopleField = new JTextField (15);
		roomsField = new JTextField (15);
		weaponsField = new JTextField (15);
		
		JPanel container = new JPanel();
		container.setLayout(new GridLayout(6,1));
		
		container.add(myCards);
		
		for(Card c: cardList){
			switch (c.getCardType()){
			case PERSON:
				if (person.equals("")){
					person = c.getCardName();
				}else{
				person = person + ", " +c.getCardName();
				}
				break;
			case WEAPON:
				if (weapon.equals("")){
					weapon = c.getCardName();
				}else{
				weapon = weapon + ", " + c.getCardName();
				}
				break;
			case ROOM:
				if (place.equals("")){
					place = c.getCardName();
				}else{
				place = place  + ", " + c.getCardName();
				}
				break;			
			}
		}
		
		JPanel people = new JPanel();
		people.setBorder(BorderFactory.createTitledBorder(borderAppearance,"People"));
		peopleField.setText(person);
		peopleField.setEditable(false);
		people.add(peopleField);
		container.add(people);
		
		JPanel rooms = new JPanel();
		rooms.setBorder(BorderFactory.createTitledBorder(borderAppearance,"Rooms"));
		roomsField.setText(place);
		roomsField.setEditable(false);
		rooms.add(roomsField);
		container.add(rooms);
		
		JPanel weapons = new JPanel();
		weapons.setBorder(BorderFactory.createTitledBorder(borderAppearance,"Weapons"));
		weaponsField.setText(weapon);
		weaponsField.setEditable(false);
		weapons.add(weaponsField);
		container.add(weapons);
		
		add(container);
	}
	
	
}
