package AuctionHouse.Messages;

public class LaunchAuctionMessage extends AuctionMessage {
	
	public LaunchAuctionMessage(String service) {
		super(service);
	}
	
	@Override
	public MessageType getType() {
		return MessageType.LaunchAuction;
	}
}
