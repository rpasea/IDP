package AuctionHouse.DataContext;

public class ServiceEntry {
	public enum State {
		NO_OFFER,
		OFFER_MADE,
		OFFER_ACCEPTED,
		OFFER_REJECTED,
		OFFER_EXCEED
	}
	
	private State state;
	
	private ServicesObserver observer;
	
	private Service service;
	private String person;
	private String status;
	private String offer;
	
	private Object transaction;
	
	public ServiceEntry(String person, String status, String offer) {
		this.person = person;
		this.status = status;
		this.offer = offer;
		this.transaction = null;
		state = State.NO_OFFER;
	}

	public Object getTransaction() {
		return transaction;
	}

	public void setTransaction(Object transaction) {
		this.transaction = transaction;
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

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		
		// Notify the observer
		if(observer != null)
			observer.notify(this);
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public void registerObserver(ServicesObserver observer) {
		this.observer = observer;
	}
}
