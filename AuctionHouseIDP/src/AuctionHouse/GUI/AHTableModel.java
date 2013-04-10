package AuctionHouse.GUI;

import java.util.Vector;

import javax.swing.JTable;
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
	
	public int getRowNr(String service){
		Vector<Vector<Object>> data = getDataVector();
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
			return -1;
		return rowNr;
	}
	
	public AHTableModel getInnerTableModel(String service){
		return (AHTableModel) ((JTable) getValueAt(getRowNr(service), 2)).getModel();
	}

	public boolean isCellEditable (int row, int column) {
		//if (column == this.getColumnCount() - 1)
		//	return true;
		return false;
	}

}
