package br.com.uol.model;

public class Player {

	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String codinome;
	private String grupo;
	
	
	public Player(String nome, String email, String telefone, String codinome, String grupo) {
		
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.codinome = codinome;
		this.grupo = grupo;
	}
	
	public Player(int id, String nome, String email, String telefone) {
		
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	public Player(int id, String nome, String email, String telefone, String codinome, String grupo) {

		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.codinome = codinome;
		this.grupo = grupo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCodinome() {
		return codinome;
	}

	public void setCodinome(String codinome) {
		this.codinome = codinome;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + nome + "]";
	}	
}
