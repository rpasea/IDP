package AuctionHouse.Mediator;

import java.util.HashMap;

import AuctionHouse.Commands.*;
import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.XMLDataManager;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Main.TestWorker;
import AuctionHouse.Messages.*;

public class Mediator implements GUIMediator, NetworkMediator,
		WebClientMediator {

	public static final int ROL_FURNIZOR = 0;
	public static final int ROL_CUMPARATOR = 1;

	private ControllerMediator controllerMediator;
	private DataManager dataManager;
	private HashMap<String, Transaction> transactions;

	public Mediator(DataManager dataMgr) {
		controllerMediator = new ControllerMediator(this);
		dataManager = dataMgr;
		transactions = new HashMap<String, Transaction>();
	}

	public void init() {
		controllerMediator.initGui();
		dataManager.init();
	}

	/*
	 * Methods of GUIMediator interface
	 */

	@Override
	public Object sendGuiMessage(Message message) {
		Object result = null;
		String tip = "";
		switch (message.getType()) {
		case Login: {
			LoginMessage mess = (LoginMessage) message;
			Command com = new LoginCommand(mess.getUser(), mess.getPassword(),
					mess.getRole(), dataManager, controllerMediator);
			result = com.run();

			tip = "Login";
			break;
		}
		case Logout:
			result = controllerMediator.logout();

			tip = "Logout";
			break;
		case LaunchAuction: {
			LaunchAuctionMessage mess = (LaunchAuctionMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new LaunchAuctionCommand(mess.getService(),
					controllerMediator, dataManager, null);
			result = com.run();

			tip = "LaunchAuction";
			break;
		}
		case DropAuction: {
			DropAuctionMessage mess = (DropAuctionMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new DropAuctionCommand(mess.getService(),
					controllerMediator, dataManager, null);
			result = com.run();

			tip = "DropAuction";
			break;
		}
		case AcceptOffer: {
			AcceptOfferMessage mess = (AcceptOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new AcceptOfferCommand(mess.getService(),
					mess.getPerson(), mess.getOffer(), controllerMediator,
					dataManager, null, this);
			result = com.run();

			tip = "AcceptOffer";
			break;
		}
		case RejectOffer: {
			RejectOfferMessage mess = (RejectOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new RejectOfferCommand(mess.getService(),
					mess.getPerson(), controllerMediator, dataManager, null);
			result = com.run();

			tip = "RejectOffer";
			break;
		}
		case MakeOffer: {
			MakeOfferMessage mess = (MakeOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new MakeOfferCommand(mess.getService(),
					mess.getPerson(), controllerMediator, dataManager, null);
			result = com.run();

			tip = "MakeOffer";
			break;
		}
		case DropOffer: {
			DropOfferMessage mess = (DropOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new DropOfferCommand(mess.getService(),
					mess.getPerson(), controllerMediator, dataManager, null);
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
	 * Methods of NetworkMediator interface
	 */

	@Override
	public Object sendWebClientMessage(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Methods of WebClientMediator interface
	 */

	@Override
	public Object sendNetworkMessage(Message message) {
		Object result = null;
		String tip = "";
		switch (message.getType()) {
		case StartTransaction: {
			StartTransactionMessage mess = (StartTransactionMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new StartTransactionCommand(mess.getService(),
					mess.getSeller(), mess.getBuyer(), mess.getOffer(),
					controllerMediator, dataManager, null);
			result = com.run();
			if (result != null) {
				transactions.put(mess.getService() + "_" + mess.getSeller()
						+ "_" + mess.getBuyer(), (Transaction) result);
				final Transaction t = (Transaction)result;
				result = true;
				//for testing purpose 
				(new TestWorker(t)).execute();
			} else {
				result = false;
			}
			tip = "StartTransaction";
			break;
		}

		}
		System.out.println("Mesaj: " + tip);
		return result;
	}

}
