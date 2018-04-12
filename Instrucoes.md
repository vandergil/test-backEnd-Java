/** Instruções para rodar o projeto de cadastro de jogadores do UOL **/

1) Criar uma base de dados no HSQLDB com o nome testdb ou alterar o arquivo context.xml do projeto com o nome de sua base na linha: url="jdbc:hsqldb:hsql://localhost/testdb"

2) Criar a PLAYERS_UOL seguindo o seguinte select:  

	CREATE TABLE PLAYERS_UOL (
	  id INTEGER IDENTITY PRIMARY KEY,
	  nome VARCHAR(30) NOT NULL,
	  email  VARCHAR(50)  NOT NULL,
	  telefone  VARCHAR(50), 
	  codinome  VARCHAR(50)  NOT NULL,
	  grupo  VARCHAR(50)  NOT NULL
	);
	
3) Rodar o projeto


/** 
OBS: Projeto foi desenvolvido com a IDE eclipse e o web server TOMCAT 
**/