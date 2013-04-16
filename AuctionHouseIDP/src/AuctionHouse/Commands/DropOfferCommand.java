package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import org.apache.log4j.Logger;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.DataContext.DataManager;

public class DropOfferCommand implements Command {
	
	final Logger logger = Logger.getLogger("generic.mediator.dropoffer");
	
	private DataManager dataManager;
	private String service;
	private String buyer;
	private ControllerMediator mediator;
	
	public DropOfferCommand(String service, String buyer,
			ControllerMediator mediator,
			DataManager dataManager) {
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
		
		se.setState(ServiceEntry.State.NO_OFFER);
		se.setStatus("No Offer");
		embeddedModel.setValueAt("No Offer", offerRow, 1);
		embeddedModel.setValueAt("", offerRow, 2);
		
		/*
		 * TODO: use the network module to notify the buyer
		 */
		
		return true;
	}

}
