package AuctionHouse.Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;

import AuctionHouse.NetworkMessages.NetworkMessage;

/**
 * Communicates directly (p2p) with another user (of opposite role)
 * Also sends/receives services (files)
 */

public class NetworkCommunicator extends Thread {
	private NetworkCommMediator netMediator;
	private InetSocketAddress hostSocketAddress;
	private Selector selector;
	private ArrayList<SocketChannel> socketChannels;
	private boolean running;
	
	public NetworkCommunicator(NetworkCommMediator mediator,
				String hostIp, int hostPort){
		super();
		
		netMediator = mediator;
		hostSocketAddress = new InetSocketAddress(hostIp, hostPort);
		try {
			selector = SelectorProvider.provider().openSelector();
		} catch (Exception e) {
			System.err.println("Error on Selector creation: " + e);
			e.printStackTrace();
		}
		
		socketChannels = new ArrayList<SocketChannel>();
	}
	
	public synchronized void startRunning() {
		this.running = true;
		this.start();
	}
	
	public synchronized void stopRunning() {
		this.running = false;
		this.selector.wakeup();
	}
	
	protected synchronized boolean isRunning() {
		return this.running;
	}
	
	public void run() {
		System.out.println("Server started on port: " + hostSocketAddress.getPort());
		
		// Creeaza ServerSocketChannels
		ServerSocketChannel serverChannel = null;
		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			
			serverChannel.socket().bind(hostSocketAddress);
			serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
		} catch (Exception e) {
			System.err.println("Error on ServerSocketChannel creation: " + e);
			e.printStackTrace();
		}

		while (this.isRunning()) {
			try {
				this.selector.select(10000);
				
				// Proceseaza cheile pentru care s-au produs evenimente.
				Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid()) {
						continue;
					}

					if (key.isAcceptable()) {
						System.out.println("Accept with key: " + key);
						this.doAccept(key);
					}
					
					if (key.isWritable()) {
						System.out.println("Write with key: " + key);
						this.doWrite(key);
					}
					
					if (key.isReadable()) {
						System.out.println("Read with key: " + key);
						this.doRead(key);
					} 
				}
								
			} catch (Exception e) {
				System.err.println("Exceptie in thread-ul Selectorului: " + e);
				e.printStackTrace();
			}
		}
		
		// Inchide ServerSocketChannel
		try {
			serverChannel.close();
		} catch (IOException e1) {}

		// Inchide toate SocketChannel-urile.
		for (SocketChannel schan: this.socketChannels) {
			try {
				schan.close();
			} catch (Exception e) {}
		}

		System.out.println("Server stopped");
	}
	
	protected void doAccept(SelectionKey key) throws Exception {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(this.selector, SelectionKey.OP_READ);

		this.socketChannels.add(socketChannel);
	}
	
	protected void doRead(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
	}

	protected void doWrite(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();

	}
	

	public void sendMessage(NetworkMessage netMsg) {
		String person = netMsg.getDestinationPerson();
		InetSocketAddress addr = netMediator.getPersonsAddress(person);
		byte[] msg = netMsg.getSerialized();
		
		try {
			System.out.println("Trying to connect to port: " + addr.getPort());
			
			Socket clientSocket = new Socket(addr.getAddress(), addr.getPort());
			
			// Send to socket
			clientSocket.getOutputStream().write(msg);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
