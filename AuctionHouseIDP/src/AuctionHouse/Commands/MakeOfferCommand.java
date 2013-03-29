package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;

public class MakeOfferCommand implements Command {
	private DataManager dataManager;

	// TODO: the network module goes here
	private Object networkCommunicator;
	private String service;
	private String buyer;
	private String offer;
	private ControllerMediator mediator;
	
	public MakeOfferCommand(String service, String buyer, String offer,
			ControllerMediator mediator, DataManager dataManager,
			Object networkCommunicator) {
		this.service = service;
		this.buyer = buyer;
		this.offer = offer;
		this.mediator = mediator;
		this.dataManager = dataManager;
		this.networkCommunicator = networkCommunicator;
	}
	
	@Override
	public Object run() {
		AHTableModel model = mediator.getModel();

		Vector<Vector<Object>> data = model.getDataVector();
		Vector<Object> row = null;
		int rowNr = 0;

		for (Vector<Object> r : data) {
			if (r.get(0).equals(service)) {
				row = r;
				break;
			}
			rowNr++;
		}

		if (row == null)
			return false;

		Service s = dataManager.getService(service);
		if (s == null)
			return false;

		ServiceEntry se = s.getEntry(buyer);
		if (se == null)
			return false;

		JTable embedded = (JTable) model.getValueAt(rowNr, 2);
		AHTableModel embeddedModel = (AHTableModel) embedded.getModel();
		
		int offerRow = 0;
		for (int i = 0 ; i < embeddedModel.getRowCount(); i++) {
			String name = (String)embeddedModel.getValueAt(i, 0);
			if (name.equals(buyer))
				break;
			offerRow++;
		}
		
		if (offerRow == embeddedModel.getRowCount())
			return false;
		
		se.setStatus("Offer Made");
		embeddedModel.setValueAt("Offer Made", offerRow, 1);
		embeddedModel.setValueAt(offer, offerRow, 2);
		/*
		 * TODO: use the network module to send the offer
		 */
		
		return true;
	}

}
