package not_used;

import javax.swing.JOptionPane;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public abstract class Connection {
	
    DB baseDados;
	DBCollection colecao;
	BasicDBObject document = new BasicDBObject();
	String resultNome;
	boolean sucess; 
	
	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

	public String getResultNome() {
		return resultNome;
	}

	public void setResultNome(String resultNome) {
		this.resultNome = resultNome;
	}

	public Connection() {
		try {
	        
	        MongoClient mongoClient = new MongoClient("localhost", 27017);
	        baseDados = mongoClient.getDB("loja");
	        colecao = baseDados.getCollection("login");
	        System.out.println("Teste efetuado com sucesso");

	        //mongoClient.getDB("myMongoDB");

	        //List<String> listofDB= mongoClient.getDatabaseNames();

	        //for(String dbName : listofDB){
	        //    System.out.println(dbName);
	        //}


	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public boolean inserir(String nome, String senha) {
		
		document.put("nome", nome);
		document.put("password", senha);
		colecao.insert(document);
		return true;
		
	}
	
	public void mostrar() {
		DBCursor cursor = colecao.find();
		while(cursor.hasNext()) {
			System.out.println(cursor.next());
		}
		
	}
	
	public boolean atualizar(String dadoAntigo, String dadoNovo) {
		
		document.put("nome", dadoAntigo);
		BasicDBObject docNovo = new BasicDBObject();
		docNovo.put("nome", dadoNovo);
		colecao.findAndModify(document, docNovo);
		return true;
		
	}
	
	public boolean remover(String dado) {
		document.put("nome", dado);
		colecao.remove(document);
		return true;
	}
	
	public void login(String nome, String password) {
		DBObject query = new BasicDBObject("nome", nome).append("password", password);
		DBCursor cursor = colecao.find(query);
		if(cursor.hasNext()) {
			
			String[] resultInArray = cursor.next().toString().split(",");
			String resultNomeParcial = resultInArray[1].substring(10);
			resultNome = resultNomeParcial.substring(0,resultNomeParcial.length()-1);
			JOptionPane.showMessageDialog(null, "Bem vindo(a) "+resultNome);
			sucess = true;
			
		}else {
			JOptionPane.showMessageDialog(null, "Usuario e/ou senha invalidos ");
			sucess = false;
		}
		
		
	}

}
