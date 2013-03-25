package AuctionHouse.Messages;

public class AcceptOfferMessage implements Message {

	private String service, person;
	
	public AcceptOfferMessage() {
		service = null;
		person = null;
	}
	
	public AcceptOfferMessage(String service, String person) {
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

	@Override
	public MessageType getType() {
		return MessageType.AcceptOffer;
	}

}
