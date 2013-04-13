package AuctionHouse.Messages;

import java.nio.channels.SocketChannel;

public class StartTransactionMessage implements Message {

	private String service, seller, buyer, offer;
	private SocketChannel socketChannel;
	private int fileSize;
	
	@Override
	public MessageType getType() {
		return MessageType.StartTransaction;
	}

	public StartTransactionMessage(String service, String seller, String buyer,
			String offer, int fileSize, SocketChannel socketChannel) {
		this.service = service;
		this.seller = seller;
		this.buyer = buyer;
		this.offer = offer;
		this.fileSize = fileSize;
		this.socketChannel = socketChannel;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}

	public SocketChannel getSocketChannel() {
		return socketChannel;
	}

	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}

}
