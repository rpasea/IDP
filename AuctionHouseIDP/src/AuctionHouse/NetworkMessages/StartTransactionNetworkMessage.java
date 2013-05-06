package AuctionHouse.NetworkMessages;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import AuctionHouse.Messages.Message;
import AuctionHouse.Messages.StartTransactionMessage;
import AuctionHouse.Network.SerializableString;

public class StartTransactionNetworkMessage extends NetworkMessage {

	private SerializableString service, seller, buyer, offer;
	private int fileSize;
	private ByteArrayOutputStream outputStream;
	
	public StartTransactionNetworkMessage(String service,
			String seller, String buyer,
			String offer, int fileSize) {
		
		this.service = new SerializableString(service);
		this.seller = new SerializableString(seller);
		this.buyer = new SerializableString(buyer);
		this.offer = new SerializableString(offer);
		this.fileSize = fileSize;
		dest = "";
		source = "";
		type = START_TRANSACTION;
		this.socketChannel = null;
		this.outputStream = new ByteArrayOutputStream();
	}
	
	public StartTransactionNetworkMessage() {
		this.service = new SerializableString();
		this.seller = new SerializableString();
		this.buyer = new SerializableString();
		this.offer = new SerializableString();
		dest = "";
		source = "";
		type = START_TRANSACTION;
		this.socketChannel = null;
		this.outputStream = new ByteArrayOutputStream();
	}

	@Override
	public byte[] serialize() {
		int size = 4 + 4; // the type int + the fileSize int
		byte[] service = this.service.serialize();
		byte[] seller = this.seller.serialize();
		byte[] buyer = this.buyer.serialize();
		byte[] offer = this.offer.serialize();
		
		size += service.length + seller.length + buyer.length + offer.length;

		ByteBuffer bbuf = ByteBuffer.allocate( 4 /* sizeof(int) */ + size);
		bbuf.putInt(size);
		bbuf.putInt(type);
		bbuf.putInt(fileSize);
		bbuf.put(service);
		bbuf.put(seller);
		bbuf.put(buyer);
		bbuf.put(offer);
		
		return bbuf.array();
	}

	@Override
	public void deserialize() {
		ByteBuffer bbuf = ByteBuffer.wrap(outputStream.toByteArray());
		fileSize = bbuf.getInt();
		service.deserialize(bbuf);
		seller.deserialize(bbuf);
		buyer.deserialize(bbuf);
		offer.deserialize(bbuf);

	}

	@Override
	public Message toMessage() {
		if ( seller.getString().equals(source) )
			return new StartTransactionMessage(service.getString(),seller.getString(),buyer.getString(),offer.getString(), fileSize, this.socketChannel);
		else
			return null;
	}
	
	public void put(byte b) {
		outputStream.write(b);
	}

}
