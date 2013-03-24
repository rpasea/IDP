package AuctionHouse.GUI;

import AuctionHouse.Mediator.GUIMediator;
import Messages.LoginMessage;
import Messages.LogoutMessage;


public class MainController {
    private MainView view;
    private AHTableModel tableModel;
    private ControllerMediator mediator;
    private LoginController loginController;
    
    public MainController(MainView view, AHTableModel model,
            ControllerMediator med, LoginController loginController){
        this.view = view;
        tableModel = model;
        mediator = med;
        this.loginController = loginController;
    }
    
    public void logout(){
        // Just switch to LoginView window
        // I think...
    	LogoutMessage msg = new LogoutMessage();
        mediator.sendMessage(msg);
    }
    
    public void setVisibility(boolean value){
        view.setVisible(value);
    }
}
