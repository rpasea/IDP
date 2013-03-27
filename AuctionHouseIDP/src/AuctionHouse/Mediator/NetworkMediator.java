package AuctionHouse.Mediator;

import AuctionHouse.Messages.Message;

public interface NetworkMediator {
	public Object sendNetworkMessage(Message message);
}
