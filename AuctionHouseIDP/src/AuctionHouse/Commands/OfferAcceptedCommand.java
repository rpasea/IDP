package AuctionHouse.Commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import AuctionHouse.Network.AHFileInputStream;
import AuctionHouse.Network.NetworkCommunicator;
import AuctionHouse.NetworkMessages.FileNetworkMessage;
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
			ControllerMediator mediator, DataManager dataManager,
			NetworkCommunicator networkCommunicator) {
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
		
		File f = new File (dataManager.getIdentity().getName() + "/" + service);
		if (!f.exists()) {
			/*
			 * Do something to notify the buyer
			 */
			return false;
		}
		
		if (!f.isFile()) {
			/*
			 * Do something to notify the buyer
			 */
			return false;
		}
		
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
						dataManager.getIdentity().getName(), buyer, offer, (int) f.length());
		transaction.addObserver(new Observer() {
			
			@Override
			public void update(final Observable arg0, Object arg1) {
				 java.awt.EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
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
			});}});
		
		se.setState(ServiceEntry.State.OFFER_ACCEPTED);
		se.setStatus("Offer Accepted");
		embeddedModel.setValueAt("Offer Accepted", offerRow, 1);
		StartTransactionNetworkMessage msg = new StartTransactionNetworkMessage(service,
				dataManager.getIdentity().getName(), buyer, offer,
				(int) f.length());
		msg.setSource(dataManager.getIdentity().getName());
		msg.setDestinationPerson(buyer);
		
		

		// FIXME: O chestie hidoasa care vrea sa faca din lipsa Webserviceului bici
		// --- Incepe magaria ---
		FileOutputStream fop = null;
		File file;
		String source = dataManager.getIdentity().getName();
 
		try {
 
			file = new File("cine.txt");
			fop = new FileOutputStream(file);
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
 
			// get the content in bytes
			byte[] contentInBytes = source.getBytes();
 
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
 
			System.out.println("### Done writing my name to file");
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// --- Se termina magaria ---
		
		
		
		FileNetworkMessage fileMsg = new FileNetworkMessage(f.getName(), (int) f.length());
		fileMsg.setDestinationPerson(buyer);
		
		try {
			AHFileInputStream stream = new AHFileInputStream(f, transaction);
			networkCommunicator.startTransaction(msg, fileMsg, stream, transaction);
		} catch (FileNotFoundException e) {
			// should not happen, already checked for this
			transaction = null;
			e.printStackTrace();
		}
		
		
		return transaction;
	}
}
