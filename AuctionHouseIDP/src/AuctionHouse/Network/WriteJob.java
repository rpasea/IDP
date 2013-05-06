package AuctionHouse.Network;

import java.io.IOException;
import java.nio.channels.SelectionKey;

public class WriteJob implements Runnable {
	private NetworkCommunicator netComm;
	private SelectionKey key;
	
	public WriteJob(NetworkCommunicator netComm, SelectionKey key) {
		this.netComm = netComm;
		this.key = key;
	}
	@Override
	public void run() {
		try {
			this.netComm.doWrite(this.key);
		} catch (Exception e) {
			try {
				this.netComm.CancelChannel(key.channel());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.err.println("ReadJob exception: " + e);
			//e.printStackTrace();
		}
	}
}
