package AuctionHouse.NetworkMessages;

import java.nio.ByteBuffer;

import AuctionHouse.Messages.Message;

/**
 * Takes care of network messages serialization
 */

public interface NetworkMessage {
	public static final int MAKE_OFFER = 0;
	
	/**
	 * TODO: getDestination()
	 */
	String getDestinationPerson();
	
	/**
	 * Returns a serialized message
	 */
	byte[] serialize();
	
	/**
	 * Inits current object with the deserialized msg. msg should NOT contain the size and type int-s.
	 */
	void deserialize(ByteBuffer msg);
	
	/*
	 * Transforms the network message into an internal message
	 */
	Message toMessage();
}
