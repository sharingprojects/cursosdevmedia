package com.devmedia.treinamento.webservice.config;

import java.sql.*;


public class BDConfig {
	
	
	//Opcoes para configurar e acessar Banco de dados: atrav�s do driver, chamando o getConnection(Classe interna do Java para conex�o atrav�s do Driver)
	//ou atrav�s do hibernate e jpa
	//Nas grandes empresas utilizam datasources para conex�o.
	
	//static n�o precisa instanciar
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		
		//informa para o driver a classe especifica que vai gerenciar a conex�o
		Class.forName("org.postgresql.Driver");
				
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/notes_db", "postgres", "postgres");
		
	}

}
