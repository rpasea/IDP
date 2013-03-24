package Commands;

import java.util.LinkedList;

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
		String[] columnNames  = { "Name" , "People" , "Status" , "Progress" };
        String[] people = {"Bibi, Sibi, Cici" };
        LinkedList<String> list = new LinkedList<String> ();
        for(String s : people) 
                list.add(s);
        Object[][] data = {
                    {"BaniGratis", list,
                     "Active", null}
        };
        AHTableModel tableModel = new AHTableModel(data, columnNames);
        
        // Instantiates a new MainWindow to be displayed
        mediator.switchToMain(tableModel);
        
        System.out.println("LOGED IN: " + user + " / " + password + " (role: " + role + ")");
        return true;
	}

}
