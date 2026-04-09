package PamakBook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class PossibleInfectionsPanel extends JFrame {

	private JButton Back_to_login;
	private JTextArea PossibleInfections;
	private JPanel panel;
	
	public PossibleInfectionsPanel(User infected)
	{
		Back_to_login = new JButton("Back to Login Screen");
		PossibleInfections = new JTextArea(20, 40);
		panel = new JPanel();
		
		panel.add(PossibleInfections);
		panel.add(Back_to_login);
		
		PossibleInfections.setEditable(false);
		PossibleInfections.setLineWrap(true);
		
		//creating and adjusting the frame
		this.setContentPane(panel);
		
		this.setVisible(true);
		this.setSize(500, 400);
		this.setLocation(850, 400); //located at the center of the screen
		this.setTitle("Possible Infection");
		
		//adding a button listener in the buttons
		ButtonListener listener = new ButtonListener();
		Back_to_login.addActionListener(listener);
		
		//directs the output of the System.out.println() of the resent post method in the resent post text area
		RedirectSystemStream();
		
		infected.possibleInfection();
		
		
	}
	
	//set the redirection of the ouput stream
	class TextAreaOutputStream extends OutputStream 
	{
		private JTextArea textArea;

		public TextAreaOutputStream(JTextArea textArea) 
		{
			this.textArea = textArea;
		}

		@Override
	   public void write(int b) throws IOException {
	        SwingUtilities.invokeLater(() -> {
	            textArea.append(String.valueOf((char) b));
	           textArea.setCaretPosition(textArea.getDocument().getLength());
	        });
	    }
	}
	
	private void RedirectSystemStream()
	{
		PrintStream printStream = new PrintStream(new TextAreaOutputStream(PossibleInfections));
		System.setOut(printStream);
	}

	public class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource().equals(Back_to_login))
			{
				new LoginWindow(User.getUsers());
			}
			
		}
		
	}
}

//Artemis Dara-2025
