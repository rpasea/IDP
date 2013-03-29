package AuctionHouse.Messages;

public class StartTransactionMessage implements Message {

	private String service, seller, buyer, offer;
	
	@Override
	public MessageType getType() {
		return MessageType.StartTransaction;
	}

	public StartTransactionMessage(String service, String seller, String buyer,
			String offer) {
		this.service = service;
		this.seller = seller;
		this.buyer = buyer;
		this.offer = offer;
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

}
