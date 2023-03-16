package telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import persistence.DAO;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.awt.event.InputEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cadastro extends JFrame {

	private JPanel contentPane;
	private JTextField nome;
	private JTextField email;
	private JTextField endereco;
	private JTextField produto;
	private JTextField preco;
	private JTextField descricao;
	private JTextField codigo;
	private JComboBox unidade;
	private JFormattedTextField cpf;
	private JFormattedTextField celular;
	private MaskFormatter mascaraCpf;
	private MaskFormatter mascaraCelular;
	private JTable tblProdutos;
	private JTable tblClientes;
	private JTextField txtFoto;
	JLabel lblFoto = new JLabel("Foto");
	
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
					Cadastro frame = new Cadastro();
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public Cadastro() {
		conexao = DAO.conector();
		try {
			MaskFormatter mascaraCpf = new MaskFormatter ("###.###.###-##");
			MaskFormatter mascaraCelular = new MaskFormatter ("(##) #####-####");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 521);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Ajuda");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Clientes");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Produtos");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Sistema");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Sobre o Sistema");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
		JSeparator separator = new JSeparator();
		mnNewMenu_1.add(separator);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Sair");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);			}
		});
		mntmNewMenuItem_2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK));
		mnNewMenu_1.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 638, 459);
		contentPane.add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Produtos", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblProduto = new JLabel("Produto");
		lblProduto.setBounds(6, 11, 61, 16);
		panel_1.add(lblProduto);
		
		//Evento para preencher a tabela digitando em tempo real
		produto = new JTextField();
		produto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarProduto();
			}
		});
		
		produto.setColumns(10);
		produto.setBounds(103, 6, 292, 26);
		panel_1.add(produto);
		
		JLabel lblQuantidade = new JLabel("Código");
		lblQuantidade.setBounds(6, 52, 95, 16);
		panel_1.add(lblQuantidade);
		
		JLabel lblUnidade = new JLabel("Unidade");
		lblUnidade.setBounds(6, 95, 61, 16);
		panel_1.add(lblUnidade);
		
		JLabel lblPreco = new JLabel("Preco");
		lblPreco.setBounds(6, 136, 61, 16);
		panel_1.add(lblPreco);
		
		preco = new JTextField();
		preco.setColumns(10);
		preco.setBounds(103, 131, 292, 26);
		panel_1.add(preco);
		
		JLabel lblDescricao = new JLabel("Descricao");
		lblDescricao.setBounds(6, 180, 95, 16);
		panel_1.add(lblDescricao);
		
		JButton btnLimparProduto = new JButton("");
		btnLimparProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaTelaProduto();
			}
		});
		btnLimparProduto.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/limpar-limpo-2.png")));
		btnLimparProduto.setBounds(11, 227, 73, 70);
		panel_1.add(btnLimparProduto);
		
		JButton btnSalvarProduto = new JButton("");
		btnSalvarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(produto.getText().equals("") || codigo.getText().equals("") || preco.getText().equals("") || descricao.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}else {
					salvarProduto();
					limpaTelaProduto();
				}
			}
		});
		btnSalvarProduto.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/salvar-2.png")));
		btnSalvarProduto.setBounds(141, 227, 73, 70);
		panel_1.add(btnSalvarProduto);
		
		JButton btnAlterarProduto = new JButton("");
		btnAlterarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int question = JOptionPane.showConfirmDialog(null,"Deseja Salvar as alterações?","Atenção",JOptionPane.YES_NO_OPTION);
				if(produto.getText().equals("") || codigo.getText().equals("") || preco.getText().equals("") || descricao.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}else {
					if(question == JOptionPane.YES_OPTION) {
						alterarProduto();
						limpaTelaProduto();
					}else {
						JOptionPane.showMessageDialog(null, "Nenhuma alteração foi realizada.","Aviso",JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnAlterarProduto.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/lapis.png")));
		btnAlterarProduto.setBounds(264, 227, 73, 70);
		panel_1.add(btnAlterarProduto);
		
		JButton btnExcluirProduto = new JButton("");
		btnExcluirProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int question = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja excluir o registro?","Atenção",JOptionPane.YES_NO_OPTION);
				if(produto.getText().equals("") || codigo.getText().equals("") || preco.getText().equals("") || descricao.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Exclusão não efetuada, verifique os campos");
					limpaTelaProduto();
				}else {
					if(question == JOptionPane.YES_OPTION) {
						excluirProduto();
						limpaTelaProduto();
						pesquisarProduto();
						
					}else {
						JOptionPane.showMessageDialog(null, "Nenhuma alteração foi realizada.","Aviso",JOptionPane.WARNING_MESSAGE);
					}
				
				}
			}
		});
		btnExcluirProduto.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/lixo.png")));
		btnExcluirProduto.setBounds(404, 227, 73, 70);
		panel_1.add(btnExcluirProduto);
		
		JButton btnPesquisarProduto = new JButton("");
		btnPesquisarProduto.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/arquivo-de-documento.png")));
		btnPesquisarProduto.setBounds(538, 227, 73, 70);
		panel_1.add(btnPesquisarProduto);
		
		descricao = new JTextField();
		descricao.setColumns(10);
		descricao.setBounds(103, 175, 292, 26);
		panel_1.add(descricao);
		
		codigo = new JTextField();
		codigo.setColumns(10);
		codigo.setBounds(103, 47, 292, 26);
		panel_1.add(codigo);
		
		unidade = new JComboBox();
		unidade.setModel(new DefaultComboBoxModel(new String[] {"Kilo", "Unidade", "Saco", "Gramas", "Pote", "Duzia", "Litro"}));
		unidade.setBounds(103, 91, 292, 27);
		panel_1.add(unidade);
		
		//evento mouse clicked, quando seleciona linha da tabela, preenche os campos automaticamente
		tblProdutos = new JTable();
		tblProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCamposProdutos();
			}
		});
		
		tblProdutos.setBounds(16, 309, 595, 98);
		panel_1.add(tblProdutos);
		
		//JLabel lblFoto = new JLabel("Foto");
		lblFoto.setBounds(407, 11, 204, 141);
		panel_1.add(lblFoto);
		
		txtFoto = new JTextField();
		txtFoto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				ImageIcon imageIcon = new ImageIcon(new ImageIcon("/Users/edmar_sr/Desktop/Edmar/Programacao/Java/Agrestina/imagensProdutos/"+txtFoto.getText()+".png").getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT));
				lblFoto.setIcon(imageIcon);
			}
		});
		txtFoto.setColumns(10);
		txtFoto.setBounds(470, 175, 141, 26);
		panel_1.add(txtFoto);
		
		JLabel lblNomeFoto = new JLabel("Imagem");
		lblNomeFoto.setBounds(404, 180, 61, 16);
		panel_1.add(lblNomeFoto);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Clientes", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(6, 11, 61, 16);
		panel.add(lblNome);
		
		JLabel lblCelular = new JLabel("Celular");
		lblCelular.setBounds(6, 52, 61, 16);
		panel.add(lblCelular);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(6, 95, 61, 16);
		panel.add(lblEmail);
		
		JLabel lblEndereco = new JLabel("Endereco");
		lblEndereco.setBounds(6, 136, 61, 16);
		panel.add(lblEndereco);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setBounds(6, 180, 61, 16);
		panel.add(lblCpf);
		try {
			cpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		cpf.setBounds(103, 175, 508, 26);
		panel.add(cpf);
		try {
			celular = new JFormattedTextField(new MaskFormatter("(##) #####-####"));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		celular.setBounds(103, 47, 508, 26);
		panel.add(celular);
		
		nome = new JTextField();
		nome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				pesquisarClientes();
			}
		});
		nome.setBounds(103, 6, 508, 26);
		panel.add(nome);
		nome.setColumns(10);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(103, 90, 508, 26);
		panel.add(email);
		
		endereco = new JTextField();
		endereco.setColumns(10);
		endereco.setBounds(103, 131, 508, 26);
		panel.add(endereco);
		
		JButton btnSalvarCliente = new JButton("");
		btnSalvarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(nome.getText().equals("") ||  celular.getText().equals("(  )      -    ") || email.getText().equals("") || endereco.getText().equals("") || cpf.getText().equals("   .   .   -  ")){
					JOptionPane.showMessageDialog(null, "Existem campos vazios");
				}else {
					salvarCliente();
					limpaTelaCliente();
				}
				
			}
		});
		btnSalvarCliente.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/salvar-2.png")));
		btnSalvarCliente.setBounds(145, 227, 73, 70);
		panel.add(btnSalvarCliente);
		
		JButton btnLimparCliente = new JButton("");
		btnLimparCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpaTelaCliente();
				
			}
		});
		btnLimparCliente.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/limpar-limpo-2.png")));
		btnLimparCliente.setBounds(11, 227, 73, 70);
		panel.add(btnLimparCliente);
		
		JButton btnExcluirCliente = new JButton("");
		btnExcluirCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int question = JOptionPane.showConfirmDialog(null,"Tem certeza que deseja excluir o registro?","Atenção",JOptionPane.YES_NO_OPTION);
				if(nome.getText().equals("") || endereco.getText().equals("") || cpf.getText().equals("") || celular.getText().equals("") || email.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Exclusão não efetuada, verifique os campos");
					limpaTelaCliente();
				}else {
					if(question == JOptionPane.YES_OPTION) {
						excluirCliente();
						limpaTelaCliente();
						pesquisarClientes();
						
					}else {
						JOptionPane.showMessageDialog(null, "Nenhuma alteração foi realizada.","Aviso",JOptionPane.WARNING_MESSAGE);
					}
				
				}	
			}
		});
		btnExcluirCliente.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/lixo.png")));
		btnExcluirCliente.setBounds(410, 227, 73, 70);
		panel.add(btnExcluirCliente);
		
		JButton btnAlterarCliente = new JButton("");
		btnAlterarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int question = JOptionPane.showConfirmDialog(null,"Deseja Salvar as alterações?","Atenção",JOptionPane.YES_NO_OPTION);
				if(nome.getText().equals("") || endereco.getText().equals("") || celular.getText().equals("") || cpf.getText().equals("") || email.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos");
				}else {
					if(question == JOptionPane.YES_OPTION) {
						alterarCliente();
						limpaTelaCliente();
					}else {
						JOptionPane.showMessageDialog(null, "Nenhuma alteração foi realizada.","Aviso",JOptionPane.WARNING_MESSAGE);
					}
				}	
			}
		});
		btnAlterarCliente.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/lapis.png")));
		btnAlterarCliente.setBounds(277, 227, 73, 70);
		panel.add(btnAlterarCliente);
		
		JButton btnPesquisarCliente = new JButton("");
		btnPesquisarCliente.setIcon(new ImageIcon(Cadastro.class.getResource("/Imagens/arquivo-de-documento.png")));
		btnPesquisarCliente.setBounds(538, 227, 73, 70);
		panel.add(btnPesquisarCliente);
		
		tblClientes = new JTable();
		tblClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCamposClientes();
			}
		});
		tblClientes.setBounds(6, 304, 605, 103);
		panel.add(tblClientes);
	}

	private void excluirProduto() {
		String sql = "delete from produtos where codigo = ?";
		String sqlConsultaProduto = "select * from produtos where codigo = ?";
		try {
			pst = conexao.prepareStatement(sqlConsultaProduto);
			pst.setString(1, codigo.getText());
			rs = pst.executeQuery();
		
			if(rs.next()) {
				pst = conexao.prepareStatement(sql);
				pst.setString(1, codigo.getText());
				
				boolean alteracao = pst.execute();
				JOptionPane.showMessageDialog(null, "O Produto com código "+codigo.getText()+" foi excluído.","Aviso",JOptionPane.INFORMATION_MESSAGE);
				
			}else {
				JOptionPane.showMessageDialog(null, "Exclusão não efetuada. Não foi encontrado produto com código "+codigo.getText(), "Erro!", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void excluirCliente() {
		String sql = "delete from clientes where cpf_cnpj = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, cpf.getText());
			boolean alteracao = pst.execute();
			JOptionPane.showMessageDialog(null, "O Cliente com CNPJ/CPF "+cpf.getText()+" foi excluído","Aviso",JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void alterarProduto() {
		String sql = "update produtos set preco = ?, nome = ?, descricao = ?, unidade = ?, foto = ? where codigo = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, preco.getText());
			pst.setString(2, produto.getText());
			pst.setString(3, descricao.getText());
			pst.setString(4, (String) unidade.getSelectedItem());
			pst.setString(5, txtFoto.getText());
			pst.setString(6, codigo.getText());
			int alteracao = pst.executeUpdate();
			
			if(alteracao > 0) {
				JOptionPane.showMessageDialog(null, "Alteração realizada com sucesso");
				pesquisarProduto();
			}else {
				JOptionPane.showMessageDialog(null, "Nenhuma alteração foi realizada.","Aviso",JOptionPane.WARNING_MESSAGE);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e,"Erro!",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void alterarCliente() {
		
		//String sql = "update clientes set nome = ?, endereco = ?, cpf_cnpj = ?, telefone = ?, email = ? where id = ?";
		String sql = "update clientes set nome = ?, endereco = ?, telefone = ?, email = ? where cpf_cnpj = ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, nome.getText());
			pst.setString(2, endereco.getText());
			//pst.setString(3, cpf.getText());
			pst.setString(3, celular.getText());
			pst.setString(4, email.getText());
			pst.setString(5, cpf.getText());
			//pst.setString(6, txtIdCliente.getText());
			int alteracao = pst.executeUpdate();
			//System.out.println(alteracao);
			
			if(alteracao > 0) {
				JOptionPane.showMessageDialog(null, "Alteração realizada com sucesso");
				pesquisarClientes();
			}else {
				JOptionPane.showMessageDialog(null, "Nenhuma alteração foi realizada.","Aviso",JOptionPane.WARNING_MESSAGE);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}

	//metodo para preencher automaticamente a jtable com like do que for digitado no campo produto - funciona junto com o evento de key do campo produto
	private void pesquisarProduto() {
		String sql = "select * from produtos where nome like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, produto.getText()+"%");
			rs = pst.executeQuery();
			
			tblProdutos.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	private void pesquisarClientes() {
		String sql = "select * from clientes where nome like ?";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, nome.getText()+"%");
			rs = pst.executeQuery();
			
			tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		
	}
	
	//metodo para preencher automaticamente os campos quando selecionada a linha na tabela - funciona junto com o evento de click do mouise na jtable
	private void setCamposProdutos() {
		int setar = tblProdutos.getSelectedRow();
		produto.setText(tblProdutos.getModel().getValueAt(setar, 0).toString());
		codigo.setText(tblProdutos.getModel().getValueAt(setar, 1).toString());
		preco.setText(tblProdutos.getModel().getValueAt(setar, 2).toString());
		descricao.setText(tblProdutos.getModel().getValueAt(setar, 4).toString());
		unidade.setSelectedItem(tblProdutos.getModel().getValueAt(setar, 3).toString());
		//txtIdProduto.setText(tblProdutos.getModel().getValueAt(setar, 0).toString());
		txtFoto.setText(tblProdutos.getModel().getValueAt(setar, 5).toString());
		ImageIcon imageIcon = new ImageIcon(new ImageIcon("/Users/edmar_sr/Desktop/Edmar/Programacao/Java/Agrestina/imagensProdutos/"+txtFoto.getText()+".png").getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT));
		lblFoto.setIcon(imageIcon);
	}
	
	private void setCamposClientes() {
		int setar = tblClientes.getSelectedRow();
		//txtIdCliente.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
		nome.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
		endereco.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
		cpf.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
		celular.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
		email.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
	}

	private void limpaTelaCliente() {
		nome.setText(null);
		celular.setText(null);
		cpf.setText(null);
		email.setText(null);
		endereco.setText(null);
		//txtIdCliente.setText(null);
	}

	private void limpaTelaProduto() {
		produto.setText(null);
		preco.setText(null);
		codigo.setText(null);
		descricao.setText(null);
		//txtIdProduto.setText(null);
		txtFoto.setText(null);
		lblFoto.setIcon(null);
	}

	private void salvarProduto() {
		
		String sql = "insert into produtos (codigo,preco,descricao,nome,unidade,foto) values (?,?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, codigo.getText());
			pst.setString(2, preco.getText());
			pst.setString(3, descricao.getText());
			pst.setString(4, produto.getText());
			pst.setString(5, (String) unidade.getSelectedItem());
			pst.setString(6, txtFoto.getText());
			pst.execute();
			
			JOptionPane.showMessageDialog(null, "Produto incluído com sucesso");

		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Não é possível inserir o mesmo código duas vezes."+"\n"+e,"Erro!",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e,"Erro!",JOptionPane.ERROR_MESSAGE);
		}
	}

	private void salvarCliente() {
		
		String sql = "insert into clientes (nome,endereco,cpf_cnpj,telefone,email) values (?,?,?,?,?)";
		try {
			pst = conexao.prepareStatement(sql);
			pst.setString(1, nome.getText());
			pst.setString(2, endereco.getText());
			pst.setString(3, cpf.getText());
			pst.setString(4, celular.getText());
			pst.setString(5, email.getText());
			pst.execute();
			
			JOptionPane.showMessageDialog(null, "Cliente incluído com sucesso");

		} catch (SQLIntegrityConstraintViolationException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Não é possível inserir o mesmo CPF/CNPJ duas vezes."+"\n"+e,"Erro!",JOptionPane.ERROR_MESSAGE);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e,"Erro!",JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
