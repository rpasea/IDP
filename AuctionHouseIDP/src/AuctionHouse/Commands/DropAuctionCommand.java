package AuctionHouse.Commands;

import org.apache.log4j.Logger;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;

public class DropAuctionCommand implements Command{
	
	final Logger logger = Logger.getLogger("generic.mediator.auction");
	

	private DataManager dataManager;
	private String service;
	private ControllerMediator mediator;

	public DropAuctionCommand(String service, ControllerMediator mediator,
			DataManager dataManager) {
		this.service = service;
		this.mediator = mediator;
		this.dataManager = dataManager;
	}

	@Override
	public Object run() {
		final AHTableModel model = mediator.getModel();
		int rowNr = model.getRowNr(service);
		Service s = dataManager.getService(service);
		
		s.setActive(false);
		s.setStatus("Inactive");
		model.setValueAt("Inactive", rowNr, 1);
		model.setValueAt("", rowNr, 2);
		mediator.refreshGUI();
		return true;
		
	}
}