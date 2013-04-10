package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


public class ControlPanel extends JPanel
{
	private JTextField whoseTurn;
	private JTextField roll;
	private JTextField guess;
	private JTextField guessResult;
	public JButton nextPlayer;
	public JButton accusation;
	private JLabel whoseTurnLabel;
	private JLabel rollLabel;
	private JLabel guessLabel;
	private JLabel guessResultLabel;
	
	
	public ControlPanel()
	{	
		//Borders
		Border spacerBorder = BorderFactory.createEmptyBorder(5,5,5,5);
		Border borderAppearance = BorderFactory.createBevelBorder(1);
	//	Border bottonBorder = BorderFactory.createCompoundBorder(spacerBorder,borderAppearance);
		
		JPanel container = new JPanel();						//outer most container allows top/bottom row to have equal vertical space
		container.setLayout(new GridLayout(2,1));				//Grid layout enforces equal vertical size of both rows
		
		JPanel topRow = new JPanel();							//JPanel for top row
		topRow.setLayout(new GridLayout(1,3));					//Grid layout enforces equal horizontal size for 3 cells in top row
		topRow.setBorder(spacerBorder);									
		JPanel whoseBox = new JPanel();							//JPanel for first cell of top row
		topRow.add(whoseBox);									//nested inside topRow JPanel
		
		JPanel bottomRow = new JPanel();						//JPanel for bottom row (non-equal horizontal spacing so stays in flow layout)		
		bottomRow.setBorder(spacerBorder);
		JPanel first = new JPanel();							//JPanel for first cell of bottom row
		bottomRow.add(first);									//nested inside bottomRow JPanel
		JPanel second = new JPanel();							//JPanel for second cell of bottom row
		bottomRow.add(second);									//nested inside bottomRow JPanel
		JPanel third = new JPanel();							//JPanel for third cell of bottom row
		bottomRow.add(third);									//nested inside bottomRow JPanel
		
		add(container,BorderLayout.CENTER);						//Container added to JFrame
		container.add(topRow,BorderLayout.NORTH);				//topRow added to Container
		container.add(bottomRow, BorderLayout.SOUTH);			//bottomRow added to Container
		
		//First cell of top row
		whoseTurnLabel = new JLabel("Whose Turn?");
		whoseTurn = new JTextField (18);
		whoseTurn.setEditable(false);
		whoseBox.add(whoseTurnLabel,BorderLayout.NORTH);
		whoseBox.add(whoseTurn,BorderLayout.CENTER);
		whoseBox.setBorder(spacerBorder);
		
		//Two buttons in top row
		nextPlayer = new JButton ("Next Player");
		topRow.add(nextPlayer);
		nextPlayer.addActionListener(new ButtonListener());
		//nextPlayer.setBorder(bottonBorder);
		accusation = new JButton ("Make an accusation");
		topRow.add(accusation);
		//accusation.setBorder(bottonBorder);
		
		//First cell of bottom row
		rollLabel = new JLabel ("Roll:");
		roll = new JTextField (5);
		roll.setEditable(false);
		first.add(rollLabel);
		first.add(roll);
		Border titlefirst = BorderFactory.createTitledBorder(borderAppearance,"Die");
		first.setBorder(titlefirst);
		
		//Second cell of bottom row
		guessLabel = new JLabel ("Guess:");
		guess = new JTextField (35);
		guess.setEditable(false);
		second.add(guessLabel);
		second.add(guess);
		Border titlesecond = BorderFactory.createTitledBorder(borderAppearance,"Guess");
		second.setBorder(titlesecond);
		
		//Third cell of bottom row
		guessResultLabel = new JLabel ("Response:");
		guessResult = new JTextField (20);
		guessResult.setEditable(false);
		third.add(guessResultLabel);
		third.add(guessResult);
		Border titlethird = BorderFactory.createTitledBorder(borderAppearance,"Guess Result");
		third.setBorder(titlethird);
		
		
	}
	
	public void Dice(int n)
	{
		roll.setText(Integer.toString(n));
	}
	
	public void whoseTurn(String n){
		whoseTurn.setText(n);
	}
	
	public void showSuggestion(String person, String place, String weapon){
		if (person == null){
			guess.setText(" ");
		}else {
			guess.setText(person + " in the " + place + " with the " + weapon);
		}
	}
	
	public void showResult(String n){
		if (n == null){
			guessResult.setText(" ");
		}else {
			guessResult.setText(n);
		}
	}
	
	class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == nextPlayer){
				
			}
			
		}
		
	}

}