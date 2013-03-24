package AuctionHouse.Mediator;

import Messages.Message;

public interface GUIMediator {
	
	public Object sendGuiMessage (Message message);
	//public boolean isLoginValid(String user, String password, int category);
}
