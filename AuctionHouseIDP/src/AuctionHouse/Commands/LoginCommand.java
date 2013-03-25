package AuctionHouse.Commands;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import AuctionHouse.GUI.AHTableCellRenderer;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;

public class LoginCommand implements Command {

	private String user, password;
	private int role;
	private ControllerMediator mediator;
	
	public LoginCommand (String user, String password, int role, ControllerMediator med) {
		this.user = user;
		this.password = password;
		this.role = role;
		this.mediator = med;
	}
	public Object run() {
		String[] columnNames  = { "Service", "Status" , "Users"};
		String[] embeddedCNames = { "Name", "Status" , "Progress" };
		
        Object[][] embeddedData = {
        		{ "Bibi", "Active", ""},
        		{ "Sibi", "Active", ""},
        		{ "Cici", "Active", ""}
        };
        
        final AHTableModel embeddedTableModel = new AHTableModel(embeddedData, embeddedCNames);
        
        JTable embedded = new JTable(embeddedTableModel);
        embedded.setName("EmbeddedTable");
        TableColumnModel tcm = embedded.getColumnModel();
        TableCellRenderer tcr = new AHTableCellRenderer();
        for(int it = 0; it < tcm.getColumnCount(); it++){
            tcm.getColumn(it).setCellRenderer(tcr);
        }
        
        Object[][] data = {
                    {"BaniGratis", "Active", embedded },
                    {"BaniGratis", "Active", embedded }
        };
        final AHTableModel tableModel = new AHTableModel(data, columnNames); 
        embeddedTableModel.addTableModelListener(new TableModelListener() {		
			@Override
			public void tableChanged(TableModelEvent arg0) {
				tableModel.fireTableDataChanged();
				mediator.refreshGUI();
				
			}
		});
        
        // Instantiates a new MainWindow to be displayed
        mediator.switchToMain(tableModel);
        
        System.out.println("LOGED IN: " + user + " / " + password + " (role: " + role + ")");

        return true;
	}

}
