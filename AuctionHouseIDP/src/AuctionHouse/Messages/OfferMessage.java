package AuctionHouse.Messages;

public abstract class OfferMessage implements Message {

	private String service, person, offer;
	
	public OfferMessage() {
		service = null;
		person = null;
	}
	
	public OfferMessage(String service, String person, String offer) {
		this.service = service;
		this.person = person;
		this.offer = offer;
	}
	
	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
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
