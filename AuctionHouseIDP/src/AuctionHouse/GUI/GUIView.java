package AuctionHouse.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class GUIView extends JPanel {
	AHTableModel model;

	public GUIView(AHTableModel model) {
		this.model = model;
		init();
	}

	public void init() {
		JPanel top = new JPanel(new GridLayout(1, 0));
		JPanel bottom = new JPanel(new FlowLayout());
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(bottom, BorderLayout.SOUTH);
	}

	public static void startGUI(final GUIView view) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Auction House");
				frame.setContentPane(view); 
				frame.setSize(500, 300); 
				frame.setLocationRelativeTo(null); 
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
				frame.setVisible(true);
			}
		});
	}

}
