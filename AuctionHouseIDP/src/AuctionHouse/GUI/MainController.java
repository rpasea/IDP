package AuctionHouse.GUI;

import AuctionHouse.Mediator.GUIMediator;


public class MainController {
    private MainView view;
    private AHTableModel tableModel;
    private GUIMediator mediator;
    private LoginController loginController;
    
    public MainController(MainView view, AHTableModel model,
            GUIMediator med, LoginController loginController){
        this.view = view;
        tableModel = model;
        mediator = med;
        this.loginController = loginController;
    }
    
    public void logout(){
        // Just switch to LoginView window
        // I think...
        view.setVisible(false);
        loginController.showLoginWin();
    }
}
