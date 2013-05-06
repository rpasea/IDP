package AuctionHouse.Network;

import java.nio.channels.*;

public class ChangeRequest {
	public SelectionKey key;
	public SocketChannel socketChannel;
	public int newOps;
	
	public ChangeRequest(SelectionKey key, int newOps) {
		this.key = key;
		this.newOps = newOps;
		this.socketChannel = null;
	}
	
	public ChangeRequest(SocketChannel socketChannel, int newOps) {
		this.socketChannel = socketChannel;
		this.newOps = newOps;
	}
}
