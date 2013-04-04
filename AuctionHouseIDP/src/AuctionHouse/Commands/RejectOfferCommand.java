package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Network.NetworkCommMediator;

public class RejectOfferCommand implements Command {
	private NetworkCommMediator networkCommMediator;
	private String service;
	private String seller;
	private ControllerMediator mediator;

	public RejectOfferCommand(String service, String seller,
			ControllerMediator mediator, NetworkCommMediator networkCommMediator) {
		this.service = service;
		this.seller = seller;
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
		if (s.getStatus().equals("Inactive") || row.get(1).equals("Inactive"))
			return false;

		ServiceEntry se = s.getEntry(seller);
		if (se == null)
			return false;
		if (!se.getStatus().equals("Offer Made"))
			return false;
		JTable embedded = (JTable) model.getValueAt(rowNr, 2);
		AHTableModel embeddedModel = (AHTableModel) embedded.getModel();
		
		int offerRow = 0;
		for (int i = 0 ; i < embeddedModel.getRowCount(); i++) {
			String name = (String)embeddedModel.getValueAt(i, 0);
			if (name.equals(seller))
				break;
			offerRow++;
		}
		
		if (offerRow == embeddedModel.getRowCount())
			return false;
		
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
