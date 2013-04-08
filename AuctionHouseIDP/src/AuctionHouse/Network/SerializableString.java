package AuctionHouse.Network;

import java.nio.ByteBuffer;

public class SerializableString {
	private String string;
	
	public SerializableString() {
		string = "";
	}
	
	public SerializableString(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}
	
	public byte[] serialize() {
		
		ByteBuffer bbuf = ByteBuffer.allocate( 4 /* sizeof(int) */ + 2 /*sizeof(char) */ * string.length());
		bbuf.putInt(string.length());
		
		for ( char c : string.toCharArray()) {
			bbuf.putChar(c);
		}
		
		return bbuf.array();
	}
	
	/*
	 * The buffer's position is set after this string!
	 */
	public void deserialize(ByteBuffer bbuf) {
		string = "";
		int size = bbuf.getInt();
		for (int i = 0; i < size; i++) {
			string += bbuf.getChar();
		}
	}
}
