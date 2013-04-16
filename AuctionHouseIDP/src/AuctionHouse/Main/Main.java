/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AuctionHouse.Main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import AuctionHouse.DataContext.DataManager;
import AuctionHouse.DataContext.XMLDataManager;
import AuctionHouse.Mediator.Mediator;


public class Main {
	static int simnr;
	
    public static void main(String args[]) {
    	// Log4j
    	PropertyConfigurator.configure("log4j.properties");
    	final Logger logger = Logger.getLogger("generic");
    	
    	
    	// FIXME: Doar pentru folosirea targeturilor din build.xml
    	simnr = Integer.parseInt(args[0]);
    	
        /* Set the Nimbus look and feel */
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
        	logger.fatal(ex.getMessage());
        } catch (InstantiationException ex) {
        	logger.fatal(ex.getMessage());
        } catch (IllegalAccessException ex) {
        	logger.fatal(ex.getMessage());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
        	logger.fatal(ex.getMessage());
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	int port = 50000 + simnr;
            	
            	logger.info("HostPort: " + port);
            	
            	DataManager dm = new XMLDataManager("Database.xml");
                // Create a new Mediator for all the components
            	// FIXME: Remove `simnr` argument then resolve all the chain (exists just for test)
                Mediator med = new Mediator(dm, "127.0.0.1", port,   simnr);
                
                med.init();
            }
        });
    }
}
