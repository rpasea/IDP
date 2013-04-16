package AuctionHouse.Commands;

import org.apache.log4j.Logger;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Network.NetworkCommunicator;
import AuctionHouse.NetworkMessages.AcceptOfferNetworkMessage;
import AuctionHouse.NetworkMessages.NetworkMessage;
import AuctionHouse.DataContext.DataManager;

public class AcceptOfferCommand implements Command {

	final Logger logger = Logger.getLogger("generic.mediator.acceptoffer");

	private String service;
	private String seller;
	private String offer;
	private ControllerMediator controllerMediator;
	private NetworkCommunicator networkCommunicator;
	private DataManager dataManager;

	public AcceptOfferCommand(String service, String seller, String offer,
			ControllerMediator mediator, DataManager dataManager,
			NetworkCommunicator networkCommunicator) {
		this.service = service;
		this.seller = seller;
		this.offer = offer;
		this.controllerMediator = mediator;
		this.networkCommunicator = networkCommunicator;
		this.dataManager = dataManager;
	}

	@Override
	public Object run() {
		AHTableModel model = controllerMediator.getModel();
		Service s = dataManager.getService(service);
		ServiceEntry se = s.getEntry(seller);
		
		if (se.getState() != ServiceEntry.State.OFFER_MADE)
			return false;
		
		AHTableModel embeddedModel = model.getInnerTableModel(se.getService().getName());
		int offerRow = embeddedModel.getInnerPersonRowNr(se.getPerson());
		
		se.setState(ServiceEntry.State.OFFER_ACCEPTED);
		se.setStatus("Offer Accepted");
		se.setOffer(offer);
		embeddedModel.setValueAt("Offer Accepted", offerRow, 1);
		
		// Use the network module to notify the seller
		NetworkMessage msg = new AcceptOfferNetworkMessage(service, offer);
		msg.setDestinationPerson(seller);
		networkCommunicator.sendMessage(msg);
		
		return true;
	}

}
