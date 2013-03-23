package AuctionHouse.GUI;

public class GUIController {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] columnNames  = { "Name" , "People" , "Status" , "Progress" };
		GUIView.startGUI(new GUIView(new AHTableModel( columnNames, 0)));
	}

}
