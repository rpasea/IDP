package AuctionHouse.NetworkMessages;

/**
 * Takes care of network messages serialization
 */

public interface NetworkMessage {
	
	/**
	 * TODO: getDestination()
	 */
	
	/**
	 * Returns a serialized message
	 */
	byte[] getSerialized();
	
	/**
	 * Inits current object with the deserialized msg
	 */
	void deserialize(byte[] msg);
}
