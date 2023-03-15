
import java.awt.EventQueue;

import javax.swing.JFrame;

import not_used.Connection;
import persistence.DAO;
import telas.Login;

public class Principal extends JFrame{

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
