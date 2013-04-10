package AuctionHouse.Mediator;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;

import AuctionHouse.Commands.*;
import AuctionHouse.DataContext.DataManager;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Main.SimulatorThread;
import AuctionHouse.Main.TestWorker;
import AuctionHouse.Messages.*;
import AuctionHouse.Network.NetworkCommunicator;

public class Mediator implements GUIMediator,
		NetworkMediator, WebClientMediator {

	public static final int ROL_FURNIZOR = 0;
	public static final int ROL_CUMPARATOR = 1;

	private ControllerMediator controllerMediator;
	private DataManager dataManager;
	private HashMap<String, Transaction> transactions;
	private NetworkCommunicator networkCommunicator;

	public Mediator(String hostIp, int hostPort) {
		controllerMediator = new ControllerMediator(this);
		networkCommunicator = new NetworkCommunicator(this, hostIp, hostPort);
		transactions = new HashMap<String, Transaction>();
	}
	
	/*
	 * used for DataManager injection
	 */
	public Mediator(DataManager dataManager, String hostIp, int hostPort) {
		this.dataManager = dataManager;
		controllerMediator = new ControllerMediator(this);
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
				SimulatorThread simulation = new SimulatorThread(this, controllerMediator);
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
					dataManager, this);
			result = com.run();

			tip = "AcceptOffer";
			break;
		}
		case RejectOffer: {
			RejectOfferMessage mess = (RejectOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new RejectOfferCommand(mess.getService(),
					mess.getPerson(), controllerMediator, dataManager);
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

		System.out.println("Mesaj: " + tip);

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
		switch (message.getType()) {
		/**
		 * Buyer
		 */
		case StartTransaction: {
			StartTransactionMessage mess = (StartTransactionMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new StartTransactionCommand(mess.getService(),
					mess.getSeller(), mess.getBuyer(), mess.getOffer(),
					controllerMediator, dataManager);
			result = com.run();
			if (result != null) {
				transactions.put(mess.getService() + "_" + mess.getSeller()
						+ "_" + mess.getBuyer(), (Transaction) result);
				final Transaction t = (Transaction) result;
				result = true;
				// FIXME: for testing purpose
				//(new TestWorker(t)).execute();
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
					dataManager);
			result = com.run();
			if (result != null) {
				final Transaction t = (Transaction)result;
				result = true;
				//FIXME: for testing purpose
				(new TestWorker(t)).execute();
			} else {
				result = false;
			}
			
			tip = "OfferAccepted";
			break;
		}
		case OfferRefused: {
			OfferRefusedMessage mess = (OfferRefusedMessage) message;
			Command com = new OfferRefusedCommand(mess.getService(),
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
		System.out.println("Mesaj: " + tip);
		return result;
	}
	
	/*
	 * TODO: implement this
	 */
	public String getPerson(SocketAddress addr) {
		if (dataManager.getIdentity().getName().equals("gicu"))
			return "Lulache";
		else 
			return "gicu";
	}
	
	public InetSocketAddress getPersonsAddress(String person){
		InetSocketAddress destAddr;
		
		if(networkCommunicator.getPort() == 50001)
			destAddr = new InetSocketAddress("127.0.0.1", 50002);
		else
			destAddr = new InetSocketAddress("127.0.0.1", 50001);
		
		return destAddr;
	}

}
