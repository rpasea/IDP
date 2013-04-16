package AuctionHouse.NetworkMessages;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import AuctionHouse.Messages.Message;
import AuctionHouse.Messages.OfferExceedMessage;
import AuctionHouse.Network.SerializableString;

public class OfferExceedNetworkMessage extends NetworkMessage {
	private SerializableString service;
	/*
	 * Do we need this?
	 */
	private SerializableString offer;
	
	private ByteArrayOutputStream outputStream;

	public OfferExceedNetworkMessage() {
		this.service = new SerializableString();
		this.offer = new SerializableString();
		this.type = NetworkMessage.OFFER_EXCEEDED;
		this.source = "";
		this.dest = "";
		this.socketChannel = null;
		this.outputStream = new ByteArrayOutputStream();
	}

	public OfferExceedNetworkMessage(String service, String offer) {
		this.service = new SerializableString(service);
		this.offer = new SerializableString(offer);
		this.type = NetworkMessage.OFFER_EXCEEDED;
		this.source = "";
		this.dest = "";
		this.socketChannel = null;
		this.outputStream = new ByteArrayOutputStream();
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
	public void deserialize() {
		ByteBuffer bbuf = ByteBuffer.wrap(outputStream.toByteArray());
		service.deserialize(bbuf);
		offer.deserialize(bbuf);
	}

	@Override
	public Message toMessage() {
		return new OfferExceedMessage(service.getString(), source, offer.getString());
	}

	@Override
	public void put(byte b) {
		outputStream.write(b);
	}
}
