package AuctionHouse.Messages;

public class LaunchOfferMessage implements Message {

	private String service;
	
	public LaunchOfferMessage(String service) {
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
		return MessageType.LaunchOffer;
	}

}
