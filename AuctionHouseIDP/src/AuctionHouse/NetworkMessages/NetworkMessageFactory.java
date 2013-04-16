package AuctionHouse.NetworkMessages;

public class NetworkMessageFactory {
	public static NetworkMessage createMessage(int type) {
		switch (type) {
		case NetworkMessage.MAKE_OFFER:
			return new MakeOfferNetworkMessage();
		case NetworkMessage.DROP_OFFER:
			return new DropOfferNetworkMessage();
		case NetworkMessage.REJECT_OFFER:
			return new RejectOfferNetworkMessage();
		case NetworkMessage.ACCEPT_OFFER:
			return new AcceptOfferNetworkMessage();
		case NetworkMessage.START_TRANSACTION:
			return new StartTransactionNetworkMessage();
		case NetworkMessage.FILE_TRANSFER:
			return new FileNetworkMessage();
		case NetworkMessage.OFFER_EXCEEDED:
			return new OfferExceedNetworkMessage();
		default:
			return null;
		}
	}
}
