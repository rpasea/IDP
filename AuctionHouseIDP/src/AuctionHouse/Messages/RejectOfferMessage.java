package AuctionHouse.Messages;

public class RejectOfferMessage extends AcceptOfferMessage {
	
	public RejectOfferMessage(String service, String supplier) {
		super(service,supplier);
	}

	@Override
	public MessageType getType() {
		return MessageType.RejectOffer;
	}

}
