package AuctionHouse.Commands;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JProgressBar;
import javax.swing.JTable;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.Mediator;
import AuctionHouse.Mediator.Transaction;
import AuctionHouse.DataContext.DataManager;

public class StartTransactionCommand implements Command {

	private String service, seller, buyer, offer;
	private ControllerMediator controllerMediator;
	private DataManager dataManager;

	public StartTransactionCommand(String service, String seller, String buyer,
			String offer, ControllerMediator controllerMediator,
			DataManager dataManager) {
		this.service = service;
		this.seller = seller;
		this.buyer = buyer;
		this.offer = offer;
		this.controllerMediator = controllerMediator;
		this.dataManager = dataManager;
	}

	@Override
	public Object run() {
		AHTableModel model = controllerMediator.getModel();
		Service s = dataManager.getService(service);
		ServiceEntry se;
		
		if (dataManager.getRole() == Mediator.ROL_CUMPARATOR) {
			se = s.getEntry(seller);
		} else {
			se = s.getEntry(buyer);
		}
		if (se.getState() != ServiceEntry.State.OFFER_ACCEPTED)
			return null;

		final AHTableModel embeddedModel = model.getInnerTableModel(se.getService().getName());
		final int offerRow = embeddedModel.getInnerPersonRowNr(se.getPerson());
		
		final JProgressBar progressBar = new JProgressBar(0, Transaction.MaxProgress);
		progressBar.setSize(50, 8);
		
		embeddedModel.setValueAt(progressBar, offerRow, 3);
		progressBar.setVisible(true);
		
		Transaction transaction = new Transaction (service, seller, buyer, offer);
		transaction.addObserver(new Observer() {
			
			@Override
			public void update(Observable arg0, Object arg1) {
				Transaction t = (Transaction) arg0;
				if ( t.getProgress() >= 0) {
					progressBar.setValue(t.getProgress());
					embeddedModel.setValueAt(progressBar, offerRow, 3);
					//embeddedModel.fireTableDataChanged();
				} else {
					embeddedModel.setValueAt("", offerRow, 3);
					progressBar.setVisible(false);
				}
				
				embeddedModel.setValueAt(t.getState(), offerRow, 1);
			}
		});
		
		return transaction;
		
	}

}
