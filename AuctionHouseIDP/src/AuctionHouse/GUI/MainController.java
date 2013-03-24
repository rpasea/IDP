package AuctionHouse.GUI;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import Messages.LogoutMessage;

public class MainController {
	private MainView view;
	private AHTableModel tableModel;
	private ControllerMediator mediator;
	private LoginController loginController;

	public MainController(MainView view, AHTableModel model,
			ControllerMediator med, LoginController loginController) {
		this.view = view;
		tableModel = model;
		mediator = med;
		this.loginController = loginController;
	}

	public void logout() {
		LogoutMessage msg = new LogoutMessage();
		mediator.sendMessage(msg);
	}

	public void setVisibility(boolean value) {
		view.setVisible(value);
	}

	public void tableClicked(java.awt.event.MouseEvent evt) {

		if (evt.getButton() == MouseEvent.BUTTON3) {
			JTable table = view.getTable();
			int row = table.rowAtPoint(evt.getPoint());
			int col = table.columnAtPoint(evt.getPoint());
			if (row >= 0 && col >= 0) {
				if (col == table.getColumnCount() - 1) {
					TableCellRenderer tableCellRenderer = table
							.getCellRenderer(row, col);
					Component c = table.prepareRenderer(tableCellRenderer, row,
							col);
					JTable innerTable = (JTable) c;
					if (innerTable != null) {
						Point pnt = evt.getPoint();
						Rectangle cellRect = table.getCellRect(row, col, false);
						pnt.translate(-cellRect.x, -cellRect.y);
						int rowIndex = innerTable.rowAtPoint(pnt);
						int colIndex = innerTable.columnAtPoint(pnt);
						System.out.println("INNERTABLE:" + rowIndex + "*"
								+ colIndex);
					}
				} else if (col == 1) {

				} else {

				}
			}
		} else {
			
		}

		System.out.println("Cliiiiiick!");
	}
}
