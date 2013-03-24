package Commands;

import java.awt.Dimension;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import AuctionHouse.GUI.AHTableCellRenderer;
import AuctionHouse.GUI.AHTableModel;
import AuctionHouse.GUI.ControllerMediator;
import AuctionHouse.GUI.MainView;
import AuctionHouse.Mediator.Mediator;

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
		
        String[] people = {"Bibi, Sibi, Cici" };
        Object[][] embeddedData = {
        		{ "Bibi", "Active", ""},
        		{ "Sibi", "Active", ""},
        		{ "Cici", "Active", ""}
        };
        
        AHTableModel embeddedTableModel = new AHTableModel(embeddedData, embeddedCNames);
        JTable embedded = new JTable(embeddedTableModel);
        embedded.setName("EmbeddedTable");
        TableColumnModel tcm = embedded.getColumnModel();
        TableCellRenderer tcr = new AHTableCellRenderer();
        for(int it = 0; it < tcm.getColumnCount(); it++){
            tcm.getColumn(it).setCellRenderer(tcr);
        }
        //JScrollPane panel = new JScrollPane();
        //panel.setViewportView(embedded);
        //panel.setPreferredSize(new Dimension(embedded.getPreferredSize().width,embedded.getPreferredSize().height + 30));
        
        
        Object[][] data = {
                    {"BaniGratis", "Active", embedded }
        };
        AHTableModel tableModel = new AHTableModel(data, columnNames);
        
        // Instantiates a new MainWindow to be displayed
        mediator.switchToMain(tableModel);
        
        System.out.println("LOGED IN: " + user + " / " + password + " (role: " + role + ")");
        return true;
	}

}
