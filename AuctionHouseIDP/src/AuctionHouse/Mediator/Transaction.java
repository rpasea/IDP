package AuctionHouse.Mediator;


import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Observable;

public class Transaction extends Observable {

	public static int MaxProgress = 1000;
	private String service, seller, buyer, offer;
	private int progress;
	private int soFar;
	private String state;
	private int fileLength;
	private SocketChannel socketChannel;
	
	public Transaction(String service, String seller, String buyer, String offer, int length) {
		super();
		this.service = service;
		this.seller = seller;
		this.buyer = buyer;
		this.offer = offer;
		progress = 0;
		state = "Transfer Started";
		soFar = 0;
		this.fileLength = length;
		socketChannel = null;
	}
	
	public SocketChannel getSocketChannel() {
		return socketChannel;
	}

	public void setSocketChannel(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}

	public void addProgress (int nr) {
		if (nr <= 0)
			return;
		soFar += nr;
		if (soFar < fileLength) {
			progress =  soFar * MaxProgress / fileLength;
			state = "Transfer in Progress";
		} else {
			progress = MaxProgress;
			state = "Transfer Complete";
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void setProgress (int bytesWritten) {
		soFar = bytesWritten;
		if (bytesWritten < fileLength) {
			progress =  soFar * MaxProgress / fileLength;
			state = "Transfer in Progress";
		} else {
			progress = MaxProgress;
			state = "Transfer Complete";
		}
		
		setChanged();
		notifyObservers();
	}
	
	public void setToStarted() {
		progress = 0;
		state = "Transfer Started";
		setChanged();
		notifyObservers();
	}
	
	public void setToCompleted() {
		progress = MaxProgress;
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

	public void finishTransaction(String userName, boolean delete) throws IOException {
		socketChannel.close();
		setToFailed();
		if (delete) {
			File f = new File (userName + "/" + service);
			if (f.exists()) {
				f.delete();
			}
		}
		
	}
}
