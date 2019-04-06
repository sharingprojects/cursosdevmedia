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
 *  dentro de uma serviço do RestFul, especificamente utilizando 
 *  a biblioteca Jersey em java, a primeira coisa que precisa definir é
 *  o endpoint(pedaço de url que será direcionado para esse serviço.
 *  Uma vez que o Restful é totalmente baseado na sua url, e a sua url defini onde
 *  as coisas são executadas no servidor do seu serviço, podemos também ter várias
 *  classes de serviços destintas, cada pedaço da url, a parte depois da barra, é rederecionada
 *  para um serviço especifico. Cada serviço, tem que ter um Path(caminho que redereciona a requisição
 *  direto a aquela classe), ou seja qual requisição.
 *  
 *  Ações no Java são tomadas através de métodos, logo a requisição /notas/list será uma chamada para um método no java
 *  
 *  A definição purista para o uso de serviços REST indica que o verbo/ação não deve ser indicado na url,
 *  mas sim no verbo HTTP utilizado; por exemplo:
 *             url: www.dominio.com/rest/notas/1 - verbo PUT/POST: significa que estamos editando a nota de id=1
 *             
 *             url: www.dominio.com/rest/notas/1 - verbo DELETE: significa que estamos removendo a nota de id=1
 *             
 *  Neste curso, o autor seguiu uma linha prática adotada por algumas empresas, 
 *  onde a ação é descrita no verbo HTTP e também na URL: 
 *  		
 *  		 url: www.dominio.com/rest/notas/edit/1 - verbo PUT/POST
 *  		 url: www.dominio.com/rest/notas/remove/1 - verbo DELETE
 *  
 * No REST tem duas anotações básicas:
 * @Consumes = define o que vai ser consumido, ou seja quando o método recebe uma parâmetro
 * @Produces = define o que vai ser produzido: 
 *   exemplo: (MediaType.APPLICATION_JSON) : o método quando for chamada irá retornar um tipo Json do Objeto List<Notas>
 *   ou seja, o POJOMapping do xml, irá fazer isso essa conversão automaticamente.
  
 */
@Path("/notas")
public class NotasService {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";

	private NotaDAO notaDAO;
	
	//Essa anotação é utilizada para não precisa criar um construtor
	//Pois o construtor será a própria biblioteca do jersey e o java que vai criá-lo.
	//Pois essa classe de serviço ou resource, não é instanciada.
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
	
	
	//@Consumes é TEXT_PLAIN, pois o esse método não recebe uma entidade completa, mas sim um tipo text, que será convertido para um tipo int no Java. 
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
