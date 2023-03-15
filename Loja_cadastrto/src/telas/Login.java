package telas;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JTextField;

import persistence.DAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Login extends JFrame{

	public JFrame frame;
	private JTextField txtUsuario;
	private JPasswordField pwdSenha;
	
	//static Login window = new Login();
	
	Connection conexao = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
					window.frame.setLocationRelativeTo(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
		conexao = DAO.conector();
		System.out.println(conexao);
	}
	
	public void logar() {
		String sql = "select * from login where nome=? and senha=?";
		
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, txtUsuario.getText());
			pst.setString(2, pwdSenha.getText());
			rs = pst.executeQuery();
			
			if(rs.next()) {
				//this.setVisible(false);
				//this.dispose();
				//dispose();
				Cadastro principal = new Cadastro();
				principal.setVisible(true);
				principal.setResizable(false);
				principal.setLocationRelativeTo(null);
				conexao.close();
				//System.exit(0); //fecha a aplição
			}else {
				JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválido(s)");
			}

		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 361, 246);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(195, 61, 130, 26);
		frame.getContentPane().add(txtUsuario);
		txtUsuario.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(58, 66, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setBounds(58, 116, 61, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		pwdSenha = new JPasswordField();
		pwdSenha.setBounds(195, 111, 130, 26);
		frame.getContentPane().add(pwdSenha);
		
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				logar();
				
			}
		});
		btnOk.setBounds(128, 162, 117, 29);
		frame.getContentPane().add(btnOk);
	}
}
