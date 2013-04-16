package AuctionHouse.Commands;

import org.apache.log4j.Logger;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;

public class OfferRejectedCommand implements Command {
	
	final Logger logger = Logger.getLogger("generic.mediator.offerrejected");
	
	private DataManager dataManager;
	private String service;
	private String buyer;
	private ControllerMediator mediator;

	public OfferRejectedCommand(String service, String buyer,
			ControllerMediator mediator, DataManager dataManager,
			Object networkCommunicator) {
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

		se.setState(ServiceEntry.State.OFFER_REJECTED);
		se.setStatus("Offer Rejected");
		embeddedModel.setValueAt("Offer Rejected", offerRow, 1);

		return true;
	}
}
