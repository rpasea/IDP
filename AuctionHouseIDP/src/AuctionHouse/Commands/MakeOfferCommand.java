package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.NetworkMediator;
import AuctionHouse.Network.NetworkCommMediator;

public class MakeOfferCommand implements Command {
	private NetworkCommMediator networkCommMediator;
	private String service;
	private String buyer;
	private String offer;
	private ControllerMediator controllerMediator;
	private NetworkMediator mediator;
	
	public MakeOfferCommand(String service, String buyer, String offer,
			ControllerMediator controllerMediator, NetworkCommMediator networkCommMediator,
			NetworkMediator networkMediator) {
		this.service = service;
		this.buyer = buyer;
		this.offer = offer;
		this.controllerMediator = controllerMediator;
		this.networkCommMediator = networkCommMediator;
		this.mediator = networkMediator;
	}
	
	@Override
	public Object run() {
		AHTableModel model = controllerMediator.getModel();

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

		ServiceEntry se = s.getEntry(buyer);
		if (se == null)
			return false;
		if (!se.getStatus().equals("No Offer") && !se.getStatus().equals("Offer Exceed"))
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
		 * Use the network module to send the offer
		 */
		networkCommMediator.sendMakeOffer(service, buyer, offer);
		
		return true;
	}

}
