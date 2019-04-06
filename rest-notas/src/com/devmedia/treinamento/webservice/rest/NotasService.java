package com.devmedia.treinamento.webservice.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.devmedia.treinamento.webservice.dao.NotaDAO;
import com.devmedia.treinamento.webservice.entidade.Nota;

/**
 * Resource
 * @author andreza.cascais
 *  dentro de uma servi�o do RestFul, especificamente utilizando 
 *  a biblioteca Jersey em java, a primeira coisa que precisa definir �
 *  o endpoint(peda�o de url que ser� direcionado para esse servi�o.
 *  Uma vez que o Restful � totalmente baseado na sua url, e a sua url defini onde
 *  as coisas s�o executadas no servidor do seu servi�o, podemos tamb�m ter v�rias
 *  classes de servi�os destintas, cada peda�o da url, a parte depois da barra, � rederecionada
 *  para um servi�o especifico. Cada servi�o, tem que ter um Path(caminho que redereciona a requisi��o
 *  direto a aquela classe), ou seja qual requisi��o.
 *  
 *  A��es no Java s�o tomadas atrav�s de m�todos, logo a requisi��o /notas/list ser� uma chamada para um m�todo no java
 *  
 *  A defini��o purista para o uso de servi�os REST indica que o verbo/a��o n�o deve ser indicado na url,
 *  mas sim no verbo HTTP utilizado; por exemplo:
 *             url: www.dominio.com/rest/notas/1 - verbo PUT/POST: significa que estamos editando a nota de id=1
 *             
 *             url: www.dominio.com/rest/notas/1 - verbo DELETE: significa que estamos removendo a nota de id=1
 *             
 *  Neste curso, o autor seguiu uma linha pr�tica adotada por algumas empresas, 
 *  onde a a��o � descrita no verbo HTTP e tamb�m na URL: 
 *  		
 *  		 url: www.dominio.com/rest/notas/edit/1 - verbo PUT/POST
 *  		 url: www.dominio.com/rest/notas/remove/1 - verbo DELETE
 *  
 * No REST tem duas anota��es b�sicas:
 * @Consumes = define o que vai ser consumido, ou seja quando o m�todo recebe uma par�metro
 * @Produces = define o que vai ser produzido: 
 *   exemplo: (MediaType.APPLICATION_JSON) : o m�todo quando for chamada ir� retornar um tipo Json do Objeto List<Notas>
 *   ou seja, o POJOMapping do xml, ir� fazer isso essa convers�o automaticamente.
  
 */
@Path("/notas")
public class NotasService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";

	private NotaDAO notaDAO;
	
	//Essa anota��o � utilizada para n�o precisa criar um construtor
	//Pois o construtor ser� a pr�pria biblioteca do jersey e o java que vai cri�-lo.
	//Pois essa classe de servi�o ou resource, n�o � instanciada.
	@PostConstruct
	private void init() {
		notaDAO = new NotaDAO();
	}
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON+CHARSET_UTF8)
	public List<Nota> listarNotas() {
		List<Nota> lista = null;
		try {
			lista = notaDAO.listarNotas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON+CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String addNota(Nota nota) {
		String msg = "";

		System.out.println(nota.getTitulo());

		try {
			int idGerado = notaDAO.addNota(nota);
			StringBuilder sb = new StringBuilder();
			sb.append("Nota salva com id: ");
			sb.append(idGerado);
			msg = sb.toString();
		} catch (Exception e) {
			msg = "Erro ao add a nota!";
			e.printStackTrace();
		}

		return msg;
	}
	
	
	//@Consumes � TEXT_PLAIN, pois o esse m�todo n�o recebe uma entidade completa, mas sim um tipo text, que ser� convertido para um tipo int no Java. 
	@GET
	@Path("/get/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON+CHARSET_UTF8)
	public Nota buscarPorId(@PathParam("id") int idNota) {
		Nota nota = null;
		try {
			nota = notaDAO.buscarNotaPorId(idNota);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return nota;
	}

	@PUT
	@Path("/edit/{id}")
	@Consumes(MediaType.APPLICATION_JSON+CHARSET_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public String editarNota(Nota nota, @PathParam("id") int idNota) {
		String msg = "";
		
		System.out.println(nota.getTitulo());
		
		try {
			
			notaDAO.editarNota(nota, idNota);
			StringBuilder sb = new StringBuilder();
			sb.append("Nota com id: ");
			sb.append(nota.getId());
			sb.append(" foi alterado com sucesso!");
			msg = sb.toString();
		
		} catch (Exception e) {
			
			msg = "Erro ao editar a nota!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removerNota(@PathParam("id") int idNota) {
		String msg = "";
		
		try {
			notaDAO.removerNota(idNota);
			
			msg = "Nota removida com sucesso!";
		} catch (Exception e) {
			msg = "Erro ao remover a nota!";
			e.printStackTrace();
		}
		
		return msg;
	}
	
}
