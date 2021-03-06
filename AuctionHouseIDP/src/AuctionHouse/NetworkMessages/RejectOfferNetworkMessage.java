package AuctionHouse.NetworkMessages;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import AuctionHouse.Messages.Message;
import AuctionHouse.Messages.OfferRefusedMessage;
import AuctionHouse.Network.SerializableString;

public class RejectOfferNetworkMessage extends NetworkMessage {
	private final SerializableString service;
	private final SerializableString seller;
	private final SerializableString offer;
	private ByteArrayOutputStream outputStream;
	
	public RejectOfferNetworkMessage() {
		this.service = new SerializableString();
		this.seller = new SerializableString();
		this.offer = new SerializableString();
		this.source = null;
		this.type = NetworkMessage.REJECT_OFFER;
		this.socketChannel = null;
		this.outputStream = new ByteArrayOutputStream();
	}

	public RejectOfferNetworkMessage(String service, String seller, String offer){
		this.service = new SerializableString(service);
		this.seller = new SerializableString(seller);
		this.offer = new SerializableString(offer);
		this.type = NetworkMessage.REJECT_OFFER;
		this.socketChannel = null;
		this.outputStream = new ByteArrayOutputStream();
	}
	
	@Override
	public byte[] serialize() {
		int size = 4; // the type int
		byte[] service = this.service.serialize();
		byte[] seller = this.seller.serialize();
		byte[] offer = this.offer.serialize();
		
		size += service.length + seller.length + offer.length;

		ByteBuffer bbuf = ByteBuffer.allocate( 4 /* sizeof(int) */ + size);
		bbuf.putInt(size);
		bbuf.putInt(type);
		bbuf.put(service);
		bbuf.put(seller);
		bbuf.put(offer);
		
		return bbuf.array();
	}

	@Override
	public Message toMessage() {
		return new OfferRefusedMessage(service.getString(), source, offer.getString());
	}

	@Override
	public void deserialize() {
		ByteBuffer bbuf = ByteBuffer.wrap(outputStream.toByteArray());
		service.deserialize(bbuf);
		seller.deserialize(bbuf);
		offer.deserialize(bbuf);
	}

	@Override
	public void put(byte b) {
		outputStream.write(b);
	}
}
