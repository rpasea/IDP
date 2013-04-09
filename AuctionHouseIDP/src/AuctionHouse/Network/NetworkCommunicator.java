package AuctionHouse.Network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import AuctionHouse.Mediator.NetworkMediator;
import AuctionHouse.NetworkMessages.NetworkMessage;

/**
 * Communicates directly (p2p) with another user (of opposite role) Also
 * sends/receives services (files)
 */

public class NetworkCommunicator extends Thread {

	/*
	 * this should be made configurable from file!!!
	 */
	public static final int NumberOfThreadsInPool = 3;

	private NetworkCommMediator netMediator;
	private NetworkMediator mediator;

	private InetSocketAddress hostSocketAddress;
	private Selector selector;
	private ArrayList<SocketChannel> socketChannels;
	private ExecutorService threadPool;
	private LinkedBlockingQueue<ByteBuffer> bufferPool;
	private HashMap<SocketAddress, String> addressToPerson;

	private LinkedList<ChangeRequest> changeRequestQueue;

	// private ByteBuffer rBuffer = ByteBuffer.allocate(8192);

	private HashMap<SelectionKey, MessageBuffer> readBuffers;
	private HashMap<SocketChannel, LinkedList<byte[]>> writeBuffers;
	private boolean running;

	public NetworkCommunicator(NetworkCommMediator netMediator, String hostIp,
			int hostPort, NetworkMediator mediator) {
		super();

		this.mediator = mediator;
		this.netMediator = netMediator;
		hostSocketAddress = new InetSocketAddress(hostIp, hostPort);
		try {
			selector = SelectorProvider.provider().openSelector();
		} catch (Exception e) {
			System.err.println("Error on Selector creation: " + e);
			e.printStackTrace();
		}
		this.threadPool = Executors.newFixedThreadPool(NumberOfThreadsInPool);
		this.bufferPool = new LinkedBlockingQueue<ByteBuffer>();

		for (int i = 0; i < NumberOfThreadsInPool; i++) {
			this.bufferPool.add(ByteBuffer.allocate(8192));
		}

		readBuffers = new HashMap<SelectionKey, MessageBuffer>();
		writeBuffers = new HashMap<SocketChannel, LinkedList<byte[]>>();
		addressToPerson = new HashMap<SocketAddress, String>();
		this.changeRequestQueue = new LinkedList<ChangeRequest>();

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
		System.out.println("Server started on port: "
				+ hostSocketAddress.getPort());

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
				Iterator<SelectionKey> selectedKeys = this.selector
						.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid()) {
						continue;
					}

					if (key.isConnectable()) {
						System.out.println("Connect with key: " + key);
						key.interestOps(key.interestOps()
								^ SelectionKey.OP_CONNECT);
						this.doConnect(key);
					}
					if (key.isAcceptable()) {
						System.out.println("Accept with key: " + key);
						key.interestOps(key.interestOps()
								^ SelectionKey.OP_ACCEPT);
						this.doAccept(key);
					}

					if (key.isWritable()) {
						System.out.println("Write with key: " + key);
						key.interestOps(key.interestOps()
								^ SelectionKey.OP_WRITE);
						this.threadPool.submit(new WriteJob(this, key));
					}

					if (key.isReadable()) {
						System.out.println("Read with key: " + key);
						// this.doRead(key);
						/*
						 * We set the key as unreadable, so no more reads from
						 * this key until the current one is treated
						 */
						key.interestOps(key.interestOps()
								^ SelectionKey.OP_READ);

						this.threadPool.submit(new ReadJob(this, key));
					}

					/*
					 * We process selection key changes here because new
					 * selection keys can only be added by the thread running
					 * the selector (not really sure)
					 */
				}
				
				ChangeRequest creq;
				synchronized (this.changeRequestQueue) {
					while ((creq = this.changeRequestQueue.poll()) != null) {
						if (creq.socketChannel == null) {
							creq.key.interestOps(creq.newOps);
						} else {
							creq.socketChannel.register(this.selector,
									creq.newOps);
						}
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
		} catch (IOException e1) {
		}

		// Inchide toate SocketChannel-urile.
		for (SocketChannel schan : this.socketChannels) {
			try {
				schan.close();
			} catch (Exception e) {
			}
		}

		System.out.println("Server stopped");
	}

	private void doConnect(SelectionKey key) {
		SocketChannel socketChannel = (SocketChannel) key
				.channel();
		
		try {
		      socketChannel.finishConnect();
		    } catch (IOException e) {
		      // Cancel the channel's registration with our selector
		      key.cancel();
		      return;
		    }
		  
		/*
		 * Set the key to accept writes
		 */
		synchronized (this.changeRequestQueue) {
			this.changeRequestQueue.add(new ChangeRequest(key, SelectionKey.OP_WRITE));
		}

		this.selector.wakeup();

	}

	protected void doAccept(SelectionKey key) throws Exception {
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key
				.channel();
		SocketChannel socketChannel = serverSocketChannel.accept();
		socketChannel.configureBlocking(false);
		socketChannel.register(this.selector, SelectionKey.OP_READ);

		addressToPerson.put(socketChannel.getRemoteAddress(),
				mediator.getPerson(socketChannel.getRemoteAddress()));
		this.socketChannels.add(socketChannel);
	}

	protected void doRead(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		SocketAddress addr = socketChannel.getRemoteAddress();

		ByteBuffer rBuffer = this.bufferPool.take();
		rBuffer.clear();

		int numRead;

		try {
			numRead = socketChannel.read(rBuffer);
		} catch (Exception e) {
			numRead = -1000000000;
		}

		if (numRead <= 0) {
			System.out
					.println("[NetworkCommunicator] S-a inchis socket-ul asociat cheii "
							+ key);
			key.channel().close();
			key.cancel();
			readBuffers.remove(key);
			return;
		}

		System.out.println("[NetworkCommunicator] S-au citit " + numRead
				+ " bytes.");

		byte[] currentBuf = rBuffer.array();
		MessageBuffer msgBuf = readBuffers.get(key);

		if (msgBuf == null) {
			msgBuf = new MessageBuffer();
			msgBuf.setSource(mediator.getPerson(addr));
			this.readBuffers.put(key, msgBuf);
		}

		msgBuf.putBytes(currentBuf, numRead);

		if (msgBuf.hasMessages())
			for (NetworkMessage nMess : msgBuf.getAndClearMessages())
				mediator.sendNetworkMessage(nMess.toMessage());

		this.bufferPool.add(rBuffer);

		/*
		 * Set the key to accept further reads
		 */
		synchronized (this.changeRequestQueue) {
			this.changeRequestQueue.add(new ChangeRequest(key, key
					.interestOps() | SelectionKey.OP_READ));
		}

		this.selector.wakeup();

	}

	protected void doWrite(SelectionKey key) throws Exception {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		ByteBuffer wBuffer = this.bufferPool.take();
		List<byte[]> wbuf = null;
		
		synchronized (key) {
			wbuf = this.writeBuffers.get(socketChannel);

			while (wbuf.size() > 0) {
				byte[] bbuf = wbuf.get(0);
				wbuf.remove(0);
				
				wBuffer.clear();
				wBuffer.put(bbuf);
				wBuffer.flip();
	
				int numWritten = socketChannel.write(wBuffer);
				System.out.println("[NetworkCommunicator] Am scris " + numWritten + " bytes pe socket-ul asociat cheii " + key);
	
				if (numWritten < bbuf.length) {
					byte[] newBuf = new byte[bbuf.length - numWritten];
	
					// Copiaza datele inca nescrise din bbuf in newBuf.
					for (int i = numWritten; i < bbuf.length; i++) {
						newBuf[i - numWritten] = bbuf[i];
					}
					
					wbuf.add(0, newBuf);
				}
			}
		
			if (wbuf.size() > 0) {
				synchronized (this.changeRequestQueue) {
					this.changeRequestQueue.add(new ChangeRequest(key, key.interestOps() | SelectionKey.OP_WRITE));
				}

				this.selector.wakeup();
			}
		}
		
		this.bufferPool.add(wBuffer);

	}

	public void sendMessage(NetworkMessage netMsg) {
		String person = netMsg.getDestinationPerson();
		InetSocketAddress addr = netMediator.getPersonsAddress(person);
		byte[] msg = netMsg.serialize();

		/*TODO: Look for the socketChannel... if existing connection, then use it
		 * if found, this code should start the writing, after setting up the write buffer  
		 * synchronized (this.changeRequestQueue) {
		 *	this.changeRequestQueue.add(new ChangeRequest(socketChannel.keyFor(selector),
		 *			socketChannel.keyFor(selector).interestOps() | SelectionKey.Write));
		 *}
		 */
		
		try {
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
			
			socketChannel.connect(new InetSocketAddress(addr.getAddress(), addr
					.getPort()));
			LinkedList<byte[]> list = new LinkedList<byte[]>();
			list.add(msg);
			writeBuffers.put(socketChannel, list);

			synchronized (this.changeRequestQueue) {
				this.changeRequestQueue.add(new ChangeRequest(socketChannel,
						SelectionKey.OP_CONNECT));
			}
			
			this.selector.wakeup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
