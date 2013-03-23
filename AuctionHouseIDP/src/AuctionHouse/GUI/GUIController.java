package AuctionHouse.GUI;

import java.util.LinkedList;

public class GUIController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] columnNames  = { "Name" , "People" , "Status" , "Progress" };
		String[] people = {"Bibi, Sibi, Cici" };
		LinkedList<String> list = new LinkedList<String> ();
		for ( String s : people) 
			list.add(s);
		Object[][] data = {
			    {"BaniGratis", list,
			     "Active", null}
		};
		AHTableModel mockModel = new AHTableModel(data, columnNames);
		GUIView.startGUI(new GUIView(mockModel));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mockModel.addRow(data[0]);
		
	}

}
