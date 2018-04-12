package br.com.uol.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.uol.http.HTTPRequestJson;
import br.com.uol.http.HTTPRequestXML;
import br.com.uol.model.Player;

/**
 * Classe utilizada para realizar as operações com o banco de dados.
 */

public class PlayerDbUtil {

	private DataSource dataSource;

	public PlayerDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Player> getPlayers() throws Exception {
		
		List<Player> players = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// pega uma conexão
			myConn = dataSource.getConnection();
			
			// cria a query
			String sql = "select * from PLAYERS_UOL order by nome";
			
			// cria o sql statement
			myStmt = myConn.createStatement();
			
			// executa a query
			myRs = myStmt.executeQuery(sql);
			
			// processo o result set
			while (myRs.next()) {
				
				// busca dados do result set
				int id = myRs.getInt("id");
				String nome = myRs.getString("nome");
				String email = myRs.getString("email");
				String telefone = myRs.getString("telefone");
				String codinome = myRs.getString("codinome");
				String grupo = myRs.getString("grupo");
				
				
				// cria um novo objeto
				Player tempPlayer = new Player(id, nome, email, telefone, codinome, grupo);
				
				// adiciona na lista de jogadores
				players.add(tempPlayer);				
			}
			
			return players;		
		}
		finally {
			// fecha os objetos JDBC
			close(myConn, myStmt, myRs);
		}		
	}
	
	public boolean IsCodenameAvailable(String codinome) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			// pega uma conexão
			myConn = dataSource.getConnection();

			// cria o sql para verificar se o codinome já existe na base
			String sql = "select * from PLAYERS_UOL where codinome=?";

			// prepara o statement
			myStmt = myConn.prepareStatement(sql);

			// preenche os dados da query
			myStmt.setString(1, codinome);

			// executa query
			myRs = myStmt.executeQuery();

			if (myRs.next()) {
				return false;
			} else {
				return true;
			}

		} finally {
			// limpa objetos JDBC
			close(myConn, myStmt, myRs);
		}
	}

	public boolean IsNameOrEmailAvailable(String campo, String valorCampo, String grupo) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {

			// pega uma conexão
			myConn = dataSource.getConnection();

			// cria o sql para verificar se o nome ou email já existe na base
			String sql = "select * from PLAYERS_UOL where " + campo + "=? AND grupo=?";
			
			// prepara o statement
			myStmt = myConn.prepareStatement(sql);

			// set parametros
			myStmt.setString(1, valorCampo);
			myStmt.setString(2, grupo);

			// executa  statement
			myRs = myStmt.executeQuery();

			// retorna dados do result set
			if (myRs.next()) {
				return false;
			} else {
				return true;
			}

		} finally {
			// limpa objetos JDBC
			close(myConn, myStmt, myRs);
		}
	}
	
	public String getAvengersCodename() throws Exception {
		
		// cria a conexão com a URL do arquivo json
		HTTPRequestJson req = new HTTPRequestJson("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json");
		
		// popula o arrayList com os codinomes do arquivo
		ArrayList<String> avengersCodenamesList = req.getAvengersCodenames(req.UrlConnection());
		
		String codinome = null;
		
		// percorre o array list de codinomes
		for (int i = 0; i < avengersCodenamesList.size(); i++) {	
			
			// verifica se o codinome existe na base
			if(IsCodenameAvailable(avengersCodenamesList.get(i))){
				codinome = avengersCodenamesList.get(i);
				// encerra o loop assim que encontrar um codinome disponível
				break;
			}
		}
		
		// retorna o codinome
		return codinome;
		
	}
	
	public String getJusticeLeagueCodename() throws Exception {
		
		// cria a conexão com a URL do arquivo xml
		HTTPRequestXML req = new HTTPRequestXML("https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml");
		
		// popula o arrayList com os codinomes do arquivo
		ArrayList<String> justiceLeagueCodenamesList = req.getJusticeLeagueCodenames(req.UrlConnection());

		String codinome = null;
		
		// percorre o array list de codinomes
		for (int i = 0; i < justiceLeagueCodenamesList.size(); i++) {	
			
			// verifica se o codinome existe na base
			if(IsCodenameAvailable(justiceLeagueCodenamesList.get(i))){
				codinome = justiceLeagueCodenamesList.get(i);
				// encerra o loop assim que encontrar um codinome disponível
				break;
			}
		}
		
		// retorna o codinome
		return codinome;
		
	}
	
	public void addPlayer(Player thePlayer) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// pega uma conexão
			myConn = dataSource.getConnection();
			
			// cria o SQL para inserir
			String sql = "insert into PLAYERS_UOL "
					   + "(NOME, EMAIL, TELEFONE, CODINOME, GRUPO) "
					   + "values (?, ?, ?, ?, ?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// seta os parametros do jogador
			myStmt.setString(1, thePlayer.getNome());
			myStmt.setString(2, thePlayer.getEmail());
			myStmt.setString(3, thePlayer.getTelefone());
			myStmt.setString(4, thePlayer.getCodinome());
			myStmt.setString(5, thePlayer.getGrupo());
			
			// executa o sql
			myStmt.execute();
		}
		finally {
			// limpa objetos JDBC
			close(myConn, myStmt, null);
		}
	}	
	
	public void deleteStudent(String thethePlayerId) throws Exception {

		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// converte id do jogador para int
			int playerId = Integer.parseInt(thethePlayerId);
			
			// pega uma conexão
			myConn = dataSource.getConnection();
			
			// cria o sql para excluir
			String sql = "delete from players_uol where id=?";
			
			// prepara o statement
			myStmt = myConn.prepareStatement(sql);
			
			// seta parametros
			myStmt.setInt(1, playerId);
			
			// executa o sql statement
			myStmt.execute();
		}
		finally {
			// limpa objetos JDBC
			close(myConn, myStmt, null);
		}	
	}
	
	public void updateStudent(Player thePlayer) throws Exception {
		
		Connection myConn = null;
		PreparedStatement myStmt = null;

		try {
			// pega uma conexão
			myConn = dataSource.getConnection();
			
			// cria SQL de update
			String sql = "update players_uol "
						+ "set nome=?, email=?, telefone=?"
						+ "where id=?";
			
			// prepara o statement
			myStmt = myConn.prepareStatement(sql);
			
			// set parametros
			myStmt.setString(1, thePlayer.getNome());
			myStmt.setString(2, thePlayer.getEmail());
			myStmt.setString(3, thePlayer.getTelefone());
			myStmt.setInt(4, thePlayer.getId());
			
			// executa SQL statement
			myStmt.execute();
		}
		finally {
			// limpa objetos JDBC
			close(myConn, myStmt, null);
		}
	}
	
	public Player getPlayer(String thePlayerId) throws Exception {

		Player thePlayer = null;
		
		Connection myConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;
		int playerId;
		
		try {
			// converte id do jogador para int
			playerId = Integer.parseInt(thePlayerId);
			
			// pega uma conexão
			myConn = dataSource.getConnection();
			
			// cria o SQL para selecionar um jogador
			String sql = "select * from players_uol where id=?";
			
			// prepara o statement
			myStmt = myConn.prepareStatement(sql);
			
			// seta os parametros
			myStmt.setInt(1, playerId);
			
			// executa o statement
			myRs = myStmt.executeQuery();
			
			// busca dados do result set
			if (myRs.next()) {
				String nome = myRs.getString("nome");
				String email = myRs.getString("email");
				String telefone = myRs.getString("telefone");
				String codinome = myRs.getString("codinome");
				String grupo = myRs.getString("grupo");
				
				// usa o id do jogador no contrutor
				thePlayer = new Player(playerId, nome, email, telefone, codinome, grupo);
			}
			else {
				throw new Exception("Jogador nao econtrado: " + playerId);
			}				
			
			return thePlayer;
		}
		finally {
			// limpa objetos JDBC
			close(myConn, myStmt, myRs);
		}
	}
	
 	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close(); 
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}















