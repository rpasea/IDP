package AuctionHouse.Messages;

public class DropOfferMessage implements Message{

	private String service;
	
	public DropOfferMessage(String service) {
		this.service = service;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	@Override
	public MessageType getType() {
		return MessageType.DropOffer;
	}

}