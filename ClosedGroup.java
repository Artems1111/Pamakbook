package PamakBook;

import java.io.Serializable;

public class ClosedGroup extends Group implements Serializable{

	private static final long serialVersionUID = -3629051814124279721L;
	
	//Constructor
	public ClosedGroup(String name, String description)
	{
		super(name, description);
	}

	//add users to closed group. Override addMember of Group class
	public void addMember(User user)
	{
		boolean flag = false;
		
		if (this.getMembers().isEmpty())
		{
		    flag = true;
		}
		else
		{
		   for(User m : this.getMembers())
		    {
				if(user.isFriend(m)) //check if user is a friend of a member in the group
    			{
    					flag = true;
    				break;
    			}
		    } 
		}
		
		
		if(flag)
		{
			super.addMember(user); //apply the addMember from the Group class
		}
	/*	else
		{
			System.out.println("FAILED: "+ user.getName() +" cannot be enrolled in group " + this.getName());
		}
	*/
	}

}

//Artemis Dara-2025
