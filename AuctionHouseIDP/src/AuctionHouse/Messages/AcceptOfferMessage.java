package AuctionHouse.Messages;

public class AcceptOfferMessage extends OfferMessage {

	public AcceptOfferMessage(String service, String person, String offer) {
		super(service, person, offer);
	}

	@Override
	public MessageType getType() {
		return MessageType.AcceptOffer;
	}
}
