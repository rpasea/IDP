package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.DataContext.DataManager;

public class RejectOfferCommand implements Command {
	private DataManager dataManager;
	private String service;
	private String seller;
	private ControllerMediator mediator;

	public RejectOfferCommand(String service, String seller,
			ControllerMediator mediator, DataManager dataManager) {
		this.service = service;
		this.seller = seller;
		this.mediator = mediator;
		this.dataManager = dataManager;
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
		 * TODO: Do something to attach the transaction to the service entry
		 * 		create the progress bar, propagate it's change events all the 
		 *      way  to the primary table
		 */
		return true;
	}
}
