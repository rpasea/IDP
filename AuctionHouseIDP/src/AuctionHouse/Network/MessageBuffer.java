package AuctionHouse.Network;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import AuctionHouse.NetworkMessages.NetworkMessage;
import AuctionHouse.NetworkMessages.NetworkMessageFactory;

public class MessageBuffer {
	private byte[] intBuffer;
	private LinkedList<NetworkMessage> messages;
	private Integer currentMsgSize;
	private Integer currentMsgType;
	private ByteBuffer byteBuffer;
	private String source;
	private int intBufferPos;

	public MessageBuffer() {
		intBuffer = new byte[4];
		currentMsgSize = null;
		currentMsgType = null;
		messages = new LinkedList<NetworkMessage>();
		intBufferPos = 0;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public synchronized void putBytes(byte[] bytes, int size) {
		for (int i = 0 ; i < size; i++) {
			byte b = bytes[i];
			if (currentMsgSize == null) {
				intBuffer[intBufferPos] = b;
				intBufferPos++;
				if (intBufferPos == 4) {
					currentMsgSize = ByteBuffer.wrap(intBuffer).getInt();
					/*
					 * allocate the size minus the type int
					 */
					byteBuffer = ByteBuffer.allocate(currentMsgSize - 4);
					intBufferPos = 0;
				}
			} else if (currentMsgType == null) {
				intBuffer[intBufferPos] = b;
				intBufferPos++;
				if (intBufferPos == 4) {
					currentMsgType = ByteBuffer.wrap(intBuffer).getInt();
					intBufferPos = 0;
				}
			} else {
				byteBuffer.put(b);
				if (byteBuffer.position() == (currentMsgSize - 4)) {
					NetworkMessage msg = NetworkMessageFactory
							.createMessage(currentMsgType);
					
					/* dubiosenia dubioseniilor */
					byteBuffer.flip();
					
					msg.deserialize(byteBuffer);
					msg.setSource(source);
					
					byteBuffer.flip();
					
					messages.add(msg);
					currentMsgSize = null;
					currentMsgType = null;
				}
			}
		}
	}
	
	public synchronized List<NetworkMessage> getAndClearMessages() {
		List<NetworkMessage> tmp = messages;
		messages = new LinkedList<NetworkMessage>();
		return tmp;
	}
	
	public boolean hasMessages() {
		return messages.size() > 0;
	}
}
