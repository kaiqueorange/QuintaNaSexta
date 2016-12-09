package br.ufg.inf.quintacalendario.testes.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Categoria;
import br.ufg.inf.quintacalendario.model.Evento;
import br.ufg.inf.quintacalendario.service.CategoriaService;
import br.ufg.inf.quintacalendario.service.EventoService;
import br.ufg.inf.quintacalendario.service.InstitutoService;
import br.ufg.inf.quintacalendario.service.RegionalService;

public class TesteCategoria {
	
	SessionFactory sessionFactory;
	
	@Before
	public void init(){
		sessionFactory = Application.getInstance().getSessionFactory();
		
		limparObjetoEvento();
		new RegionalService(sessionFactory).limparTabela();
		new EventoService(sessionFactory).limparTabela();
		new InstitutoService(sessionFactory).limparTabela();
		new CategoriaService(sessionFactory).limparTabela();
	}
	
	@After
	public void finalizar(){
		new CategoriaService(sessionFactory).limparTabela();
	}
	
	@Test
	public void testeSalvarCategoria(){
		Categoria categoria = new Categoria();
		categoria.setNome("Feriado");
		boolean retorno = new CategoriaService(sessionFactory).salvar(categoria);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void testeSalvarCategoriaComNomeVazio(){
		Categoria categoria = new Categoria();
		categoria.setNome("");
		boolean retorno = new CategoriaService(sessionFactory).salvar(categoria);
		Assert.assertFalse(retorno);
	}
	
	@Test
	public void testeEditarCategoria(){
		inserirCategoria("Feriado");
		
		CategoriaService service = new CategoriaService(sessionFactory);
		
		List<Categoria> categorias = service.listar();
		Categoria categoria = categorias.get(0);
		Integer id = Integer.valueOf(String.valueOf(categoria.getId()));
		
		service.editar(id, "Recesso Academico");
		
		categoria  = service.listarPorId(id);
		
		Assert.assertTrue(categoria.getNome().equals("Recesso Academico"));
	}
	
	@Test
	public void testeListarPorDescricao(){
		inserirCategoria("Feriado");
		inserirCategoria("Recesso");
		
		CategoriaService service = new CategoriaService(sessionFactory);
		List<Categoria> categorias = service.listar("Feriado");
		
		boolean result = categorias.stream().anyMatch(x->x.getNome().equals("Feriado"));
		Assert.assertTrue(result);
	}
	
	@Test
	public void testeListarPorDescricaoComDescricaoInvalida(){
		inserirCategoria("Feriado");
		inserirCategoria("Recesso");
		
		CategoriaService service = new CategoriaService(sessionFactory);
		List<Categoria> categorias = service.listar("Greve");
		
		Assert.assertTrue(categorias.isEmpty());
	}
	
	
	@Test
	public void testeListarPorId(){
		Integer id = 0;
		Categoria categoria = null;
		
		inserirCategoria("Feriado");
		
		CategoriaService service = new CategoriaService(sessionFactory);
		List<Categoria> categorias = service.listar();
		
		if (!categorias.isEmpty()) {
			categoria = categorias.get(0);
			id = (int) categoria.getId();
			categoria = service.listarPorId(id);
		}
		Assert.assertTrue(categoria != null);
	}
	
	@Test
	public void testeRemoverCategoria(){
		inserirCategoria("Feriado");
		Categoria categoria = null;
		Integer id = 0;
		
		CategoriaService service = new CategoriaService(sessionFactory);
		List<Categoria> categorias = service.listar();
		
		if (!categorias.isEmpty()) {
			categoria = categorias.get(0);
			id = (int) categoria.getId();
			
			service.remover(id);
			Assert.assertTrue(service.listar().isEmpty());
		}else{
			Assert.assertTrue(false);
		}
	}
	
	public void inserirCategoria(String nome){
		Categoria categoria = new Categoria();
		categoria.setNome(nome);
		
		CategoriaService service = new CategoriaService(sessionFactory);
		service.salvar(categoria);
	}
	
	public void limparObjetoEvento(){
		EventoService eventoService = new EventoService(sessionFactory);
		List<Evento> eventos = eventoService.listar();
		
		eventos.stream().forEach(x-> eventoService.limparObjeto(x));
	}
}
