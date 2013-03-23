/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AuctionHouse.GUI;

import java.util.LinkedList;

/**
 *
 * @author iulius
 */
public class GUILoginController {
    public boolean login(String user, String password, int role){
        String[] columnNames  = { "Name" , "People" , "Status" , "Progress" };
        String[] people = {"Bibi, Sibi, Cici" };
        LinkedList<String> list = new LinkedList<String> ();
        for(String s : people) 
                list.add(s);
        Object[][] data = {
                    {"BaniGratis", list,
                     "Active", null}
        };
        AHTableModel mockModel = new AHTableModel(data, columnNames);
        GUIView gwindow = new GUIView(mockModel);
        gwindow.setVisible(true);
        
        System.out.println("LOGED IN: " + user + " / " + password + " (role: " + role + ")");
        
        return true;
    }
}
