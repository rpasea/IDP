package AuctionHouse.Messages;

public abstract class OfferMessage implements Message {

	private String service, person;
	
	public OfferMessage() {
		service = null;
		person = null;
	}
	
	public OfferMessage(String service, String person) {
		this.service = service;
		this.person = person;
	}
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}
}
