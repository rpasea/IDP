package AuctionHouse.NetworkMessages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import AuctionHouse.Messages.Message;
import AuctionHouse.Network.SerializableString;

public class FileNetworkMessage extends NetworkMessage {

	private SerializableString service;
	private FileOutputStream outputStream;
	private Integer currentStringSize;

	private ByteBuffer stringBuffer;
	private int intBufferPos;
	private byte[] intBuffer;

	public FileNetworkMessage() {
		outputStream = null;
		stringBuffer = null;
		currentStringSize = null;
		intBuffer = new byte[4];
		intBufferPos = 0;
		service = new SerializableString();
		type = NetworkMessage.FILE_TRANSFER;
	}

	/*
	 * not needed
	 */
	@Override
	public byte[] serialize() {
		
		return null;
	}

	@Override
	public void deserialize() {
		if (outputStream != null) {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public Message toMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(byte b) {
		if (currentStringSize == null) {
			intBuffer[intBufferPos] = b;
			intBufferPos++;
			if (intBufferPos == 4) {
				currentStringSize = ByteBuffer.wrap(intBuffer).getInt();
				stringBuffer = ByteBuffer.allocate(currentStringSize);
				intBufferPos = 0;
			}
		} else if (stringBuffer.position() < currentStringSize) {
			stringBuffer.put(b);
			if (stringBuffer.position() == currentStringSize) {
				service.deserialize(stringBuffer);
				new File(NetworkMessage.CurrentUserName).mkdirs();
				try {
					outputStream = new FileOutputStream(new File(
							NetworkMessage.CurrentUserName + "/"
									+ service.getString()));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				outputStream.write(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
