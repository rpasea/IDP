package AuctionHouse.Commands;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JProgressBar;
import javax.swing.JTable;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.Transaction;
import AuctionHouse.Network.NetworkCommunicator;
import AuctionHouse.NetworkMessages.NetworkMessage;
import AuctionHouse.NetworkMessages.StartTransactionNetworkMessage;

public class OfferAcceptedCommand implements Command {
	private DataManager dataManager;
	private String service;
	private String buyer;
	private final String offer;
	private ControllerMediator mediator;
	private NetworkCommunicator networkCommunicator;


	public OfferAcceptedCommand(String service, String buyer, String offer,
			ControllerMediator mediator, DataManager dataManager, NetworkCommunicator networkCommunicator) {
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
		Service s = dataManager.getService(service);
		ServiceEntry se = s.getEntry(buyer);
		
		if (se.getState() != ServiceEntry.State.OFFER_MADE)
			return false;
		
		final AHTableModel embeddedModel = model.getInnerTableModel(se.getService().getName());
		int offerRow = embeddedModel.getInnerPersonRowNr(se.getPerson());
		
		final JProgressBar progressBar = new JProgressBar(0, Transaction.MaxProgress);
		progressBar.setSize(50, 8);
		
		final int embRowNr = offerRow;
		embeddedModel.setValueAt(progressBar, embRowNr, 3);
		progressBar.setVisible(true);
		
		Transaction transaction = new Transaction (service,
						dataManager.getIdentity().getName(), buyer, offer);
		transaction.addObserver(new Observer() {
			
			@Override
			public void update(Observable arg0, Object arg1) {
				Transaction t = (Transaction) arg0;
				if ( t.getProgress() >= 0) {
					progressBar.setValue(t.getProgress());
					embeddedModel.setValueAt(progressBar, embRowNr, 3);
					//embeddedModel.fireTableDataChanged();
				} else {
					embeddedModel.setValueAt("", embRowNr, 3);
					progressBar.setVisible(false);
				}
				
				embeddedModel.setValueAt(t.getState(), embRowNr, 1);
			}
		});
		
		se.setState(ServiceEntry.State.OFFER_ACCEPTED);
		se.setStatus("Offer Accepted");
		embeddedModel.setValueAt("Offer Accepted", offerRow, 1);
		NetworkMessage msg = new StartTransactionNetworkMessage(service,dataManager.getIdentity().getName(), buyer, offer);
		msg.setSource(dataManager.getIdentity().getName());
		
		networkCommunicator.sendMessage(msg);
		
		return transaction;
	}
}
