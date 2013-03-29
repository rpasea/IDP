package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.NetworkMediator;
import AuctionHouse.Messages.Message;
import AuctionHouse.Messages.StartTransactionMessage;

public class AcceptOfferCommand implements Command {
	private DataManager dataManager;

	// TODO: the network module goes here
	private Object networkCommunicator;
	private String service;
	private String seller;
	private String offer;
	private ControllerMediator mediator;
	private NetworkMediator networkMediator;

	public AcceptOfferCommand(String service, String seller, String offer,
			ControllerMediator mediator, DataManager dataManager,
			Object networkCommunicator, NetworkMediator networkMediator) {
		this.service = service;
		this.seller = seller;
		this.offer = offer;
		this.mediator = mediator;
		this.dataManager = dataManager;
		this.networkCommunicator = networkCommunicator;
		this.networkMediator = networkMediator;
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
		
		se.setStatus("Offer Accepted");
		embeddedModel.setValueAt("Offer Accepted", offerRow, 1);
		
		/*
		 * TODO: use the network module to notify the seller
		 */
		Message msg = new StartTransactionMessage(service,seller, dataManager.getIdentity().getName(),offer);
		networkMediator.sendNetworkMessage(msg);
		return true;
	}

}
