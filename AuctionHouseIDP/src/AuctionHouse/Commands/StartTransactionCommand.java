package AuctionHouse.Commands;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.Mediator;
import AuctionHouse.Mediator.Transaction;

public class StartTransactionCommand implements Command {

	private String service, seller, buyer, offer;
	private ControllerMediator mediator;
	private DataManager dataManager;
	// TODO: the network module goes here
	private Object networkCommunicator;

	public StartTransactionCommand(String service, String seller, String buyer,
			String offer, ControllerMediator mediator, DataManager dataManager,
			Object networkCommunicator) {
		this.service = service;
		this.seller = seller;
		this.buyer = buyer;
		this.offer = offer;
		this.mediator = mediator;
		this.dataManager = dataManager;
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
			return null;

		Service s = dataManager.getService(service);
		ServiceEntry se = null;
		if (s == null)
			return null;
		
		if (dataManager.getRole() == Mediator.ROL_CUMPARATOR) {
			if (s.getStatus().equals("Inactive")
					|| row.get(1).equals("Inactive"))
				return null;

			se = s.getEntry(seller);
			if (se == null)
				return null;
			if (!se.getStatus().equals("Offer Accepted"))
				return null;
		} else {
			se = s.getEntry(buyer);
			if (se == null)
				return null;
			if (!se.getStatus().equals("Offer Accepted"))
				
				return null;
		}
		
		JTable embedded = (JTable) model.getValueAt(rowNr, 2);
		final AHTableModel embeddedModel = (AHTableModel) embedded.getModel();
		
		int embeddedRow = 0;
		for (int i = 0 ; i < embeddedModel.getRowCount(); i++) {
			String name = (String)embeddedModel.getValueAt(i, 0);
			if (name.equals(se.getPerson()))
				break;
			embeddedRow++;
		}
		
		if (embeddedRow == embeddedModel.getRowCount())
			return null;
		
		final JProgressBar progressBar = new JProgressBar(0, Transaction.MaxProgress);
		progressBar.setSize(50, 8);
		
		final int embRowNr = embeddedRow;
		embeddedModel.setValueAt(progressBar, embRowNr, 3);
		progressBar.setVisible(true);
		
		Transaction transaction = new Transaction (service, seller, buyer, offer);
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
		
		return transaction;
		
	}

}
