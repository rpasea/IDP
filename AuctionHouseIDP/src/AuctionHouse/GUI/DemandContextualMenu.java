package AuctionHouse.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import AuctionHouse.Messages.AcceptOfferMessage;
import AuctionHouse.Messages.Message;
import AuctionHouse.Messages.RejectOfferMessage;

public class DemandContextualMenu implements MenuState {
	private MainController mediator;
	private String service;
	private String supplier;
	private String offer;
	private JPopupMenu popup;

	public DemandContextualMenu(MainController mediator, int x, int y,
			String service, String supplier, String offer) {
		this.mediator = mediator;
		this.service = service;
		this.supplier = supplier;
		this.offer = offer;
		
		popup = new JPopupMenu();
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchOfferHandler(e);
			}
		};
		
		JMenuItem item;
		popup.setBounds(x, y, 75, 50);
		popup.add( item = new JMenuItem("Accept offer"));
		item.addActionListener(menuListener);
		popup.add( item = new JMenuItem("Reject offer"));
		
		menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dropOfferHandler(e);
			}
		};
		item.addActionListener(menuListener);
		popup.show(mediator.getTable(), x, y);
	}
	
	private void launchOfferHandler(ActionEvent event) {
		Message mess = new AcceptOfferMessage ( service,supplier,offer);
		mediator.sendMessage(mess);
	}
	
	private void dropOfferHandler(ActionEvent event) {
		Message mess = new RejectOfferMessage ( service,supplier,offer);
		mediator.sendMessage(mess);
	}

	@Override
	public void killMenu() {
		popup.setVisible(false);
		popup = null;
	}
}
