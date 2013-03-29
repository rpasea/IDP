package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableCellRenderer;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;

public class DropAuctionCommand implements Command{

	private DataManager dataManager;

	// TODO: the network module goes here
	private Object networkCommunicator;
	private String service;
	private ControllerMediator mediator;

	public DropAuctionCommand(String service, ControllerMediator mediator,
			DataManager dataManager, Object networkCommunicator) {
		this.service = service;
		this.mediator = mediator;
		this.dataManager = dataManager;
		this.networkCommunicator = networkCommunicator;
	}

	@Override
	public Object run() {
		final AHTableModel model = mediator.getModel();
		
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
		
		s.setStatus("Inactive");
		model.setValueAt("Inactive", rowNr, 1);
		model.setValueAt("", rowNr, 2);
		mediator.refreshGUI();
		return true;
		
	}
}