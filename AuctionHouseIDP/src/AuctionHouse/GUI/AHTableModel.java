package AuctionHouse.GUI;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class AHTableModel extends DefaultTableModel{
	
	public AHTableModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	public AHTableModel (Object[] columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	public AHTableModel (Vector<Object> columnNames, int rowCount) {
		super(columnNames, rowCount);
	}
	
	public AHTableModel(Vector<Object> data, Vector<Object> columnNames) {
		super(data, columnNames);
	}

	public boolean isCellEditable (int row, int column) {
		//if (column == this.getColumnCount() - 1)
		//	return true;
		return false;
	}

}
