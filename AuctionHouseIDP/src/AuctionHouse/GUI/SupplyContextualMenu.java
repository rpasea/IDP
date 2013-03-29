package AuctionHouse.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import AuctionHouse.Messages.DropOfferMessage;
import AuctionHouse.Messages.MakeOfferMessage;
import AuctionHouse.Messages.Message;

public class SupplyContextualMenu implements MenuState {
	private MainController mediator;
	private String service;
	private String supplier;
	private JPopupMenu popup;

	public SupplyContextualMenu(MainController mediator, int x, int y,
			String service, String supplier) {
		this.mediator = mediator;
		this.service = service;
		this.supplier = supplier;
		
		popup = new JPopupMenu();
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchOfferHandler(e);
			}
		};
		
		JMenuItem item;
		popup.setBounds(x, y, 75, 50);
		popup.add( item = new JMenuItem("Make offer"));
		item.addActionListener(menuListener);
		popup.add( item = new JMenuItem("Drop auction"));
		
		menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dropOfferHandler(e);
			}
		};
		item.addActionListener(menuListener);
		popup.show(mediator.getTable(), x, y);
	}
	
	private void launchOfferHandler(ActionEvent event) {
		Message mess = new MakeOfferMessage(service,supplier);
		mediator.sendMessage(mess);
	}
	
	private void dropOfferHandler(ActionEvent event) {
		Message mess = new DropOfferMessage ( service,supplier);
		mediator.sendMessage(mess);
	}

	@Override
	public void killMenu() {
		popup.setVisible(false);
		popup = null;
	}
}
