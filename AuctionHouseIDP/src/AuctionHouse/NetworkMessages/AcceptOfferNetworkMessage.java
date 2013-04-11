package AuctionHouse.NetworkMessages;

import java.nio.ByteBuffer;

import AuctionHouse.Messages.Message;
import AuctionHouse.Messages.OfferAcceptedMessage;
import AuctionHouse.Network.SerializableString;

public class AcceptOfferNetworkMessage extends NetworkMessage {
	private SerializableString service;
	/*
	 * Do we need this?
	 */
	private SerializableString offer;

	public AcceptOfferNetworkMessage() {
		this.service = new SerializableString();
		this.offer = new SerializableString();
		this.type = NetworkMessage.ACCEPT_OFFER;
		this.source = "";
		this.dest = "";
	}

	public AcceptOfferNetworkMessage(String service, String offer) {
		this.service = new SerializableString(service);
		this.offer = new SerializableString(offer);
		this.type = NetworkMessage.ACCEPT_OFFER;
		this.source = "";
		this.dest = "";
	}

	@Override
	public byte[] serialize() {
		int size = 4; // the type int
		byte[] service = this.service.serialize();
		byte[] offer = this.offer.serialize();
		
		size += service.length + offer.length;

		ByteBuffer bbuf = ByteBuffer.allocate( 4 /* sizeof(int) */ + size);
		bbuf.putInt(size);
		bbuf.putInt(type);
		bbuf.put(service);
		bbuf.put(offer);
		
		return bbuf.array();
	}

	@Override
	public void deserialize(ByteBuffer msg) {
		service.deserialize(msg);
		offer.deserialize(msg);
	}

	@Override
	public Message toMessage() {
		return new OfferAcceptedMessage(service.getString(),source,offer.getString());
	}

}
