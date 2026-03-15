package PamakBook;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;


@SuppressWarnings("serial")
public class UserPage extends JFrame {
	//PANELS
	private JPanel panelCenter, panelNorth, panelSouth;	
	private JPanel postPanel, recentPanel, groupPanel;
	
	//TEXT FIELDS
	private JTextField nameField, emailField, addFriendField;
	
	//BUTTONS
	private JButton Back_to_login, post, addFriendButton,  addMemberButton;
	
	//TEXT AREAS
	private JTextArea postArea, ResentPost;
	
	//LISTS
	private JList<String> suggestedFriends;
	DefaultListModel<String> friendModel;
	private JList<String> groups;
	DefaultListModel<String> groupModel;
	
	//LABELS
	private JLabel resent_post_txt, suggested_fr_txt;
	
	//SCROLL PANE
	private JScrollPane groupScrollPane;	
	
	//CONSTRUCTOR
	public UserPage(User user, ArrayList<Group> gr)
	{
		//setting border layout on the frame
		Container contentPane = this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		//PANEL CREATION
		panelCenter = new JPanel();
		panelNorth = new JPanel();
		panelSouth = new JPanel();
		
		postPanel = new JPanel();
		recentPanel = new JPanel();
		groupPanel = new JPanel();
		
		//TEXTFIELD CREATION
		nameField = new JTextField(13);
		emailField = new JTextField(13);
		addFriendField = new JTextField(13);
		
		//BUTTON CREATION
		Back_to_login = new JButton("Back to Login Screen");
		post = new JButton("Post");
		addFriendButton = new JButton("Add Friend");
		addMemberButton = new JButton("Add Member");
		
		//TEXTAREA CREATION
		postArea = new JTextArea(20,25);
		ResentPost = new JTextArea(20, 25); 
		
		//LIST CREATION
		suggestedFriends = new JList<>();
		friendModel = new DefaultListModel<>(); 
		groups = new JList<>();
		groupModel = new DefaultListModel<String>();
		
		
		//LABEL CREATION
		resent_post_txt = new JLabel("Resent Posts By Friends");
		suggested_fr_txt = new JLabel("Suggested Friends");
		
		//SCROLL PANE CREATION
		groupScrollPane = new JScrollPane();
		
		panelCenter.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		//setting the elements of friend list
		suggestedFriends.setModel(friendModel);
		for(String s: user.suggestedFriends())
		{
			friendModel.addElement(s);
		}
		
		//setting the elements of group list
		groups.setModel(groupModel);
		for(String g: Group.getGroups())
		{
			groupModel.addElement(g);
		}
		groupScrollPane.setViewportView(groups);
		
		nameField.setText(user.getName());
		emailField.setText(user.getEmail());
		
		//Add components on panels
		panelNorth.add(nameField);
		panelNorth.add(emailField);
		panelNorth.add(Back_to_login);
		
		postPanel.add(postArea);
		postPanel.add(post);
		recentPanel.add(resent_post_txt);
		recentPanel.add(ResentPost);
		groupPanel.add(groupScrollPane);
		groupPanel.add(addMemberButton);
		
		panelCenter.add(postPanel);
		panelCenter.add(recentPanel);
		panelCenter.add(groupPanel);
		
		panelSouth.add(addFriendField);
		panelSouth.add(addFriendButton);
		panelSouth.add(suggested_fr_txt);
		panelSouth.add(suggestedFriends);
				
		//Add panels on border layout
		contentPane.add(panelNorth, BorderLayout.NORTH);
		contentPane.add(panelCenter, BorderLayout.CENTER);
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		
		//creating and adjusting the frame
		this.setContentPane(contentPane);
		
		this.setVisible(true);
		this.setSize(500, 1000);
		this.setLocation(850, 400); //located at the center of the screen
		this.setResizable(true);
		this.setTitle("User Page");
		
		//for textArea resent post
		ResentPost.setEditable(false);
		ResentPost.setLineWrap(true);
		
		//for post textArea
		postArea.setLineWrap(true);
		
		//adding a button listener in the buttons
		ButtonListener listener = new ButtonListener(user, gr);
		Back_to_login.addActionListener(listener);
		post.addActionListener(listener);
		
		addFriendButton.addActionListener(listener);
		addMemberButton.addActionListener(listener);
		
		//directs the output of the System.out.println() of the resent post method in the resent post text area
		RedirectSystemStream();
		
		user.resentPost();
		
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
			PrintStream printStream = new PrintStream(new TextAreaOutputStream(ResentPost));
			System.setOut(printStream);
		}
	
	public class ButtonListener implements ActionListener{

		private User user;
		private ArrayList<Group> gr;
		
		public ButtonListener(User user, ArrayList<Group> groups)
		{
			super();
			this.user = user;
			gr = groups;
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			boolean flag = false;
			String username = addFriendField.getText();
			User friend = null;
			String selectedGroupName = groups.getSelectedValue();
			
			if(e.getSource().equals(Back_to_login))
			{
				new LoginWindow(User.getUsers());
			}
			else if(e.getSource().equals(post))
			{
				ResentPost.setText(null);
				new Post(postArea.getText(), user);
				RedirectSystemStream();
				user.resentPost();
				postArea.setText(null);
			}
			else if (e.getSource().equals(addFriendButton))
			{
				for(User u: User.getUsers())
				{
					if(username.equals(u.getName()))
					{
						flag = true;
						friend = u;
						break;
					}
				}
				
				if(flag)
				{
					if(user.isFriend(friend))
					{
						JOptionPane.showMessageDialog(panelCenter, "User " + friend.getName() + " is already a friend");
					}
					else
					{
						user.addFriend(friend);
						new FriendGraph(User.getUsers());
					}
					
				}
				else
				{
					JOptionPane.showMessageDialog(panelCenter, "User " + username + " does not exist");
				}
				
				addFriendField.setText(null);
				
			}
			else
			{
				flag = false;
				
				for(Group g: gr)
				{
					if(g.getName().equals(selectedGroupName))
					{
						if(g.isMember(user))
						{
							JOptionPane.showMessageDialog(panelCenter, "User " + user.getName() + " is already a member");
							break;
						}
						g.addMember(user);
						if(g.isMember(user))
						{
							JOptionPane.showMessageDialog(panelCenter, user.getName()+" has been successfully enrolled in group " + g.getName());
						}
						else
						{
							JOptionPane.showMessageDialog(panelCenter, "FAILED: "+ user.getName() +" cannot be enrolled in group " + g.getName());
						}
						
						break;
						
					}
						
				}
				
			}
		}
		
	}
	
}