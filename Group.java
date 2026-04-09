package PamakBook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Group implements Serializable{
	
	private static final long serialVersionUID = -3629051814124279721L;
	
		//fields
		private String name;
		private String description;
		private ArrayList<User> members;
		
		private static ArrayList<String> groups = new ArrayList<>();
		private static ArrayList<Group> g = new ArrayList<>();
		
		//Constructor
		public Group(String name, String description)
		{
			this.name = name;
			this.description = description;
			members = new ArrayList<>();
			groups.add(this.name);
			g.add(this);

		}


		//Getters
		public String getName()
		{
			return name;
		}

		public String getDescription()
		{
			return description;
		}

		public ArrayList<User> getMembers() {
			return members;
		}
		public static ArrayList<String> getGroups()
		{
			return groups;
		}
		public static ArrayList<Group> getG()
		{
			return g;
		}
		
		@Override
		public String toString()
		{
			return name+ ", "+ description;
		}


		//check if a user is a member of a group
		public boolean isMember(User user)
		{
			boolean flag = false;	
			for(User m : members) 
				{
					if(m.equals(user))
						{
							flag = true;
						}
				}
			
			return flag;
			
		}

		//add to group arraylist
		public void addMember(User user)
		{
			members.add(user);
			user.setGroups(this);
			//System.out.println(user.getName()+" has been successfully enrolled in group " + name);
		}

		//print members
		public void printMembers()
		{
			System.out.println("**************************");
			System.out.println("Members of group " + this.name );
			System.out.println("**************************");
			
			Collections.sort(members, User.UNameComp); //sort by name
			
			int i = 1; 
			
			for(User m : members)
			{
				System.out.println(i + ": " + m.getName());
				i++;
			}
			System.out.println("----------------------------");
		}
}

//Artemis Dara-2025
