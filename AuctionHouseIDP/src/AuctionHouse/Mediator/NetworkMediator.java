package AuctionHouse.Mediator;

import AuctionHouse.Messages.Message;
import AuctionHouse.NetworkMessages.NetworkMessage;

public interface NetworkMediator {
	public Object sendNetworkMessage(Message message);

}
