package AuctionHouse.Messages;

public class OfferExceedMessage extends OfferMessage{

	public OfferExceedMessage(String service, String person, String offer) {
		super(service, person, offer);
	}

	@Override
	public MessageType getType() {
		return MessageType.OfferExceed;
	}
}
