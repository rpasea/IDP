package AuctionHouse.Messages;

public class DropAuctionMessage extends AcceptOfferMessage {
	
	public DropAuctionMessage(String service, String person) {
		super(service,person);
	}

	@Override
	public MessageType getType() {
		return MessageType.DropAuction;
	}
}
