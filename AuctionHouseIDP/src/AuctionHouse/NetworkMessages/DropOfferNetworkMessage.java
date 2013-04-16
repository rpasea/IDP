package AuctionHouse.NetworkMessages;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import AuctionHouse.Messages.DropOfferMessage;
import AuctionHouse.Messages.Message;
import AuctionHouse.Network.SerializableString;

public class DropOfferNetworkMessage extends NetworkMessage {
	private final SerializableString service;
	private final SerializableString buyer;
	private final SerializableString offer;
	private ByteArrayOutputStream outputStream;
	
	public DropOfferNetworkMessage() {
		this.service = new SerializableString();
		this.buyer = new SerializableString();
		this.offer = new SerializableString();
		this.source = null;
		this.socketChannel = null;
		this.type = NetworkMessage.DROP_OFFER;
		this.outputStream = new ByteArrayOutputStream();
	}

	public DropOfferNetworkMessage(String service, String buyer, String offer){
		this.service = new SerializableString(service);
		this.buyer = new SerializableString(buyer);
		this.offer = new SerializableString(offer);
		this.socketChannel = null;
		this.type = NetworkMessage.DROP_OFFER;
		this.outputStream = new ByteArrayOutputStream();
	}
	
	@Override
	public byte[] serialize() {
		int size = 4; // the type int
		byte[] service = this.service.serialize();
		byte[] buyer = this.buyer.serialize();
		byte[] offer = this.offer.serialize();
		
		size += service.length + buyer.length + offer.length;

		ByteBuffer bbuf = ByteBuffer.allocate( 4 /* sizeof(int) */ + size);
		bbuf.putInt(size);
		bbuf.putInt(type);
		bbuf.put(service);
		bbuf.put(buyer);
		bbuf.put(offer);
		
		return bbuf.array();
	}

	@Override
	public void deserialize() {
		ByteBuffer bbuf = ByteBuffer.wrap(outputStream.toByteArray());
		service.deserialize(bbuf);
		buyer.deserialize(bbuf);
		offer.deserialize(bbuf);
	}

	@Override
	public Message toMessage() {
		return new DropOfferMessage(service.getString(), source, offer.getString());
	}

	@Override
	public void put(byte b) {
		outputStream.write(b);	
	}
}
