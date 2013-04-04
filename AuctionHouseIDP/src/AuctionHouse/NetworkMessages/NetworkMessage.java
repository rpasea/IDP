package AuctionHouse.NetworkMessages;

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
	byte[] getSerialized();
	
	/**
	 * Inits current object with the deserialized msg
	 */
	void deserialize(byte[] msg);
}
