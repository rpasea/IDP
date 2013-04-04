package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Network.NetworkCommMediator;

public class DropOfferCommand implements Command {
	private NetworkCommMediator networkCommMediator;
	private String service;
	private String buyer;
	private ControllerMediator mediator;
	
	public DropOfferCommand(String service, String buyer,
			ControllerMediator mediator,
			NetworkCommMediator networkCommMediator) {
		this.service = service;
		this.buyer = buyer;
		this.mediator = mediator;
		this.networkCommMediator = networkCommMediator;
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

		Service s = networkCommMediator.getService(service);
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
		embeddedModel.setValueAt("No Offer", offerRow, 1);
		embeddedModel.setValueAt("", offerRow, 2);
		
		/*
		 * TODO: use the network module to notify the buyer
		 */
		
		return true;
	}

}
