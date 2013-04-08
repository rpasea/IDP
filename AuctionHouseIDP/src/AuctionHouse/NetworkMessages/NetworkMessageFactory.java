package AuctionHouse.NetworkMessages;

public class NetworkMessageFactory {
	public static NetworkMessage createMessage(int type) {
		switch (type) {
		case NetworkMessage.MAKE_OFFER:
			return new MakeOfferNetworkMessage();
		default:
			return null;
		}
	}
}
