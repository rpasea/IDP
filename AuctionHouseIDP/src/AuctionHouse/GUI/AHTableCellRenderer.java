package AuctionHouse.GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class AHTableCellRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		if (value == null)
			return null;
		if (value.getClass().getName().equals("javax.swing.JTable")) {

			((Component) value).setBackground(Color.white);
			((JTable)value).setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));

			return (Component) value;
		}else if (value.getClass().getName().equals("javax.swing.JProgressBar")) {
			//((Component) value).setBackground(Color.white);

			return (Component) value;
		} else {
			Component c = table.getDefaultRenderer(value.getClass())
					.getTableCellRendererComponent(table, value, isSelected,
							hasFocus, row, column);

			c.setBackground(Color.white);
			((JLabel)c).setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
			return c;
		}

	}

}
