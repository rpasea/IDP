package AuctionHouse.Network;

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
			System.err.println("ReadJob exception: " + e);
			e.printStackTrace();
		}
	}
}
