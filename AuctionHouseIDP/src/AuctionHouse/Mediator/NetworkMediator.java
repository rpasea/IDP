package AuctionHouse.Mediator;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import AuctionHouse.Messages.Message;

public interface NetworkMediator {
	public Object sendNetworkMessage(Message message);
	public String getPerson(SocketAddress addr);
	public InetSocketAddress getPersonsAddress(String person);
	public void CheckTransactionChannelClosed(SocketChannel chan);
}
