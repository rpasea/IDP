package AuctionHouse.DataContext;

import java.util.Vector;

import javax.swing.JTable;

import AuctionHouse.DataContext.ServiceEntry.State;
import AuctionHouse.GUI.AHTableModel;

public class ServicesObserver {
	
	private final AHTableModel model;

	public ServicesObserver(AHTableModel model){
		this.model = model;
		
	}
	
	public void notify(Service s){
		
	}
	
	public void notify(ServiceEntry se){
		AHTableModel embeddedModel = model.getInnerTableModel(se.getService().getName());
		
		int offerRow = 0;
		for (int i = 0 ; i < embeddedModel.getRowCount(); i++) {
			String name = (String)embeddedModel.getValueAt(i, 0);
			if (name.equals(se.getPerson()))
				break;
			offerRow++;
		}
		
		switch(se.getState()){
		case OFFER_MADE:
			embeddedModel.setValueAt("Offer Made", offerRow, 1);
			embeddedModel.setValueAt(se.getOffer(), offerRow, 2);
			break;
		}
	}
}
