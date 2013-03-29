package AuctionHouse.Messages;

public class AcceptOfferMessage extends OfferMessage {

	public AcceptOfferMessage(String service, String person) {
		super(service, person);
	}

	@Override
	public MessageType getType() {
		return MessageType.AcceptOffer;
	}
}
