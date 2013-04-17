package AuctionHouse.Commands;

import org.apache.log4j.Logger;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Network.NetworkCommunicator;
import AuctionHouse.NetworkMessages.DropOfferNetworkMessage;
import AuctionHouse.NetworkMessages.NetworkMessage;
import AuctionHouse.DataContext.DataManager;

public class DropOfferCommand implements Command {
	
	final Logger logger = Logger.getLogger("generic.mediator.dropoffer");
	
	private DataManager dataManager;
	private String service;
	private String buyer;
	private String offer;
	private ControllerMediator mediator;
	private NetworkCommunicator communicator;

	private boolean sendToNetwork;
	
	public DropOfferCommand(String service, String buyer, String offer,
			ControllerMediator mediator,
			DataManager dataManager,
			NetworkCommunicator communicator) {
		this.service = service;
		this.buyer = buyer;
		this.offer = offer;
		this.mediator = mediator;
		this.dataManager = dataManager;
		this.communicator = communicator;
		this.sendToNetwork = true;
	}
	
	public DropOfferCommand(String service, String buyer, String offer,
			ControllerMediator mediator,
			DataManager dataManager,
			NetworkCommunicator communicator, boolean sendToNetwork) {
		this.service = service;
		this.buyer = buyer;
		this.mediator = mediator;
		this.dataManager = dataManager;
		this.communicator = communicator;
		this.sendToNetwork = sendToNetwork;
	}
	
	@Override
	public Object run() {
		AHTableModel model = mediator.getModel();
		Service s = dataManager.getService(service);
		ServiceEntry se = s.getEntry(buyer);
		
		if (se.getState() != ServiceEntry.State.OFFER_MADE
				&& se.getState() != ServiceEntry.State.OFFER_EXCEED)
			return false;
		
		AHTableModel embeddedModel = model.getInnerTableModel(se.getService().getName());
		int offerRow = embeddedModel.getInnerPersonRowNr(se.getPerson());
		
		se.setState(ServiceEntry.State.NO_OFFER);
		se.setStatus("No Offer");
		embeddedModel.setValueAt("No Offer", offerRow, 1);
		embeddedModel.setValueAt("", offerRow, 2);
		
		/*
		 * Use the network module to notify the buyer
		 */
		if (sendToNetwork){
			NetworkMessage netMsg = new DropOfferNetworkMessage(service, buyer, offer);
			netMsg.setDestinationPerson(buyer);
			communicator.sendMessage(netMsg);
		}
		
		return true;
	}

}
