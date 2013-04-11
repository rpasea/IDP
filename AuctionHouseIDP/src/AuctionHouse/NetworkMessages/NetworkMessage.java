package AuctionHouse.NetworkMessages;

import java.nio.ByteBuffer;

import AuctionHouse.Messages.Message;

/**
 * Takes care of network messages serialization
 */

public abstract class NetworkMessage {
	public static final int MAKE_OFFER = 0;
	public static final int REJECT_OFFER = 1;
	public static final int ACCEPT_OFFER = 2;
	public static final int START_TRANSACTION = 3;
	
	/*
	 * This field is generated on send, don't serialize it
	 */
	protected String dest;
	/*
	 * This field is generated on receive, don't serialize it
	 */
	protected String source;
	

	protected int type;
	
	/**
	 * TODO: getDestination()
	 */
	public String getDestinationPerson() {
		return dest;
	}
	public void setDestinationPerson(String dest) {
		this.dest = dest;
	}
	
	/**
	 * Returns a serialized message
	 */
	public abstract byte[] serialize();
	
	/**
	 * Inits current object with the deserialized msg. msg should NOT contain the size and type int-s.
	 */
	public abstract void deserialize(ByteBuffer msg);
	
	/*
	 * Transforms the network message into an internal message
	 */
	public abstract Message toMessage();
	
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getSource() {
		return source;
	}
}
