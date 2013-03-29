package AuctionHouse.Messages;

public abstract class AuctionMessage implements Message {

	private String service;
	
	public AuctionMessage(String service) {
		this.service = service;
	}
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
}
