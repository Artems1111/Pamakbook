package PamakBook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame {
	//creating fields for GUI components
	//text fields
	private JTextField NameField, EmailField;
	
	//buttons
	private JButton NewUser, SavePamakBook, EnterButton, PossibleInfectionsBtn;
	
	//create panel
	private JPanel LoginPanel; 
	
	//field for user list inside the frame
	private ArrayList<User> users;
	
	public LoginWindow(ArrayList<User> users)
	{
		//construct the GUI components 
		NameField = new JTextField(10);
		EmailField = new JTextField(10);
		NewUser = new JButton("New User");
		 EnterButton = new JButton("Enter User Page");
		 PossibleInfectionsBtn = new JButton("Show Potential Infections");
		 SavePamakBook = new JButton("Save PamakBook");
		 
		//construct panel
		 LoginPanel = new JPanel();
		
		 //set the elements of the user list in the frame as the element of the list of users created in the User class.
		 this.users = users;
		 
		 //add components to the panel
		 LoginPanel.add(NewUser);
		 LoginPanel.add(NameField);
		 LoginPanel.add(EmailField);
		 LoginPanel.add(EnterButton);
		 LoginPanel.add(PossibleInfectionsBtn);
		 LoginPanel.add(SavePamakBook);
		 
		 //
		 NameField.setText("user name");
		 EmailField.setText("user email");
		 
		 //creating and adjusting the frame
		 this.setTitle("User Login");
		 
		 this.setContentPane(LoginPanel);
		 
		 this.setVisible(true);
		 this.setSize(450, 200);
		 this.setLocation(850, 400); //located at the center of the screen
		 
		 //adding a button listener in the buttons
		 ButtonListener listener = new ButtonListener();
		 NewUser.addActionListener(listener);
		 EnterButton.addActionListener(listener);
		 PossibleInfectionsBtn.addActionListener(listener);
		 SavePamakBook.addActionListener(listener);
	}
	
	
	public class ButtonListener implements ActionListener
	{	
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String username = NameField.getText(); //stores the input of the text of the name field
			String email = EmailField.getText();
			Boolean flag = false;
			User user = null;
			
			//checks from which button the action came
			if(e.getSource().equals(NewUser))
			{
				for(User aUser: users)
				{
					//check if the username exist as a name of a user
					if(aUser.getName().equals(username) && aUser.getEmail().equals(email))
					{
						flag = true;
						break;
					}
				}
				
				if(flag)
				{
					JOptionPane.showMessageDialog(LoginPanel, "User "+ username + " already exists");
				}
				else
				{
					/* iis, ics, dai: 3 characters
					 *@uom.edu.gr: 11 characters 
					 *when characters of id (AM) = 5, email length = 19
					 *when characters of id (AM) = 4, email length = 18
					 *when characters of id (AM) = 3, email length = 17
					*/
					
					if(email.length() == 19) //checking email length
					{
						if((email.substring(0,3).equals("dai") || email.substring(0,3).equals("ics") || email.substring(0,3).equals("iis") ) 
								&& (email.substring(8,19).equals("@uom.edu.gr"))) 
							{
								new User(username, email);
								JOptionPane.showMessageDialog(LoginPanel, "User "+ username + " is created!");
							}
							else
							{
								JOptionPane.showMessageDialog(LoginPanel, "User "+ username +" has not been created. Email format is not acceptable.");
							}
					}
					
					else if(email.length() == 18) 
					{
						if((email.substring(0,3).equals("dai") || email.substring(0,3).equals("ics") || email.substring(0,3).equals("iis") )
								&& (email.substring(7,18).equals("@uom.edu.gr")))
							{
								new User(username, email);
								JOptionPane.showMessageDialog(LoginPanel, "User "+ username + " is created!");
							}
							else
							{
								JOptionPane.showMessageDialog(LoginPanel, "User "+ username +" has not been created. Email format is not acceptable.");
							}
					}
					else if(email.length() == 17) 
					{
						if((email.substring(0,3).equals("dai") || email.substring(0,3).equals("ics") || email.substring(0,3).equals("iis") )
								&& (email.substring(6,17).equals("@uom.edu.gr")))
							{
								new User(username, email);
								JOptionPane.showMessageDialog(LoginPanel, "User "+ username + " is created!");
							}
							else
							{
								JOptionPane.showMessageDialog(LoginPanel, "User "+ username +" has not been created. Email format is not acceptable.");
							}
					}
					else  
					{
						JOptionPane.showMessageDialog(LoginPanel, "User "+ username +" has not been created. Email format is not acceptable.");
					}	
				}
				
				NameField.setText("user name");
				EmailField.setText("user email");
			}
			
			else if(e.getSource().equals(EnterButton))
			{
				for(User aUser: users)
				{
					//check if the username exist as a name of a user
					if(aUser.getName().equals(username))
					{
						flag = true;
						user = aUser;
						break;
					}
				}
				
				if(flag)
				{
					new UserPage(user, Group.getG());
				}
				else
				{
					JOptionPane.showMessageDialog(LoginPanel, "User "+ username + " Not Found");
				}
				
				NameField.setText(null);
			}
			else if (e.getSource().equals(PossibleInfectionsBtn))
			{
				for(User u: users)
				{
					if(username.equals(u.getName()))
					{
						flag = true;
						user = u;
						break;
					}
				}
				
				if(flag)
				{
					new PossibleInfectionsPanel(user);
				}
				else
				{
					JOptionPane.showMessageDialog(LoginPanel, "User "+ username + " Not Found");
				}
				
			}
			else
			{
				ObjectOutputStream out;
				try {
						FileOutputStream fileOut = new FileOutputStream("PamakBook.ser");
						out = new ObjectOutputStream(fileOut);
				
					
						for(User u: User.getUsers())
						{
							out.writeObject(u);
						}
							
						fileOut.close();
						out.close();
						JOptionPane.showMessageDialog(LoginPanel, "PamakBook Saved!");
				
					
					} 
				catch (IOException e1) 
				{
					JOptionPane.showMessageDialog(LoginPanel, "Unable to save PamakBook, try later.");
				}
			}	
		}
		
	}
	
}

//Artemis Dara-2025
