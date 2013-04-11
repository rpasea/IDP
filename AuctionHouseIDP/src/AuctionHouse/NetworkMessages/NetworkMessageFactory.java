package AuctionHouse.NetworkMessages;

public class NetworkMessageFactory {
	public static NetworkMessage createMessage(int type) {
		switch (type) {
		case NetworkMessage.MAKE_OFFER:
			return new MakeOfferNetworkMessage();
		case NetworkMessage.REJECT_OFFER:
			return new RejectOfferNetworkMessage();
		case NetworkMessage.ACCEPT_OFFER:
			return new AcceptOfferNetworkMessage();
		case NetworkMessage.START_TRANSACTION:
			return new StartTransactionNetworkMessage();
		default:
			return null;
		}
	}
}
