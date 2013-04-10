package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.DataContext.DataManager;
import AuctionHouse.Network.NetworkCommunicator;
import AuctionHouse.NetworkMessages.MakeOfferNetworkMessage;
import AuctionHouse.NetworkMessages.NetworkMessage;

/*
 * Class that incapsulates de Make Offer command from the GUI
 * It is also used to notify the GUI when an offer is received through the network 
 * module
 */
public class MakeOfferCommand implements Command {
	private DataManager dataManager;
	private String service;
	private String buyer;
	private String offer;
	private ControllerMediator controllerMediator;
	private NetworkCommunicator communicator;
	private boolean sendToNetwork;
	
	public MakeOfferCommand(String service, String buyer, String offer,
			ControllerMediator controllerMediator, DataManager dataManager,
			NetworkCommunicator communicator) {
		this.service = service;
		this.buyer = buyer;
		this.offer = offer;
		this.controllerMediator = controllerMediator;
		this.dataManager = dataManager;
		this.communicator = communicator;
		this.sendToNetwork = true;
	}
	
	public MakeOfferCommand(String service, String buyer, String offer,
			ControllerMediator controllerMediator, DataManager dataManager,
			NetworkCommunicator communicator, boolean sendToNetwork) {
		this.service = service;
		this.buyer = buyer;
		this.offer = offer;
		this.controllerMediator = controllerMediator;
		this.dataManager = dataManager;
		this.communicator = communicator;
		this.sendToNetwork = sendToNetwork;
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

		Service s = dataManager.getService(service);
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
		if (sendToNetwork){
			NetworkMessage netMsg = new MakeOfferNetworkMessage(service, buyer, offer);
			netMsg.setDestinationPerson(buyer);
			communicator.sendMessage(netMsg);
		}
		
		return true;
	}

}
