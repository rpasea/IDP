package AuctionHouse.Commands;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableCellRenderer;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Network.NetworkCommMediator;

public class LaunchAuctionCommand implements Command{

	private NetworkCommMediator networkCommMediator;
	private String service;
	private ControllerMediator mediator;

	public LaunchAuctionCommand(String service, ControllerMediator mediator,
			NetworkCommMediator networkCommMediator) {
		this.service = service;
		this.mediator = mediator;
		this.networkCommMediator = networkCommMediator;
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
		
		Service s = networkCommMediator.getService(service);
		if (s == null)
			return false;
		if (s.getStatus().equals("Active") || row.get(1).equals("Active"))
			return false;
		
		Vector<Object> embeddedCNames = new Vector<Object>();
		embeddedCNames.add("Name");
		embeddedCNames.add("Status");
		embeddedCNames.add("Offer");
		embeddedCNames.add("Progress");
		
		Vector<Object> innerTable = new Vector<Object>();
		for (ServiceEntry entry : s.getEntries()) {
			Vector<Object> innerTableEntry = new Vector<Object>();
			innerTableEntry.add(entry.getPerson());
			innerTableEntry.add(entry.getStatus());
			innerTableEntry.add(entry.getOffer());
			// TODO: progress bar goes here
			innerTableEntry.add("");
			innerTable.add(innerTableEntry);
		}
		AHTableModel embeddedTableModel = new AHTableModel(innerTable,
				embeddedCNames);
		JTable embedded = new JTable(embeddedTableModel);
		embedded.setName("EmbeddedTable");
		TableColumnModel tcm = embedded.getColumnModel();
		TableCellRenderer tcr = new AHTableCellRenderer();
		for (int it = 0; it < tcm.getColumnCount(); it++) {
			tcm.getColumn(it).setCellRenderer(tcr);
		}
		embeddedTableModel
				.addTableModelListener(new TableModelListener() {
					@Override
					public void tableChanged(TableModelEvent arg0) {
						model.fireTableDataChanged();
						mediator.refreshGUI();

					}
				});
		
		model.setValueAt("Active", rowNr, 1);
		model.setValueAt(embedded, rowNr, 2);
		s.setStatus("Active");
		mediator.refreshGUI();
		return true;
		
	}
}
