package AuctionHouse.NetworkMessages;

public class MakeOfferNetworkMessage implements NetworkMessage {
	private final String service;
	private final String buyer;
	private final String offer;

	public MakeOfferNetworkMessage(String service, String buyer, String offer){
		this.service = service;
		this.buyer = buyer;
		this.offer = offer;
		
	}
	
	@Override
	public byte[] getSerialized() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deserialize(byte[] msg) {
		// TODO Auto-generated method stub

	}

}
