package AuctionHouse.Messages;

public class MakeOfferMessage extends OfferMessage {
	
	public MakeOfferMessage(String service, String person) {
		super(service, person);
	}

	@Override
	public MessageType getType() {
		return MessageType.MakeOffer;
	}
}
