package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;


public class DAO {

	protected Connection conexao;
	
	public static Connection conector(){
		
		java.sql.Connection conexao = null;
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/agrestina";
		String usu = "root";
		String pass = "1996testE";
		
		try {
			Class.forName(driver);
			conexao = DriverManager.getConnection(url, usu, pass);
			return conexao;
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
		
	}

}
