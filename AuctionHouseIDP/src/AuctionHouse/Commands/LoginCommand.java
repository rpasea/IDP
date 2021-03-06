package AuctionHouse.Commands;

import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.Service;
import AuctionHouse.DataContext.ServiceEntry;
import AuctionHouse.GUI.AHTableCellRenderer;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.Mediator.Mediator;
import AuctionHouse.NetworkMessages.NetworkMessage;

public class LoginCommand implements Command {
	
	final Logger logger = Logger.getLogger("generic.mediator.login");

	private String user, password;
	private int role;
	private ControllerMediator mediator;
	private DataManager dataManager;

	public LoginCommand(String user, String password, int role,
			DataManager dataManager, ControllerMediator med) {
		this.user = user;
		this.password = password;
		this.role = role;
		this.mediator = med;
		this.dataManager = dataManager;
	}

	public Object run() {
		if (!dataManager.isLoginValid(user, password, role))
			return false;

		List<Service> services = dataManager.doLogin(user, password, role);
		if (services == null)
			return false;
		
		// Log4J: switch from 'generic' file to a user specific file
		RollingFileAppender appender = (RollingFileAppender) Logger.getRootLogger().getAppender("Afile");
    	appender.setFile(user + ".log");
    	appender.activateOptions();
		// ---

		Vector<Object> columnNames = new Vector<Object>();
		columnNames.add("Service");
		columnNames.add("Status");
		columnNames.add("Users");
		Vector<Object> embeddedCNames = new Vector<Object>();
		embeddedCNames.add("Name");
		embeddedCNames.add("Status");
		embeddedCNames.add("Offer");
		embeddedCNames.add("Progress");

		final AHTableModel tableModel = new AHTableModel(columnNames, 0);

		for (Service s : services) {
			Vector<Object> outerTableEntry = new Vector<Object>();
			outerTableEntry.add(s.getName());
			if (role == Mediator.ROL_FURNIZOR) {
				outerTableEntry.add("Active");
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
								tableModel.fireTableDataChanged();
								mediator.refreshGUI();
							}
							
						});
				outerTableEntry.add(embedded);
				tableModel.addRow(outerTableEntry);
				s.setStatus("Active");
			} else {
				outerTableEntry.add("Inactive");
				outerTableEntry.add("");
				tableModel.addRow(outerTableEntry);
				s.setStatus("Inactive");
			}

		}

		// Instantiates a new MainWindow to be displayed
		mediator.switchToMain(tableModel);

		logger.info("Logged in as: " + user + " / " + password + " (role: "
				+ role + ")");

		NetworkMessage.CurrentUserName = user;
		return true;
	}
}
