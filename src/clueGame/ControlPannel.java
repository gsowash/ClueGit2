package clueGame;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class ControlPannel extends JPanel{
	private JPanel turnPanel, dicePanel, guessPanel, responsePanel, topPanel, botPanel;
	private JButton nextPlayer, accusation;
	private JLabel turn, dice, guess, response, diceValue, guessValue, responseValue;
	private JTextField turnValue;
	

	public ControlPannel(){
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 200);
		turn = new JLabel("Whose Turn?");
		dice = new JLabel("Roll");
		guess = new JLabel("Guess");
		response = new JLabel("Response");
		nextPlayer = new JButton("Next player");
		accusation = new JButton("Make an acusation");
		diceValue = new JLabel();
		guessValue = new JLabel();
		responseValue = new JLabel();
		turnValue = new JTextField(15);
		dicePanel = new JPanel();
		guessPanel = new JPanel();
		responsePanel = new JPanel();
		turnPanel = new JPanel();
		topPanel = new JPanel();
		botPanel = new JPanel();
		this.add(topPanel,BorderLayout.CENTER);
		this.add(botPanel,BorderLayout.SOUTH);
		topPanel.setLayout(new GridLayout(1,3));
		botPanel.setLayout(new GridLayout(1,3));
		turnPanel.add(turn);
		turnPanel.add(turnValue);
		turnValue.setBorder(new EtchedBorder());
		topPanel.add(turnPanel);
		topPanel.add(nextPlayer);
		topPanel.add(accusation);
		
		dicePanel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		responsePanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		dicePanel.add(dice);
		diceValue.setText("    ");
		dicePanel.add(diceValue);
		diceValue.setBorder(new EtchedBorder());
		botPanel.add(dicePanel);
	
		guessPanel.add(guess);
		guessValue.setText("                                     ");
		guessPanel.add(guessValue);
		guessValue.setBorder(new EtchedBorder());
		botPanel.add(guessPanel);
		
		responsePanel.add(response);
		responseValue.setText("                            ");
		responsePanel.add(responseValue);
		responseValue.setBorder(new EtchedBorder());
		botPanel.add(responsePanel);
	}
	public static void main(String[] args) {
		ControlPannel newPannel = new ControlPannel();
		newPannel.setVisible(true);
	}

}
