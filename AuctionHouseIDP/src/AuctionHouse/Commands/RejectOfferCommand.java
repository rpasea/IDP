package AuctionHouse.Commands;

import org.apache.log4j.Logger;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Network.NetworkCommunicator;
import AuctionHouse.NetworkMessages.NetworkMessage;
import AuctionHouse.NetworkMessages.RejectOfferNetworkMessage;
import AuctionHouse.DataContext.DataManager;

public class RejectOfferCommand implements Command {
	
	final Logger logger = Logger.getLogger("generic.mediator.rejectoffer");
	
	private DataManager dataManager;
	private String service;
	private String seller;
	private ControllerMediator mediator;
	private NetworkCommunicator communicator;
	private String offer;

	public RejectOfferCommand(String service, String seller, String offer,
			ControllerMediator mediator, DataManager dataManager,
			NetworkCommunicator communicator) {
		this.service = service;
		this.seller = seller;
		this.offer = offer;
		this.mediator = mediator;
		this.dataManager = dataManager;
		this.communicator = communicator;
	}

	@Override
	public Object run() {
		AHTableModel model = mediator.getModel();
		Service s = dataManager.getService(service);
		ServiceEntry se = s.getEntry(seller);
		
		if (se.getState() != ServiceEntry.State.OFFER_MADE)
			return false;
		
		AHTableModel embeddedModel = model.getInnerTableModel(se.getService().getName());
		int offerRow = embeddedModel.getInnerPersonRowNr(se.getPerson());
		
		se.setState(ServiceEntry.State.OFFER_REJECTED);
		se.setStatus("Offer Rejected");
		embeddedModel.setValueAt("Offer Rejected", offerRow, 1);
		
		/*
		 * Use the network module to send the offer
		 */
		NetworkMessage netMsg = new RejectOfferNetworkMessage(service, seller, offer);
		netMsg.setDestinationPerson(seller);
		communicator.sendMessage(netMsg);
		return true;
	}
}
