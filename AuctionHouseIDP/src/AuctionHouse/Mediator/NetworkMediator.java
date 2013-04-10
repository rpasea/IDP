package AuctionHouse.Mediator;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import AuctionHouse.Messages.Message;
import AuctionHouse.NetworkMessages.NetworkMessage;

public interface NetworkMediator {
	public Object sendNetworkMessage(Message message);
	public String getPerson(SocketAddress addr);
	public InetSocketAddress getPersonsAddress(String person);
}
