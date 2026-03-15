package PamakBook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class User implements Serializable{
		
	private static ArrayList<User> users = new ArrayList<>();
	private static final long serialVersionUID =-3629051814124279721L;
	
		//πεδία
		private String name;
		private String email;
		private ArrayList<User> friends;
		private ArrayList<Group> groups;
		private ArrayList<Post> posts;

		//Constructor
		public User(String name, String email)
		{
			this.name = name;
			this.email = email;
			friends = new ArrayList<>();
			groups = new ArrayList<>();
			posts = new ArrayList<>();
			users.add(this);	
			
		}

		//Getters
		public String getName()
		{
			return name;
		}

		public String getEmail()
		{
			return email;
		}
		public ArrayList<User> getFriends()
		{
			return friends;
		}
		
		public ArrayList<Post> getPost()
		{
			return posts;
		}
		public static ArrayList<User> getUsers()
		{
			return users;
		}
		
		
		//setters
		public void setGroups(Group group)
		{
			groups.add(group);
		}
		public void setPost(Post post)
		{
			this.posts.add(post);
		}
		/*public static void setUsers(User user)
		{
			users.add(user);
		}*/
		
		 @Override
		public String toString()
		{
			return (name+ " ," + email);
		}
		

		
		//Comparator to sort users by name. 
		public static Comparator<User> UNameComp = new Comparator<User>() 
		{
			public int compare(User a, User b)
			{
				String UserNamea = a.name;
				String UserNameb = b.name;
				
				return UserNamea.compareTo(UserNameb); 
			}
		};
		
		//Comparator to sort posts by timestamp. 
		public static Comparator<Post> timeStamp = new Comparator<Post>() 
		{
			public int compare(Post a, Post b)
			{
				String Ta = a.getTimestamp();
				String Tb = b.getTimestamp();
				
				return Ta.compareTo(Tb); 
			}
		};
		

		//Check if someone is a friend of a user. 
		public boolean isFriend(User user)
		{
			boolean flag = false;
			if(this.equals(user)) 
			{
				flag = false;
			}
			
			else
			{
				for( User f : friends)
				{
					if(f.equals(user))
						{
							flag =  true;
						}
				}
			}
			
			return flag;
			
		}


		//Method to add friends in the respective arraylist
		public void addFriend(User user)
		{
			friends.add(user);
			user.friends.add(this); //the user is added to the friend list of his/her friend
			//System.out.println(this.name+ " and "+ user.name + " are now friends!");
		}


		//Find common friends
		public void commonFriends(User user)
		{
			System.out.println("**************************");
			System.out.println("The common friends of " + this.name + " and " + user.name);
			System.out.println("**************************");
			
			Collections.sort(friends, User.UNameComp);
			int i = 1; //index for the printing part
			for(User f: friends)
			{
				if(f.isFriend(user))
				{
					System.out.println(i + ": " +f.name);
					i++;
				}
			}
			System.out.println("----------------------------");
		}


		//Print Friends
		public void printFriends()
		{
			System.out.println("**************************");
			System.out.println("Friends of " + this.name);
			System.out.println("**************************");
			
			Collections.sort(friends, User.UNameComp); //sort by name
			
			int i = 1; 
			
			for(User f: friends)
			{
				System.out.println(i + ": " +f.name);
				i++;
			}
			System.out.println("----------------------------");
		}

		public void printGroups()
		{
			System.out.println("**************************");
			System.out.println("Groups that " + this.name + " has been enrolled in");
			System.out.println("**************************");
			
			int i = 1; 
			
			for(Group g: groups)
			{
				System.out.println(i + ": " + g.getName());
				i++;
			}
			System.out.println("----------------------------");
		}



		//Method to find possible infections
		public void possibleInfection()
		{
			ArrayList<User> infected = new ArrayList<>(); //list to store all the user that might be infected
			for(User f: friends)
			{
				//adds user's friends
				if(!(infected.contains(f) || f.equals(this))) //if the element is the sick person or is already on the list, it doesn't add it
				{
					infected.add(f);
				} 
				//adds friends of user's friends
				for(User fr: f.friends) 
				{
					if(!(infected.contains(fr) || fr.equals(this))) 
					{
						infected.add(fr);
					}
				}
			}
			//print results
			System.out.println("*********************************************************************");
			System.out.println(this.name + " has been infected. The following users have to be tested");
			System.out.println("*********************************************************************");
			
			
			Collections.sort(infected, User.UNameComp); //sort infected by name
			for(User inf: infected) //print list of possible infections
			{
				System.out.println(inf.name);
			}
			System.out.println("----------------------------");
		}
		
		//Version 2 new methods!
		
		//method to find the resent post of a user and his/her friends
		public void resentPost()
		{
			ArrayList<Post> sortedPosts = new ArrayList<>();
			
			//adds user posts
			for(Post p: posts)
			{
				sortedPosts.add(p);
			}
			
			//adds posts of user's friends
			for(User f : friends)
			{
				for(Post p: f.posts)
				{
					sortedPosts.add(p);
				}
			}
			
			Collections.sort(sortedPosts, timeStamp);
			
			for(Post p: sortedPosts)
			{
				System.out.println(p.getUserName() + ", " + p.getTimestamp() + ", "+ p.getText());;
			}
		}

		//Method that suggests friends. (akoloutha trigoniki kleistotita)
		public ArrayList<String> suggestedFriends() {
			ArrayList<String> suggestedFriends = new ArrayList<>();
			
			for(int i=0; i<this.friends.size()-1; i++) //select a user's friend. 
			{
				User b = friends.get(i);
				
				for(User f1: b.friends) //check the friend list of one of user's friend
				{
					//making sure that the list doesn't include user's friends or the user himself/herself or the list of suggested friends contains the same name twice
					if(!(f1.equals(this) || suggestedFriends.contains(f1.name) || friends.contains(f1)))
					{
						suggestedFriends.add(f1.name);
					}
				}
				
				Collections.sort(suggestedFriends); //no comparator because suggestedFriends has string values	
			}
			
			return suggestedFriends;
		}
		
		
}
		