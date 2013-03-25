package AuctionHouse.Mediator;

import AuctionHouse.Messages.Message;

public interface GUIMediator {
	
	public Object sendGuiMessage (Message message);
	public int getRole();
	//public boolean isLoginValid(String user, String password, int category);
}
