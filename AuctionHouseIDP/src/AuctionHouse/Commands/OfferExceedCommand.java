package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;

public class OfferExceedCommand implements Command {
	private DataManager dataManager;
	private String service;
	private String buyer;
	private ControllerMediator mediator;

	public OfferExceedCommand(String service, String buyer,
			ControllerMediator mediator, DataManager dataManager) {
		this.service = service;
		this.buyer = buyer;
		this.mediator = mediator;
		this.dataManager = dataManager;
	}

	@Override
	public Object run() {
		AHTableModel model = mediator.getModel();
		Service s = dataManager.getService(service);
		ServiceEntry se = s.getEntry(buyer);
		
		if (se.getState() != ServiceEntry.State.OFFER_MADE)
			return false;
		
		AHTableModel embeddedModel = model.getInnerTableModel(se.getService().getName());
		int offerRow = embeddedModel.getInnerPersonRowNr(se.getPerson());
		
		se.setState(ServiceEntry.State.OFFER_EXCEED);
		se.setStatus("Offer Exceed");
		embeddedModel.setValueAt("Offer Exceed", offerRow, 1);
		
		/*
		 * TODO: Do something to attach the transaction to the service entry
		 * 		create the progress bar, propagate it's change events all the 
		 *      way  to the primary table
		 */
		return true;
	}
}
