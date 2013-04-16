package AuctionHouse.Mediator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.apache.log4j.Logger;

import AuctionHouse.Commands.*;
import AuctionHouse.DataContext.DataManager;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Main.SimulatorThread;
import AuctionHouse.Main.TestWorker;
import AuctionHouse.Messages.*;
import AuctionHouse.Network.NetworkCommunicator;

public class Mediator implements GUIMediator, NetworkMediator,
		WebClientMediator {

	final Logger logger = Logger.getLogger("generic.mediator");
	
	public static final int ROL_FURNIZOR = 0;
	public static final int ROL_CUMPARATOR = 1;

	private ControllerMediator controllerMediator;
	private DataManager dataManager;
	private HashMap<String, Transaction> transactions;
	private NetworkCommunicator networkCommunicator;

	public Mediator(String hostIp, int hostPort,   int simnr) {
		controllerMediator = new ControllerMediator(this,   simnr);
		networkCommunicator = new NetworkCommunicator(this, hostIp, hostPort);
		transactions = new HashMap<String, Transaction>();
	}

	/*
	 * used for DataManager injection
	 */
	public Mediator(DataManager dataManager, String hostIp, int hostPort,   int simnr) {
		this.dataManager = dataManager;
		controllerMediator = new ControllerMediator(this,   simnr);
		networkCommunicator = new NetworkCommunicator(this, hostIp, hostPort);
		transactions = new HashMap<String, Transaction>();
	}

	public void init() {
		controllerMediator.initGui();
	}

	/*
	 * Methods of GUIMediator interface
	 */

	@Override
	public Object sendGuiMessage(Message message) {
		Object result = null;
		String tip = "";
		switch (message.getType()) {
		/**
		 * Login
		 */
		case Login: {
			LoginMessage mess = (LoginMessage) message;
			Command com = new LoginCommand(mess.getUser(), mess.getPassword(),
					mess.getRole(), dataManager, controllerMediator);
			result = com.run();

			// Should probably be in the login command
			if ((Boolean) result) {
				networkCommunicator.startRunning();
			}

			// FIXME: for testing purpose
			if ((Boolean) result) {
				SimulatorThread simulation = new SimulatorThread(this,
						controllerMediator);
				if (mess.getUser().equals("gicu")) {
					simulation.role = ROL_CUMPARATOR;
				} else {
					simulation.role = ROL_FURNIZOR;
				}
				// Porneste simularea
				simulation.start();
			}

			tip = "Login";
			break;
		}
		case Logout:
			result = controllerMediator.logout();
			networkCommunicator.stopRunning();

			tip = "Logout";
			break;
		/**
		 * Buyer
		 */
		case LaunchAuction: {
			LaunchAuctionMessage mess = (LaunchAuctionMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new LaunchAuctionCommand(mess.getService(),
					controllerMediator, dataManager);
			result = com.run();

			tip = "LaunchAuction";
			break;
		}
		case DropAuction: {
			DropAuctionMessage mess = (DropAuctionMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new DropAuctionCommand(mess.getService(),
					controllerMediator, dataManager);
			result = com.run();

			tip = "DropAuction";
			break;
		}
		case AcceptOffer: {
			AcceptOfferMessage mess = (AcceptOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new AcceptOfferCommand(mess.getService(),
					mess.getPerson(), mess.getOffer(), controllerMediator,
					dataManager, networkCommunicator);
			result = com.run();

			tip = "AcceptOffer";
			break;
		}
		case RejectOffer: {
			RejectOfferMessage mess = (RejectOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new RejectOfferCommand(mess.getService(),
					mess.getPerson(), mess.getOffer(), controllerMediator,
					dataManager, networkCommunicator);
			result = com.run();

			tip = "RejectOffer";
			break;
		}
		/**
		 * Seller
		 */
		case MakeOffer: {
			MakeOfferMessage mess = (MakeOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new MakeOfferCommand(mess.getService(),
					mess.getPerson(), mess.getOffer(), controllerMediator,
					dataManager, networkCommunicator);
			result = com.run();

			tip = "MakeOffer";
			break;
		}
		case DropOffer: {
			DropOfferMessage mess = (DropOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new DropOfferCommand(mess.getService(),
					mess.getPerson(), controllerMediator, dataManager);
			result = com.run();
			tip = "DropOffer";
			break;
		}
		}

		logger.debug("Mesaj: " + tip);

		return result;
	}

	public int getRole() {
		return dataManager.getRole();
	}

	/*
	 * Methods of WebClientMediator interface
	 */

	@Override
	public Object sendWebClientMessage(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Methods of NetworkMediator interface
	 */

	@Override
	public Object sendNetworkMessage(Message message) {
		Object result = null;
		String tip = "";
		if (message == null)
			return false;
		switch (message.getType()) {
		/**
		 * Buyer
		 */
		case StartTransaction: {
			StartTransactionMessage mess = (StartTransactionMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new StartTransactionCommand(mess.getService(),
					mess.getSeller(), mess.getBuyer(), mess.getOffer(),
					controllerMediator, dataManager, mess.getFileSize(),
					mess.getSocketChannel(), networkCommunicator);
			result = com.run();
			if (result != null) {
				transactions.put(mess.getService() + "_" + mess.getSeller()
						+ "_" + mess.getBuyer(), (Transaction) result);
				final Transaction t = (Transaction) result;
				result = true;
			} else {
				result = false;
			}
			tip = "StartTransaction";
			break;
		}
		/**
		 * Seller
		 */
		case OfferAccepted: {
			OfferAcceptedMessage mess = (OfferAcceptedMessage) message;
			Command com = new OfferAcceptedCommand(mess.getService(),
					mess.getPerson(), mess.getOffer(), controllerMediator,
					dataManager, networkCommunicator);
			result = com.run();
			if (result != null) {
				final Transaction t = (Transaction) result;
				transactions.put(t.getService() + "_" + t.getSeller()
						+ "_" + t.getBuyer(), t);
				result = true;
			} else {
				result = false;
			}

			tip = "OfferAccepted";
			break;
		}
		case OfferRefused: {
			OfferRefusedMessage mess = (OfferRefusedMessage) message;
			Command com = new OfferRejectedCommand(mess.getService(),
					mess.getPerson(), controllerMediator, dataManager, null);
			result = com.run();

			tip = "OfferRefused";
			break;
		}
		case OfferExceed: {
			OfferExceedMessage mess = (OfferExceedMessage) message;
			Command com = new OfferExceedCommand(mess.getService(),
					mess.getPerson(), controllerMediator, dataManager);
			result = com.run();

			tip = "OfferExceed";
			break;
		}
		case MakeOffer: {
			MakeOfferMessage mess = (MakeOfferMessage) message;

			Command com = new MakeOfferCommand(mess.getService(),
					mess.getPerson(), mess.getOffer(), controllerMediator,
					dataManager, networkCommunicator, false);
			result = com.run();

			tip = "Make Offer";
			break;
		}
		}
		logger.debug("Mesaj-retea: " + tip);
		return result;
	}

	public void CheckTransactionChannelClosed(SocketChannel chan) {
		/*
		 * Use a new list because i am going to modify the map
		 */

		LinkedList<String> list = new LinkedList<String>();
		list.addAll(transactions.keySet());

		for (String key : list) {
			Transaction t = transactions.get(key);
			if (t.getSocketChannel().equals(chan)) {
				transactions.remove(key);
				try {
					t.finishTransaction(dataManager.getIdentity().getName(),
							dataManager.getRole() == ROL_CUMPARATOR );
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * TODO: implement this
	 */
	public String getPerson(SocketAddress addr) {
		// FIXME: Repara magaria (la etapa 3)
		BufferedReader br = null;
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("cine.txt"));
 
			sCurrentLine = br.readLine();
			logger.debug("%%% " + sCurrentLine);
			
			return sCurrentLine;
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	public InetSocketAddress getPersonsAddress(String person) {
		InetSocketAddress destAddr;

		if (person.equals("gicu"))
			destAddr = new InetSocketAddress("127.0.0.1", 50001);
		else if (person.equals("Lulache"))
			destAddr = new InetSocketAddress("127.0.0.1", 50002);
		else // vene
			destAddr = new InetSocketAddress("127.0.0.1", 50003);

		return destAddr;
	}

	@Override
	public String getSelf() {
		return dataManager.getIdentity().getName();
	}

}
