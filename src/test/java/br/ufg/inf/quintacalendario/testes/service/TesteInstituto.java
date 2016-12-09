package br.ufg.inf.quintacalendario.testes.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Evento;
import br.ufg.inf.quintacalendario.model.Instituto;
import br.ufg.inf.quintacalendario.service.CategoriaService;
import br.ufg.inf.quintacalendario.service.EventoService;
import br.ufg.inf.quintacalendario.service.InstitutoService;
import br.ufg.inf.quintacalendario.service.RegionalService;

public class TesteInstituto {
	
	private SessionFactory sessionFactory;
	
	@Before
	public void init(){
		
		sessionFactory = Application.getInstance().getSessionFactory();
		
		limparObjetoEvento();
		new EventoService(sessionFactory).limparTabela();
		new RegionalService(sessionFactory).limparTabela();
		new CategoriaService(sessionFactory).limparTabela();
		new InstitutoService(sessionFactory).limparTabela();
	}
	
	@After
	public void finalizar(){
		new InstitutoService(sessionFactory).limparTabela();
	}
	
	@Test
	public void testeSalvarInstituto(){
		Instituto instituto = new Instituto();
		instituto.setNome("Feriado");
		boolean retorno = new InstitutoService(sessionFactory).salvar(instituto);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void testeSalvarInstitutoComNomeVazio(){
		Instituto instituto = new Instituto();
		instituto.setNome("");
		boolean retorno = new InstitutoService(sessionFactory).salvar(instituto);
		Assert.assertFalse(retorno);
	}
	
	@Test
	public void testeSalvarInstitutoComNomeMenorQueQuatroCaracteres(){
		Instituto instituto = new Instituto();
		instituto.setNome("ABC");
		boolean retorno = new InstitutoService(sessionFactory).salvar(instituto);
		Assert.assertFalse(retorno);
	}
	
	@Test
	public void testeEditarInstituto(){
		inserirInstituto("INF - Instituto de informatica");
		
		InstitutoService service = new InstitutoService(sessionFactory);
		
		List<Instituto> institutos = service.listar();
		Instituto instituto = institutos.get(0);
		Integer id = Integer.valueOf(String.valueOf(instituto.getId()));
		
		service.editar(id, "IME - Instituto de matematica e estatistica");
		
		instituto  = service.listarPorId(id);
		
		Assert.assertTrue(instituto.getNome().equals("IME - Instituto de matematica e estatistica"));
	}
	
	@Test
	public void testeListarPorDescricao(){
		inserirInstituto("INF - Instituto de informatica");
		inserirInstituto("IME - Instituto de matematica e estatistica");
		
		InstitutoService service = new InstitutoService(sessionFactory);
		List<Instituto> institutos = service.listar("INF");
		
		boolean result = institutos.stream().anyMatch(x->x.getNome().equals("INF - Instituto de informatica"));
		Assert.assertTrue(result);
	}
	
	@Test
	public void testeListarPorDescricaoComDescricaoInvalida(){
		inserirInstituto("INF - Instituto de informatica");
		inserirInstituto("IME - Instituto de matematica e estatistica");
		
		InstitutoService service = new InstitutoService(sessionFactory);
		List<Instituto> institutos = service.listar("FEF - Faculdade de Educação Fisica");
		
		Assert.assertTrue(institutos.isEmpty());
	}
	
	
	@Test
	public void testeListarPorId(){
		Integer id = 0;
		Instituto instituto = null;
		
		inserirInstituto("IME - Instituto de matematica e estatistica");
		
		InstitutoService service = new InstitutoService(sessionFactory);
		List<Instituto> institutos = service.listar();
		
		if (!institutos.isEmpty()) {
			instituto = institutos.get(0);
			id = (int) instituto.getId();
			instituto = service.listarPorId(id);
		}
		Assert.assertTrue(instituto != null);
	}
	
	@Test
	public void testeListarInstituto(){
		inserirInstituto("IME - Instituto de matematica e estatistica");
		InstitutoService service = new InstitutoService(sessionFactory);
		List<Instituto> listar = service.listar();
		Assert.assertTrue(!listar.isEmpty());
	}
	
	@Test
	public void testeRemoverInstituto(){
		inserirInstituto("IME - Instituto de matematica e estatistica");
		Instituto instituto = null;
		Integer id = 0;
		
		InstitutoService service = new InstitutoService(sessionFactory);
		List<Instituto> institutos = service.listar();
		
		if (!institutos.isEmpty()) {
			instituto = institutos.get(0);
			id = (int) instituto.getId();
			
			service.remover(id);
			Assert.assertTrue(service.listar().isEmpty());
		}else{
			Assert.assertTrue(false);
		}
	}
	
	public void inserirInstituto(String nome){
		Instituto instituto = new Instituto();
		instituto.setNome(nome);
		
		InstitutoService service = new InstitutoService(sessionFactory);
		service.salvar(instituto);
	}
	
	
	public void limparObjetoEvento(){
		EventoService eventoService = new EventoService(sessionFactory);
		List<Evento> eventos = eventoService.listar();
		
		eventos.stream().forEach(x-> eventoService.limparObjeto(x));
	}
}
