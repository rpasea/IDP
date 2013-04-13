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
	
	private int size;

	private ByteBuffer stringBuffer;
	private int intBufferPos;
	private byte[] intBuffer;

	public FileNetworkMessage() {
		outputStream = null;
		stringBuffer = null;
		currentStringSize = null;
		intBuffer = new byte[4];
		intBufferPos = 0;
		service = null;
		type = NetworkMessage.FILE_TRANSFER;
	}
	
	public FileNetworkMessage(String name, int size) {
		service = new SerializableString(name);
		this.size = size;
		type = NetworkMessage.FILE_TRANSFER;
	}

	@Override
	public byte[] serialize() {
		int size = 4; // the type int
		byte[] service = this.service.serialize();
		
		size += service.length; 

		ByteBuffer bbuf = ByteBuffer.allocate( 4 /* sizeof(int) */ + size);
		bbuf.putInt(size + this.size /* the actual size of the file*/);
		bbuf.putInt(type);
		bbuf.put(service);
		
		return bbuf.array();
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
				stringBuffer = ByteBuffer.allocate(currentStringSize * 2);
				intBufferPos = 0;
			}
		} else if (service == null) {
			stringBuffer.put(b);
			if (stringBuffer.position() == currentStringSize * 2 /*sizeof(char)*/) {
				service = new SerializableString();
				stringBuffer.flip();
				ByteBuffer reconstr =  ByteBuffer.allocate(currentStringSize * 2 + 4);
				reconstr.putInt(currentStringSize);
				reconstr.put(stringBuffer.array());
				reconstr.flip();
				service.deserialize(reconstr);
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
