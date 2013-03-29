package AuctionHouse.Messages;

public class OfferAcceptedMessage extends OfferMessage{

	public OfferAcceptedMessage(String service, String person, String offer) {
		super(service, person, offer);
	}

	@Override
	public MessageType getType() {
		return MessageType.OfferAccepted;
	}
}
