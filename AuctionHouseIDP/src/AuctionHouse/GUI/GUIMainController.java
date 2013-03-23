package AuctionHouse.GUI;

import AuctionHouse.Mediator.GUIMediator;


public class GUIMainController {
    private GUIMainView view;
    private AHTableModel tableModel;
    private GUIMediator mediator;
    private GUILoginController loginController;
    
    public GUIMainController(GUIMainView view, AHTableModel model,
            GUIMediator med, GUILoginController loginController){
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
