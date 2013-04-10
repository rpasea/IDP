package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.NetworkMediator;
import AuctionHouse.Messages.Message;
import AuctionHouse.Messages.StartTransactionMessage;
import AuctionHouse.DataContext.DataManager;

public class AcceptOfferCommand implements Command {
	private String service;
	private String seller;
	private String offer;
	private ControllerMediator controllerMediator;
	private NetworkMediator networkMediator;
	private DataManager dataManager;

	public AcceptOfferCommand(String service, String seller, String offer,
			ControllerMediator mediator, DataManager dataManager,
			NetworkMediator networkMediator) {
		this.service = service;
		this.seller = seller;
		this.offer = offer;
		this.controllerMediator = mediator;
		this.networkMediator = networkMediator;
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
		Message msg = new StartTransactionMessage(service,seller, dataManager.getIdentity().getName(),offer);
		networkMediator.sendNetworkMessage(msg);
		return true;
	}

}
