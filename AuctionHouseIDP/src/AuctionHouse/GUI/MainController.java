package AuctionHouse.GUI;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import AuctionHouse.Mediator.Mediator;
import AuctionHouse.Messages.LogoutMessage;
import AuctionHouse.Messages.Message;

public class MainController {
	private MainView view;
	private AHTableModel tableModel;
	private ControllerMediator mediator;
	private MenuStateManager menuManager;

	public MainController(MainView view, AHTableModel model,
			ControllerMediator med) {
		this.view = view;
		tableModel = model;
		mediator = med;

		menuManager = new MenuStateManager(this);
	}

	public void logout() {
		LogoutMessage msg = new LogoutMessage();
		mediator.sendMessage(msg);
	}

	public void setVisibility(boolean value) {
		view.setVisible(value);
	}

	public void tableClicked(java.awt.event.MouseEvent evt) {
		// Check if triggered by Right-click
		if (evt.getButton() == MouseEvent.BUTTON3) {
			JTable table = view.getTable();
			// Get clicked cell
			int row = table.rowAtPoint(evt.getPoint());
			int col = table.columnAtPoint(evt.getPoint());

			if (row >= 0 && col >= 0) {
				if (col == table.getColumnCount() - 1) {
					// The click was inside the sub-table on the last column
					TableCellRenderer tableCellRenderer = table
							.getCellRenderer(row, col);
					Component c = table.prepareRenderer(tableCellRenderer, row,
							col);
					if ( ! (c instanceof JTable)) {
						menuManager.setToNoMenu();
						return;
					}
					JTable innerTable = (JTable) c;
					if (innerTable != null) {
						Point pnt = evt.getPoint();
						Rectangle cellRect = table.getCellRect(row, col, false);
						pnt.translate(-cellRect.x, -cellRect.y);
						// Identify clicked cell of the inner table
						int rowIndex = innerTable.rowAtPoint(pnt);
						int colIndex = innerTable.columnAtPoint(pnt);
						if (mediator.getRole() == Mediator.ROL_CUMPARATOR) {
							// Check if an offer is present
							if (!innerTable.getModel()
										.getValueAt(rowIndex, 1)
										.toString().equals("No Offer")){
								menuManager
									.setToDemandContextualMenu(evt.getX(), evt
											.getY(),
											tableModel.getValueAt(row, 0)
													.toString(),
											innerTable.getModel()
													.getValueAt(rowIndex, 0)
													.toString(),
													innerTable.getModel()
													.getValueAt(rowIndex, 2)
													.toString());
							}
						} else {  // ROL_FURNIZOR
							menuManager
									.setToOfferContextualMenu(evt.getX(), evt
											.getY(),
											tableModel.getValueAt(row, 0)
													.toString(),
											innerTable.getModel()
													.getValueAt(rowIndex, 0)
													.toString(),
													innerTable.getModel()
													.getValueAt(rowIndex, 2)
													.toString());
						}
					}
				} else if (col == 0) {
					// The click was on Service column
					if (mediator.getRole() == Mediator.ROL_CUMPARATOR)
						menuManager.setToDemandServiceMenu(evt.getX(), evt
								.getY(), tableModel.getValueAt(row, col)
								.toString());
					else
						menuManager.setToNoMenu();
				} else {
					menuManager.setToNoMenu();
				}
			}
		} else {
			menuManager.setToNoMenu();
		}
	}

	public void refresh() {
		view.resizeTable();
	}

	public JTable getTable() {
		return view.getTable();
	}

	public Object sendMessage(Message m) {
		return mediator.sendMessage(m);
	}
	
	public AHTableModel getModel() {
		return tableModel;
	}

}
