package PamakBook;

import java.io.*;

public class Main {

	public static void main(String[] args) {
		
		//Creating users
				User u1 = new User("Makis", "iis2598@uom.edu.gr");
				User u2 = new User("Petros", "ics2524@uom.edu.gr");
				User u3 = new User("Maria", "iis2512@uom.edu.gr");
				User u4 = new User("Gianna", "iis25133@uom.edu.gr");
				User u5 = new User("Nikos", "dai1758@uom.edu.gr");
				User u6 = new User("Babis", "ics25104@uom.edu.gr");
				//User u7 = new User("Stella", "dai1827@uom.edu.gr");
				//User u8 = new User("Eleni", "ics2586@gmail.com");
				
				//users make friends
				u1.addFriend(u2);
				u1.addFriend(u5);
				u5.addFriend(u6);
				u3.addFriend(u4);
				u3.addFriend(u2);
				u4.addFriend(u6);
				u5.addFriend(u3);
				u1.addFriend(u6);
				u5.addFriend(u2);
				//u7.addFriend(u1);
				
		
		//Create Groups
		Group g1 = new Group("WebGurus","A group for web passionates");
		ClosedGroup g2 = new ClosedGroup("ExamSolutions","Solutions to common exam questions");
		
		try {
			FileInputStream fileIn = new FileInputStream("PamakBook.ser"); 
			ObjectInputStream In = new ObjectInputStream(fileIn); 
			
			
				 try {
					
					 while(true)
					 {
						 User u = (User) In.readObject();
						 if(u==null)
						 {
							 break;
						 }
						 User.getUsers().add(u);
						 
					 }
					 
					
					
				 } catch (ClassNotFoundException e) {
					e.printStackTrace();
				 } 
				 catch(EOFException e)
				 {
					 
				 }

			
				 In.close();
				 fileIn.close();
			
			
			
			//Display the login window
			//new LoginWindow(User.getUsers());
			new FriendGraph(User.getUsers());
			
			
		} catch (IOException e) {
			 new File("PamakBook.ser");
			 new LoginWindow(User.getUsers());
		}
		
		
	}
}