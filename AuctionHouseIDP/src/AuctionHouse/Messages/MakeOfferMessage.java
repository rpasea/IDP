package AuctionHouse.Messages;

public class MakeOfferMessage extends AcceptOfferMessage {
	
	public MakeOfferMessage(String service, String person) {
		super(service,person);
	}

	@Override
	public MessageType getType() {
		return MessageType.MakeOffer;
	}
}
