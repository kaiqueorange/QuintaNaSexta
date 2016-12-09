package br.ufg.inf.quintacalendario.testes.service;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ufg.inf.quintacalendario.main.Application;
import br.ufg.inf.quintacalendario.model.Evento;
import br.ufg.inf.quintacalendario.model.Regional;
import br.ufg.inf.quintacalendario.service.CategoriaService;
import br.ufg.inf.quintacalendario.service.EventoService;
import br.ufg.inf.quintacalendario.service.InstitutoService;
import br.ufg.inf.quintacalendario.service.RegionalService;

public class TesteRegional {
	
	private SessionFactory sessionFactory;
	
	@Before
	public void init(){
		sessionFactory = Application.getInstance().getSessionFactory();
		
		limparObjetoEvento();
		
		new EventoService(sessionFactory).limparTabela();
		new InstitutoService(sessionFactory).limparTabela();
		new CategoriaService(sessionFactory).limparTabela();
		new RegionalService(sessionFactory).limparTabela();
	}
	
	@After
	public void finalizar(){
		new RegionalService(sessionFactory).limparTabela();
	}
	
	@Test
	public void testeSalvarRegional(){
		Regional regional = criarRegional();
		
		boolean retorno = new RegionalService(sessionFactory).salvar(regional);
		Assert.assertTrue(retorno);
	}
	
	@Test
	public void testeRegionalComNomeVazio(){
		Regional regional = new Regional();
		regional.setNome("");
		
		boolean retorno = new RegionalService(sessionFactory).salvar(regional);
		Assert.assertFalse(retorno);
	}
	
	@Test
	public void testeRegionalComNomeMenorQueQuatroCaracteres(){
		Regional regional = new Regional();
		regional.setNome("ABC");
		
		boolean retorno = new RegionalService(sessionFactory).salvar(regional);
		Assert.assertFalse(retorno);
	}
	
	@Test
	public void testeListarRegionaisPorDescricao(){
		RegionalService service = new RegionalService(sessionFactory);
		
		service.salvar(criarRegional());
		List<Regional> regionais = service.listar("Goiania");
		
		Assert.assertTrue(!regionais.isEmpty());
	}
	
	@Test
	public void testeAlterarRegional(){
		RegionalService service = new RegionalService(sessionFactory);
		long id = 0;
		service.salvar(criarRegional());
		
		List<Regional> regionais = service.listar();
		
		if (!regionais.isEmpty()) {
			Regional regional = regionais.get(0);
			id = regional.getId();
			service.editar(id, "Catalão");
		}
		
		Regional regional = service.listarPorId(id);
		Assert.assertTrue(regional.getNome().equals("Catalão"));
	}
	
	@Test
	public void testeRemoverRegional(){
		inserirRegional("Goiania");
		Regional regional = null;
		Integer id = 0;
		
		RegionalService service = new RegionalService(sessionFactory);
		List<Regional> regionais = service.listar();
		
		if (!regionais.isEmpty()) {
			regional = regionais.get(0);
			id = (int) regional.getId();
			
			service.remover(id);
			Assert.assertTrue(service.listar().isEmpty());
		}else{
			Assert.assertTrue(false);
		}
	}
	
	private void inserirRegional(String nome) {
		Regional regional = new Regional();
		regional.setNome(nome);
		
		RegionalService service = new RegionalService(sessionFactory);
		service.salvar(regional);
	}

	public Regional criarRegional(){
		Regional regional = new Regional();
		regional.setNome("Goiania");
		return regional;
	}
	
	public void limparObjetoEvento(){
		EventoService eventoService = new EventoService(sessionFactory);
		List<Evento> eventos = eventoService.listar();
		
		eventos.stream().forEach(x-> eventoService.limparObjeto(x));
	}
}
