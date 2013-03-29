package AuctionHouse.Commands;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.GUI.ControllerMediator;

public class MakeOfferCommand implements Command {
	private DataManager dataManager;

	// TODO: the network module goes here
	private Object networkCommunicator;
	private String service;
	private String buyer;
	private ControllerMediator mediator;
	
	public MakeOfferCommand(String service, String buyer,
			ControllerMediator mediator, DataManager dataManager,
			Object networkCommunicator) {
		this.service = service;
		this.buyer = buyer;
		this.mediator = mediator;
		this.dataManager = dataManager;
		this.networkCommunicator = networkCommunicator;
	}
	
	@Override
	public Object run() {
		
		/**
		 * TODO: Afiseaza dialog oferta
		 */
		
		return null;
	}

}
