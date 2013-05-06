package AuctionHouse.Messages;

public class DropOfferMessage extends OfferMessage{

	public DropOfferMessage(String service, String person, String offer) {
		super(service, person, offer);
	}

	@Override
	public MessageType getType() {
		return MessageType.DropOffer;
	}
}
