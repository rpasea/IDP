package AuctionHouse.Messages;

public class MakeOfferMessage extends OfferMessage {
	
	public MakeOfferMessage(String service, String person, String offer) {
		super(service, person, offer);
	}

	@Override
	public MessageType getType() {
		return MessageType.MakeOffer;
	}
}
