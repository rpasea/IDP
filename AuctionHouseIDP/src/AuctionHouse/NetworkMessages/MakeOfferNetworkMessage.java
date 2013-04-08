package AuctionHouse.NetworkMessages;

import java.nio.ByteBuffer;
import java.util.LinkedList;

import AuctionHouse.Messages.MakeOfferMessage;
import AuctionHouse.Messages.Message;
import AuctionHouse.Network.SerializableString;

public class MakeOfferNetworkMessage implements NetworkMessage {
	private final SerializableString service;
	private final SerializableString buyer;
	private final SerializableString offer;
	private final int type;
	
	public MakeOfferNetworkMessage() {
		this.service = new SerializableString();
		this.buyer = new SerializableString();
		this.offer = new SerializableString();
		this.type = NetworkMessage.MAKE_OFFER;
	}

	public MakeOfferNetworkMessage(String service, String buyer, String offer){
		this.service = new SerializableString(service);
		this.buyer = new SerializableString(buyer);
		this.offer = new SerializableString(offer);
		this.type = NetworkMessage.MAKE_OFFER;
		
	}

	@Override
	public String getDestinationPerson() {
		return buyer.getString();
	}
	
	@Override
	public byte[] serialize() {
		LinkedList<byte[]> list = new LinkedList<byte[]>();
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
	public void deserialize(ByteBuffer msg) {
		
		service.deserialize(msg);
		buyer.deserialize(msg);
		offer.deserialize(msg);
	}

	@Override
	public Message toMessage() {
		return new MakeOfferMessage(service.getString(),buyer.getString(),offer.getString());
	}
}
