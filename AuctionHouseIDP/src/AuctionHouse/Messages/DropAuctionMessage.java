package AuctionHouse.Messages;

public class DropAuctionMessage extends AuctionMessage {
	
	public DropAuctionMessage(String service) {
		super(service);
	}

	@Override
	public MessageType getType() {
		return MessageType.DropAuction;
	}
}
