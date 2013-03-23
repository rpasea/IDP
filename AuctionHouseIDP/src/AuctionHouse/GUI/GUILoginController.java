/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AuctionHouse.GUI;

import AuctionHouse.Mediator.GUIMediator;
import java.util.LinkedList;

/**
 *
 * @author iulius
 */
public class GUILoginController {
    private GUILoginView view;
    private GUIMediator mediator;
    
    public GUILoginController(GUILoginView associatedView, GUIMediator med){
        view = associatedView;
        mediator = med;
    }
    
    public boolean login(String user, String password, int role){
        if(!mediator.isLoginValid(user, password, role))
            return false;
        
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
        GUIMainView guiMainWindow = new GUIMainView(tableModel, mediator, this);
        
        // Switch to MainView window
        view.setVisible(false);
        guiMainWindow.setVisible(true);
        
        System.out.println("LOGED IN: " + user + " / " + password + " (role: " + role + ")");
        
        return true;
    }
    
    public void showLoginWin(){
        view.setVisible(true);
    }
}
