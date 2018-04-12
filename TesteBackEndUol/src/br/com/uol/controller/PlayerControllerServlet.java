package br.com.uol.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import br.com.uol.jdbc.PlayerDbUtil;
import br.com.uol.model.Player;


/**
 * Classe utilizada para atender e gerenciar as requisições da aplicação.
 */


@WebServlet("/PlayerControllerServlet")
public class PlayerControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private PlayerDbUtil playerDbUtil;
	private String nomeLoad = "";
	private String emailLoad = "";
	
	@Resource(name="jdbc/teste_backend_uol")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		// cria nosso player db util e passa no pool de conexões / datasource
		try {
			playerDbUtil = new PlayerDbUtil(dataSource);
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// le o parametro "command"
			String theCommand = request.getParameter("command");
			
			// O default é listar os jogadores
			if (theCommand == null) {
				theCommand = "LIST";
			}
			
			// Direciona para o método apropriado
			switch (theCommand) {
				
			case "LIST":
				listPlayer(request, response);
				break;
				
			case "ADD":
				addPlayer(request, response);
				break;
				
			case "LOAD":
				loadPlayer(request, response);
				break;
				
			case "UPDATE":
				updatePlayer(request, response);
				break;
			
			case "DELETE":
				deletePlayer(request, response);
				break;
	
				default:
					listPlayer(request, response);
			}
				
		}
		catch (Exception exc) {
			throw new ServletException(exc);
		}
		
	}
	
	private void updatePlayer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// lê as informações dos jogadores do formulário 
		int id = Integer.parseInt(request.getParameter("playerId"));
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String telefone = request.getParameter("telefone");
		String codinome = request.getParameter("codinome");
		String grupo = request.getParameter("grupo");
		
		Player thePlayerTemp = new Player(id, nome, email, telefone, codinome, grupo);		
		request.setAttribute("THE_PLAYER", thePlayerTemp);
		
		Map<String, String> messages = formValidate(nome, email, telefone, grupo, nomeLoad, emailLoad);
		
		if (messages.isEmpty()) {
		
			// cria um novo objeto
			Player thePlayer = new Player(id, nome, email, telefone);
	
			// faz o update no base de dados
			playerDbUtil.updateStudent(thePlayer);
	
			// envia para a página de listagem de jogadores
			listPlayer(request, response);
		
		} else {
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("update-player-form.jsp").forward(request, response);
		}
	}

	private void loadPlayer(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// lê o id do jogador do formulário
		String thePlayerId = request.getParameter("playerId");
				
		// pega o jogador da base (db util)
		Player thePlayer = playerDbUtil.getPlayer(thePlayerId);
		
		nomeLoad = thePlayer.getNome();
		emailLoad = thePlayer.getEmail();
		
		// adiciona o jogador no request attribute
		request.setAttribute("THE_PLAYER", thePlayer);
				
		// envia para a jsp: update-player-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-player-form.jsp");
		dispatcher.forward(request, response);	
		
	}

	private void listPlayer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// pega os jogadores da base
		List<Player> players = playerDbUtil.getPlayers();

		// adiciona o jogador no request attribute
		request.setAttribute("PLAYER_LIST", players);

		// envia para a jsp:list-players (view)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-players.jsp");
		dispatcher.forward(request, response);
	}

	private void addPlayer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// lê as informações dos jogadores do formulário 
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String telefone = request.getParameter("telefone");
		String grupo = request.getParameter("grupo");
		String codinome = null;

		// adiciona informações do jogador nos request attribute
		request.setAttribute("PLAYER_NOME", nome);
		request.setAttribute("PLAYER_EMAIL", email);
		request.setAttribute("PLAYER_TEL", telefone);
		request.setAttribute("PLAYER_GRUPO", grupo);
		
		// Faz a validação dos dados do formulário
		Map<String, String> messages = formValidate(nome, email, telefone, grupo, null, null);
		
		// Verifica se passou na validação
		if (messages.isEmpty()) {
			
			// Verifica a escolha do grupo pelo usuário
			if (grupo.equals("Vingadores")) {
				
				// Busca o codinome para inserir na base
				codinome = playerDbUtil.getAvengersCodename();

			} else if (grupo.equals("Liga da Justica")) {
				codinome = playerDbUtil.getJusticeLeagueCodename();
			}

			if (codinome != null) {

				// cria um novo objeto
				Player thePlayer = new Player(nome, email, telefone, codinome, grupo);

				// adiciona o jogador na base de dados
				playerDbUtil.addPlayer(thePlayer);

				// adiciona informações do jogador nos request attribute				
				request.setAttribute("PLAYER_NOME", "");
				request.setAttribute("PLAYER_EMAIL", "");
				request.setAttribute("PLAYER_TEL", "");
				
				messages.put("codinome", "\"" + nome + "\" \ncadastrado com Sucesso!!");
				request.setAttribute("messages", messages);
				
				// envia para a jsp: add-player-form.jsp 
				request.getRequestDispatcher("add-player-form.jsp").forward(request, response);

			} else {
				messages.put("codinome", "Codinome Indisponível");
				request.setAttribute("messages", messages);
				request.getRequestDispatcher("add-player-form.jsp").forward(request, response);

			}
		} else {

			request.setAttribute("messages", messages);
			request.getRequestDispatcher("add-player-form.jsp").forward(request, response);

		}

	}
	
	private void deletePlayer(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

			// lê o id do jogador do formulário
			String thePlayerId = request.getParameter("playerId");
			
			// exclui jogador da base
			playerDbUtil.deleteStudent(thePlayerId);
			
			// envia para a página de listagem de jogadores
			listPlayer(request, response);
		}
	
	private Map<String, String> formValidate(String nome, String email, String telefone, String grupo, String nomeLoad, String emailLoad) throws Exception {
		
		Map<String, String> messages = new HashMap<String, String>();
		
		// verifica se o nome está vazio e se já existe na base
		if (nome.trim().isEmpty()) {			
			messages.put("name", "Preencha o Nome");			
		}else if(nome.length() < 4){
			messages.put("name", "Nome deve conter entre 5 e 30 caracteres");			
		} else if(playerDbUtil.IsNameOrEmailAvailable("NOME", nome, grupo) == false) {			
			// verifica se é no método de INSERT ou UPDATE
			if(nomeLoad == null)
				messages.put("name", "Nome já existente no grupo " + grupo);
			else if(nomeLoad != null && (!nome.equals(nomeLoad)))
				messages.put("name", "Nome já existente no grupo " + grupo);
		}
		
		// verifica se o email está vazio e se já existe na base
		if (email.trim().isEmpty()) {			
			messages.put("email", "Preencha o Email");			
		}else if(nome.length() < 6){
			messages.put("email", "Email deve conter no minino 6 caracteres");	
		}else if(playerDbUtil.IsNameOrEmailAvailable("EMAIL", email, grupo) == false) {			
			// verifica se é no método de INSERT ou UPDATE
			if(emailLoad == null)
				messages.put("email", "Email já existente no grupo " + grupo);
			else if(emailLoad != null && (!email.equals(emailLoad)))
				messages.put("email", "Email já existente no grupo " + grupo);	
		}
		return messages;
	}
	
}













