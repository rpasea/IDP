package AuctionHouse.Mediator;

import AuctionHouse.Commands.*;
import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.XMLDataManager;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Messages.*;

public class Mediator implements GUIMediator, NetworkMediator,
		WebClientMediator {

	public static final int ROL_FURNIZOR = 0;
	public static final int ROL_CUMPARATOR = 1;

	private ControllerMediator controllerMediator;
	private DataManager dataManager;

	public Mediator() {
		controllerMediator = new ControllerMediator(this);
		dataManager = new XMLDataManager("Database.xml");
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
		switch (message.getType()) {
		case Login: {
			LoginMessage mess = (LoginMessage) message;
			Command com = new LoginCommand(mess.getUser(), mess.getPassword(),
					mess.getRole(), dataManager, controllerMediator);
			result = com.run();
			break;
		}
		case Logout:
			result = controllerMediator.logout();
			break;
		case LaunchOffer: {
			LaunchOfferMessage mess = (LaunchOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new LaunchOfferCommand(mess.getService(),
					controllerMediator, dataManager, null);
			result = com.run();
			break;
		}
		case DropOffer: {
			DropOfferMessage mess = (DropOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new DropOfferCommand(mess.getService(),
					controllerMediator, dataManager, null);
			result = com.run();
			break;
		}
		case AcceptOffer: {
			AcceptOfferMessage mess = (AcceptOfferMessage) message;
			// TODO: don't forget the NetworkCommunicator here
			Command com = new AcceptOfferCommand(mess.getService(),
					mess.getPerson(), controllerMediator, dataManager, null);
			result = com.run();
			break;
		}
		}

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
		// TODO Auto-generated method stub
		return null;
	}

}
