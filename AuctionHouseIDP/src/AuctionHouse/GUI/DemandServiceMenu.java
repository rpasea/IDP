package AuctionHouse.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import AuctionHouse.Messages.DropAuctionMessage;
import AuctionHouse.Messages.LaunchAuctionMessage;
import AuctionHouse.Messages.Message;

public class DemandServiceMenu implements MenuState {
	private MainController mediator;
	private String service;
	private JPopupMenu popup;

	public DemandServiceMenu(MainController mediator, int x, int y,
			String service) {
		this.mediator = mediator;
		this.service = service;
		
		popup = new JPopupMenu();
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchOfferHandler(e);
			}
		};
		
		JMenuItem item;
		popup.setBounds(x, y, 75, 50);
		popup.add( item = new JMenuItem("Launch Auction Request"));
		item.addActionListener(menuListener);
		popup.add( item = new JMenuItem("Drop Auction Request"));
		
		menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dropAuctionHandler(e);
			}
		};
		item.addActionListener(menuListener);
		popup.show(mediator.getTable(), x, y);
	}
	
	private void launchOfferHandler(ActionEvent event) {
		Message mess = new LaunchAuctionMessage ( service);
		mediator.sendMessage(mess);
	}
	
	private void dropAuctionHandler(ActionEvent event) {
		Message mess = new DropAuctionMessage(service);
		mediator.sendMessage(mess);
	}

	@Override
	public void killMenu() {
		popup.setVisible(false);
		popup = null;
	}
}
