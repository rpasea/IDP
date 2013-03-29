package AuctionHouse.Messages;

public class OfferRefusedMessage extends OfferMessage{

	public OfferRefusedMessage(String service, String person, String offer) {
		super(service, person, offer);
	}

	@Override
	public MessageType getType() {
		return MessageType.OfferRefused;
	}
}
