package AuctionHouse.Mediator;


import java.util.List;
import java.util.Observable;
import java.util.concurrent.Semaphore;

import javax.swing.SwingWorker;

public class Transaction extends Observable {

	public static int MaxProgress = 2;
	private String service, seller, buyer, offer;
	private int progress;
	private String state;
	public Transaction(String service, String seller, String buyer, String offer) {
		super();
		this.service = service;
		this.seller = seller;
		this.buyer = buyer;
		this.offer = offer;
		progress = 0;
		state = "Transfer Started";
	}
	
	public void setToStarted() {
		progress = 0;
		state = "Transfer Started";
		setChanged();
		notifyObservers();
	}
	
	public void setToInProgress() {
		progress = 1;
		state = "Transfer in Progress";
		setChanged();
		notifyObservers();
	}
	
	public void setToCompleted() {
		progress = 2;
		state = "Transfer Complete";
		setChanged();
		notifyObservers();
	}
	
	public void setToFailed() {
		progress = -1;
		state = "Transfer Failed";
		setChanged();
		notifyObservers();
	}
	
	public int getProgress() {
		return progress;
	}
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getOffer() {
		return offer;
	}
	public void setOffer(String offer) {
		this.offer = offer;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	
	

}
