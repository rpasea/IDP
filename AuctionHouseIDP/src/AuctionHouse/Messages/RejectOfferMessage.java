package AuctionHouse.Messages;

public class RejectOfferMessage extends OfferMessage {
	
	public RejectOfferMessage(String service, String supplier, String offer) {
		super(service,supplier, offer);
	}

	@Override
	public MessageType getType() {
		return MessageType.RejectOffer;
	}

}
