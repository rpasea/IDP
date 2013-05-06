package AuctionHouse.Network;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import AuctionHouse.Mediator.Transaction;
import AuctionHouse.NetworkMessages.FileNetworkMessage;
import AuctionHouse.NetworkMessages.NetworkMessage;
import AuctionHouse.NetworkMessages.NetworkMessageFactory;

public class MessageBuffer {
	private byte[] intBuffer;
	private LinkedList<NetworkMessage> messages;
	private Integer currentMsgSize;
	private Integer currentMsgType;
	private int messageBufferPos;
	private String source;
	private int intBufferPos;
	private Transaction transaction;

	public MessageBuffer() {
		intBuffer = new byte[4];
		currentMsgSize = null;
		currentMsgType = null;
		messages = new LinkedList<NetworkMessage>();
		intBufferPos = 0;
		messageBufferPos = 0;
		transaction = null;
	}
	
	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
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
					intBufferPos = 0;
				}
			} else if (currentMsgType == null) {
				intBuffer[intBufferPos] = b;
				intBufferPos++;
				if (intBufferPos == 4) {
					currentMsgType = ByteBuffer.wrap(intBuffer).getInt();
					messages.add(NetworkMessageFactory
							.createMessage(currentMsgType));
					intBufferPos = 0;
				}
			} else {
				messages.getLast().put(b);
				messageBufferPos++;
				if (messageBufferPos == (currentMsgSize - 4)) {
					messages.getLast().deserialize();
					messages.getLast().setSource(source);
					currentMsgSize = null;
					currentMsgType = null;
					messageBufferPos = 0;
				}
			}
		}
		
		/*
		 * Update the transaction progress
		 * 
		 * When a transaction is attached, the only message remaining in the buffer is 
		 * a file message, so it is ok to cast
		 */
		if (transaction != null) {
			if (messages.getLast() != null) {
				FileNetworkMessage fmsg = (FileNetworkMessage) messages.getLast();
				transaction.setProgress(fmsg.getBytesWritten());
			}
		}
	}
	
	public synchronized List<NetworkMessage> getAndClearMessages() {
		List<NetworkMessage> tmp;
		if ( messageBufferPos == 0 && intBufferPos == 0) {
			tmp = messages;
			messages = new LinkedList<NetworkMessage>();
		} else { // there is an incomplete message, we need to save it
			tmp =  new LinkedList<NetworkMessage>();
			NetworkMessage msg = messages.pollLast();
			tmp.addAll(messages);
			messages.clear();
			messages.add(msg);
		}
		return tmp;
	}
	
	public boolean hasMessages() {
		return messages.size() > 0;
	}
}
