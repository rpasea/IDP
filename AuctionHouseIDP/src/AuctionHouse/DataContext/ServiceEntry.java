package AuctionHouse.DataContext;

public class ServiceEntry {
	String person, status;
	String offer;
	
	public ServiceEntry(String person, String status, String offer) {
		this.person = person;
		this.status = status;
		this.offer = offer;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOffer() {
		return offer;
	}

	public void setOffer(String offer) {
		this.offer = offer;
	}
}
