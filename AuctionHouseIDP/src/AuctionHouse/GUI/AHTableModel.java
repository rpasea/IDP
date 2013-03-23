package AuctionHouse.GUI;

import javax.swing.table.DefaultTableModel;

public class AHTableModel extends DefaultTableModel{
	
	public AHTableModel (Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	
	public boolean isCellEditable (int row, int column) {
		return false;
	}

}
