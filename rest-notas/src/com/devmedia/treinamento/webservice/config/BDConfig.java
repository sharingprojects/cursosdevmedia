package com.devmedia.treinamento.webservice.config;

import java.sql.*;


public class BDConfig {
	
	
	//Opcoes para configurar e acessar Banco de dados: através do driver, chamando o getConnection(Classe interna do Java para conexão através do Driver)
	//ou através do hibernate e jpa
	//Nas grandes empresas utilizam datasources para conexão.
	
	//static não precisa instanciar
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		
		//informa para o driver a classe especifica que vai gerenciar a conexão
		Class.forName("org.postgresql.Driver");
				
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/notes_db", "postgres", "postgres");
		
	}

}
