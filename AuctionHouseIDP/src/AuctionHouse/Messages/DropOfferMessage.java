package AuctionHouse.Messages;

public class DropOfferMessage extends OfferMessage{

	public DropOfferMessage(String service, String person) {
		super(service, person);
	}

	@Override
	public MessageType getType() {
		return MessageType.DropOffer;
	}
}
